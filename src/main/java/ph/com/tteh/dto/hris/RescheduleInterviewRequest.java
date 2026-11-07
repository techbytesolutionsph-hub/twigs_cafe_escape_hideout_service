package ph.com.tteh.dto.hris;

import lombok.*;
import ph.com.tteh.enums.hris.InterviewMethod;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RescheduleInterviewRequest {

    private Long applicantId;
    private Long interviewId;
    private InterviewMethod interviewMethod;
    private LocalDate interviewDate;
    private LocalTime interviewTime;
    private String meetingLink;
}
