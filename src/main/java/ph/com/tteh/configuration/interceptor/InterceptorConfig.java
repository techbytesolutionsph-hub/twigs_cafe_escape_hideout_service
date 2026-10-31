package ph.com.tteh.configuration.interceptor;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class InterceptorConfig implements WebMvcConfigurer {

    private final ApiLoggingInterceptor apiLoggingInterceptor;
    private final BreadcrumbInterceptor breadcrumbInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(apiLoggingInterceptor)
                .addPathPatterns("/api/v1/**");

        registry.addInterceptor(breadcrumbInterceptor)
                .addPathPatterns("/app/portal/**")
                .excludePathPatterns(
                        "/css/**",
                        "/js/**",
                        "/img/**",
                        "/scss/**",
                        "/vendor/**"
                );
    }
}