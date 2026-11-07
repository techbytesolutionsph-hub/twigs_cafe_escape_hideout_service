package ph.com.tteh.dto.hris;

import lombok.*;
import ph.com.tteh.enums.hris.InterviewResult;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class InterviewResultRequest {

    private Long applicantId;
    private Long interviewId;
    private InterviewResult interviewResult;
}
