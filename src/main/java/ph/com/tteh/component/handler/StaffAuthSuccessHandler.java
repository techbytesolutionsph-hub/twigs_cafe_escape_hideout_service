package ph.com.tteh.component.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Component;
import ph.com.tteh.dto.auth.PortalLoginRequest;
import ph.com.tteh.entity.user.AppUser;
import ph.com.tteh.service.util.logging.LoggingService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class StaffAuthSuccessHandler {

    private final AuthenticationManager authenticationManager;
    private final LoggingService loggingService;

    public void createStaffSession(HttpServletRequest httpRequest,
                                   AppUser appUser, PortalLoginRequest request) {

        HttpSession session = httpRequest.getSession();

        /* 1-hour session */
        session.setMaxInactiveInterval(60 * 60);

        List<SimpleGrantedAuthority> authorities =
                appUser.getUserRoles()
                        .stream()
                        .map(role -> new SimpleGrantedAuthority(
                                "ROLE_" + role.getUserRole().name()
                        ))
                        .toList();

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword(),
                        authorities
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        session.setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext()
        );

        loggingService.info("", this.getClass().getName(), "", "Staff login success!");
    }
}
