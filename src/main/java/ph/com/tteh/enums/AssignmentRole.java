package ph.com.tteh.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AssignmentRole {
    PRIMARY_CASE_MANAGER("Primary Case Manager"),
    PRIMARY_BEHAVIORAL_THERAPIST("Primary Behavioral Therapist"),
    SECONDARY_BEHAVIORAL_THERAPIST("Secondary Behavioral Therapist");

    private final String displayName;
}
