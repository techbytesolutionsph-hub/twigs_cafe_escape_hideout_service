package ph.com.tteh.repository.hris;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ph.com.tteh.entity.hris.management.AppApplicantRequirement;

@Repository
public interface AppApplicantRequirementRepository extends JpaRepository<AppApplicantRequirement, Long> {
}