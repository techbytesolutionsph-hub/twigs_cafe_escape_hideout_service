package ph.com.tteh.controller.frontend;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ph.com.tteh.service.util.logging.LoggingService;
import ph.com.tteh.service.util.uuid.GenerateUUIDService;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/app/portal")
public class PortalViewController {

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
        model.addAttribute("title",  "Dashboard | The Twigs");
        return "pages/portal/dashboard/index";
    }

    private void setupPage(Model model, String nav, String pageTitle) {
        model.addAttribute("page", "seller");
        model.addAttribute("nav", nav);
        model.addAttribute("pageTitle", pageTitle);
    }
}
