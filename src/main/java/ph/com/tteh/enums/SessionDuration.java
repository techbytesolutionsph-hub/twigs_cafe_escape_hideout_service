package ph.com.tteh.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SessionDuration {
    MINUTES_30(30),
    MINUTES_45(45),
    MINUTES_60(60),
    MINUTES_90(90),
    MINUTES_120(120);

    private final int minutes;
}