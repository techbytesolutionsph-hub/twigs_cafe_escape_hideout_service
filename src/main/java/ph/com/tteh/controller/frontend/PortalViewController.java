package ph.com.tteh.controller.frontend;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ph.com.tteh.dto.admin.AppUserResponse;
import ph.com.tteh.dto.hris.ApplicantResponse;
import ph.com.tteh.dto.response.DashboardMetricsResponse;
import ph.com.tteh.exception.ServiceException;
import ph.com.tteh.service.api.front.PortalFrontService;
import ph.com.tteh.service.util.logging.LoggingService;
import ph.com.tteh.service.util.uuid.GenerateUUIDService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/app/portal")
public class PortalViewController {

    private final PortalFrontService portalFrontService;
    private final GenerateUUIDService generateUUIDService;
    private final LoggingService loggingService;

    @GetMapping(value = "/login")
    public String loginPage(HttpSession session, Model model){
        model.addAttribute("page", "login");
        return "pages/auth/index";
    }

    @GetMapping(value = "/logout")
    public String logoutPage(HttpServletRequest request) {
        SecurityContextHolder.clearContext();
        request.getSession().invalidate();
        return "redirect:pages/auth/index";
    }

    @GetMapping(value = "/dashboard")
    public String dashboardPage(HttpSession session, Model model) {
        this.setupPage(model, "dashboard", "Dashboard", "");
        model.addAttribute("title",  "Dashboard | The Twigs");
        return "pages/portal/dashboard/index";
    }

    /* User Management - START */

    @GetMapping(value = "/admin/user-management")
    public String userManagementPage(HttpSession session, Model model) {
        this.setupPage(model, "admin", "User Management", "");
        model.addAttribute("title",  "User Management | The Twigs");

        List<AppUserResponse> users = portalFrontService.getAllUsers();
        model.addAttribute("users", users);

        return "pages/portal/admin/user-management/index";
    }

    @GetMapping(value = "/admin/user-management/add-user-account")
    public String addUserAccountPage(HttpSession session, Model model) {
        this.setupPage(model, "admin", "Add User Account",
                "Create user accounts, assign roles, and manage system access.");
        model.addAttribute("title",  "Add User Account | The Twigs");

        return "pages/portal/admin/user-management/add/index";
    }

    @GetMapping(value = "/admin/user-management/edit-user-account")
    public String editUserAccountPage(Model model, @RequestParam("id") String username) throws ServiceException {
        String uuid = generateUUIDService.generateUUID();
        this.setupPage(model, "admin", "Edit User Account",
                "Modify user accounts, assign roles, and manage system access.");
        model.addAttribute("title",  "Edit User Account | The Twigs");

        loggingService.info(uuid, this.getClass().getName(), "", "username : " + username);
        AppUserResponse user = portalFrontService.findByUsername(username);
        model.addAttribute("user", user);

        return "pages/portal/admin/user-management/update/index";
    }

    /* User Management - END */
    @GetMapping(value = "/hr-recruitment")
    public String hrRecruitmentDashboardPage(Model model) {
        this.setupPage(model, "recruitment", "Recruitment Dashboard",
                "Oversee recruitment activities, monitor hiring progress, and track candidate applications.");

        return "pages/portal/hr-admin/recruitment/dashboard/index";
    }

    @GetMapping(value = "/hr-recruitment/candidates")
    public String candidatePage(Model model) {
        this.setupPage(model, "recruitment", "Candidates",
                "Manage applicants throughout the recruitment and selection process.");

        List<ApplicantResponse> applicants = portalFrontService.getAllApplicants();
        model.addAttribute("applicants", applicants);

        DashboardMetricsResponse kpi = portalFrontService.candidateKPIs();
        model.addAttribute("kpi", kpi);

        return "pages/portal/hr-admin/recruitment/candidate/index";
    }

    @GetMapping(value = "/hr-recruitment/candidates/add-candidate")
    public String addCandidatePage(Model model) {
        this.setupPage(model, "recruitment", "Add Candidate",
                "Manage candidates and track recruitment progress.");

        return "pages/portal/hr-admin/recruitment/candidate/add/index";
    }

    @GetMapping(value = "/hr-recruitment/candidates/edit-candidate")
    public String editCandidatePage(Model model, @RequestParam("id") Long applicantId) throws ServiceException {
        this.setupPage(model, "recruitment", "Edit Candidate",
                "Update candidate details and maintain accurate recruitment records.");

        ApplicantResponse applicant = portalFrontService.getApplicant(applicantId);
        loggingService.info("", this.getClass().getName(), "", "applicant : " + applicant);
        model.addAttribute("applicant", applicant);

        return "pages/portal/hr-admin/recruitment/candidate/edit/index";
    }
    /* HR Management - END */

    private void setupPage(Model model, String nav, String pageTitle, String pageSubTitle) {
        model.addAttribute("nav", nav);
        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("pageSubTitle", pageSubTitle);
    }
}
