package DataManagers;

import Data.DriverData;
import Server.PostgreSQLInitialization;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DriverDataManager {

    private static final PostgreSQLInitialization databaseConnection = PostgreSQLInitialization.getInstance();

    public static boolean insertDriver(DriverData driverData) {

        String query = "INSERT INTO driver (user_id, license_id, vehicle_id) VALUES ( ?, ?, ?);";

        Connection connection = databaseConnection.startConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1,UserDataManager.userId);
            preparedStatement.setInt(2,driverData.getLicenseID());
            preparedStatement.setInt(3,driverData.getVehicleID());



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

    public static DriverData selectDriverData()
    {
        DriverData driverData = null;
        String query = "SELECT id, user_id, license_id, vehicle_id FROM driver WHERE user_id = ?;";

        Connection connection = databaseConnection.startConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, UserDataManager.userId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next())
                {
                    return new DriverData(resultSet.getInt("id"),
                            resultSet.getInt("user_id"),
                            resultSet.getInt("license_id"),
                            resultSet.getInt("vehicle_id"));
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

    public static boolean deleteDriver() {

        String query= "DELETE FROM driver WHERE user_id = ?";

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
