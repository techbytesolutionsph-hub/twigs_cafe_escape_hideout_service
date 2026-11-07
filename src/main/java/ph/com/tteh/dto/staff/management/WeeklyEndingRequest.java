package ph.com.tteh.dto.staff.management;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class WeeklyEndingRequest {

    private String employeeId;
    private LocalDate weekEnding;
}
