package ph.com.tteh.repository.hris;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ph.com.tteh.entity.hris.management.AppApplicant;
import ph.com.tteh.enums.hris.ApplicantStatus;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AppApplicantRepository extends JpaRepository<AppApplicant, Long> {

    List<AppApplicant> findByAppliedStage(ApplicantStatus appliedStage);
    List<AppApplicant> findByAppliedStageIn(List<ApplicantStatus> appliedStages);
    long countByAppliedDateBetween(LocalDate startDate, LocalDate endDate);
    long countByAppliedStageAndAppliedDateBetween(ApplicantStatus appliedStage, LocalDate startDate, LocalDate endDate);
}
