package ph.com.tteh.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginDetailsResponse {

    private String sessionId;
    private Long userId;
    private String username;
    private String email;
    private List<String> roleList;
    private List<String> permissionList;
    private String loginStatus;
    private String ipAddress;
    private String deviceInfo;
}
