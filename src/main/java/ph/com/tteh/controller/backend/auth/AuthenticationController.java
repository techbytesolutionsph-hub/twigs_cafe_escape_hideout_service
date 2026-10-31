package ph.com.tteh.controller.backend.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ph.com.tteh.dto.auth.LoginResponse;
import ph.com.tteh.dto.auth.PortalLoginRequest;
import ph.com.tteh.dto.response.CommonResponse;
import ph.com.tteh.exception.ServiceException;
import ph.com.tteh.service.api.auth.AuthenticationService;
import ph.com.tteh.service.util.logging.LoggingService;
import ph.com.tteh.service.util.uuid.GenerateUUIDService;

@RestController
@RequiredArgsConstructor
@Tag(name = "Login Controller")
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final LoggingService loggingService;
    private final GenerateUUIDService generateUUIDService;

    @Operation(summary = "Portal Login")
    @PostMapping("/portal/login")
    public ResponseEntity<LoginResponse> portalLogin(
            @Valid @RequestBody PortalLoginRequest request,
            HttpServletRequest httpRequest) throws ServiceException {
        String uuid = generateUUIDService.generateUUID();
        loggingService.info(uuid, this.getClass().getName(), "", "StaffLoginRequest : " + request.toString());
        return ResponseEntity.ok(authenticationService.portalLogin(uuid, request, httpRequest));
    }

    @Operation(summary = "Portal Logout")
    @PostMapping("/portal/logout")
    public ResponseEntity<CommonResponse> portalLogout(
            HttpServletRequest httpRequest) {
        String uuid = generateUUIDService.generateUUID();
        loggingService.info(uuid, this.getClass().getName(), "", "Logout staff!");
        return ResponseEntity.ok(authenticationService.logout(uuid, true, httpRequest));
    }
}
