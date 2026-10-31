package ph.com.tteh.exception;

import lombok.Getter;

@Getter
public class ServiceException extends Exception {

    private final int code;

    public ServiceException(int code, String message) {
        super(message);
        this.code = code;
    }

    public ServiceException(String message) {
        super(message);
        this.code = 500;
    }
}
