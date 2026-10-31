package ph.com.tteh.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class LoginHistoryRequest {

    private Long loginId;
    private String ipAddress;
    private String deviceInfo;
    private LocalDateTime loginTime;
    private boolean isSuccessful;
}
