package ph.com.tteh.service.api.admin;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ph.com.tteh.dto.admin.AppUserResponse;
import ph.com.tteh.dto.admin.CreateUserRequest;
import ph.com.tteh.dto.response.CommonResponse;
import ph.com.tteh.entity.staff.generalinfo.AppEmployeeProfile;
import ph.com.tteh.entity.staff.timesheet.AppWeeklyTimesheet;
import ph.com.tteh.entity.user.*;
import ph.com.tteh.enums.TimesheetStatus;
import ph.com.tteh.enums.UserRole;
import ph.com.tteh.enums.UserStatus;
import ph.com.tteh.exception.ServiceException;
import ph.com.tteh.repository.user.*;
import ph.com.tteh.service.db.SequenceGeneratorService;
import ph.com.tteh.service.security.PortalUserDetails;
import ph.com.tteh.service.util.IdGeneratorUtils;
import ph.com.tteh.service.util.PasswordGeneratorService;
import ph.com.tteh.service.util.logging.LoggingService;
import ph.com.tteh.utils.BCryptUtils;
import ph.com.tteh.utils.ObjectUtils;

import java.util.*;

@RequiredArgsConstructor
@Service
public class UserAccountService {

    private final AppUserRepository appUserRepository;
    private final AppUserRoleRepository appUserRoleRepository;
    private final AppRoleMappingRepository appRoleMappingRepository;
    private final AppRolePermissionRepository appRolePermissionRepository;
    private final AppPermissionRepository appPermissionRepository;
    private final SequenceGeneratorService sequenceGeneratorService;
    private final PasswordGeneratorService passwordGeneratorService;
    private final IdGeneratorUtils idGeneratorUtils;
    private final LoggingService loggingService;
    private final BCryptUtils encoder;

    public String getIdRunningSequence(String dateHired) throws ServiceException {
        long nextUserSeq = sequenceGeneratorService.getStaffIdNextSequence();
        return idGeneratorUtils.generateEmployeeId(dateHired, nextUserSeq);
    }

    @Transactional
    public CommonResponse createUserAccount(String uuid, CreateUserRequest request) throws ServiceException {

        /* Check existing email */
        if (appUserRepository.existsByEmail(request.getEmail())) {
            loggingService.error(uuid, getClass().getName(), "Email already exists.", HttpStatus.CONFLICT.value());
            throw new ServiceException(HttpStatus.CONFLICT.value(), "Email already exists.");
        }

        /* Check existing username */
        if (appUserRepository.existsByUsername(request.getUsername())) {
            loggingService.error(uuid, getClass().getName(), "Username already exists.", HttpStatus.CONFLICT.value());
            throw new ServiceException(HttpStatus.CONFLICT.value(), "Username already exists.");
        }

        /* Create User Account */
        AppUser appUser = new AppUser();
        appUser.setUsername(request.getUsername());
        appUser.setEmail(request.getEmail());
        appUser.setPassword(encoder.encodePassword(request.getPassword()));
        appUser.setLastPassword(request.getPassword());
        appUser.setStatus(request.getStatus());
        appUser.setProfileImageUrl(request.getProfileImageUrl());
        appUser.setActive(true);

        /* Create User Role */
        AppUserRole appUserRole = AppUserRole.builder()
                .userRole(request.getRole())
                .appUser(appUser)
                .build();

        Set<AppRolePermission> rolePermissions = new HashSet<>();

        /* Load role mappings */
        List<AppRoleMapping> roleMappings =
                appRoleMappingRepository.findByRole(request.getRole().name());

        for (AppRoleMapping mapping : roleMappings) {

            /*
             * ALWAYS CREATE A NEW PERMISSION
             */
            AppPermission permission = new AppPermission();
            permission.setPermissionCode(mapping.getPermissionCode());
            permission.setDescription(mapping.getDescription());

            permission = appPermissionRepository.save(permission);

            /*
             * CREATE ROLE PERMISSION
             */
            AppRolePermission rolePermission = new AppRolePermission();
            rolePermission.setUserRole(appUserRole);
            rolePermission.setPermission(permission);

            rolePermissions.add(rolePermission);
        }

        appUserRole.setRolePermissions(rolePermissions);

        appUser.setUserRoles(new HashSet<>());
        appUser.getUserRoles().add(appUserRole);

        appUserRepository.save(appUser);

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("appUser", appUser);

        return CommonResponse.builder()
                .returnCode(HttpStatus.CREATED.value())
                .returnMessage("User account created successfully!")
                .responseBody(responseBody)
                .build();
    }

