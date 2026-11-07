package ph.com.tteh.dto.staff.management;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import ph.com.tteh.enums.AbsenceDuration;
import ph.com.tteh.enums.LeaveType;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Leave request")
public class LeaveRequest {

    @Schema(
            description = "Employee ID submitting the leave request",
            example = "3LC-0326-0001",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String employeeId;

    @Schema(
            description = "Type of leave",
            example = "VACATION",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private LeaveType leaveType;

    @Schema(
            description = "Duration of the leave",
            example = "FULL_DAY",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private AbsenceDuration absenceDuration;

    @Schema(
            description = "Start date of the leave",
            example = "2026-12-28",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private LocalDate startDate;

    @Schema(
            description = "End date of the leave",
            example = "2026-12-28",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private LocalDate endDate;

    @Schema(
            description = "Leave start time",
            example = "08:00"
    )
    private LocalTime startTime;

    @Schema(
            description = "Leave end time",
            example = "17:00"
    )
    private LocalTime endTime;

    @Schema(
            description = "Number of leave days requested",
            example = "1 day",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Double requestedDays;

    @Schema(
            description = "Date when the employee returns to work",
            example = "2027-01-04",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private LocalDate returningToWorkOn;

    @Schema(
            description = "Additional comment or notes",
            example = "Test"
    )
    private String comment;
}