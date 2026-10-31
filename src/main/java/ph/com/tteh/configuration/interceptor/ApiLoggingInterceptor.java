package ph.com.tteh.configuration.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class ApiLoggingInterceptor implements HandlerInterceptor {

    private static final String START_TIME = "startTime";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        long startTime = System.currentTimeMillis();
        request.setAttribute(START_TIME, startTime);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication != null
                ? authentication.getName()
                : "Anonymous";

        log.info("API START | Method={} | URI={} | User={}",
                request.getMethod(),
                request.getRequestURI(),
                username
        );

        return true;
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        Long startTime = (Long) request.getAttribute(START_TIME);

        long executionTime = 0;

        if (startTime != null) {
            executionTime = System.currentTimeMillis() - startTime;
        }

        log.info("API END | Method={} | URI={} | Status={} | Time={}ms",
                request.getMethod(),
                request.getRequestURI(),
                response.getStatus(),
                executionTime
        );

        if (ex != null) {
            log.error("API ERROR | URI={} | Message={}",
                    request.getRequestURI(),
                    ex.getMessage()
            );
        }
    }
}