    public String generateTemporaryPassword(){
        return passwordGeneratorService.generatePassword(16);
    }

    public List<AppUserResponse> getAllUsers(){
        return ObjectUtils.copyListAs(appUserRepository.findAll(
                Sort.by(Sort.Direction.DESC, "id")
        ), AppUserResponse.class);
    }

    public AppUserResponse findByUsername(String username) throws ServiceException {

        /* Find user */
        AppUser appUser = appUserRepository.findUserByUsername(username)
                .orElseThrow(() -> new ServiceException(
                        HttpStatus.NOT_FOUND.value(),
                        "User not found: " + username));

        AppUserResponse response = ObjectUtils.copyAs(appUser, AppUserResponse.class);

        if (appUser.getUserRoles() != null) {
            appUser.getUserRoles().stream()
                    .findFirst()
                    .ifPresent(userRole -> response.setRole(userRole.getUserRole()));
        }

        return response;
    }

    @Transactional
    public CommonResponse updateUserAccount(String uuid, CreateUserRequest request) throws ServiceException {

        /* Find existing user */
        AppUser appUser = appUserRepository.findUserByUsername(request.getUsername())
                .orElseThrow(() -> new ServiceException(
                        HttpStatus.NOT_FOUND.value(),
                        "User not found: " + request.getUsername()));

        /* Check duplicate email */
        if (!Objects.equals(appUser.getEmail(), request.getEmail())) {

            Optional<AppUser> existingEmail =
                    appUserRepository.findUserByEmail(request.getEmail());

            if (existingEmail.isPresent()
                    && !existingEmail.get().getId().equals(appUser.getId())) {

                loggingService.error(
                        uuid,
                        getClass().getName(),
                        "Email already exists.",
                        HttpStatus.CONFLICT.value());

                throw new ServiceException(
                        HttpStatus.CONFLICT.value(),
                        "Email already exists.");
            }
        }

        /* Update basic information */
        appUser.setUsername(request.getUsername());
        appUser.setEmail(request.getEmail());
        appUser.setPassword(encoder.encodePassword(request.getPassword()));
        appUser.setLastPassword(request.getPassword());
        appUser.setStatus(request.getStatus());
        appUser.setProfileImageUrl(request.getProfileImageUrl());

        /* Get current role */
        AppUserRole appUserRole = appUser.getUserRoles()
                .stream()
                .findFirst()
                .orElseThrow(() -> new ServiceException(
                        HttpStatus.NOT_FOUND.value(),
                        "User role not found."));

        /* Role changed */
        if (!appUserRole.getUserRole().equals(request.getRole())) {

            /*
             * STEP 1
             * Save all permission IDs
             */
            List<Long> permissionIds = appUserRole.getRolePermissions()
                    .stream()
                    .map(rp -> rp.getPermission().getPermissionId())
                    .toList();

            /*
             * STEP 2
             * Delete all role permissions
             */
            appRolePermissionRepository.deleteAll(appUserRole.getRolePermissions());
            appRolePermissionRepository.flush();

            appUserRole.getRolePermissions().clear();

            /*
             * STEP 3
             * Delete all permissions
             */
            if (!permissionIds.isEmpty()) {
                appPermissionRepository.deleteAllById(permissionIds);
                appPermissionRepository.flush();
            }

            /*
             * STEP 4
             * Update role
             */
            appUserRole.setUserRole(request.getRole());

            /*
             * STEP 5
             * Insert new permissions
             */
            List<AppRoleMapping> mappings =
                    appRoleMappingRepository.findByRole(request.getRole().name());

            for (AppRoleMapping mapping : mappings) {

                AppPermission permission = new AppPermission();
                permission.setPermissionCode(mapping.getPermissionCode());
                permission.setDescription(mapping.getDescription());

                permission = appPermissionRepository.save(permission);

                AppRolePermission rolePermission = new AppRolePermission();
                rolePermission.setUserRole(appUserRole);
                rolePermission.setPermission(permission);

                appUserRole.getRolePermissions().add(rolePermission);
            }
        }

        appUserRepository.save(appUser);

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("appUser", appUser);

        return CommonResponse.builder()
                .returnCode(HttpStatus.OK.value())
                .returnMessage("User account updated successfully!")
                .responseBody(responseBody)
                .build();
    }

