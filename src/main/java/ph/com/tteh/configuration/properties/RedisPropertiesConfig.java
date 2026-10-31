package ph.com.tteh.configuration.properties;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "upstash.redis")
public class RedisPropertiesConfig {

    @NotBlank
    private String url;

    @NotBlank
    private String token;
}
