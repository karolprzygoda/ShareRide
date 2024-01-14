package DataManagers;

import DataManagers.PasswordEncoder;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

public class Argon2PE implements PasswordEncoder {

    private static final Argon2PasswordEncoder encoder = new Argon2PasswordEncoder(16, 32, 1, 4096, 3);

    @Override
    public String encodePassword(String password) {
        return encoder.encode(password);
    }

    @Override
    public boolean matchPassword(String rawPassword, String encodedPassword) {
        return encoder.matches(rawPassword, encodedPassword);
    }
}
