package ph.com.tteh.service.api.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ph.com.tteh.component.handler.StaffAuthSuccessHandler;
import ph.com.tteh.dto.auth.*;
import ph.com.tteh.dto.response.CommonResponse;
import ph.com.tteh.entity.user.AppUser;
import ph.com.tteh.enums.ResponseStatus;
import ph.com.tteh.enums.UserStatus;
import ph.com.tteh.exception.ServiceException;
import ph.com.tteh.repository.user.AppUserRepository;
import ph.com.tteh.service.api.redis.RedisService;
import ph.com.tteh.service.util.CommonStringUtils;
import ph.com.tteh.service.util.logging.LoggingService;
import ph.com.tteh.utils.BCryptUtils;
import ph.com.tteh.utils.LocalDateUtils;
import tools.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.springframework.http.HttpHeaders.USER_AGENT;

@RequiredArgsConstructor
@Service
public class AuthenticationService {

    private final AppUserRepository appUserRepository;
    private final StaffAuthSuccessHandler staffAuthSuccessHandler;
    private final RedisService redisService;
    private final LoggingService loggingService;
    private final BCryptUtils encoder;

    public LoginResponse portalLogin(String uuid, PortalLoginRequest request,
                                     HttpServletRequest httpRequest) throws ServiceException {

        AppUser appUser = appUserRepository.findByUsername(request.getUsername());
        if(!Objects.isNull(appUser)){
            if(appUser.getStatus().equals(UserStatus.DISABLED)){
                loggingService.error(uuid, getClass().getName(),
                        CommonStringUtils.ERR_CODE_LOGIN_USERNAME_NOT_CONNECTED, HttpStatus.NOT_FOUND.value());
                throw new ServiceException(HttpStatus.NOT_FOUND.value(), CommonStringUtils.ERR_CODE_LOGIN_USERNAME_NOT_CONNECTED);
            } else if (appUser.getStatus().equals(UserStatus.INACTIVE) || appUser.getStatus().equals(UserStatus.SUSPENDED)){
                loggingService.error(uuid, getClass().getName(),
                        CommonStringUtils.ERR_CODE_INACTIVE_SUSPENDED, HttpStatus.UNAUTHORIZED.value());
                throw new ServiceException(HttpStatus.UNAUTHORIZED.value(), CommonStringUtils.ERR_CODE_INACTIVE_SUSPENDED);
            }else{
                if(encoder.passwordMatches(request.getPassword(), appUser.getPassword())){

                    appUser.setAttempts(0);
                    appUserRepository.save(appUser);

                    staffAuthSuccessHandler.createStaffSession(httpRequest, appUser, request);

                    List<String> roles = this.findAllRoles(appUser);
                    List<String> permissions = this.findAllPermissions(appUser);
                    loggingService.info(uuid, this.getClass().getName(), "", "User Permissions : " + permissions);

                    HttpSession session = httpRequest.getSession();

                    session.setAttribute("USER_ID", appUser.getId());
                    session.setAttribute("USERNAME", appUser.getUsername());
                    session.setAttribute("ROLES", roles);
                    session.setAttribute("PERMISSIONS", permissions);

                    this.saveLoginHistory(uuid, request.getUsername(), httpRequest);

                    return PortalLoginResponse.builder()
                            .success(Boolean.TRUE)
                            .message("Login successfully!")
                            .timestamp(LocalDateUtils.getLocalDateTime())
                            .data(buildLoginDetailsResponse(httpRequest, appUser, roles))
                            .build();
                } else {
                    this.handleIncorrectPassword(uuid, appUser);
                    loggingService.error(uuid, getClass().getName(),
                            CommonStringUtils.ERR_CODE_LOGIN_INCORRECT_PASSWORD, HttpStatus.UNAUTHORIZED.value());
                    throw new ServiceException(HttpStatus.UNAUTHORIZED.value(), CommonStringUtils.ERR_CODE_LOGIN_INCORRECT_PASSWORD);
                }
            }
        }else{
            loggingService.error(uuid, getClass().getName(),
                    CommonStringUtils.ERR_CODE_LOGIN_USERNAME_NOT_CONNECTED, HttpStatus.NOT_FOUND.value());
            throw new ServiceException(HttpStatus.NOT_FOUND.value(), CommonStringUtils.ERR_CODE_LOGIN_USERNAME_NOT_CONNECTED);
        }
    }

