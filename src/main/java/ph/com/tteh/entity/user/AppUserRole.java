package ph.com.tteh.entity.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import ph.com.tteh.enums.UserRole;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@Entity
@Table(name = "tteh_app_user_role")
@NoArgsConstructor
@AllArgsConstructor
public class AppUserRole implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role", nullable = false)
    private UserRole userRole;

    /*
     * USER ROLE -> PERMISSIONS
     */
    @JsonManagedReference
    @OneToMany(mappedBy = "userRole", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AppRolePermission> rolePermissions = new HashSet<>();

    /*
     * ROLE -> USER
     */
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_user_id", nullable = false)
    private AppUser appUser;

}