    @Transactional
    public CommonResponse softDeleteUserAccount(String uuid, String username) throws ServiceException {

        /* Find user */
        AppUser appUser = appUserRepository.findUserByUsername(username)
                .orElseThrow(() -> new ServiceException(
                        HttpStatus.NOT_FOUND.value(),
                        "User not found: " + username));

        /* Prevent deleting Super Admin */
        boolean isSuperAdmin = appUser.getUserRoles()
                .stream()
                .anyMatch(role -> role.getUserRole() == UserRole.SUPER_ADMIN);

        if (isSuperAdmin) {
            loggingService.error(uuid, getClass().getName(),"Super Admin account cannot be disabled.", HttpStatus.FORBIDDEN.value());
            throw new ServiceException(HttpStatus.FORBIDDEN.value(), "Super Admin account cannot be disabled.");
        }

        /* Already disabled */
        if (appUser.getStatus() == UserStatus.DISABLED) {
            return CommonResponse.builder()
                    .returnCode(HttpStatus.OK.value())
                    .returnMessage("User account [" + username + "] is already disabled.")
                    .build();
        }

        /* Soft delete */
        appUser.setStatus(UserStatus.DISABLED);
        appUser.setActive(false);

        appUserRepository.save(appUser);

        return CommonResponse.builder()
                .returnCode(HttpStatus.OK.value())
                .returnMessage("User account " + username + " has been disabled.")
                .build();
    }

    public Map<String, Object> getUserInfo(String username) throws ServiceException {

        /* Find user */
        AppUser appUser = appUserRepository.findUserByUsername(username)
                .orElseThrow(() -> new ServiceException(
                        HttpStatus.NOT_FOUND.value(),
                        "User not found: " + username));

        AppWeeklyTimesheet currentTimesheet = appUser.getAppEmployeeProfile()
                .getTimesheets()
                .stream()
                .max(Comparator.comparing(AppWeeklyTimesheet::getWeekEnding))
                .orElse(null);

        TimesheetStatus status = currentTimesheet != null ? currentTimesheet.getStatus() : TimesheetStatus.OPEN;
        loggingService.info("", this.getClass().getName(), "", "TimesheetStatus : " + status);

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("employeeId", appUser.getAppEmployeeProfile().getEmployeeId());
        responseBody.put("employeeName", appUser.getAppEmployeeProfile().getFirstName() + " " + appUser.getAppEmployeeProfile().getLastName());
        responseBody.put("branchAssign", appUser.getAppEmployeeProfile().getEmploymentInformation().getBranchAssign());
        responseBody.put("status", currentTimesheet != null ? currentTimesheet.getStatus() : status);
        return responseBody;
    }

    public AppEmployeeProfile getLoggedInEmployee(HttpServletRequest request) throws ServiceException {

        HttpSession session = request.getSession(false);

        if (session == null) {
            throw new ServiceException("User session not found.");
        }

        SecurityContext securityContext = (SecurityContext) session.getAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);

        if (securityContext == null) {
            throw new ServiceException("Security context not found.");
        }

        Authentication authentication = securityContext.getAuthentication();

        if (authentication == null
                || !(authentication.getPrincipal() instanceof PortalUserDetails userDetails)) {

            throw new ServiceException("Invalid authenticated staff user.");
        }

        return Optional
                .ofNullable(userDetails.getAppEmployeeProfile())
                .orElseThrow(() -> new ServiceException("Authenticated user is not linked to an employee profile."));
    }
}
