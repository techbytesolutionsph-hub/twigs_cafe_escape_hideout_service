package ph.com.tteh.dto.hris;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import ph.com.tteh.enums.EmployeePosition;
import ph.com.tteh.enums.hris.ApplicantStatus;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Applicant Response")
public class ApplicantResponse {

    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String suffix;
    private String email;
    private String phoneNumber;
    private EmployeePosition appliedJob;
    private String location;
    private BigInteger experience;
    private LocalDate appliedDate;
    private ApplicantStatus appliedStage;
    private String resumeLink;
    private String resumeFilename;
    private String notes;
    private LocalDateTime creationDate;
    private String createdBy;
    private LocalDateTime lastModificationDate;
    private String lastModifiedBy;

    private List<ApplicantInterviewResponse> interviews;
    private ApplicantInterviewResponse latestInterview;
}
