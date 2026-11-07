package ph.com.tteh.entity.hris.management;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ph.com.tteh.enums.hris.OnboardingStatus;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "tteh_app_applicant_onboarding")
@NoArgsConstructor
@AllArgsConstructor
public class AppApplicantOnboarding implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "onboarding_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OnboardingStatus status;

    @Column(name = "onboarding_start_date")
    private LocalDate onboardingStartDate;

    @Column(name = "target_completion_date")
    private LocalDate targetCompletionDate;

    @Column(name = "completed_date")
    private LocalDate completedDate;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @JsonBackReference
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "applicant_id", nullable = false, unique = true)
    private AppApplicant applicant;

    @JsonManagedReference
    @OneToMany(mappedBy = "onboarding", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AppApplicantRequirement> requirements;

    public void addRequirement(AppApplicantRequirement requirement) {
        requirements.add(requirement);
        requirement.setOnboarding(this);
    }

    public void removeRequirement(AppApplicantRequirement requirement) {
        requirements.remove(requirement);
        requirement.setOnboarding(null);
    }
}
