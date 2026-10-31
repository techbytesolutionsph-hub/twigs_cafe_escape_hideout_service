package ph.com.tteh.entity.staff.generalinfo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ph.com.tteh.entity.staff.address.AppEmployeeAddress;
import ph.com.tteh.entity.staff.benefits.AppEmployeeBenefits;
import ph.com.tteh.entity.staff.contactinfo.AppEmployeeContactInformation;
import ph.com.tteh.entity.staff.employmentinfo.AppEmploymentInformation;
import ph.com.tteh.entity.staff.payrollinfo.AppPayrollInformation;
import ph.com.tteh.entity.staff.timesheet.AppWeeklyTimesheet;
import ph.com.tteh.entity.user.AppUser;
import ph.com.tteh.enums.Gender;
import ph.com.tteh.enums.MaritalStatus;
import ph.com.tteh.enums.Nationality;
import ph.com.tteh.utils.LocalDateUtils;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "tteh_app_employee_profile")
@NoArgsConstructor
@AllArgsConstructor
public class AppEmployeeProfile implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_profile_id")
    private Long id;

    @Column(name = "employee_id", unique = true)
    private String employeeId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "suffix")
    private String suffix;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "age")
    private BigInteger age;

    @Enumerated(EnumType.STRING)
    @Column(name = "marital_status")
    private MaritalStatus maritalStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "nationality")
    private Nationality nationality;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "profile_image_url")
    private String profileImageUrl;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "profile_completed")
    private boolean profileCompleted;

    @Column(name = "email_verified")
    private boolean emailVerified;

    @Column(name = "phone_verified")
    private boolean phoneVerified;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "last_modification_date")
    private LocalDateTime lastModificationDate;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @JsonManagedReference
    @OneToMany(mappedBy = "appEmployeeProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AppEmployeeAddress> address;

    @JsonManagedReference
    @OneToOne(mappedBy = "appEmployeeProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    private AppEmployeeContactInformation contactInformation;

    @JsonManagedReference
    @OneToOne(mappedBy = "appEmployeeProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    private AppEmploymentInformation employmentInformation;

    @JsonManagedReference
    @OneToOne(mappedBy = "appEmployeeProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    private AppEmployeeBenefits employeeBenefits;

    @JsonManagedReference
    @OneToOne(mappedBy = "appEmployeeProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    private AppPayrollInformation payrollInformation;

    @JsonManagedReference
    @OneToMany(mappedBy = "appEmployeeProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AppWeeklyTimesheet> timesheets;

    @JsonBackReference
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_user_id", unique = true, nullable = false)
    private AppUser appUser;

    @PrePersist
    private void prePersist() {
        creationDate = LocalDateUtils.getLocalDateTime();
        createdBy = "SYSTEM";
    }

    @PreUpdate
    private void preUpdate() {
        lastModificationDate = LocalDateUtils.getLocalDateTime();
        lastModifiedBy = "SYSTEM";
    }

}
