package ph.com.tteh.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ph.com.tteh.entity.user.AppRoleMapping;

import java.util.List;

@Repository
public interface AppRoleMappingRepository extends JpaRepository<AppRoleMapping, Long> {
    List<AppRoleMapping> findByRole(String role);
}
