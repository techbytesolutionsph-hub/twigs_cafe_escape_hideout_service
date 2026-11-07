package ph.com.tteh.service.api.management;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ph.com.tteh.dto.response.CommonResponse;
import ph.com.tteh.dto.staff.EmployeeRequest;
import ph.com.tteh.dto.staff.EmployeeResponse;
import ph.com.tteh.entity.staff.address.AppEmployeeAddress;
import ph.com.tteh.entity.staff.benefits.AppEmployeeBenefits;
import ph.com.tteh.entity.staff.contactinfo.AppEmployeeContactInformation;
import ph.com.tteh.entity.staff.employmentinfo.AppEmploymentInformation;
import ph.com.tteh.entity.staff.generalinfo.AppEmployeeProfile;
import ph.com.tteh.entity.staff.payrollinfo.AppPayrollInformation;
import ph.com.tteh.entity.user.AppUser;
import ph.com.tteh.enums.EmploymentStatus;
import ph.com.tteh.exception.ServiceException;
import ph.com.tteh.repository.management.AppEmployeeProfileRepository;
import ph.com.tteh.repository.user.AppUserRepository;
import ph.com.tteh.service.util.logging.LoggingService;
import ph.com.tteh.utils.ObjectUtils;


import java.math.BigInteger;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmploymentRegistryService {

    private final AppEmployeeProfileRepository employeeProfileRepository;
    private final AppUserRepository appUserRepository;
    private final LoggingService loggingService;
    private final AppEmployeeProfileRepository appEmployeeProfileRepository;

    @Transactional
    public CommonResponse createEmployee(String uuid, EmployeeRequest request) throws ServiceException {

        /*
         * Retrieve App User
         */
        String username = request.getAccountAccess().getUsername();

        /* Find existing user */
        AppUser appUser = appUserRepository.findUserByUsername(username)
                .orElseThrow(() -> new ServiceException(HttpStatus.NOT_FOUND.value(), "User not found: " + username));

        /*
         * Validate Existing Employee Profile
         */
        if (employeeProfileRepository.existsByAppUser(appUser)) {
            loggingService.error(uuid, getClass().getName(), "Employee profile already exists for username: " + username, HttpStatus.CONFLICT.value());
            throw new ServiceException(HttpStatus.CONFLICT.value(), "Employee profile already exists for username: " + username);
        }

        /*
         * Employee Profile
         */
        AppEmployeeProfile employee = new AppEmployeeProfile();

        EmployeeRequest.PersonalInformationDTO personal = request.getPersonalInformation();
        String employeeId = request.getEmploymentInformation().getEmployeeIdNumber();

        employee.setEmployeeId(employeeId);
        employee.setFirstName(personal.getFirstName());
        employee.setMiddleName(personal.getMiddleName());
        employee.setLastName(personal.getLastName());
        employee.setSuffix(personal.getSuffix());
        employee.setDateOfBirth(LocalDate.parse(personal.getBirthDate()));
        employee.setAge(BigInteger.valueOf(personal.getAge()));
        employee.setGender(personal.getGender());
        employee.setMaritalStatus(personal.getMaritalStatus());
        employee.setNationality(personal.getNationality());
        employee.setEmail(personal.getEmail());
        employee.setPhoneNumber(personal.getPhoneNumber());
        employee.setProfileImageUrl(request.getProfileImageUrl());

        employee.setActive(Boolean.TRUE);
        employee.setPhoneVerified(Boolean.TRUE);
        employee.setEmailVerified(Boolean.TRUE);
        employee.setProfileCompleted(Boolean.TRUE);

        /*
         * Contact Information
         */
        EmployeeRequest.ContactInformationDTO contact = request.getContactInformation();
        AppEmployeeContactInformation contactInfo = new AppEmployeeContactInformation();

        contactInfo.setContactNumber(contact.getContactNumber());
        contactInfo.setWorkEmail(contact.getWorkEmail());
        contactInfo.setEmergencyContactName(contact.getEmergencyContactName());
        contactInfo.setEmergencyContactNumber(contact.getEmergencyContactNumber());
        contactInfo.setRelationship(contact.getRelationship());

        contactInfo.setAppEmployeeProfile(employee);
        employee.setContactInformation(contactInfo);

        /*
         * Address
         */
        EmployeeRequest.AddressDTO addressDTO = request.getAddress();
        AppEmployeeAddress address = new AppEmployeeAddress();

        address.setBlockLot(addressDTO.getBlockLot());
        address.setStreet(addressDTO.getStreet());
        address.setSubdivision(addressDTO.getSubdivision());
        address.setBarangay(addressDTO.getBarangay());
        address.setCity(addressDTO.getCity());
        address.setProvince(addressDTO.getProvince());
        address.setCountry(addressDTO.getCountry());
        address.setZipNumber(addressDTO.getPostalCode());
        address.setPermanentResidence(true);//for checking

        address.setAppEmployeeProfile(employee);
        employee.setAddress(List.of(address));

        /*
         * Employment Information
         */
        EmployeeRequest.EmploymentInformationDTO employmentDTO = request.getEmploymentInformation();
        AppEmploymentInformation employment = new AppEmploymentInformation();

        employment.setPosition(employmentDTO.getPosition());
        employment.setDateHired(employmentDTO.getDateHired());
        employment.setEmployeeId(employmentDTO.getEmployeeIdNumber());
        employment.setAddress(employmentDTO.getCompanyAddress());
        employment.setBranchAssign(employmentDTO.getBranch());
        employment.setEmploymentType(employmentDTO.getEmploymentType());
        employment.setEmploymentStatus(employmentDTO.getEmploymentStatus());
        employment.setImmediateSupervisor(employmentDTO.getImmediateSupervisor());

        employment.setAppEmployeeProfile(employee);
        employee.setEmploymentInformation(employment);

        /*
         * Benefits
         */
        EmployeeRequest.BenefitsDTO benefitsDTO = request.getBenefits();
        AppEmployeeBenefits benefits = new AppEmployeeBenefits();

        benefits.setTinNumber(benefitsDTO.getTinNumber());
        benefits.setSssNumber(benefitsDTO.getSssNumber());
        benefits.setPhilHealthNumber(benefitsDTO.getPhilhealthNumber());
        benefits.setPagIbigNumber(benefitsDTO.getPagibigNumber());
        benefits.setAllowance(benefitsDTO.getAllowance());
        benefits.setRiceAllowance(benefitsDTO.getRiceAllowance());
        benefits.setTransportationAllowance(benefitsDTO.getTransportationAllowance());
        benefits.setCommunicationAllowance(benefitsDTO.getCommunicationAllowance());
        benefits.setSickLeave(benefitsDTO.getSickLeave());
        benefits.setVacationLeave(benefitsDTO.getVacationLeave());
        benefits.setPaternityLeave(benefitsDTO.getPaternityLeave());
        benefits.setMaternityLeave(benefitsDTO.getMaternityLeave());
        benefits.setHmoProvider(benefitsDTO.getHmoProvider());
        benefits.setHmoCardNumber(benefits.getHmoCardNumber());

        benefits.setHasHmo(
                benefitsDTO.getHmoProvider() != null && !benefitsDTO.getHmoProvider().isBlank()
                && benefits.getHmoCardNumber() != null && !benefits.getHmoCardNumber().isBlank()
        );
        benefits.setSssActive(
                benefitsDTO.getSssNumber() != null && !benefitsDTO.getSssNumber().isBlank()
        );

        benefits.setPagIbigActive(
                benefitsDTO.getPagibigNumber() != null && !benefitsDTO.getPagibigNumber().isBlank()
        );

        benefits.setPhilHealthActive(
                benefitsDTO.getPhilhealthNumber() != null && !benefitsDTO.getPhilhealthNumber().isBlank()
        );

        benefits.setAppEmployeeProfile(employee);
        employee.setEmployeeBenefits(benefits);

        /*
         * Payroll
         */
        EmployeeRequest.PayrollInformationDTO payrollDTO = request.getPayrollInformation();
        AppPayrollInformation payroll = new AppPayrollInformation();

        payroll.setBasicSalary(payrollDTO.getBasicSalary());
        payroll.setDailyRate(payrollDTO.getDailyRate());
        payroll.setHourlyRate(payrollDTO.getHourlyRate());
        payroll.setBankName(payrollDTO.getBankName());
        payroll.setBankAccountNumber(payrollDTO.getBankAccountNumber());
        payroll.setBankBranch(payrollDTO.getBankBranch());
        payroll.setSalaryType(payrollDTO.getSalaryType());
        payroll.setPayrollCycle(payrollDTO.getPayrollCycle());
        payroll.setEffectiveDate(payrollDTO.getEffectiveDate());

        payroll.setIsTaxable(Boolean.TRUE);//To do: check if taxable or not
        payroll.setHolidayPayEligible(Boolean.TRUE);
        payroll.setOvertimeEligible(Boolean.TRUE);
        payroll.setThirteenthMonthEligible(Boolean.TRUE);

        payroll.setAppEmployeeProfile(employee);
        employee.setPayrollInformation(payroll);

        /*
         * Link AppUser <-> Employee Profile
         */
        employee.setAppUser(appUser);
        appUser.setAppEmployeeProfile(employee);

        AppUser saved = appUserRepository.save(appUser);

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("appUser", saved);

        return CommonResponse.builder()
                .returnCode(HttpStatus.CREATED.value())
                .returnMessage("Employee created successfully!")
                .responseBody(responseBody)
                .build();
    }

    public List<EmployeeResponse> getEmployees() {
        return ObjectUtils.copyListAs(
                appEmployeeProfileRepository.findAll(Sort.by(Sort.Direction.DESC, "id")),
                EmployeeResponse.class);
    }

    public AppEmployeeProfile getAppEmployeeProfile(String uuid, String employeeId) throws ServiceException {
        return appEmployeeProfileRepository
                .findByEmployeeId(employeeId)
                .orElseThrow(() -> {
                    loggingService.error(uuid, getClass().getName(), "Employee not found with ID: " + employeeId, HttpStatus.NOT_FOUND.value());
                    return new ServiceException(HttpStatus.NOT_FOUND.value(), "Employee not found with ID: " + employeeId);
                });
    }

    @Transactional
    public CommonResponse updateEmployee(String uuid, EmployeeRequest request) throws ServiceException {

        String employeeId = request.getEmploymentInformation().getEmployeeIdNumber();

        AppEmployeeProfile employee = appEmployeeProfileRepository
                .findByEmployeeId(employeeId)
                .orElseThrow(() -> {
                    loggingService.error(uuid, getClass().getName(), "Employee not found with ID: " + employeeId, HttpStatus.NOT_FOUND.value());
                    return new ServiceException(HttpStatus.NOT_FOUND.value(), "Employee not found with ID: " + employeeId);
                });

        /*
         * Personal Information
         */
        EmployeeRequest.PersonalInformationDTO personal = request.getPersonalInformation();

        employee.setFirstName(personal.getFirstName());
        employee.setMiddleName(personal.getMiddleName());
        employee.setLastName(personal.getLastName());
        employee.setSuffix(personal.getSuffix());
        employee.setDateOfBirth(LocalDate.parse(personal.getBirthDate()));
        employee.setAge(BigInteger.valueOf(personal.getAge()));
        employee.setGender(personal.getGender());
        employee.setMaritalStatus(personal.getMaritalStatus());
        employee.setNationality(personal.getNationality());
        employee.setEmail(personal.getEmail());
        employee.setPhoneNumber(personal.getPhoneNumber());
        employee.setProfileImageUrl(request.getProfileImageUrl());

        /*
         * Contact Information
         */
        EmployeeRequest.ContactInformationDTO contact = request.getContactInformation();
        AppEmployeeContactInformation contactInfo = employee.getContactInformation();

        contactInfo.setContactNumber(contact.getContactNumber());
        contactInfo.setWorkEmail(contact.getWorkEmail());
        contactInfo.setEmergencyContactName(contact.getEmergencyContactName());
        contactInfo.setContactNumber(contact.getEmergencyContactNumber());
        contactInfo.setRelationship(contact.getRelationship());

        /*
         * Address
         */
        EmployeeRequest.AddressDTO addressDTO = request.getAddress();
        AppEmployeeAddress address = employee.getAddress().get(0);

        address.setBlockLot(addressDTO.getBlockLot());
        address.setStreet(addressDTO.getStreet());
        address.setSubdivision(addressDTO.getSubdivision());
        address.setBarangay(addressDTO.getBarangay());
        address.setCity(addressDTO.getCity());
        address.setProvince(addressDTO.getProvince());
        address.setCountry(addressDTO.getCountry());
        address.setZipNumber(addressDTO.getPostalCode());

        /*
         * Employment Information
         */
        EmployeeRequest.EmploymentInformationDTO employmentDTO = request.getEmploymentInformation();
        AppEmploymentInformation employment = employee.getEmploymentInformation();

        employment.setPosition(employmentDTO.getPosition());
        employment.setDateHired(employmentDTO.getDateHired());
        employment.setEmployeeId(employmentDTO.getEmployeeIdNumber());
        employment.setAddress(employmentDTO.getCompanyAddress());
        employment.setBranchAssign(employmentDTO.getBranch());
        employment.setEmploymentType(employmentDTO.getEmploymentType());
        employment.setEmploymentStatus(employmentDTO.getEmploymentStatus());
        employment.setImmediateSupervisor(employmentDTO.getImmediateSupervisor());

        /*
         * Benefits
         */
        EmployeeRequest.BenefitsDTO benefitsDTO = request.getBenefits();
        AppEmployeeBenefits benefits = employee.getEmployeeBenefits();

        benefits.setTinNumber(benefitsDTO.getTinNumber());
        benefits.setSssNumber(benefitsDTO.getSssNumber());
        benefits.setPhilHealthNumber(benefitsDTO.getPhilhealthNumber());
        benefits.setPagIbigNumber(benefitsDTO.getPagibigNumber());
        benefits.setAllowance(benefitsDTO.getAllowance());
        benefits.setRiceAllowance(benefitsDTO.getRiceAllowance());
        benefits.setTransportationAllowance(benefitsDTO.getTransportationAllowance());
        benefits.setCommunicationAllowance(benefitsDTO.getCommunicationAllowance());
        benefits.setSickLeave(benefitsDTO.getSickLeave());
        benefits.setVacationLeave(benefitsDTO.getVacationLeave());
        benefits.setPaternityLeave(benefitsDTO.getPaternityLeave());
        benefits.setMaternityLeave(benefitsDTO.getMaternityLeave());
        benefits.setHmoProvider(benefitsDTO.getHmoProvider());
        benefits.setHmoCardNumber(benefits.getHmoCardNumber());

        benefits.setHasHmo(
                benefitsDTO.getHmoProvider() != null && !benefitsDTO.getHmoProvider().isBlank()
                        && benefits.getHmoCardNumber() != null && !benefits.getHmoCardNumber().isBlank()
        );

        /*
         * Payroll Information
         */
        EmployeeRequest.PayrollInformationDTO payrollDTO = request.getPayrollInformation();
        AppPayrollInformation payroll = employee.getPayrollInformation();

        payroll.setBasicSalary(payrollDTO.getBasicSalary());
        payroll.setDailyRate(payrollDTO.getDailyRate());
        payroll.setHourlyRate(payrollDTO.getHourlyRate());
        payroll.setBankName(payrollDTO.getBankName());
        payroll.setBankAccountNumber(payrollDTO.getBankAccountNumber());
        payroll.setBankBranch(payrollDTO.getBankBranch());
        payroll.setSalaryType(payrollDTO.getSalaryType());
        payroll.setPayrollCycle(payrollDTO.getPayrollCycle());
        payroll.setEffectiveDate(payrollDTO.getEffectiveDate());

        appEmployeeProfileRepository.save(employee);

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("employee", employee);

        return CommonResponse.builder()
                .returnCode(HttpStatus.OK.value())
                .returnMessage("Employee updated successfully!")
                .responseBody(responseBody)
                .build();
    }

    public long getEmployeesCount() {
        return employeeProfileRepository.count();
    }

    public long getActiveEmployeeCount() {
        return employeeProfileRepository.countByEmploymentInformation_EmploymentStatus(EmploymentStatus.ACTIVE);
    }

    public long getOnLeaveEmployeeCount() {
        return employeeProfileRepository.countByEmploymentInformation_EmploymentStatus(EmploymentStatus.ON_LEAVE);
    }

    public long getResignedEmployeeCount() {
        return employeeProfileRepository.countByEmploymentInformation_EmploymentStatus(EmploymentStatus.RESIGNED);
    }
}
