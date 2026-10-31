package ph.com.tteh.service.util;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Component("enumUtil")
public class EnumUtil {

    public String formatEnum(Enum<?> value) {
        if (value == null) {
            return "N/A";
        }

        return Arrays.stream(value.name().split("_"))
                .map(word -> word.substring(0, 1).toUpperCase()
                        + word.substring(1).toLowerCase())
                .collect(Collectors.joining(" "));
    }
}