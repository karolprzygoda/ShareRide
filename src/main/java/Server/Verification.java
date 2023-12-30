package Server;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

public class Verification {
    public static boolean verify(List<Object> fields) {
        if(IsFirstNameValid(fields.get(0).toString()) & IsLastNameValid(fields.get(1).toString()) & IsPasswordValid(fields.get(6).toString()) & IsMailValid(fields.get(2).toString())
                & IsPhoneNumberValid(fields.get(3).toString()) & IsAgeValid(Date.valueOf(fields.get(5).toString())))
        {
            System.out.println("User accepted");
            Logs.writeLog("ServerVerification", "User accepted");
            return true;
        }
        else {
            System.out.println("Verification did not pass");
            Logs.writeLog("ServerVerification", "Verification did not pass");
            return false;
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
        boolean isMailRegistered;

        String sql = "SELECT id_uzytkownika FROM users WHERE email = ?";
        try (PreparedStatement preparedStatement = PostgreSQLInitialization.getInstance().connection.prepareStatement(sql)) {
            preparedStatement.setString(1, input);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                isMailRegistered = resultSet.next();
                if (isMailRegistered) {
                    System.out.println("Email w użyciu!.");
                    //log.writeLog("Error, registering mail found in database");

                } else {
                    System.out.println("Email wolny!");
                    //log.writeLog("Registering mail not found in database");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Błędny email: " + e.getMessage());
            isMailRegistered = true;
        }

        return validMail & !IsLongerThan(input,1,50) & !isMailRegistered;
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
