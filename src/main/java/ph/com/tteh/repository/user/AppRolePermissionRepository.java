package ph.com.tteh.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;
import ph.com.tteh.entity.user.AppRolePermission;
import ph.com.tteh.entity.user.AppUserRole;

@Repository
public interface AppRolePermissionRepository extends JpaRepository<AppRolePermission, Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM AppRolePermission rp WHERE rp.userRole = :userRole")
    void deleteByUserRole(@Param("userRole") AppUserRole userRole);
}
