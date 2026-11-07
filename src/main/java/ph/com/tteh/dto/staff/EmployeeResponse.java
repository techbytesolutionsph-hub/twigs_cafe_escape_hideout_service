package ph.com.tteh.dto.staff;

import lombok.*;
import ph.com.tteh.enums.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponse {

    private Long id;
    private String employeeId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String suffix;
    private LocalDate dateOfBirth;
    private BigInteger age;
    private MaritalStatus maritalStatus;
    private Gender gender;
    private String email;
    private String phoneNumber;
    private Nationality nationality;
    private String profileImageUrl;
    private Boolean profileCompleted;
    private Boolean emailVerified;
    private Boolean phoneVerified;
    private Boolean active;
    private LocalDateTime creationDate;
    private String createdBy;
    private LocalDateTime lastModificationDate;
    private String lastModifiedBy;

    private List<AddressResponse> address;
    private ContactInformationResponse contactInformation;
    private EmergencyContactResponse emergencyContact;
    private EmploymentInformationResponse employmentInformation;
    private BenefitsResponse employeeBenefits;
    private PayrollResponse payrollInformation;
    private List<TimesheetResponse> timesheets;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AddressResponse {
        private Long id;
        private String street;
        private String barangay;
        private String city;
        private String province;
        private String country;
        private String zipNumber;
        private Boolean permanentResidence;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ContactInformationResponse {
        private Long id;
        private String contactNumber;
        private String workEmail;
        private String emergencyContactName;
        private String emergencyContactNumber;
        private String relationship;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EmergencyContactResponse {
        private Long id;
        private String name;
        private String contactNumber;
        private String relationship;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EmploymentInformationResponse {
        private Long id;
        private String employeeId;
        private EmployeePosition position;
        private LocalDate dateHired;
        private LocalDate regularStartDate;
        private String address;
        private EmploymentType employmentType;
        private EmploymentStatus employmentStatus;
        private String branchAssign;
        private String immediateSupervisor;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BenefitsResponse {
        private Long id;
        private String tinNumber;
        private String sssNumber;
        private String philHealthNumber;
        private String pagIbigNumber;
        private BigDecimal allowance;
        private BigDecimal riceAllowance;
        private BigDecimal transportationAllowance;
        private BigDecimal communicationAllowance;
        private BigDecimal sickLeave;
        private BigDecimal vacationLeave;
        private BigDecimal paternityLeave;
        private BigDecimal maternityLeave;
        private Boolean sssActive;
        private Boolean philHealthActive;
        private Boolean pagIbigActive;
        private Boolean hasHmo;
        private String hmoProvider;
        private String hmoCardNumber;
    }


    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PayrollResponse {
        private Long id;
        private BigDecimal basicSalary;
        private BigDecimal dailyRate;
        private BigDecimal hourlyRate;
        private SalaryType salaryType;
        private PayrollCycle payrollCycle;
        private LocalDate effectiveDate;
        private String bankName;
        private String bankBranch;
        private String bankAccountNumber;
        private Boolean taxable;
        private Boolean overtimeEligible;
        private Boolean holidayPayEligible;
        private Boolean thirteenthMonthEligible;
    }


    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TimesheetResponse {
        private Long id;
        private LocalDate weekEnding;
        private TimesheetStatus status;
        private List<TimesheetEntryResponse> entries;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TimesheetEntryResponse {
        private Long id;
        private BigDecimal monday;
        private BigDecimal tuesday;
        private BigDecimal wednesday;
        private BigDecimal thursday;
        private BigDecimal friday;
        private BigDecimal saturday;
        private BigDecimal sunday;
        private BigDecimal totalHours;
    }
}