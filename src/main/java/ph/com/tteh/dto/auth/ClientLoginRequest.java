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
public class ClientLoginRequest {

    @Schema(example = "client")
    @NotBlank(message = "Username is required")
    private String username;

    @Schema(example = "P@ssw0rd!")
    @NotBlank(message = "Password is required")
    private String password;
}
