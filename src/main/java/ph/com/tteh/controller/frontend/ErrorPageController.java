package ph.com.tteh.controller.frontend;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.webmvc.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import ph.com.tteh.service.util.logging.LoggingService;
import ph.com.tteh.service.util.uuid.GenerateUUIDService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/app/portal/error")
public class ErrorPageController implements ErrorController {

    private final LoggingService loggingService;
    private final GenerateUUIDService generateUUIDService;

    @RequestMapping("/error-page")
    public String handleError(HttpServletRequest request, Model model) {
        String uuid = generateUUIDService.generateUUID();
        Integer statusCode = (Integer) request.getAttribute("jakarta.servlet.error.status_code");
        String errorMessage = (String) request.getAttribute("jakarta.servlet.error.message");
        loggingService.info(uuid, this.getClass().getName(), "", "statusCode : " + statusCode);

        HttpStatus status = HttpStatus.resolve(statusCode != null ? statusCode : 500);

        model.addAttribute("title", "Error " + status.value() + " | The Twigs");
        model.addAttribute("errorCode", status.value());
        model.addAttribute("errorMessage", status.getReasonPhrase());

        model.addAttribute("errorDescription",
                errorMessage != null && !errorMessage.isBlank()
                        ? errorMessage
                        : getDescription(String.valueOf(status.value())));

        return "pages/error/index";
    }

    private String getDescription(String code) {

        return switch (code) {
            case "400" -> "The server could not understand your request.";
            case "401" -> "You must log in to access this page.";
            case "403" -> "Sorry, you do not have permission to access this page.";
            case "404" -> "Sorry, the page you are looking for does not exist.";
            case "405" -> "The HTTP method is not supported.";
            case "408" -> "Your request took too long to process.";
            case "500" -> "Oops! Something went wrong on our end.";
            case "502" -> "Invalid response from upstream server.";
            case "503" -> "Service temporarily unavailable.";
            case "504" -> "Server response timeout.";
            default -> "An unexpected error occurred.";
        };
    }
}