package ph.com.tteh.controller.backend.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ph.com.tteh.dto.hris.ApplicantRequest;
import ph.com.tteh.dto.hris.ApplicantResponse;
import ph.com.tteh.dto.response.CommonResponse;
import ph.com.tteh.exception.ServiceException;
import ph.com.tteh.service.api.hris.RecruitmentService;
import ph.com.tteh.service.util.logging.LoggingService;
import ph.com.tteh.service.util.uuid.GenerateUUIDService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Candidate Controller")
@RequestMapping("/api/v1/recruitment")
public class RecruitmentController {

    private final RecruitmentService recruitmentService;
    private final GenerateUUIDService generateUUIDService;
    private final LoggingService loggingService;

    @Operation(summary = "Register Candidate")
    @PostMapping("/register-candidate")
    public ResponseEntity<CommonResponse> createApplicant(@Valid @RequestBody ApplicantRequest request) {
        String uuid = generateUUIDService.generateUUID();
        loggingService.info(uuid, this.getClass().getName(), "", "AppApplicantRequest : " + request.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(recruitmentService.createApplicant(uuid, request));
    }

    @Operation(summary = "Get all candidates")
    @GetMapping("/get-candidates")
    public ResponseEntity<List<ApplicantResponse>> getAllApplicants() {
        return ResponseEntity.ok(recruitmentService.getAllApplicants());
    }

    @Operation(summary = "Update Candidate")
    @PutMapping("/update-candidate")
    public ResponseEntity<CommonResponse> updateApplicant(@Valid @RequestBody ApplicantRequest request) throws ServiceException {
        String uuid = generateUUIDService.generateUUID();
        loggingService.info(uuid, this.getClass().getName(), "", "AppApplicantRequest : " + request.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(recruitmentService.updateApplicant(uuid, request));
    }
}
