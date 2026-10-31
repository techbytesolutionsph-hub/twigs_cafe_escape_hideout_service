package ph.com.tteh.entity.staff.timesheet;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "tteh_app_timesheet_entry")
@NoArgsConstructor
@AllArgsConstructor
public class AppTimesheetEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private BigDecimal monday;
    private BigDecimal tuesday;
    private BigDecimal wednesday;
    private BigDecimal thursday;
    private BigDecimal friday;
    private BigDecimal saturday;
    private BigDecimal sunday;
    private BigDecimal totalHours;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "timesheet_id")
    private AppWeeklyTimesheet timesheet;
}
