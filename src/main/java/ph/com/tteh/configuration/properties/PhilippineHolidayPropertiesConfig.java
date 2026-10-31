package ph.com.tteh.configuration.properties;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "philippine.holiday")
public class PhilippineHolidayPropertiesConfig {

    @NotBlank
    private String url;
}
