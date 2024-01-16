package DataManagers;

import Data.AnnouncementsData;
import Data.PassengersData;
import Data.UserData;
import Server.PostgreSQLInitialization;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PassengersDataManager {
    private static final PostgreSQLInitialization databaseConnection = PostgreSQLInitialization.getInstance();
    public static boolean insertPassenger(PassengersData passengersData) {

        String query = "INSERT INTO passengers (announcements_id, user_id) VALUES ( ?, ?);";

        Connection connection = databaseConnection.startConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1,passengersData.getAnnouncementId());
            preparedStatement.setInt(2,passengersData.getUserId());

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

    public static ArrayList<UserData> selectPassengers(PassengersData passengersData)
    {
        ArrayList<UserData> listData = new ArrayList<>();

        String query = "SELECT name, last_name, email, phone_number, gender,EXTRACT(YEAR FROM AGE(NOW(), users.birth_date)) AS age " +
                        "FROM users " +
                        "JOIN passengers ON users.id = passengers.user_id WHERE passengers.announcements_id = ?";

        Connection connection = databaseConnection.startConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, passengersData.getAnnouncementId());

            ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next())
                {
                    UserData userData = new UserData();

                   userData.setName(resultSet.getString("name"));
                   userData.setLastName(resultSet.getString("last_name"));
                   userData.setEmail(resultSet.getString("email"));
                   userData.setPhoneNumber(resultSet.getString("phone_number"));
                   userData.setGender(resultSet.getString("gender"));
                   userData.setAge(resultSet.getInt("age"));

                   listData.add(userData);

                }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Błąd: " + e.getMessage());
        }finally {
            databaseConnection.closeConnection();
        }

        return listData;
    }

    public static boolean checkIfPassengerHasAlreadyJoinedTheRide(PassengersData passengersData){


        String query = "SELECT id FROM passengers WHERE user_id = ? AND announcements_id = ?";

        Connection connection = databaseConnection.startConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, passengersData.getUserId());
            preparedStatement.setInt(2,passengersData.getAnnouncementId());

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next())
            {
                passengersData.setId(resultSet.getInt("id"));

                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Błąd: " + e.getMessage());
        }finally {
            databaseConnection.closeConnection();
        }

        return false;

    }


    /*public static boolean deleteAnnouncement() { //TODO

        String query= "DELETE FROM announcements WHERE id = ?";

        Connection connection = databaseConnection.startConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1,UserDataManager.userId);

            preparedStatement.executeUpdate();
            return true;


        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Błąd SQL: " + e.getMessage());
            return false;
        }finally {
            databaseConnection.closeConnection();
        }
    }*/
}
