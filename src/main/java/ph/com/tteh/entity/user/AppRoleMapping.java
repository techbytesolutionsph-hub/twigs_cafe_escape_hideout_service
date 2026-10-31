package ph.com.tteh.entity.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "tteh_app_role_mapping")
@NoArgsConstructor
@AllArgsConstructor
public class AppRoleMapping implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_mapping_id")
    private Long roleMappingId;

    @Column(name = "role", nullable = false, length = 50)
    private String role;

    @Column(name = "permission_code", nullable = false, length = 100)
    private String permissionCode;

    @Column(name = "description", length = 255)
    private String description;
}
