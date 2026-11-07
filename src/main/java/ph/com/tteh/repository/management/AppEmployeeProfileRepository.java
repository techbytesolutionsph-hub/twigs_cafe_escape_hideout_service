package ph.com.tteh.repository.management;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ph.com.tteh.entity.staff.generalinfo.AppEmployeeProfile;
import ph.com.tteh.entity.user.AppUser;
import ph.com.tteh.enums.EmployeePosition;
import ph.com.tteh.enums.EmploymentStatus;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppEmployeeProfileRepository extends JpaRepository<AppEmployeeProfile, Long> {

    boolean existsByAppUser(AppUser appUser);
    Optional<AppEmployeeProfile> findByEmployeeId(String employeeId);
    List<AppEmployeeProfile> findByEmploymentInformation_PositionIn(List<EmployeePosition> positions);
    Double findByEmploymentInformation_Position_AndEmployeeId(EmployeePosition position, String employeeId);
    long countByEmploymentInformation_EmploymentStatus(EmploymentStatus employmentStatus);
    AppEmployeeProfile findByEmploymentInformation_Position(EmployeePosition position);
}