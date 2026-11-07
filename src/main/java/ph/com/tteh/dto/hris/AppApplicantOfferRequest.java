package ph.com.tteh.dto.hris;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import ph.com.tteh.enums.EmployeePosition;
import ph.com.tteh.enums.EmploymentType;
import ph.com.tteh.enums.SalaryType;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Applicant Job Offer Request")
public class AppApplicantOfferRequest {

    private Long id;
    private Long applicantId;
    private String hiringManagerId;
    private EmployeePosition positionTitle;
    private EmploymentType employmentType;
    private BigDecimal proposedSalary;
    private SalaryType salaryPeriod;
    private String workLocation;
    private LocalDate proposedStartDate;
    private LocalDate expirationDate;
    private String offerNotes;

    private String offerFileName;
    private String offerLetterUrl;
    private String offerLetterPublicId;
}
