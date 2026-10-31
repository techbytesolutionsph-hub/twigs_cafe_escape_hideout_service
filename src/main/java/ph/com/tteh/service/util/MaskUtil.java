package ph.com.tteh.service.util;

import org.springframework.stereotype.Component;

@Component("maskUtil")
public class MaskUtil {

    public String maskBankAccount(String accountNumber) {
        if (accountNumber == null || accountNumber.length() <= 4) {
            return accountNumber;
        }

        return "*".repeat(accountNumber.length() - 4)
                + accountNumber.substring(accountNumber.length() - 4);
    }
}
