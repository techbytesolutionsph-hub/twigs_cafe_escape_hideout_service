package ph.com.tteh.service.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ph.com.tteh.entity.staff.generalinfo.AppEmployeeProfile;
import ph.com.tteh.entity.user.AppPermission;
import ph.com.tteh.entity.user.AppRolePermission;
import ph.com.tteh.entity.user.AppUser;
import ph.com.tteh.entity.user.AppUserRole;
import ph.com.tteh.enums.Gender;
import ph.com.tteh.enums.UserRole;


import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class PortalUserDetails implements UserDetails {
    private final AppUser user;
    private final String staffFirstName;
    private final String staffFullName;
    private final String employeeId;
    private final String profileImageUrl;
    private String position;

    public PortalUserDetails(AppUser user) {
        this.user = user;

        AppEmployeeProfile staff = (user.getAppEmployeeProfile() != null
                && user.getAppEmployeeProfile().isActive())
                ? user.getAppEmployeeProfile()
                : null;

        this.staffFirstName = (staff != null)
                ? staff.getFirstName()
                : user.getUserRoles().stream().anyMatch(role -> role.getUserRole() == UserRole.SUPER_ADMIN)
                ? "System Administrator" : "Staff";

        this.staffFullName = (staff != null)
                ? staff.getFirstName() + " " + staff.getLastName()
                : user.getUserRoles().stream()
                    .anyMatch(role -> role.getUserRole() == UserRole.SUPER_ADMIN)
                ? "System Administrator" : "Staff";

        this.employeeId = (staff != null)
                ? staff.getEmployeeId()
                : null;

        this.position = (staff != null)
                ? staff.getEmploymentInformation().getPosition().getDisplayPosition()
                : user.getUserRoles().stream()
                    .anyMatch(r -> r.getUserRole() == UserRole.SUPER_ADMIN)
                ? "Super Admin"
                : "Unassigned";

        this.profileImageUrl = this.resolveProfileImage(staff);
    }

    public AppEmployeeProfile getAppEmployeeProfile() {
        return user.getAppEmployeeProfile();
    }

    private String resolveProfileImage(AppEmployeeProfile staff) {

        /* Super Admin profile image */
        if (user.getUserRoles().stream()
                .anyMatch(role -> role.getUserRole() == UserRole.SUPER_ADMIN)
                && user.getProfileImageUrl() != null
                && !user.getProfileImageUrl().isBlank()) {

            return user.getProfileImageUrl();
        }

        /* Employee profile image */
        if (staff != null
                && staff.getProfileImageUrl() != null
                && !staff.getProfileImageUrl().isBlank()) {

            return staff.getProfileImageUrl();
        }

        /* Default avatar based on employee gender */
        if (staff != null && staff.getGender() != null) {

            if (staff.getGender() == Gender.MALE) {
                return "/img/user/default_male_user.svg";
            }

            if (staff.getGender() == Gender.FEMALE) {
                return "/img/user/default_female_user.svg";
            }
        }

        /* Generic default */
        return "/img/default.png";
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();

        for (AppUserRole role : user.getUserRoles()) {

            /* Add Role */
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getUserRole().name()));

            /* Add Permissions */
            if (role.getRolePermissions() != null) {
                role.getRolePermissions().stream()
                        .map(AppRolePermission::getPermission)
                        .map(AppPermission::getPermissionCode)
                        .map(SimpleGrantedAuthority::new)
                        .forEach(authorities::add);
            }
        }

        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.isActive();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.isActive();
    }

    public String getStaffFirstName() {
        return staffFirstName;
    }

    public String getStaffFullName() {
        return staffFullName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getPosition() {
        return position;
    }
}
