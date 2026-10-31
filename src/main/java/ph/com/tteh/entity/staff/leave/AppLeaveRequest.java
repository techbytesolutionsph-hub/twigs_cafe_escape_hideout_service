package ph.com.tteh.entity.staff.leave;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ph.com.tteh.entity.staff.generalinfo.AppEmployeeProfile;
import ph.com.tteh.enums.AbsenceDuration;
import ph.com.tteh.enums.LeaveRequestStatus;
import ph.com.tteh.enums.LeaveType;
import ph.com.tteh.utils.LocalDateUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "tteh_app_leave_request")
@NoArgsConstructor
@AllArgsConstructor
public class AppLeaveRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Employee who submitted the leave request.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employee_id", nullable = false)
    private AppEmployeeProfile employee;

    /**
     * HR personnel who approves/rejects the leave request.
     * Nullable while the request is still pending.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "hr_approver_id")
    private AppEmployeeProfile hrApprover;

    @Enumerated(EnumType.STRING)
    @Column(name = "leave_type", nullable = false, length = 50)
    private LeaveType leaveType;

    @Enumerated(EnumType.STRING)
    @Column(name = "absence_duration", nullable = false, length = 30)
    private AbsenceDuration absenceDuration;

    @Column(name = "available_balance", nullable = false)
    private Double availableBalance;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;


    /**
     * Number of leave days being requested.
     * Examples: 1.0, 0.5, 2.0
     */
    @Column(name = "requested_days", nullable = false)
    private Double requestedDays;

    @Column(name = "returning_to_work_on")
    private LocalDate returningToWorkOn;

    @Column(name = "comment", length = 500)
    private String comment;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    private LeaveRequestStatus status;

    @Column(name = "approved_at")
    private LocalDateTime approvedAt;

    @Column(name = "rejected_at")
    private LocalDateTime rejectedAt;

    @Column(name = "approver_comment", length = 500)
    private String approverComment;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateUtils.getLocalDateTime();
        createdAt = now;
        updatedAt = now;

        if (status == null) {
            status = LeaveRequestStatus.PENDING;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateUtils.getLocalDateTime();
    }
}
