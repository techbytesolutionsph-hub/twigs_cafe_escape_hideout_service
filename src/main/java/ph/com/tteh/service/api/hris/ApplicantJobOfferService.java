package ph.com.tteh.service.api.hris;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ph.com.tteh.dto.hris.AppApplicantOfferRequest;
import ph.com.tteh.dto.response.CommonResponse;
import ph.com.tteh.entity.hris.management.AppApplicant;
import ph.com.tteh.entity.hris.management.AppApplicantOffer;
import ph.com.tteh.entity.staff.generalinfo.AppEmployeeProfile;
import ph.com.tteh.enums.OfferStatus;
import ph.com.tteh.exception.ServiceException;
import ph.com.tteh.repository.hris.AppApplicantOfferRepository;
import ph.com.tteh.service.api.management.EmploymentRegistryService;
import ph.com.tteh.service.db.SequenceGeneratorService;
import ph.com.tteh.service.util.IdGeneratorUtils;
import ph.com.tteh.service.util.logging.LoggingService;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class ApplicantJobOfferService {

    private final AppApplicantOfferRepository appApplicantOfferRepository;
    private final EmploymentRegistryService employmentRegistryService;
    private final ApplicantInterviewService applicantInterviewService;
    private final SequenceGeneratorService sequenceGeneratorService;
    private final IdGeneratorUtils idGeneratorUtils;
    private final LoggingService loggingService;

    public CommonResponse createJobOffer(String uuid, AppApplicantOfferRequest request) throws ServiceException {

        loggingService.info(uuid, this.getClass().getName(), "", "Applicant ID: " + request.getApplicantId());
        AppApplicant applicant = applicantInterviewService.findAppApplicantById(uuid, request.getApplicantId());

        loggingService.info(uuid, this.getClass().getName(), "", "Hiring Manager ID: " + request.getHiringManagerId());
        AppEmployeeProfile hiringManager = employmentRegistryService.getAppEmployeeProfile(uuid, request.getHiringManagerId());

        String dateOffered = String.valueOf(LocalDate.now().getYear());
        long nextJobOfferSeq = sequenceGeneratorService.getJobOrderIdNextSequence();
        String jobOrderNo =  idGeneratorUtils.generateJobOrderId(dateOffered, nextJobOfferSeq);

        AppApplicantOffer offer = new AppApplicantOffer();
        offer.setJobOrderNo(jobOrderNo);
        offer.setApplicant(applicant);
        offer.setPositionTitle(request.getPositionTitle());
        offer.setEmploymentType(request.getEmploymentType());
        offer.setProposedSalary(request.getProposedSalary());
        offer.setHiringManager(hiringManager);
        offer.setSalaryPeriod(request.getSalaryPeriod());
        offer.setProposedStartDate(request.getProposedStartDate());
        offer.setExpirationDate(request.getExpirationDate());
        offer.setWorkLocation(request.getWorkLocation());
        offer.setOfferNotes(request.getOfferNotes());

        offer.setOfferStatus(OfferStatus.DRAFT);

        offer.setOfferFileName(request.getOfferFileName());
        offer.setOfferLetterUrl(request.getOfferLetterUrl());
        offer.setOfferLetterPublicId(request.getOfferLetterPublicId());

        AppApplicantOffer saved = appApplicantOfferRepository.save(offer);

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("offer", saved);

        return CommonResponse.builder()
                .returnCode(HttpStatus.CREATED.value())
                .returnMessage("Applicant job offer created successfully!")
                .responseBody(responseBody)
                .build();
    }
}
