package ph.com.tteh.repository.hris;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ph.com.tteh.entity.hris.management.AppApplicantOnboarding;

import java.util.Optional;

@Repository
public interface AppApplicantOnboardingRepository extends JpaRepository<AppApplicantOnboarding, Long> {
    Optional<AppApplicantOnboarding> findByApplicantId(Long applicantId);
}
