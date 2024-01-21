package DataManagers;

import Data.UserData;
import Server.PostgreSQLInitialization;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDataManager {

    private static final Logger logger = LogManager.getLogger(UserDataManager.class);

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
            logger.info("New user with email: " + userData.getEmail() + " has registered");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("Server caught an error: " + e.getCause());
            return false;
        }finally {
            databaseConnection.closeConnection();
        }
    }

    public static UserData loginUser(UserData userData) {
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
                    userData.setId(resultSet.getInt("id"));
                    return userData;
                }
                else
                    return null;

            }
            else {
                return null;
            }

        } catch (SQLException e) {
            logger.error("Server caught an error: " + e.getCause());
            return null;
        }finally {
            databaseConnection.closeConnection();
        }
    }

    public static UserData selectUserData(UserData userData) {

        String query = "SELECT " +
                "u.name, " +
                "u.last_name, " +
                "u.email, " +
                "u.phone_number, " +
                "u.gender, " +
                "u.birth_date, " +
                "u.registration_date, " +
                "COALESCE(AVG(r.rating), 0) AS average_rating " +
                "FROM users u " +
                "LEFT JOIN passengers p ON u.id = p.user_id " +
                "LEFT JOIN ratings r ON u.id = r.user_id " +
                "WHERE u.id = ? " +
                "GROUP BY u.id";

        Connection connection = databaseConnection.startConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1,userData.getId());

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
                userData.setRating(resultSet.getDouble("average_rating"));

            }

            return userData;

        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("Server caught an error: " + e.getCause());
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
                preparedStatement.setInt(9,userData.getId());

                preparedStatement.executeUpdate();
                return true;
            } catch(SQLException e){
                e.printStackTrace();
                logger.error("Server caught an error: " + e.getCause());
                return false;
             }finally {
                databaseConnection.closeConnection();
            }
        }
        else
            return false;
    }

    public static boolean deleteUser(UserData userData) {

        String query= "DELETE FROM users WHERE id = ?";

        Connection connection = databaseConnection.startConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1,userData.getId());

            preparedStatement.executeUpdate();
            logger.info("User: " + userData.getId() + " has deleted his account" );
            return true;


        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("Server caught an error: " + e.getCause());
            return false;
        }finally {
            databaseConnection.closeConnection();
        }
    }
}
