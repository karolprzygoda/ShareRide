package Server;
import java.sql.Date;
public class Register {
    public static User CreateUser(String firstName, String lastName, String email,  String phoneNumber, String gender, Date dateOfBirth, String password) {
        if (!CheckName(firstName) | !CheckLenght(firstName, 1, 20)) {
            System.out.println("Wrong firstName");
            return null;
        }
        if (!CheckName(lastName) | !CheckLenght(firstName, 1, 25)){
            System.out.println("Wrong lastName");
            return null;
        }
        if (!CheckEmail(email)) {
            System.out.println("Wrong email");
            return null;
        }
        if (!CheckLenght(phoneNumber, 9, 9)) {
            System.out.println("Wrong phoneNumber");
            return null;
        }
        if (!CheckPassword(password) | !CheckLenght(password, 8, 40)){
            System.out.println("Wrong password");
            return null;
        }
        System.out.println("User accepted");
        return new User(firstName, lastName, email,  phoneNumber, gender,dateOfBirth, password);
    }
    public static boolean CheckName(String input) {
        return input.matches("[a-zA-Z]+");
    }
    public static boolean CheckPassword(String input) {
        return input.matches("[a-zA-Z0-9!?%&#$*]+");
    }
    public static boolean CheckLenght(String login, int min, int max) {
        int length = login.length();
        return length >= min && length <= max;
    }
    public static boolean CheckEmail(String input) {
        return input.contains("@");
    }
}
