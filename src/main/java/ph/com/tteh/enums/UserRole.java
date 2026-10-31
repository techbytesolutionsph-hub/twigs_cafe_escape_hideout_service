package ph.com.tteh.enums;

import lombok.Getter;

@Getter
public enum UserRole {

    SUPER_ADMIN("SAD"),
    ADMIN("ADM"),
    PARTNER("PTR"),
    OPERATIONS_MANAGER("OPM"),
    HEAD_COOK("HCK"),
    KITCHEN_STAFF("KST"),
    SERVICE_CREW("SCR"),
    BARISTA("BAR"),
    CASHIER("CSH");

    private final String code;

    UserRole(String code) {
        this.code = code;
    }
}