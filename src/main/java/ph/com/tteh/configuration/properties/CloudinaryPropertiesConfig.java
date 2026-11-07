package ph.com.tteh.configuration.properties;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "cloudinary")
public class CloudinaryPropertiesConfig {

    @NotBlank
    private String baseUrl;

    @NotBlank
    private String uploadImageEndpoint;

    @NotBlank
    private String deleteImageEndpoint;

    @NotBlank
    private String uploadDocumentEndpoint;

    @NotBlank
    private String deleteDocumentEndpoint;

    @NotBlank
    private String cloudName;

    @NotBlank
    private String apiKey;

    @NotBlank
    private String apiSecret;

    @NotBlank
    private String imageUploadPreset;

    @NotBlank
    private String documentUploadPreset;

    public String buildUploadImageUrl() {
        return baseUrl.replaceAll("/+$", "")
                + String.format(uploadImageEndpoint, cloudName);
    }

    public String buildDeleteImageUrl() {
        return baseUrl.replaceAll("/+$", "")
                + String.format(deleteImageEndpoint, cloudName);
    }

    public String buildUploadDocumentUrl() {
        return baseUrl.replaceAll("/+$", "")
                + String.format(uploadDocumentEndpoint, cloudName);
    }

    public String buildDeleteDocumentUrl() {
        return baseUrl.replaceAll("/+$", "")
                + String.format(deleteDocumentEndpoint, cloudName);
    }
}