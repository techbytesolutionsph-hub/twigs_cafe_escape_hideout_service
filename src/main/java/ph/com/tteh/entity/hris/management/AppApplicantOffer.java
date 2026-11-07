package ph.com.tteh.entity.hris.management;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ph.com.tteh.entity.common.BaseEntity;
import ph.com.tteh.entity.staff.generalinfo.AppEmployeeProfile;
import ph.com.tteh.enums.EmployeePosition;
import ph.com.tteh.enums.EmploymentType;
import ph.com.tteh.enums.OfferStatus;
import ph.com.tteh.enums.SalaryType;
import ph.com.tteh.utils.LocalDateUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(
        name = "tteh_app_applicant_offer",
        indexes = {
                @Index(name = "idx_offer_candidate", columnList = "applicant_id"),
                @Index(name = "idx_offer_status", columnList = "offer_status")
        }
)
@NoArgsConstructor
public class AppApplicantOffer extends BaseEntity {

    @Column(name = "job_order_no", nullable = false, unique = true, length = 30)
    private String jobOrderNo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "applicant_id", nullable = false)
    private AppApplicant applicant;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "hiring_manager", nullable = false)
    private AppEmployeeProfile hiringManager;

    @Enumerated(EnumType.STRING)
    @Column(name = "position_title", nullable = false, length = 150)
    private EmployeePosition positionTitle;

    @Enumerated(EnumType.STRING)
    @Column(name = "employment_type", nullable = false, length = 30)
    private EmploymentType employmentType;

    @Column(name = "proposed_salary", nullable = false, precision = 12, scale = 2)
    private BigDecimal proposedSalary;

    @Enumerated(EnumType.STRING)
    @Column(name = "salary_period", nullable = false, length = 20)
    private SalaryType salaryPeriod;

    @Column(name = "work_location", nullable = false, length = 255)
    private String workLocation;

    @Column(name = "proposed_start_date", nullable = false)
    private LocalDate proposedStartDate;

    @Column(name = "offer_date", nullable = false)
    private LocalDate offerDate; //Effective date

    @Column(name = "expiration_date")
    private LocalDate expirationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "offer_status", nullable = false, length = 30)
    private OfferStatus offerStatus;

    @Column(name = "offer_notes", columnDefinition = "TEXT")
    private String offerNotes;

    /**
     * Original uploaded file name.
     */
    @Column(name = "offer_file_name", length = 255)
    private String offerFileName;

    @Column(name = "offer_letter_url", length = 1000)
    private String offerLetterUrl;

    @Column(name = "offer_letter_public_id", length = 255)
    private String offerLetterPublicId;

    @Column(name = "sent_at")
    private LocalDateTime sentAt;

    @Column(name = "responded_at")
    private LocalDateTime respondedAt;

    @Column(name = "accepted_at")
    private LocalDateTime acceptedAt;

    @Column(name = "decline_reason", columnDefinition = "TEXT")
    private String declineReason;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "last_modification_date")
    private LocalDateTime lastModificationDate;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @JsonManagedReference
    @OneToMany(mappedBy = "applicantOffer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AppApplicantOfferCall> offerCalls;

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
