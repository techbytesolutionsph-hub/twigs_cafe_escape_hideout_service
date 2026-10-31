package ph.com.tteh.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ph.com.tteh.entity.user.AppPermission;
import ph.com.tteh.entity.user.AppUserRole;

import java.util.Optional;

@Repository
public interface AppPermissionRepository extends JpaRepository<AppPermission, Long> {

    Optional<AppPermission> findByPermissionCode(String permissionCode);

    @Modifying
    @Transactional
    @Query("""
        DELETE FROM AppPermission p
        WHERE p.permissionId IN (
            SELECT rp.permission.permissionId
            FROM AppRolePermission rp
            WHERE rp.userRole = :userRole
        )
    """)
    void deleteByUserRole(@Param("userRole") AppUserRole userRole);

}