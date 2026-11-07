package ph.com.tteh.dto.staff.management;

import lombok.Getter;
import lombok.Setter;
import ph.com.tteh.enums.TimesheetStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class WeeklyTimesheetRequest {

    private String employeeId;
    private LocalDate weekEnding;
    private TimesheetStatus status;
    private List<Entry> entries;

    @Getter
    @Setter
    public static class Entry {
        private BigDecimal monday;
        private BigDecimal tuesday;
        private BigDecimal wednesday;
        private BigDecimal thursday;
        private BigDecimal friday;
        private BigDecimal saturday;
        private BigDecimal sunday;
        private BigDecimal totalHours;
    }
}
