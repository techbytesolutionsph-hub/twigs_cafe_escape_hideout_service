package ph.com.tteh.dto.response;

import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DashboardCardResponse {

    private String value;
    private String message;
}
