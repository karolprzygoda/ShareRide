package DataManagers;

import Data.UserData;
import Server.PostgreSQLInitialization;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDataManager {

    protected static int userId;

    private static String password;

    private static final PostgreSQLInitialization databaseConnection = PostgreSQLInitialization.getInstance();

    public static boolean registerUser(UserData userData) {

        PasswordEncoder passwordEncoder = PasswordEncoderFactory.createPasswordEncoder();
        String encodePassword;

        String query = "INSERT INTO users (name, last_name, email, password, phone_number, gender, birth_date, registration_date) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        Connection connection = databaseConnection.startConnection();

        if (Verification.verify(userData)) {
           encodePassword = passwordEncoder.encodePassword(userData.getPassword());
        }
        else {
            return false;
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, userData.getName());
            preparedStatement.setString(2, userData.getLastName());
            preparedStatement.setString(3, userData.getEmail());
            preparedStatement.setString(4, encodePassword);
            preparedStatement.setString(5, userData.getPhoneNumber());
            preparedStatement.setString(6, userData.getGender());
            preparedStatement.setDate(7, userData.getBirthDate());
            preparedStatement.setDate(8, userData.getRegisterDate());


            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Błąd: " + e.getMessage());
            return false;
        }finally {
            databaseConnection.closeConnection();
        }
    }

    public static boolean loginUser(UserData userData) {
        String sql = "SELECT id , password FROM users WHERE email = ?";

        Connection connection = databaseConnection.startConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, userData.getEmail());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String storedPassword = resultSet.getString("password");
                password = storedPassword;

                if(Password.matchPassword(userData.getPassword(), storedPassword))
                {
                    userId = resultSet.getInt("id");
                    return  true;
                }
                else
                    return false;

            }
            else {
                System.out.println("Błąd logowania. Nieprawidłowe dane.");
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally {
            databaseConnection.closeConnection();
        }
    }

    public static UserData selectUserData() {

        UserData userData = new UserData();

        String query = "SELECT name, last_name, email, phone_number, gender, birth_date, registration_date FROM users WHERE id = ?;";

        Connection connection = databaseConnection.startConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1,userId);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next())
            {
                userData.setName(resultSet.getString("name"));
                userData.setLastName(resultSet.getString("last_name"));
                userData.setEmail(resultSet.getString("email"));
                userData.setPhoneNumber(resultSet.getString("phone_number"));
                userData.setGender(resultSet.getString("gender"));
                userData.setBirthDate(resultSet.getDate("birth_date"));
                userData.setRegisterDate(resultSet.getDate("registration_date"));

            }

            return userData;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Błąd: " + e.getMessage());
            return null;
        }finally {
            databaseConnection.closeConnection();
        }
    }

    public static boolean updateUserData(UserData userData) {

        String query = "UPDATE users SET name = ?, last_name = ?, email = ?, password = ?, phone_number = ?, " +
                "gender = ?, birth_date = ?, registration_date = ? WHERE id = ?";

        String encodePassword;
        PasswordEncoder passwordEncoder = PasswordEncoderFactory.createPasswordEncoder();

        Connection connection = databaseConnection.startConnection();


        if (userData.getName() != null && userData.getLastName() != null && userData.getEmail() != null
                && userData.getPhoneNumber() != null && userData.getGender() != null
                && userData.getBirthDate() != null && userData.getRegisterDate() != null) {

            if(userData.getPassword() == null)
            {
                encodePassword = password;
            }else{
                if (Verification.verify(userData)) {
                    encodePassword = passwordEncoder.encodePassword(userData.getPassword());
                }
                else {
                    return false;
                }
            }

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1,userData.getName());
                preparedStatement.setString(2,userData.getLastName());
                preparedStatement.setString(3,userData.getEmail());
                preparedStatement.setString(4,encodePassword);
                preparedStatement.setString(5,userData.getPhoneNumber());
                preparedStatement.setString(6,userData.getGender());
                preparedStatement.setDate(7,userData.getBirthDate());
                preparedStatement.setDate(8,userData.getRegisterDate());
                preparedStatement.setInt(9,userId);

                preparedStatement.executeUpdate();
                return true;
            } catch(SQLException e){
                e.printStackTrace();
                System.out.println("Błąd: " + e.getMessage());
                return false;
             }finally {
                databaseConnection.closeConnection();
            }
        }
        else
            return false;
    }

    public static boolean deleteUser() {

        String query= "DELETE FROM users WHERE id = ?";

        Connection connection = databaseConnection.startConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1,userId);

            preparedStatement.executeUpdate();
            return true;


        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Błąd SQL: " + e.getMessage());
            return false;
        }finally {
            databaseConnection.closeConnection();
        }
    }
}
