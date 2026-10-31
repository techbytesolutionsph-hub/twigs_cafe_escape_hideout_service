package ph.com.tteh.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ph.com.tteh.entity.user.AppUser;

import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findUserByEmail(@Param("email") String email);
    Optional<AppUser> findUserByUsername(@Param("username") String username);
    AppUser findByUsername(@Param("username") String username);

    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}
