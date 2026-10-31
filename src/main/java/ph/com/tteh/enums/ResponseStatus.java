package ph.com.tteh.enums;

import lombok.Getter;

@Getter
public enum ResponseStatus {
    SUCCESS("success"),
    ERROR("error"),
    WARNING("warning"),
    RETRY("retry");

    private final String value;

    private ResponseStatus(String value) {
        this.value = value;
    }
}
