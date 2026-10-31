package ph.com.tteh.entity.staff.payrollinfo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ph.com.tteh.entity.staff.generalinfo.AppEmployeeProfile;
import ph.com.tteh.enums.PayrollCycle;
import ph.com.tteh.enums.SalaryType;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "tteh_app_employee_payroll_information")
@NoArgsConstructor
@AllArgsConstructor
public class AppPayrollInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /* Salary Information */
    @Column(name = "basic_salary", precision = 12, scale = 2)
    private BigDecimal basicSalary;

    @Column(name = "daily_rate", precision = 12, scale = 2)
    private BigDecimal dailyRate;

    @Column(name = "hourly_rate", precision = 12, scale = 2)
    private BigDecimal hourlyRate;

    /* Payroll Configuration */
    @Enumerated(EnumType.STRING)
    @Column(name = "salary_type")
    private SalaryType salaryType;

    @Enumerated(EnumType.STRING)
    @Column(name = "payroll_cycle")
    private PayrollCycle payrollCycle;

    @Column(name = "effective_date")
    private LocalDate effectiveDate;

    /* Bank Information */
    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "bank_branch")
    private String bankBranch;

    @Column(name = "bank_account_number")
    private String bankAccountNumber;

    /* Payroll Settings */
    @Column(name = "is_taxable")
    private Boolean isTaxable;

    @Column(name = "overtime_eligible")
    private Boolean overtimeEligible;

    @Column(name = "holiday_pay_eligible")
    private Boolean holidayPayEligible;

    @Column(name = "thirteenth_month_eligible")
    private Boolean thirteenthMonthEligible;

    @JsonBackReference
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_profile_id", unique = true, nullable = false)
    private AppEmployeeProfile appEmployeeProfile;
}
