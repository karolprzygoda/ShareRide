package DataManagers;

public class PasswordEncoderFactory {
    public static PasswordEncoder createPasswordEncoder() {
        return new Argon2PE();
    }
}