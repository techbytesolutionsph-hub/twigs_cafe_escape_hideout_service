package ph.com.tteh.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EmployeePosition {
    SUPER_ADMIN("Super Admin"),
    IT_ADMINISTRATOR("IT Administrator"),
    CHIEF_OPERATING_OFFICER("Chief Operating Officer"),
    HUMAN_RESOURCE_MANAGER("Human Resource Manager"),
    CASE_MANAGER("Case Manager"),
    SENIOR_BEHAVIORAL_THERAPIST("Senior Behavioral Therapist"),
    BEHAVIORAL_THERAPIST("Behavioral Therapist"),
    SECRETARY("Secretary"),
    ACCOUNTING_OFFICER("Accounting Officer"),
    MAINTENANCE_STAFF("Maintenance Staff"),

    PSYCHOLOGIST("Psychologist"),
    SPEECH_LANGUAGE_PATHOLOGIST("Speech-Language Pathologist"),
    OCCUPATIONAL_THERAPIST("Occupational Therapist");

    private final String displayPosition;
}