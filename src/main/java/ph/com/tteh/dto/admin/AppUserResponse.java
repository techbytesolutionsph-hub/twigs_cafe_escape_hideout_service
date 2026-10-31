package ph.com.tteh.dto.admin;

import lombok.*;
import ph.com.tteh.enums.UserRole;
import ph.com.tteh.enums.UserStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppUserResponse {

    private Long id;
    private String username;
    private String password;
    private String lastPassword;
    private String email;
    private String profileImageUrl;
    private UserRole role;
    private UserStatus status;
    private boolean active;
    private LocalDateTime lastLogin;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
}