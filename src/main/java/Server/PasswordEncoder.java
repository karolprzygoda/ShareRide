package Server;

public interface PasswordEncoder {

    String encodePassword(String password);

    boolean matchPassword(String rawPassword, String encodedPassword);
}