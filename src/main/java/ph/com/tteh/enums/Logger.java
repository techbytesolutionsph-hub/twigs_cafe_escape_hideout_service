package ph.com.tteh.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Logger {
    INFO_LOG("UUID={%s} | COMPONENT={%s} | MESSAGE={%s} | INFO={%s}"),
    ERROR_LOG("UUID={%s} | COMPONENT={%s} | ERROR MSG={%s} | STATUS={%s}");

    private final String logStr;
}
