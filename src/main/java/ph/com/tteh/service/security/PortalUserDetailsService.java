package ph.com.tteh.service.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ph.com.tteh.entity.user.AppUser;
import ph.com.tteh.enums.UserRole;
import ph.com.tteh.repository.user.AppUserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class PortalUserDetailsService implements UserDetailsService {

    private final AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        AppUser user = appUserRepository.findUserByUsername(username)
                .orElseThrow(() -> {
                    log.warn("Staff not found with username: {}", username);
                    return new UsernameNotFoundException("Staff not found with username: " + username);
                });
        boolean isStaff = user.getUserRoles()
                .stream()
                .anyMatch(r ->
                        r.getUserRole().name().equals(UserRole.SUPER_ADMIN.name())
                                || r.getUserRole().name().equals(UserRole.ADMIN.name())
                                || r.getUserRole().name().equals(UserRole.PARTNER.name())
                                || r.getUserRole().name().equals(UserRole.OPERATIONS_MANAGER.name())
                                || r.getUserRole().name().equals(UserRole.HEAD_COOK.name())
                                || r.getUserRole().name().equals(UserRole.KITCHEN_STAFF.name())
                                || r.getUserRole().name().equals(UserRole.SERVICE_CREW.name())
                                || r.getUserRole().name().equals(UserRole.BARISTA.name())
                                || r.getUserRole().name().equals(UserRole.CASHIER.name())
                );

        if (!isStaff) {
            throw new UsernameNotFoundException("Not a staff account");
        }

        return new PortalUserDetails(user);
    }
}
