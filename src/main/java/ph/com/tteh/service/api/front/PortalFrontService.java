package ph.com.tteh.service.api.front;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ph.com.tteh.dto.admin.AppUserResponse;
import ph.com.tteh.service.api.admin.UserAccountService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PortalFrontService {

    private final UserAccountService userAccountService;

    public List<AppUserResponse> getAllUsers(){
        return userAccountService.getAllUsers();
    }
}
