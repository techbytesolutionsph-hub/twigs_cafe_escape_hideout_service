package ph.com.tteh.entity.user;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import ph.com.tteh.entity.common.AuditableEntity;
import ph.com.tteh.entity.staff.generalinfo.AppEmployeeProfile;
import ph.com.tteh.enums.UserStatus;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "tteh_app_user")
@NoArgsConstructor
@AllArgsConstructor
public class AppUser extends AuditableEntity {

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "last_password")
    private String lastPassword;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "profile_image_url")
    private String profileImageUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private UserStatus status;

    @Column(name = "is_active")
    private boolean isActive = true;

    @Column(name = "attempts")
    private int attempts;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @JsonManagedReference
    @OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AppUserRole> userRoles = new HashSet<>();

    @JsonManagedReference
    @OneToOne(mappedBy = "appUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private AppEmployeeProfile appEmployeeProfile;
}