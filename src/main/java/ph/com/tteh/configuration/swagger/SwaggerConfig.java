package ph.com.tteh.configuration.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("The Twigs Escape & Hideout Service API")
                        .version("1.0")
                        .description("Backend for The Twigs Escape & Hideout Service System")
                        .contact(new io.swagger.v3.oas.models.info.Contact()
                                .name("TechByte Solutions")
                                .email("dev.ecommerce.seo@techbytesolutions.com")));
    }
}