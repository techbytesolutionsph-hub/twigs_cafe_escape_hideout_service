package ph.com.tteh.controller.backend.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ph.com.tteh.dto.admin.AppUserResponse;
import ph.com.tteh.dto.admin.CreateUserRequest;
import ph.com.tteh.dto.response.CommonResponse;
import ph.com.tteh.exception.ServiceException;
import ph.com.tteh.service.api.admin.UserAccountService;
import ph.com.tteh.service.util.logging.LoggingService;
import ph.com.tteh.service.util.uuid.GenerateUUIDService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "User Account Controller")
@RequestMapping("/api/v1/account/admin")
public class UserAccountController {

    private final UserAccountService userAccountService;
    private final LoggingService loggingService;
    private final GenerateUUIDService generateUUIDService;

    @Operation(summary = "Create User Account")
    @PostMapping("/create")
    public ResponseEntity<CommonResponse> createUserAccount(
            @Valid @RequestBody CreateUserRequest request) throws ServiceException {
        String uuid = generateUUIDService.generateUUID();
        loggingService.info(uuid, this.getClass().getName(), "", "CreateUserRequest : " + request.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(userAccountService.createUserAccount(uuid, request));
    }

    @Operation(summary = "Get ID Running Sequence")
    @GetMapping("/get-running-sequence/{dateHired}")
    public ResponseEntity<String> getIdRunningSequence(@PathVariable String dateHired) throws ServiceException {
        return ResponseEntity.ok(userAccountService.getIdRunningSequence(dateHired));
    }

    @Operation(summary = "Generate Temporary User Password")
    @GetMapping("/generate-temp-password")
    public ResponseEntity<String> generateTemporaryPassword() {
        return ResponseEntity.ok(userAccountService.generateTemporaryPassword());
    }

    @Operation(summary = "Get All Registered Users")
    @GetMapping("/get-users")
    public ResponseEntity<List<AppUserResponse>> getAllUsers() {
        return ResponseEntity.ok(userAccountService.getAllUsers());
    }

    @Operation(summary = "Get Registered User by Username")
    @GetMapping("/get-user/{username}")
    public ResponseEntity<AppUserResponse> getUserByUsername(@PathVariable String username) throws ServiceException {
        return ResponseEntity.ok(userAccountService.findByUsername(username));
    }

    @Operation(summary = "Update User Account")
    @PutMapping("/update")
    public ResponseEntity<CommonResponse> updateUserAccount(
            @Valid @RequestBody CreateUserRequest request) throws ServiceException {
        String uuid = generateUUIDService.generateUUID();
        loggingService.info(uuid, this.getClass().getName(), "", "CreateUserRequest : " + request.toString());
        return ResponseEntity.ok(userAccountService.updateUserAccount(uuid, request));
    }

    @DeleteMapping("/delete/{username}")
    public ResponseEntity<CommonResponse> softDeleteUserAccount(
            @RequestHeader(value = "X-Request-ID", required = false) String uuid,
            @PathVariable String username) throws ServiceException {

        CommonResponse response = userAccountService.softDeleteUserAccount(uuid, username);
        return ResponseEntity.status(response.getReturnCode()).body(response);
    }
}
