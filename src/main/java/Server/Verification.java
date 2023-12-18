package Server;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
public class Verification {
    public static User CreateUser(String firstName, String lastName, String email, String phoneNumber, String gender, Date dateOfBirth, String password, Date joinDate) {
        if(IsFirstNameValid(firstName) & IsLastNameValid(lastName) & IsPasswordValid(password) & IsMailValid(email)
                & IsPhoneNumberValid(phoneNumber) & IsAgeValid(dateOfBirth))
        {
            System.out.println("User accepted");
            PasswordEncoder passwordEncoder = PasswordEncoderFactory.createPasswordEncoder();
            String encodePassword = passwordEncoder.encodePassword(password);
            Logs.writeLog("ServerVerification", "User accepted");
            return new User(firstName, lastName, email,  phoneNumber, gender,dateOfBirth, encodePassword, joinDate);
        }
        else {
            System.out.println("Verification did not pass");
            Logs.writeLog("ServerVerification", "Verification did not pass");
            return null;
        }
    }
    static boolean IsFirstNameValid(String input) {
        return !HaveIllegalChar(input) & !IsLongerThan(input, 1, 30);
    }
    static boolean IsLastNameValid(String input) {
        return !HaveIllegalChar(input) & !IsLongerThan(input, 1, 45);
    }
    static boolean IsPasswordValid(String input) {
        return input.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#!$%^&-+=()])(?=\\S+$).{8,20}$");
    }
    static boolean IsMailValid(String input) {
        boolean validMail = input.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}");
        return validMail & !IsLongerThan(input,1,50);
    }
    static boolean IsPhoneNumberValid(String input) {
        return input.matches("\\d{9}");
    }
    static boolean IsAgeValid(Date birthDate) {
        if(birthDate != null)
        {
            LocalDate currentDate = LocalDate.now();
            LocalDate localBirthDate = birthDate.toLocalDate();
            Period age = Period.between(localBirthDate, currentDate);
            return age.getYears() >= 18 && age.getYears() <= 120;
        }
        else
            return true;
    }
    static boolean HaveIllegalChar(String input) {
        return input.matches(".*[\\*!;@#$%^&()-=+{}|:',.<>/?].*");
    }
    static boolean IsLongerThan(String login, int min, int max) {
        int length = login.length();
        return length < min && length > max;
    }
}
