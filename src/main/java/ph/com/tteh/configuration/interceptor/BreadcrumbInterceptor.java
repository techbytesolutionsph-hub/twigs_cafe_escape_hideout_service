package ph.com.tteh.configuration.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Component
public class BreadcrumbInterceptor implements HandlerInterceptor {

    private static final String BASE_PATH = "/app/portal";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        request.setAttribute("CURRENT_URI", request.getRequestURI());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) {

        if (modelAndView == null) return;

        String uri = request.getRequestURI();
        List<Map<String, String>> breadcrumbs = buildBreadcrumbs(uri);
        modelAndView.addObject("breadcrumbs", breadcrumbs);
    }

    private List<Map<String, String>> buildBreadcrumbs(String uri) {

        List<Map<String, String>> breadcrumbs = new ArrayList<>();

        breadcrumbs.add(Map.of("name", "Home","url", BASE_PATH + "/dashboard"));

        String[] parts = uri.split("/");
        StringBuilder path = new StringBuilder(BASE_PATH);

        for (int i = 3; i < parts.length; i++) {

            if (parts[i].isBlank()) continue;

            path.append("/").append(parts[i]);

            String segment = parts[i];

            try {
                if (i > 3) {
                    String previousLabel = toLabel(parts[i - 1]);

                    if ("View Order".equalsIgnoreCase(previousLabel) || "Assign Courier".equalsIgnoreCase(previousLabel)) {
                        segment = new String(Base64.getDecoder().decode(parts[i]));
                    }
                }
            } catch (Exception ignored) {
                // fallback to original if not base64
            }

            breadcrumbs.add(Map.of(
                    "name", toLabel(segment),
                    "url", path.toString()
            ));
        }

        return breadcrumbs;
    }

    private String toLabel(String segment) {

        String label = Arrays.stream(segment.split("-"))
                .map(s -> Character.toUpperCase(s.charAt(0)) + s.substring(1))
                .reduce((a, b) -> a + " " + b)
                .orElse(segment);

        /* limit to max 3 words */
        String[] words = label.split("\\s+");

        if (words.length > 4) {
            label = String.join(" ",
                    Arrays.copyOfRange(words, 0, 4));
        }

        /* limit overall length to 20 chars */
        if (label.length() > 30) {
            label = label.substring(0, 30).trim() + "...";
        }

        return label;
    }
}