package ph.com.tteh.service.api.hris;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ph.com.tteh.entity.hris.management.AppApplicant;
import ph.com.tteh.entity.hris.management.AppApplicantOnboarding;
import ph.com.tteh.entity.hris.management.AppApplicantRequirement;
import ph.com.tteh.entity.hris.management.AppOnboardingRequirement;
import ph.com.tteh.enums.hris.ApplicantStatus;
import ph.com.tteh.enums.hris.OnboardingStatus;
import ph.com.tteh.enums.hris.RequirementStatus;
import ph.com.tteh.repository.hris.AppApplicantOnboardingRepository;
import ph.com.tteh.repository.hris.AppApplicantRepository;
import ph.com.tteh.repository.hris.AppApplicantRequirementRepository;
import ph.com.tteh.repository.hris.AppOnboardingRequirementRepository;
import ph.com.tteh.utils.LocalDateUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AppApplicantOnboardingService {

    private final AppApplicantRepository applicantRepository;
    private final AppApplicantOnboardingRepository onboardingRepository;
    private final AppApplicantRequirementRepository applicantRequirementRepository;
    private final AppOnboardingRequirementRepository requirementRepository;

    /**
     * Creates onboarding for an applicant and assigns all active onboarding requirements.
     */
    public AppApplicantOnboarding createOnboarding(Long applicantId) {

        AppApplicant applicant = applicantRepository.findById(applicantId)
                .orElseThrow(() -> new IllegalArgumentException("Applicant not found: " + applicantId));

        if (applicant.getOnboarding() != null) {
            throw new IllegalStateException("Applicant already has an onboarding record.");
        }

        AppApplicantOnboarding onboarding = new AppApplicantOnboarding();
        onboarding.setApplicant(applicant);
        onboarding.setStatus(OnboardingStatus.IN_PROGRESS);
        onboarding.setOnboardingStartDate(LocalDateUtils.getLocalDate());

        List<AppOnboardingRequirement> requirements = requirementRepository.findByActiveTrue();

        for (AppOnboardingRequirement requirement : requirements) {

            AppApplicantRequirement applicantRequirement = new AppApplicantRequirement();
            applicantRequirement.setRequirement(requirement);
            applicantRequirement.setStatus(RequirementStatus.PENDING);

            onboarding.addRequirement(applicantRequirement);
        }

        applicant.setAppliedStage(ApplicantStatus.FOR_REQUIREMENTS);

        return onboardingRepository.save(onboarding);
    }

    /**
     * Submit an applicant requirement.
     */
    public AppApplicantRequirement submitRequirement(Long applicantRequirementId, String documentUrl, String documentFileName) {

        AppApplicantRequirement applicantRequirement = getApplicantRequirement(applicantRequirementId);

        applicantRequirement.setDocumentUrl(documentUrl);
        applicantRequirement.setDocumentFileName(documentFileName);
        applicantRequirement.setSubmittedDate(LocalDateUtils.getLocalDate());
        applicantRequirement.setStatus(RequirementStatus.SUBMITTED);

        return applicantRequirement;
    }

    /**
     * Verify an applicant requirement.
     */
    public AppApplicantRequirement verifyRequirement(Long applicantRequirementId, Long verifiedByEmployeeId) {

        AppApplicantRequirement applicantRequirement = getApplicantRequirement(applicantRequirementId);

        if (applicantRequirement.getStatus() != RequirementStatus.SUBMITTED) {
            throw new IllegalStateException("Only submitted requirements can be verified.");
        }

        applicantRequirement.setStatus(RequirementStatus.VERIFIED);
        applicantRequirement.setVerifiedDate(LocalDateUtils.getLocalDate());

        updateOnboardingStatus(applicantRequirement.getOnboarding());

        return applicantRequirement;
    }

    /**
     * Reject an applicant requirement.
     */
    public AppApplicantRequirement rejectRequirement(Long applicantRequirementId, String remarks) {

        AppApplicantRequirement applicantRequirement = this.getApplicantRequirement(applicantRequirementId);
        applicantRequirement.setStatus(RequirementStatus.REJECTED);
        applicantRequirement.setRemarks(remarks);
        this.updateOnboardingStatus(applicantRequirement.getOnboarding());

        return applicantRequirement;
    }

    /**
     * Update onboarding status based on requirements.
     */
    private void updateOnboardingStatus(AppApplicantOnboarding onboarding) {

        List<AppApplicantRequirement> requirements = onboarding.getRequirements();

        boolean hasRejectedRequirement = requirements.stream()
                .anyMatch(requirement -> requirement.getStatus() == RequirementStatus.REJECTED);

        if (hasRejectedRequirement) {
            onboarding.setStatus(OnboardingStatus.IN_PROGRESS);
            onboarding.getApplicant().setAppliedStage(ApplicantStatus.FOR_REQUIREMENTS);
            return;
        }

        boolean allRequiredCompleted = requirements.stream()
                .filter(requirement -> requirement.getRequirement().isRequired())
                .allMatch(requirement -> requirement.getStatus() == RequirementStatus.VERIFIED || requirement.getStatus() == RequirementStatus.NOT_APPLICABLE);

        if (allRequiredCompleted) {
            onboarding.setStatus(OnboardingStatus.COMPLETED);
            onboarding.setCompletedDate(LocalDateUtils.getLocalDate());
            onboarding.getApplicant().setAppliedStage(ApplicantStatus.REQUIREMENTS_COMPLETED);

        } else {
            onboarding.setStatus(OnboardingStatus.IN_PROGRESS);
            onboarding.getApplicant().setAppliedStage(ApplicantStatus.FOR_REQUIREMENTS);
        }
    }

    /**
     * Get applicant requirement.
     */
    @Transactional(readOnly = true)
    public AppApplicantRequirement getApplicantRequirement(Long applicantRequirementId) {

        return applicantRequirementRepository.findById(applicantRequirementId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Applicant requirement not found: " + applicantRequirementId));
    }

    /**
     * Get applicant onboarding.
     */
    @Transactional(readOnly = true)
    public AppApplicantOnboarding getOnboarding(Long applicantId) {

        return onboardingRepository.findByApplicantId(applicantId)
                .orElseThrow(() -> new IllegalArgumentException("Onboarding not found for applicant: " + applicantId));
    }
}
