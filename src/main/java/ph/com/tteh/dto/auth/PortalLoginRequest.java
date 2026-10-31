package ph.com.tteh.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PortalLoginRequest {

    @Schema(example = "superadmin")
    @NotBlank(message = "Username is required")
    private String username;

    @Schema(example = "Password@1234")
    @NotBlank(message = "Password is required")
    private String password;
}
