package ph.com.tteh.repository.hris;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ph.com.tteh.entity.hris.management.AppOnboardingRequirement;

import java.util.List;

@Repository
public interface AppOnboardingRequirementRepository extends JpaRepository<AppOnboardingRequirement, Long> {
    List<AppOnboardingRequirement> findByActiveTrue();
}
