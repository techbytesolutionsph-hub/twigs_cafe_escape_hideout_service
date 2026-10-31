package ph.com.tteh.entity.staff.benefits;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ph.com.tteh.entity.staff.generalinfo.AppEmployeeProfile;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "tteh_app_employee_benefits")
@NoArgsConstructor
@AllArgsConstructor
public class AppEmployeeBenefits {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /* ==========================
       GOVERNMENT BENEFITS
       ========================== */

    @Column(name = "tin_number", length = 20)
    private String tinNumber;

    @Column(name = "sss_number", length = 20)
    private String sssNumber;

    @Column(name = "philhealth_number", length = 20)
    private String philHealthNumber;

    @Column(name = "pagibig_number", length = 20)
    private String pagIbigNumber;

    /* ==========================
       ALLOWANCES
       ========================== */

    @Column(name = "allowance")
    private BigDecimal allowance;

    @Column(name = "rice_allowance")
    private BigDecimal riceAllowance;

    @Column(name = "transportation_allowance")
    private BigDecimal transportationAllowance;

    @Column(name = "communication_allowance")
    private BigDecimal communicationAllowance;

    /* ==========================
       LEAVES
       ========================== */

    @Column(name = "sick_leave")
    private BigDecimal sickLeave;

    @Column(name = "vacation_leave")
    private BigDecimal vacationLeave;

    @Column(name = "paternity_leave")
    private BigDecimal paternityLeave;

    @Column(name = "maternity_leave")
    private BigDecimal maternityLeave;

    /* ==========================
       CONTRIBUTION FLAGS
       ========================== */
    @Column(name = "sss_active")
    private Boolean sssActive;

    @Column(name = "philhealth_active")
    private Boolean philHealthActive;

    @Column(name = "pagibig_active")
    private Boolean pagIbigActive;

    @Column(name = "has_hmo")
    private Boolean hasHmo;

    /* ==========================
       COMPANY HMO
       ========================== */

    @Column(name = "hmo_provider")
    private String hmoProvider;

    @Column(name = "hmo_card_number")
    private String hmoCardNumber;

    @JsonBackReference
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_profile_id", unique = true, nullable = false)
    private AppEmployeeProfile appEmployeeProfile;
}
