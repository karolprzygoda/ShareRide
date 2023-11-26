package Server;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
public interface Verification {
    static User CreateUser(String firstName, String lastName, String email, String phoneNumber, String gender, Date dateOfBirth, String password) {
        if (HaveIllegalChar(firstName) | IsLongerThan(firstName, 1, 30)) {
            System.out.println("Wrong firstName");
            Logs.writeLog("ServerVerification", "Wrong firstName");
            return null;
        }
        if (HaveIllegalChar(lastName) | IsLongerThan(firstName, 1, 45)){
            System.out.println("Wrong lastName");
            Logs.writeLog("ServerVerification", "Wrong lastName");
            return null;
        }
        if (!IsMailValid(email) | IsLongerThan(email,1,50)) {
            System.out.println("Wrong email");
            Logs.writeLog("ServerVerification", "Wrong email");
            return null;
        }
        if (!IsPhoneNumberValid(phoneNumber)) {
            System.out.println("Wrong phoneNumber");
            Logs.writeLog("ServerVerification", "Wrong phoneNumber");
            return null;
        }
        if (!IsPasswordValid(password) | IsLongerThan(password,1,50)) {
            System.out.println("Wrong password");
            Logs.writeLog("ServerVerification", "Wrong password");
            return null;
        }
        if (!IsAgeValid(dateOfBirth)) {
            System.out.println("Wrong age");
            Logs.writeLog("ServerVerification", "Wrong age");
            return null;
        }
        System.out.println("User accepted");
        String encodePassword = Password.encodePassword(password);
        Logs.writeLog("ServerVerification", "User accepted");
        return new User(firstName, lastName, email,  phoneNumber, gender,dateOfBirth, encodePassword);
    }
    static boolean HaveIllegalChar(String input) {
        return input.matches("\".*[*!;@#$%^&()-=+{}|:',.<>/?].*\"");
    }
    static boolean IsLongerThan(String login, int min, int max) {
        int length = login.length();
        return length < min && length > max;
    }
    static boolean IsPasswordValid(String input) {
        return input.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#!$%^&-+=()])(?=\\S+$).{8,20}$");
    }
    static boolean IsMailValid(String input) {
        return input.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}");
    }
    static boolean IsPhoneNumberValid(String input) {
        return input.matches("\\d{9}");
    }
    static boolean IsAgeValid(Date birthDate) {
        LocalDate currentDate = LocalDate.now();
        LocalDate localBirthDate = birthDate.toLocalDate();
        Period age = Period.between(localBirthDate, currentDate);
        return age.getYears() >= 18 && age.getYears() <= 120;
    }

}
