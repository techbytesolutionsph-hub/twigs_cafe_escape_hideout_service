package ph.com.tteh.enums.hris;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum InterviewResult {
    PASSED("Passed"),
    FAILED("Failed");

    private final String result;
}