package ph.com.tteh.dto.hris;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;
import ph.com.tteh.enums.EmployeePosition;
import ph.com.tteh.enums.hris.ApplicantStatus;

import java.math.BigInteger;
import java.time.LocalDate;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Applicant Request")
public class ApplicantRequest {

    @Schema(
            description = "Applicant ID",
            example = "1",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Long id;

    @NotBlank(message = "First name is required")
    @Size(max = 100, message = "First name must not exceed 100 characters")
    @Schema(
            description = "First name",
            example = "Heero",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String firstName;

    @Size(max = 100, message = "Middle name must not exceed 100 characters")
    @Schema(
            description = "Middle name",
            example = "Anduiza"
    )
    private String middleName;

    @NotBlank(message = "Last name is required")
    @Size(max = 100, message = "Last name must not exceed 100 characters")
    @Schema(
            description = "Last name",
            example = "Yuy",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String lastName;

    @Size(max = 20, message = "Suffix must not exceed 20 characters")
    @Schema(
            description = "Name suffix",
            example = "Jr."
    )
    private String suffix;

    @Email(message = "Invalid email address")
    @Size(max = 255, message = "Email must not exceed 255 characters")
    @Schema(
            description = "Applicant email address",
            example = "heero.yuy@gmail.com"
    )
    private String email;

    @Pattern(
            regexp = "^(09\\d{9}|\\+639\\d{9})$",
            message = "Invalid Philippine phone number"
    )
    @Schema(
            description = "Applicant phone number",
            example = "09171234567"
    )
    private String phoneNumber;

    @NotNull(message = "Applied job is required")
    @Schema(
            description = "Position applied for",
            example = "BEHAVIORAL_THERAPIST",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private EmployeePosition appliedJob;

    @Size(max = 255, message = "Location must not exceed 255 characters")
    @Schema(
            description = "Applicant location",
            example = "Catanauan, Quezon"
    )
    private String location;

    @Schema(
            description = "Years of experience",
            example = "2"
    )
    private BigInteger experience;

    @Schema(
            description = "Date when the applicant applied. Defaults to the current date if not provided.",
            example = "2026-10-05"
    )
    private LocalDate appliedDate;

    @NotNull(message = "Applicant status is required")
    @Schema(
            description = "Current applicant status",
            example = "APPLIED",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private ApplicantStatus appliedStage;

    @Size(max = 2048, message = "Resume link must not exceed 2048 characters")
    @Schema(
            description = "Link to the applicant's resume",
            example = "https://example.com/resumes/heero-yuy.pdf"
    )
    private String resumeLink;

    @Schema(
            description = "Resume Filename",
            example = "OFFICIAL_RESUME_HEERO_YUY.pdf"
    )
    private String resumeFilename;


    @Schema(
            description = "Additional notes about the applicant",
            example = "Available for an interview next week."
    )
    private String notes;

}
