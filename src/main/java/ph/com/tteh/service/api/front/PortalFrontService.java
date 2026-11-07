package ph.com.tteh.service.api.front;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ph.com.tteh.dto.admin.AppUserResponse;
import ph.com.tteh.dto.hris.ApplicantResponse;
import ph.com.tteh.dto.response.DashboardCardResponse;
import ph.com.tteh.dto.response.DashboardMetricsResponse;
import ph.com.tteh.enums.hris.ApplicantStatus;
import ph.com.tteh.exception.ServiceException;
import ph.com.tteh.service.api.admin.UserAccountService;
import ph.com.tteh.service.api.hris.RecruitmentService;
import ph.com.tteh.utils.ObjectUtils;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PortalFrontService {

    private final UserAccountService userAccountService;
    private final RecruitmentService recruitmentService;

    public List<AppUserResponse> getAllUsers(){
        return userAccountService.getAllUsers();
    }

    public AppUserResponse findByUsername(String username) throws ServiceException {
        return userAccountService.findByUsername(username);
    }

    public List<ApplicantResponse> getAllApplicants() {
        return recruitmentService.getAllApplicants();
    }

    public ApplicantResponse getApplicant(Long applicantId) throws ServiceException {
        return ObjectUtils.copyAs(recruitmentService.getApplicant(applicantId), ApplicantResponse.class);
    }

    public DashboardMetricsResponse candidateKPIs() {
        LocalDate today = LocalDate.now();
        LocalDate startDate = today.withDayOfMonth(1);
        LocalDate endDate = today.withDayOfMonth(today.lengthOfMonth());

        int quarter = (LocalDate.now().getMonthValue() - 1) / 3 + 1;
        int currentQuarter = (today.getMonthValue() - 1) / 3;
        LocalDate startQuarterDate = LocalDate.of(today.getYear(), currentQuarter * 3 + 1, 1);
        LocalDate endQuarterDate = startDate.plusMonths(3).minusDays(1);


        long candidates = recruitmentService.getAllApplicants().size();
        long totalCandidatesCount = recruitmentService.countByAppliedDateBetween(startDate, endDate);
        DashboardCardResponse totalCandidates = DashboardCardResponse.builder()
                .value(String.valueOf(candidates))
                .message((totalCandidatesCount > 0 ? "+ " : "") + totalCandidatesCount + " this month")
                .build();

        long inInterview = recruitmentService.countAllByApplicantStatus(ApplicantStatus.INTERVIEW);
        long totalInInterviewCount = recruitmentService.countByApplicantStatusAndAppliedDateBetween(ApplicantStatus.INTERVIEW, startDate, endDate);
        DashboardCardResponse totalInInterviews = DashboardCardResponse.builder()
                .value(String.valueOf(inInterview))
                .message((totalInInterviewCount > 0 ? "+ " : "") + totalInInterviewCount + " this month")
                .build();

        long offered = recruitmentService.countAllByApplicantStatus(ApplicantStatus.OFFERED);
        long totalOfferedCount = recruitmentService.countByApplicantStatusAndAppliedDateBetween(ApplicantStatus.OFFERED, startDate, endDate);
        DashboardCardResponse totalOffered = DashboardCardResponse.builder()
                .value(String.valueOf(offered))
                .message((totalOfferedCount > 0 ? "+ " : "") + totalOfferedCount + " this month")
                .build();

        long hired = recruitmentService.countAllByApplicantStatus(ApplicantStatus.HIRED);
        long totalHiredCount = recruitmentService.countByApplicantStatusAndAppliedDateBetween(ApplicantStatus.HIRED, startQuarterDate, endQuarterDate);
        DashboardCardResponse totalHired = DashboardCardResponse.builder()
                .value(String.valueOf(hired))
                .message((totalHiredCount > 0 ? "+ " : "") + totalHiredCount + " vs Q" + quarter)
                .build();

        return DashboardMetricsResponse.builder()
                .totalCandidates(totalCandidates)
                .totalInInterviews(totalInInterviews)
                .totalOffered(totalOffered)
                .totalHiredThisQuarter(totalHired)
                .build();
    }
}
