package ph.com.tteh.utils;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Slf4j
public class LocalDateUtils {

    private LocalDateUtils() {
        throw new IllegalStateException("Object Utility class");
    }

    public static LocalDate getLocalDate() {
        LocalDate localDate = LocalDate.now(ZoneId.systemDefault());
        log.info("Current Local Date: {}", localDate);
        return localDate;
    }

    public static LocalDateTime getLocalDateTime() {
        ZoneId zoneId = ZoneId.of("Asia/Manila");
        LocalDateTime localDateTime = LocalDateTime.now(zoneId);
        log.info("Current Local DateTime: {}", localDateTime);
        return localDateTime;
    }
}
