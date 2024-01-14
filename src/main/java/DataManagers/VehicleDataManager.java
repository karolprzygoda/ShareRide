package DataManagers;

import Data.VehicleData;
import Server.PostgreSQLInitialization;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VehicleDataManager {

    private static final PostgreSQLInitialization databaseConnection = PostgreSQLInitialization.getInstance();

    public static boolean insertVehicle(VehicleData vehicleData) {

        String query = "INSERT INTO vehicle (user_id, brand, model, production_date, plates_number, vin, seats_available, insurance_number, expiration_date) " +
                "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?);";

        Connection connection = databaseConnection.startConnection();


        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, UserDataManager.userId);
            preparedStatement.setString(2,vehicleData.getBrand());
            preparedStatement.setString(3,vehicleData.getModel());
            preparedStatement.setDate(4,vehicleData.getProductionDate());
            preparedStatement.setString(5,vehicleData.getPlatesNumber());
            preparedStatement.setString(6,vehicleData.getVin());
            preparedStatement.setInt(7,vehicleData.getAvailableSeats());
            preparedStatement.setString(8,vehicleData.getInsuranceNumber());
            preparedStatement.setDate(9,vehicleData.getInsuranceExpirationDate());


            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Błąd: " + e.getMessage());
            return false;
        } finally {
            databaseConnection.closeConnection();
        }
    }

    public static VehicleData selectVehicleData() {

        String query = "SELECT id, user_id, brand, model, production_date, plates_number, vin, seats_available, insurance_number, expiration_date FROM vehicle WHERE user_id = ?;";

        Connection connection = databaseConnection.startConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, UserDataManager.userId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next())
                {
                    return new VehicleData(resultSet.getInt("id"),
                            resultSet.getInt("user_id"),
                            resultSet.getString("brand"),
                            resultSet.getString("model"),
                            resultSet.getDate("production_date"),
                            resultSet.getString("plates_number"),
                            resultSet.getString("vin"),
                            resultSet.getInt("seats_available"),
                            resultSet.getString("insurance_number"),
                            resultSet.getDate("expiration_date"));

                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Błąd: " + e.getMessage());
        }
        finally {
            databaseConnection.closeConnection();
        }

        return null;
    }

    public static boolean updateVehicle(VehicleData vehicleData) {

        String query = "UPDATE vehicle SET brand = ?, model = ?, production_date = ?, plates_number = ?, vin = ?," +
                " seats_available = ?, insurance_number = ?, expiration_date = ? WHERE user_id = ?";

        if (vehicleData.getBrand() != null && vehicleData.getModel() != null &&vehicleData.getPlatesNumber() != null &
                vehicleData.getVin() != null  && vehicleData.getAvailableSeats() > 0 &&vehicleData.getInsuranceNumber() != null &&
                vehicleData.getInsuranceExpirationDate() != null) {


            Connection connection = databaseConnection.startConnection();

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, vehicleData.getBrand());
                preparedStatement.setString(2, vehicleData.getModel());
                preparedStatement.setDate(3, vehicleData.getProductionDate());
                preparedStatement.setString(4, vehicleData.getPlatesNumber());
                preparedStatement.setString(5, vehicleData.getVin());
                preparedStatement.setInt(6, vehicleData.getAvailableSeats());
                preparedStatement.setString(7, vehicleData.getInsuranceNumber());
                preparedStatement.setDate(8, vehicleData.getInsuranceExpirationDate());
                preparedStatement.setInt(9, UserDataManager.userId);

                preparedStatement.executeUpdate();
                return true;

            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Błąd: " + e.getMessage());
                return false;
            } finally {
                databaseConnection.closeConnection();
            }
        }
        else
            return false;
    }

    public static boolean deleteVehicle() {

        String query= "DELETE FROM vehicle WHERE user_id = ?";

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