    public CommonResponse logout(String uuid, boolean isSeller, HttpServletRequest httpRequest) {

        HttpSession session = httpRequest.getSession(false);

        String sessionId = null;
        boolean wasInvalidated = false;

        if (session != null) {
            sessionId = session.getId();

            loggingService.info(uuid, this.getClass().getName(), "",
                    "Session ID before logout: " + sessionId);

            if(isSeller){
                session.removeAttribute("AUTH");
                session.removeAttribute("ROLE");
            }else{
                session.removeAttribute("EMAIL");
            }

            session.invalidate();
            wasInvalidated = true;
        }

        SecurityContextHolder.clearContext();

        loggingService.info(uuid, this.getClass().getName(), "",
                "Logged out successfully!");

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("sessionId", sessionId);
        responseBody.put("wasInvalidated", wasInvalidated);

        return CommonResponse.builder()
                .returnCode(HttpStatus.OK.value())
                .returnMessage("Logged out successfully!")
                .responseBody(responseBody)
                .build();
    }

    private LoginDetailsResponse buildLoginDetailsResponse(
            HttpServletRequest request, AppUser user, List<String> roleList) {

        return LoginDetailsResponse.builder()
                .sessionId(request.getSession().getId())
                .userId(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .roleList(roleList)
                .permissionList(findAllPermissions(user))
                .loginStatus(ResponseStatus.SUCCESS.getValue())
                .ipAddress(request.getRemoteAddr())
                .deviceInfo(request.getHeader("User-Agent"))
                .build();
    }

    private void saveLoginHistory(String uuid, String email, HttpServletRequest httpRequest) throws ServiceException {

        try {
            LoginHistoryRequest history = LoginHistoryRequest.builder()
                    .loginId(System.currentTimeMillis())
                    .ipAddress(getClientIp(httpRequest))
                    .deviceInfo(httpRequest.getHeader(USER_AGENT))
                    .loginTime(LocalDateUtils.getLocalDateTime())
                    .isSuccessful(true)
                    .build();

            String key = "login_history:" + email;
            String value = new ObjectMapper().writeValueAsString(history);

            String redisResponse = redisService.set(key, value);
            loggingService.info(uuid, this.getClass().getName(), "", "Redis login history SET Response : " + redisResponse);

        } catch (Exception e) {
            loggingService.error(uuid, getClass().getName(),
                    String.format("Failed to store login history in Redis. Error: %s", e.getMessage()), 500);
            throw new ServiceException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to store login history in Redis");
        }
    }

    private List<String> findAllRoles(AppUser appUser){
        return appUser.getUserRoles()
                .stream()
                .map(role -> "ROLE_" + role.getUserRole().name())
                .toList();
    }

    private List<String> findAllPermissions(AppUser appUser) {

        return appUser.getUserRoles()
                .stream()
                .flatMap(role -> role.getRolePermissions().stream())
                .map(rolePermission -> rolePermission.getPermission().getPermissionCode())
                .distinct()
                .toList();
    }

    private void handleIncorrectPassword(String uuid, AppUser appUser) throws ServiceException {
        if (appUser.getAttempts() >= 3){
            appUser.setStatus(UserStatus.SUSPENDED);
            appUserRepository.save(appUser);
            loggingService.error(uuid, getClass().getName(),
                    CommonStringUtils.ERR_CODE_LOGIN_TEMPORARY_LOCKED, HttpStatus.UNAUTHORIZED.value());
            throw new ServiceException(HttpStatus.UNAUTHORIZED.value(), CommonStringUtils.ERR_CODE_LOGIN_TEMPORARY_LOCKED);
        }
        int attempts = appUser.getAttempts() + 1;
        appUser.setAttempts(attempts);
        appUserRepository.save(appUser);
    }


    private String getClientIp(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }

}
