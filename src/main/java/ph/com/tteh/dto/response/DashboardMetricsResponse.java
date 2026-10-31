package ph.com.tteh.dto.response;

import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DashboardMetricsResponse {

    private DashboardCardResponse totalEmployees;
    private DashboardCardResponse totalActiveEmployees;
    private DashboardCardResponse totalOnLeaveEmployees;
    private DashboardCardResponse totalResignedEmployees;

    private DashboardCardResponse totalScheduledInitialAssessments;
    private DashboardCardResponse totalScheduledTherapySessions;
    private DashboardCardResponse totalScheduledUpgradingPrograms;
    private DashboardCardResponse totalScheduledNeurodevAssessments;

    private DashboardCardResponse totalCandidates;
    private DashboardCardResponse totalInInterviews;
    private DashboardCardResponse totalOffered;
    private DashboardCardResponse totalHiredThisQuarter;
}
