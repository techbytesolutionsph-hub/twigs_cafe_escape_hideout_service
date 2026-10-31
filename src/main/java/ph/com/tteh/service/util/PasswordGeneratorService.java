package ph.com.tteh.service.util;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PasswordGeneratorService {

    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL = "@#$%&*!?";
    private static final String ALL = UPPER + LOWER + DIGITS + SPECIAL;

    private final SecureRandom random = new SecureRandom();

    public String generatePassword() {
        return generatePassword(12);
    }

    public String generatePassword(int length) {

        if (length < 8) {
            throw new IllegalArgumentException("Password length must be at least 8.");
        }

        StringBuilder password = new StringBuilder();

        password.append(randomChar(UPPER));
        password.append(randomChar(LOWER));
        password.append(randomChar(DIGITS));
        password.append(randomChar(SPECIAL));

        for (int i = 4; i < length; i++) {
            password.append(randomChar(ALL));
        }

        List<Character> chars = password.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toList());

        Collections.shuffle(chars, random);

        StringBuilder result = new StringBuilder();

        chars.forEach(result::append);

        return result.toString();
    }

    private char randomChar(String source) {
        return source.charAt(random.nextInt(source.length()));
    }
}