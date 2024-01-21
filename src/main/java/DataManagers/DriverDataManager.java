package DataManagers;

import Data.DriverData;
import Server.PostgreSQLInitialization;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DriverDataManager {

    private static final PostgreSQLInitialization databaseConnection = PostgreSQLInitialization.getInstance();

    private static final Logger logger = LogManager.getLogger(DriverDataManager.class);

    public static boolean insertDriver(DriverData driverData) {

        String query = "INSERT INTO driver (user_id, license_id, vehicle_id) VALUES ( ?, ?, ?);";

        Connection connection = databaseConnection.startConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1,driverData.getUserID());
            preparedStatement.setInt(2,driverData.getLicenseID());
            preparedStatement.setInt(3,driverData.getVehicleID());



            preparedStatement.executeUpdate();
            logger.info("User: " + driverData.getUserID() + " become driver");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Błąd: " + e.getMessage());
            logger.error("Server caught an error: " + e.getCause());
            return false;
        }finally {
            databaseConnection.closeConnection();
        }

    }

    public static DriverData selectDriverData(DriverData driverData)
    {
        String query = "SELECT id, user_id, license_id, vehicle_id FROM driver WHERE user_id = ?;";

        Connection connection = databaseConnection.startConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, driverData.getUserID());
            ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next())
                {
                    driverData.setId(resultSet.getInt("id"));
                    driverData.setUserID(resultSet.getInt("user_id"));
                    driverData.setLicenseID(resultSet.getInt("license_id"));
                    driverData.setVehicleID(resultSet.getInt("vehicle_id"));

                    return driverData;

                }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Błąd: " + e.getMessage());
            logger.error("Server caught an error: " + e.getCause());
        }finally {
            databaseConnection.closeConnection();
        }
        return null;
    }

    public static DriverData selectUserIdByDriverId(DriverData driverData)
    {
        String query = "SELECT id, user_id, license_id, vehicle_id FROM driver WHERE id= ?;";

        Connection connection = databaseConnection.startConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, driverData.getId());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next())
            {
                driverData.setId(resultSet.getInt("id"));
                driverData.setUserID(resultSet.getInt("user_id"));
                driverData.setLicenseID(resultSet.getInt("license_id"));
                driverData.setVehicleID(resultSet.getInt("vehicle_id"));

                return driverData;

            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Błąd: " + e.getMessage());
            logger.error("Server caught an error: " + e.getCause());
        }finally {
            databaseConnection.closeConnection();
        }
        return null;
    }

    public static boolean deleteDriver(DriverData driverData) {

        String query= "DELETE FROM driver WHERE user_id = ?";

        Connection connection = databaseConnection.startConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1,driverData.getUserID());

            preparedStatement.executeUpdate();
            logger.info("User: " + driverData.getUserID() + " is no longer a driver");
            return true;


        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Błąd SQL: " + e.getMessage());
            logger.error("Server caught an error: " + e.getCause());
            return false;
        }finally {
            databaseConnection.closeConnection();
        }
    }
}
