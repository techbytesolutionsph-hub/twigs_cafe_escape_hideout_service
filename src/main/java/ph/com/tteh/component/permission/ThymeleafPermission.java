package ph.com.tteh.component.permission;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("permission")
public class ThymeleafPermission {

    public boolean has(String permission) {

        Authentication authentication = SecurityContextHolder.getContext()
                        .getAuthentication();

        if (authentication == null) {
            return false;
        }

        return authentication.getAuthorities()
                .stream()
                .anyMatch(authority ->
                        authority.getAuthority().equals("FULL_ACCESS")
                                ||
                                authority.getAuthority().equals(permission)
                );
    }
}