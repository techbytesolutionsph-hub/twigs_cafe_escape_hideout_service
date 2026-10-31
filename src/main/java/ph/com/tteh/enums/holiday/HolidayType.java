package ph.com.tteh.enums.holiday;

import lombok.Getter;

@Getter
public enum HolidayType {

    REGULAR_HOLIDAY("Regular Holidays"),
    SPECIAL_NON_WORKING("Special (Non-Working) Holidays"),
    SPECIAL_WORKING("Special (Working) Holidays");

    private final String value;

    private HolidayType(String value) {
        this.value = value;
    }
}
