package ph.com.tteh.controller.frontend;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ph.com.tteh.dto.admin.AppUserResponse;
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
//        SecurityContextHolder.clearContext();
        request.getSession().invalidate();
        return "redirect:pages/auth/index";
    }

    @GetMapping(value = "/dashboard")
    public String dashboardPage(HttpSession session, Model model) {
        this.setupPage(model, "dashboard", "Dashboard");
        model.addAttribute("title",  "Dashboard | The Twigs");
        return "pages/portal/dashboard/index";
    }

    @GetMapping(value = "/admin/user-management")
    public String userManagementPage(HttpSession session, Model model) {
        this.setupPage(model, "admin", "User Management");
        model.addAttribute("title",  "User Management | The Twigs");

        List<AppUserResponse> users = portalFrontService.getAllUsers();
        model.addAttribute("users", users);

        return "pages/portal/admin/user-management/index";
    }

    @GetMapping(value = "/admin/user-management/add-user-account")
    public String addUserAccountPage(HttpSession session, Model model) {
        this.setupPage(model, "admin", "Add User Account");
        model.addAttribute("title",  "Add User Account | The Twigs");

        List<AppUserResponse> users = portalFrontService.getAllUsers();
        model.addAttribute("users", users);

        return "pages/portal/admin/user-management/add/index";
    }


    private void setupPage(Model model, String nav, String pageTitle) {
        model.addAttribute("nav", nav);
        model.addAttribute("pageTitle", pageTitle);
    }
}
