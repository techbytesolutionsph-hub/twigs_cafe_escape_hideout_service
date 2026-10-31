package ph.com.tteh.dto.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ph.com.tteh.enums.UserRole;
import ph.com.tteh.enums.UserStatus;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request payload for registering a staff account")
public class CreateUserRequest {

    @Schema(
            description = "Unique username for login",
            example = "superadmin",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String username;


    @Schema(
            description = "Initial password for the staff account",
            example = "Password@1234",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String password;


    @Schema(
            description = "Staff email address",
            example = "superadmin@thetwigs.com",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String email;

    @Schema(
            description = "Profile image URL",
            example = "https://res.cloudinary.com/dx8es7rbm/image/upload/v1777795180/cuichsukdl1fcqwlj39b.jpg"
    )
    private String profileImageUrl;

    @Schema(
            description = "User role assigned after registration",
            example = "SUPER_ADMIN"
    )
    private UserRole role;

    @Schema(
            description = "Account status",
            example = "ACTIVE",
            allowableValues = {
                    "ACTIVE",
                    "INACTIVE",
                    "SUSPENDED",
                    "DISABLED"
            }
    )
    private UserStatus status;
}