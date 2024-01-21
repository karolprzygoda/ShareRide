package DataManagers;
import Data.UserData;
import Server.PostgreSQLInitialization;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.time.Period;

public class Verification {

    private static final Logger logger = LogManager.getLogger(Verification.class);

    private static final PostgreSQLInitialization databaseConnection = PostgreSQLInitialization.getInstance();
    public static boolean verify(UserData userData) {
        if(IsFirstNameValid(userData.getName()) &
                IsLastNameValid(userData.getLastName()) &
                IsPasswordValid(userData.getPassword()) &
                IsMailValid(userData.getEmail()) &
                IsPhoneNumberValid(userData.getPhoneNumber()) &
                IsAgeValid(Date.valueOf(userData.getBirthDate().toString())))
        {
            return true;
        }
        else {
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

        Connection connection = databaseConnection.startConnection();

        String sql = "SELECT id FROM users WHERE email = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, input);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                isMailRegistered = resultSet.next();
                if (isMailRegistered) {


                } else {

                }
            }
        } catch (SQLException e) {
            logger.error("Server caught an error: " + e.getCause());
            isMailRegistered = true;
        }finally {
            databaseConnection.closeConnection();
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
        return input.matches(".*[*!;@#$%^&()-=+{}|:',.<>/?].*");
    }
    static boolean IsLongerThan(String login, int min, int max) {
        int length = login.length();
        return length < min && length > max;
    }

}
