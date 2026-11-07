package ph.com.tteh.entity.hris.management;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ph.com.tteh.enums.EmployeePosition;
import ph.com.tteh.enums.hris.ApplicantStatus;
import ph.com.tteh.utils.LocalDateUtils;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "tteh_app_applicant")
@NoArgsConstructor
@AllArgsConstructor
public class AppApplicant implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "applicant_id")
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "suffix")
    private String suffix;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "applied_job", nullable = false)
    private EmployeePosition appliedJob;

    @Column(name = "location")
    private String location;

    @Column(name = "experience")
    private BigInteger experience;

    @Column(name = "applied_date")
    private LocalDate appliedDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "applied_stage", nullable = false)
    private ApplicantStatus appliedStage;

    @Column(name = "resume_link")
    private String resumeLink;

    @Column(name = "resume_filename")
    private String resumeFilename;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "last_modification_date")
    private LocalDateTime lastModificationDate;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    /* Applicant Interviews */
    @JsonManagedReference
    @OneToMany(mappedBy = "applicant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AppApplicantInterview> interviews;

    /* Applicant Onboarding */
    @JsonManagedReference
    @OneToOne(mappedBy = "applicant", cascade = CascadeType.ALL, orphanRemoval = true)
    private AppApplicantOnboarding onboarding;

    public void setOnboarding(AppApplicantOnboarding onboarding) {
        this.onboarding = onboarding;

        if (onboarding != null && onboarding.getApplicant() != this) {
            onboarding.setApplicant(this);
        }
    }

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
