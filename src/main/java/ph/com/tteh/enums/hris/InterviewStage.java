package ph.com.tteh.enums.hris;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum InterviewStage {
    PHONE_SCREEN("Initial Interview"),
    HR_INTERVIEW("HR Interview"),
    TECHNICAL_INTERVIEW("Technical Interview"),
    FINAL_INTERVIEW("Final Interview");

    private final String label;
}
