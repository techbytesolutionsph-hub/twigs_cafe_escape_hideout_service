package ph.com.tteh.entity.staff.employmentinfo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ph.com.tteh.entity.staff.generalinfo.AppEmployeeProfile;
import ph.com.tteh.enums.EmployeePosition;
import ph.com.tteh.enums.EmploymentStatus;
import ph.com.tteh.enums.EmploymentType;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "tteh_app_employment_information")
@NoArgsConstructor
@AllArgsConstructor
public class AppEmploymentInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "employee_id")
    private String employeeId;

    @Enumerated(EnumType.STRING)
    @Column(name = "position")
    private EmployeePosition position;

    @Column(name = "date_hired")
    private LocalDate dateHired;

    @Column(name = "regular_start_date")
    private LocalDate regularStartDate;

    @Column(name = "address")
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "employment_type")
    private EmploymentType employmentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "employment_status")
    private EmploymentStatus employmentStatus;

    /**
     * Example:
     * Main Branch
     * San Jose Branch
     */
    @Column(name = "branch_assign")
    private String branchAssign;

    @Column(name = "immediate_supervisor")
    private String immediateSupervisor;

    @JsonBackReference
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_profile_id", unique = true, nullable = false)
    private AppEmployeeProfile appEmployeeProfile;
}
