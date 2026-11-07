package ph.com.tteh.dto.staff.management;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ph.com.tteh.enums.TimesheetStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeeklyTimesheetResponse {

    private Long id;
    private String employeeId;
    private String employeeName;

    private LocalDate weekEnding;
    private TimesheetStatus status;

    private LocalDateTime submittedAt;
    private LocalDateTime approvedAt;
    private LocalDateTime rejectedAt;

    private String approverName;
    private String approvalRemarks;

    private List<TimesheetEntryResponse> entries;
}
