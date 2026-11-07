package ph.com.tteh.service.api.hris;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ph.com.tteh.dto.hris.ApplicantInterviewRequest;
import ph.com.tteh.dto.hris.InterviewNotesRequest;
import ph.com.tteh.dto.hris.InterviewResultRequest;
import ph.com.tteh.dto.hris.RescheduleInterviewRequest;
import ph.com.tteh.dto.response.CommonResponse;
import ph.com.tteh.entity.hris.management.AppApplicant;
import ph.com.tteh.entity.hris.management.AppApplicantInterview;
import ph.com.tteh.entity.staff.generalinfo.AppEmployeeProfile;
import ph.com.tteh.enums.ScheduleStatus;
import ph.com.tteh.enums.hris.ApplicantStatus;
import ph.com.tteh.enums.hris.InterviewResult;
import ph.com.tteh.enums.hris.InterviewStage;
import ph.com.tteh.exception.ServiceException;
import ph.com.tteh.repository.hris.AppApplicantInterviewRepository;
import ph.com.tteh.repository.hris.AppApplicantRepository;
import ph.com.tteh.service.api.management.EmploymentRegistryService;
import ph.com.tteh.service.util.logging.LoggingService;


import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ApplicantInterviewService {

    private final AppApplicantRepository appApplicantRepository;
    private final AppApplicantInterviewRepository appApplicantInterviewRepository;
    private final EmploymentRegistryService employmentRegistryService;
    private final LoggingService loggingService;

    public CommonResponse createApplicantInterview(String uuid, ApplicantInterviewRequest request) throws ServiceException {

        AppApplicant applicant = this.findAppApplicantById(uuid, request.getApplicantId());
        AppEmployeeProfile interviewer = employmentRegistryService.getAppEmployeeProfile(uuid, request.getInterviewerId());

        AppApplicantInterview applicantInterview = new AppApplicantInterview();
        applicantInterview.setInterviewer(interviewer);
        applicantInterview.setInterviewMethod(request.getInterviewMethod());
        applicantInterview.setInterviewStage(request.getInterviewStage());
        applicantInterview.setInterviewDate(request.getInterviewDate());
        applicantInterview.setInterviewTime(request.getInterviewTime());
        applicantInterview.setScheduleStatus(request.getScheduleStatus());
        applicantInterview.setMeetingLink(request.getMeetingLink());
        applicantInterview.setNotes(request.getNotes());

        /* Set this to INTERVIEW stage */
        applicant.setAppliedStage(ApplicantStatus.INTERVIEW);

        applicantInterview.setApplicant(applicant);
        applicant.getInterviews().add(applicantInterview);

        AppApplicant saved = appApplicantRepository.save(applicant);

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("interview", saved);

        return CommonResponse.builder()
                .returnCode(HttpStatus.CREATED.value())
                .returnMessage("Applicant interview schedule saved successfully!")
                .responseBody(responseBody)
                .build();
    }

    public AppApplicant findAppApplicantById(String uuid, Long id) throws ServiceException {
        return appApplicantRepository.findById(id).orElseThrow(() -> {
            loggingService.error(uuid, getClass().getName(), "Applicant not found with ID: " + id, HttpStatus.NOT_FOUND.value());
            return new ServiceException(HttpStatus.NOT_FOUND.value(), "Applicant not found with ID: " + id);
        });
    }

    public void updateInterviewNotes(String uuid, InterviewNotesRequest request) throws ServiceException {
        AppApplicant applicant = this.findAppApplicantById(uuid, request.getApplicantId());

        AppApplicantInterview interview = applicant.getInterviews()
                .stream()
                .filter(i -> i.getId().equals(request.getInterviewId()))
                .findFirst()
                .orElseThrow(() -> new ServiceException("Interview not found"));

        interview.setNotes(request.getNotes());

        appApplicantInterviewRepository.save(interview);

    }

    public void updateInterviewResult(String uuid, InterviewResultRequest request) throws ServiceException {
        AppApplicant applicant = this.findAppApplicantById(uuid, request.getApplicantId());

        AppApplicantInterview interview = applicant.getInterviews()
                .stream()
                .filter(i -> i.getId().equals(request.getInterviewId()))
                .findFirst()
                .orElseThrow(() -> new ServiceException("Interview not found"));

        interview.setInterviewResult(request.getInterviewResult());

        if (request.getInterviewResult() == InterviewResult.PASSED
                && interview.getInterviewStage() == InterviewStage.FINAL_INTERVIEW) {
            applicant.setAppliedStage(ApplicantStatus.OFFERED);
        } else if (request.getInterviewResult() == InterviewResult.PASSED) {
            applicant.setAppliedStage(ApplicantStatus.INTERVIEW);
        } else {
            applicant.setAppliedStage(ApplicantStatus.REJECTED);
        }

        appApplicantInterviewRepository.save(interview);
    }

    public void rescheduleInterview(String uuid, RescheduleInterviewRequest request) throws ServiceException {
        AppApplicant applicant = this.findAppApplicantById(uuid, request.getApplicantId());

        AppApplicantInterview interview = applicant.getInterviews()
                .stream()
                .filter(i -> i.getId().equals(request.getInterviewId()))
                .findFirst()
                .orElseThrow(() -> new ServiceException("Interview not found"));

        interview.setInterviewDate(request.getInterviewDate());
        interview.setInterviewTime(request.getInterviewTime());
        interview.setInterviewMethod(request.getInterviewMethod());
        interview.setMeetingLink(request.getMeetingLink());
        interview.setScheduleStatus(ScheduleStatus.RESCHEDULED);

        appApplicantInterviewRepository.save(interview);
    }
}
