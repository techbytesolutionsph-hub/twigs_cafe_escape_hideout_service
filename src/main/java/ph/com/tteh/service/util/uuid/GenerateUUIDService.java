package ph.com.tteh.service.util.uuid;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GenerateUUIDService {

    /**
     * Generate a random UUID (version 4)
     * @return UUID as a string
     */
    public String generateUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * Generate UUID without dashes
     * @return UUID string without "-"
     */
    public String generateCompactUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
