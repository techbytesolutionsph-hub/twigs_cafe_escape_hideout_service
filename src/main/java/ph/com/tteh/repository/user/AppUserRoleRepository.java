package ph.com.tteh.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ph.com.tteh.entity.user.AppUserRole;

@Repository
public interface AppUserRoleRepository extends JpaRepository<AppUserRole, Long> {
}
