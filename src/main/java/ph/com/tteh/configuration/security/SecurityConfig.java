package ph.com.tteh.configuration.security;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ph.com.tteh.configuration.properties.SecurityPropertiesConfig;
import ph.com.tteh.enums.UserRole;
import ph.com.tteh.service.security.PortalUserDetailsService;

import java.io.IOException;
import java.util.stream.Stream;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final PortalUserDetailsService portalUserDetailsService;
    private final SecurityPropertiesConfig securityPropertiesConfig;

    private static final String J_SESSION_ID= "JSESSIONID";

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Primary
    public AuthenticationManager staffAuthenticationManager(PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(portalUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(authProvider);
    }

    @Bean
    @Order(0)
    public SecurityFilterChain publicChain(HttpSecurity http) {

        String[] publicPaths = Stream.of(
                securityPropertiesConfig.getSwaggerPath(),
                securityPropertiesConfig.getStaticPath(),
                securityPropertiesConfig.getInternalPath(),
                securityPropertiesConfig.getErrorPath()
        ).flatMap(Stream::of).toArray(String[]::new);

        http
                .securityMatcher(publicPaths)
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable);

        return http.build();
    }


    @Bean
    @Order(1)
    public SecurityFilterChain staffChain(HttpSecurity http) {

        String[] staffPaths = Stream.of(securityPropertiesConfig.getStaffPath()).toArray(String[]::new);

        http
                .securityMatcher(staffPaths)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionFixation(SessionManagementConfigurer.SessionFixationConfigurer::migrateSession)
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(securityPropertiesConfig.getStaffLogin())
                        .permitAll()
                        .requestMatchers(securityPropertiesConfig.getStaffPath())
                        .hasAnyRole(
                                UserRole.SUPER_ADMIN.name(),
                                UserRole.ADMIN.name(),
                                UserRole.PARTNER.name(),
                                UserRole.OPERATIONS_MANAGER.name(),
                                UserRole.HEAD_COOK.name(),
                                UserRole.KITCHEN_STAFF.name(),
                                UserRole.SERVICE_CREW.name(),
                                UserRole.BARISTA.name(),
                                UserRole.CASHIER.name()
                        )
                        .anyRequest()
                        .authenticated()
                )
                .formLogin(form -> form
                        .loginPage(securityPropertiesConfig.getStaffLogin())
                        .loginProcessingUrl(securityPropertiesConfig.getStaffLogin())
                        .defaultSuccessUrl(securityPropertiesConfig.getStaffDashboard(), true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl(securityPropertiesConfig.getStaffLogout())
                        .logoutSuccessUrl(securityPropertiesConfig.getStaffLogin())
                        .invalidateHttpSession(true)
                        .deleteCookies(J_SESSION_ID)
                )
                .exceptionHandling(ex -> ex
                        .accessDeniedHandler((request, response, accessDeniedException) ->
                                handleSecurityException(request, response, accessDeniedException, HttpServletResponse.SC_FORBIDDEN))
                        .authenticationEntryPoint((request, response, authException) ->
                                handleSecurityException(request, response, authException, HttpServletResponse.SC_UNAUTHORIZED))
                );

        return http.build();
    }

    /* CENTRALIZED SECURITY EXCEPTION HANDLER */
    private void handleSecurityException(HttpServletRequest request,
                                         HttpServletResponse response,
                                         Exception exception,
                                         int defaultStatus) throws IOException {

        /* Invalidate session if exists */
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        /* Remove JSESSIONID cookie */
        Cookie cookie = new Cookie(J_SESSION_ID, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);

        /* Determine HTTP status */
        int status;
        if (exception instanceof AccessDeniedException) {
            status = HttpServletResponse.SC_FORBIDDEN;
        } else if (exception instanceof AuthenticationException) {
            status = HttpServletResponse.SC_UNAUTHORIZED;
        } else {
            status = defaultStatus;
        }

        /* Forward to centralized error page safely */
        request.setAttribute("jakarta.servlet.error.status_code", status);
        RequestDispatcher dispatcher = request.getRequestDispatcher(securityPropertiesConfig.getErrorPagePath());
        if (dispatcher != null) {
            try {
                dispatcher.forward(request, response);
            } catch (Exception e) {
                response.sendError(status, exception.getMessage());
            }
        } else {
            response.sendError(status, exception.getMessage());
        }
    }
}
