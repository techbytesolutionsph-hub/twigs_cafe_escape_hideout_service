package ph.com.tteh.dto.hris;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import ph.com.tteh.entity.staff.generalinfo.AppEmployeeProfile;
import ph.com.tteh.enums.ScheduleStatus;
import ph.com.tteh.enums.hris.InterviewMethod;
import ph.com.tteh.enums.hris.InterviewResult;
import ph.com.tteh.enums.hris.InterviewStage;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Applicant Interview Response")
public class ApplicantInterviewResponse {

    private Long id;
    private AppEmployeeProfile interviewer;
    private InterviewMethod interviewMethod;
    private InterviewStage interviewStage;
    private InterviewResult interviewResult;
    private LocalDate interviewDate;
    private LocalTime interviewTime;
    private ScheduleStatus scheduleStatus;
    private String meetingLink;
    private String notes;

}
