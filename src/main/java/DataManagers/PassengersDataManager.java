package DataManagers;

import Data.AnnouncementsData;
import Data.PassengersData;
import Server.PostgreSQLInitialization;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    public static AnnouncementsData selectAnnouncement()//TODO
    {
        AnnouncementsData announcementsData = new AnnouncementsData();
        String query = "SELECT MAX(id) AS lastId, starting_station, destination, departure_date, seats_available FROM announcements " +
                "WHERE driver_id = ? GROUP BY starting_station, destination, departure_date, seats_available;";

        Connection connection = databaseConnection.startConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, UserDataManager.userId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next())
                {
                    announcementsData.setId(resultSet.getInt("lastId"));
                    announcementsData.setStartingStation(resultSet.getString("starting_station"));
                    announcementsData.setDestination(resultSet.getString("destination"));
                    announcementsData.setDepartureDate(resultSet.getDate("departure_date"));
                    announcementsData.setSeatsAvailable(resultSet.getInt("seats_available"));

                    return announcementsData;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Błąd: " + e.getMessage());
        }finally {
            databaseConnection.closeConnection();
        }

        return null;
    }

    public static boolean deleteAnnouncement() { //TODO

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
    }
}
