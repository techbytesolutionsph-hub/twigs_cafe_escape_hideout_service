package ph.com.tteh.entity.staff.timesheet;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ph.com.tteh.entity.staff.generalinfo.AppEmployeeProfile;
import ph.com.tteh.enums.TimesheetStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "tteh_app_weekly_timesheet")
@NoArgsConstructor
@AllArgsConstructor
public class AppWeeklyTimesheet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "week_ending")
    private LocalDate weekEnding;

    @Enumerated(EnumType.STRING)
    private TimesheetStatus status;

    @Column(name = "submitted_at")
    private LocalDateTime submittedAt;

    @Column(name = "approved_at")
    private LocalDateTime approvedAt;

    @Column(name = "rejected_at")
    private LocalDateTime rejectedAt;

    @Column(name = "approver_name", length = 150)
    private String approverName;

    @Column(name = "approval_remarks", length = 1000)
    private String approvalRemarks;

    @JsonManagedReference
    @OneToMany(mappedBy = "timesheet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AppTimesheetEntry> entries;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_profile_id", nullable = false)
    private AppEmployeeProfile appEmployeeProfile;
}
