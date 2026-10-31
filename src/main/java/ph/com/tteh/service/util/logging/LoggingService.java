package ph.com.tteh.service.util.logging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ph.com.tteh.enums.Logger;

@Service
@Slf4j
public class LoggingService {

    public void info(String uuid, String component, String message, String info) {
        log.info(String.format(Logger.INFO_LOG.getLogStr(), uuid, component, message, info));
    }

    public void error(String uuid, String component, String errorMsg, int status) {
        log.error(String.format(Logger.ERROR_LOG.getLogStr(), uuid, component, errorMsg, status));
    }
}
