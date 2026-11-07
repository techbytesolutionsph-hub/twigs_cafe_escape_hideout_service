package ph.com.tteh.repository.management;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ph.com.tteh.entity.staff.generalinfo.AppEmployeeProfile;
import ph.com.tteh.entity.staff.leave.AppLeaveRequest;
import ph.com.tteh.enums.LeaveType;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AppLeaveRequestRepository extends JpaRepository<AppLeaveRequest, Long> {

    List<AppLeaveRequest> findByEmployee(AppEmployeeProfile employee);

    @Query("""
        SELECT COALESCE(SUM(l.requestedDays), 0)
        FROM AppLeaveRequest l
        WHERE l.employee = :employee
          AND l.leaveType = :leaveType
    """)
    Double getUsedLeaveDays(
            @Param("employee") AppEmployeeProfile employee,
            @Param("leaveType") LeaveType leaveType
    );

    boolean existsByEmployeeAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            AppEmployeeProfile employee,
            LocalDate endDate,
            LocalDate startDate
    );
}
