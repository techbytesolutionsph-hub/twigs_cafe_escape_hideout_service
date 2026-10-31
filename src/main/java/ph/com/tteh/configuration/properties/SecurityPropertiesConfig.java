package ph.com.tteh.configuration.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "security.permit.paths.tteh")
public class SecurityPropertiesConfig {

    private String[] swaggerPath;
    private String[] staticPath;
    private String[] internalPath;
    private String[] errorPath;
    private String[] defaultPath;

    private String[] staffPath;
    private String staffLogin;
    private String staffLogout;
    private String staffDashboard;

    private String errorPagePath;
}
