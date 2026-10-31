package ph.com.tteh.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class ClientLoginResponse implements LoginResponse {

    private boolean success;
    private String message;
    private Object data;
    private LocalDateTime timestamp;
}
