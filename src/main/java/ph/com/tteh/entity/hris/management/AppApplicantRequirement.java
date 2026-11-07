package ph.com.tteh.entity.hris.management;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ph.com.tteh.entity.staff.generalinfo.AppEmployeeProfile;
import ph.com.tteh.enums.hris.RequirementStatus;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(
        name = "tteh_app_applicant_requirement",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_applicant_requirement",
                        columnNames = {
                                "onboarding_id",
                                "requirement_id"
                        }
                )
        }
)
@NoArgsConstructor
@AllArgsConstructor
public class AppApplicantRequirement implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "applicant_requirement_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private RequirementStatus status;

    @Column(name = "submitted_date")
    private LocalDate submittedDate;

    @Column(name = "verified_date")
    private LocalDate verifiedDate;

    /**
     * Employee/HR staff who verified the document.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "verified_by")
    private AppEmployeeProfile verifiedBy;

    /**
     * Uploaded document URL.
     */
    @Column(name = "document_url", length = 1000)
    private String documentUrl;

    /**
     * Original uploaded file name.
     */
    @Column(name = "document_file_name", length = 255)
    private String documentFileName;

    @Column(name = "remarks", columnDefinition = "TEXT")
    private String remarks;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "onboarding_id", nullable = false)
    private AppApplicantOnboarding onboarding;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requirement_id", nullable = false)
    private AppOnboardingRequirement requirement;
}
