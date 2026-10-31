package ph.com.tteh.configuration.schema;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "spring.datasource.db")
public class SchemaPropertiesConfig {

    private String schema;
    private String queryForObject;
}
