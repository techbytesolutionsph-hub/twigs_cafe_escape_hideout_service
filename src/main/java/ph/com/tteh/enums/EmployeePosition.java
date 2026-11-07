package ph.com.tteh.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EmployeePosition {
    SUPER_ADMIN("Super Admin"),
    IT_ADMINISTRATOR("IT Administrator"),
    PARTNER("Partner"),
    OPERATIONS_MANAGER("Operations Manager"),
    HEAD_COOK("Head Cook"),
    KITCHEN_STAFF("Kitchen Staff"),
    SERVICE_CREW("Service Crew"),
    BARISTA("Barista"),
    CASHIER("Cashier");

    private final String displayPosition;
}