package DataManagers;

import Data.AnnouncementsData;
import Data.DriverData;
import Server.PostgreSQLInitialization;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AnnouncementDataManager {
    private static final PostgreSQLInitialization databaseConnection = PostgreSQLInitialization.getInstance();
    public static boolean insertAnnouncement(AnnouncementsData announcementsData) {

        String query = "INSERT INTO announcements (starting_station, destination, departure_date, date_of_add_announcement, seats_available) VALUES ( ?, ?, ?, ?, ?);";

        Connection connection = databaseConnection.startConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1,announcementsData.getStartingStation());
            preparedStatement.setString(2,announcementsData.getDestination());
            preparedStatement.setDate(3,announcementsData.getDepartureDate());
            preparedStatement.setDate(4,announcementsData.getDateOfAddAnnouncement());
            preparedStatement.setInt(5,announcementsData.getSeatsAvailable());



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

    public static AnnouncementsData selectAnnouncement()
    {
        AnnouncementsData announcementsData = new AnnouncementsData();
        String query = "SELECT MAX(id) AS lastId, starting_station, destination, departure_date, seats_available FROM announcements " +
                "WHERE driver_id = ? GROUP BY starting_station, destination, departure_date, seats_available;";

        Connection connection = databaseConnection.startConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, announcementsData.getDriverId());

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
