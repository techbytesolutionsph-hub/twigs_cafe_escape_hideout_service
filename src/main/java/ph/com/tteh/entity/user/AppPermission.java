package ph.com.tteh.entity.user;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "tteh_app_permission")
@NoArgsConstructor
@AllArgsConstructor
public class AppPermission implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "permission_id")
    private Long permissionId;

    @Column(name = "permission_code", nullable = false)
    private String permissionCode;

    @Column(name = "description")
    private String description;

    @JsonManagedReference
    @OneToMany(mappedBy="permission", cascade=CascadeType.ALL)
    private Set<AppRolePermission> rolePermissions = new HashSet<>();

}