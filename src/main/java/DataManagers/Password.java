package DataManagers;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

public class Password {

    static final Argon2PasswordEncoder encoder = new Argon2PasswordEncoder(16, 32, 1, 4096, 3);

    static String encodePassword(String password) {
        return encoder.encode(password);
    }

    static boolean matchPassword(String rawPassword, String encodedPassword) {
        return encoder.matches(rawPassword, encodedPassword);
    }
}