package ph.com.tteh.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.nio.CharBuffer;

@Component
@RequiredArgsConstructor
public class BCryptUtils {

    private final PasswordEncoder encoder;

    public String encodePassword(String password){
        return encoder.encode(CharBuffer.wrap(password));
    }

    public boolean passwordMatches(String rawPassword, String encodedPassword){
        return encoder.matches(CharBuffer.wrap(rawPassword), encodedPassword);
    }

}
