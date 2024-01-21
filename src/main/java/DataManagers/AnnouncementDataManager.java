package DataManagers;

import ChainOfResponsibility.Request;
import Data.AnnouncementsData;
import Data.DriverData;
import Data.UserData;
import Server.ClientHandler;
import Server.PostgreSQLInitialization;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AnnouncementDataManager {
    private static final Logger logger = LogManager.getLogger(AnnouncementDataManager.class);
    private static final PostgreSQLInitialization databaseConnection = PostgreSQLInitialization.getInstance();

    public static boolean insertAnnouncement(AnnouncementsData announcementsData) {

        String query = "INSERT INTO announcements (starting_station, driver_id, destination, departure_date, date_of_add_announcement, seats_available) VALUES ( ?, ?, ?, ?, ?, ?);";

        Connection connection = databaseConnection.startConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1,announcementsData.getStartingStation());
            preparedStatement.setInt(2,announcementsData.getDriverId());
            preparedStatement.setString(3,announcementsData.getDestination());
            preparedStatement.setDate(4,announcementsData.getDepartureDate());
            preparedStatement.setDate(5,announcementsData.getDateOfAddAnnouncement());
            preparedStatement.setInt(6,announcementsData.getSeatsAvailable());



            preparedStatement.executeUpdate();
            logger.info("New announcement has been added from driver: " + announcementsData.getDriverId());
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("Server caught an error: " + e.getCause());
            return false;
        }finally {
            databaseConnection.closeConnection();
        }

    }

    public static ArrayList<AnnouncementsData> selectAllAnnouncements(){
        String query = "SELECT * FROM announcements WHERE seats_available > 0 AND departure_date >= CURRENT_DATE";

        Connection connection = databaseConnection.startConnection();

        ArrayList<AnnouncementsData> listData = new ArrayList<>();


        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){

                AnnouncementsData announcementsData = new AnnouncementsData();

                announcementsData.setId(resultSet.getInt("id"));
                announcementsData.setDriverId(resultSet.getInt("driver_id"));
                announcementsData.setStartingStation(resultSet.getString("starting_station"));
                announcementsData.setDestination(resultSet.getString("destination"));
                announcementsData.setDepartureDate(resultSet.getDate("departure_date"));
                announcementsData.setSeatsAvailable(resultSet.getInt("seats_available"));
                announcementsData.setDateOfAddAnnouncement(resultSet.getDate("date_of_add_announcement"));

                listData.add(announcementsData);
            }


        }catch (SQLException e){
            e.printStackTrace();
            logger.error("Server caught an error: " + e.getCause());
        }finally {
            databaseConnection.closeConnection();
        }

        return listData;
    }

    public static AnnouncementsData selectAnnouncement (AnnouncementsData announcementsData)
    {
        String query = "SELECT id, starting_station, destination, departure_date, seats_available FROM announcements " +
                        "WHERE driver_id = ? ORDER BY id DESC LIMIT 1";

        Connection connection = databaseConnection.startConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, announcementsData.getDriverId());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next())
                {
                    announcementsData.setId(resultSet.getInt("id"));
                    announcementsData.setStartingStation(resultSet.getString("starting_station"));
                    announcementsData.setDestination(resultSet.getString("destination"));
                    announcementsData.setDepartureDate(resultSet.getDate("departure_date"));
                    announcementsData.setSeatsAvailable(resultSet.getInt("seats_available"));

                    return announcementsData;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("Server caught an error: " + e.getCause());
        }finally {
            databaseConnection.closeConnection();
        }

        return null;
    }

    public static  ArrayList<AnnouncementsData> selectUserIncomingRides (UserData userData)
    {
        String query = "SELECT * FROM announcements "
                + "JOIN passengers ON announcements.id = passengers.announcements_id "
                + "WHERE announcements.departure_date >= CURRENT_DATE AND passengers.user_id = ?";


        ArrayList<AnnouncementsData> listData = new ArrayList<>();

        Connection connection = databaseConnection.startConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userData.getId());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next())
                {
                    AnnouncementsData announcementsData = new AnnouncementsData();

                    announcementsData.setId(resultSet.getInt("id"));
                    announcementsData.setStartingStation(resultSet.getString("starting_station"));
                    announcementsData.setDestination(resultSet.getString("destination"));
                    announcementsData.setDepartureDate(resultSet.getDate("departure_date"));
                    announcementsData.setDateOfAddAnnouncement(resultSet.getDate("date_of_add_announcement"));
                    announcementsData.setSeatsAvailable(resultSet.getInt("seats_available"));


                    listData.add(announcementsData);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("Server caught an error: " + e.getCause());
        }finally {
            databaseConnection.closeConnection();
        }

        return listData;
    }

    public static  ArrayList<AnnouncementsData> selectUserFinishedRides (UserData userData)
    {
        String query = "SELECT * FROM announcements "
                + "JOIN passengers ON announcements.id = passengers.announcements_id "
                + "WHERE announcements.departure_date < CURRENT_DATE AND passengers.user_id = ?";


        ArrayList<AnnouncementsData> listData = new ArrayList<>();

        Connection connection = databaseConnection.startConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userData.getId());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next())
                {
                    AnnouncementsData announcementsData = new AnnouncementsData();

                    announcementsData.setId(resultSet.getInt("id"));
                    announcementsData.setStartingStation(resultSet.getString("starting_station"));
                    announcementsData.setDestination(resultSet.getString("destination"));
                    announcementsData.setDepartureDate(resultSet.getDate("departure_date"));
                    announcementsData.setDateOfAddAnnouncement(resultSet.getDate("date_of_add_announcement"));
                    announcementsData.setSeatsAvailable(resultSet.getInt("seats_available"));


                    listData.add(announcementsData);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("Server caught an error: " + e.getCause());
        }finally {
            databaseConnection.closeConnection();
        }

        return listData;
    }


    public static boolean updateSeatsAvailable(AnnouncementsData announcementsData)
    {
        String query ="UPDATE announcements SET seats_available = seats_available - 1 " +
                "WHERE id = ?;";

        Connection connection = databaseConnection.startConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, announcementsData.getId());

            preparedStatement.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("Server caught an error: " + e.getCause());
            return false;
        } finally {
            databaseConnection.closeConnection();
        }

    }


    public static boolean deleteAnnouncement(AnnouncementsData announcementsData) {

        String query= "DELETE FROM announcements WHERE id = ?";

        Connection connection = databaseConnection.startConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1,announcementsData.getId());

            preparedStatement.executeUpdate();

            logger.info("Announcement: " + announcementsData.getId() + " has been deleted");
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
