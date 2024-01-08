package Server;

import Data.LicenseData;
import Data.UserData;
import Data.VehicleData;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

public class UpdateHandler {
    private static final Connection connection = PostgreSQLInitialization.getInstance().connection;

    /*
    public static boolean updateUser(int id, UserData userData) {

        String query = "UPDATE"

        StringBuilder query = new StringBuilder("UPDATE ");

        query.append(table).append(" SET ");

        for (Map.Entry<String, Object> entry : fieldsToUpdate.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value != null ) {
                query.append(key).append(" = ?, ");
            }
        }

        if (!fieldsToUpdate.isEmpty()) {
            query.setLength(query.length() - 2); // Usunięcie ostatniej przecinki i spacji
            query.append(" WHERE id_uzytkownika = ?;");
        }

        String sql = query.toString();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            int i = 1;

            for (Map.Entry<String, Object> entry : fieldsToUpdate.entrySet()) {

                if(entry.getKey().startsWith("data_"))
                {
                    Date value = Date.valueOf(entry.getValue().toString());
                    preparedStatement.setDate(i,value);
                }
                else
                {
                    String value = entry.getValue().toString();
                    try {
                        Integer number = Integer.parseInt(value);
                        preparedStatement.setObject(i,number);
                    }
                    catch (Exception e)
                    {
                        preparedStatement.setObject(i,value);
                    }
                }
                i++;
            }
            preparedStatement.setInt(i, id);
            System.out.println(preparedStatement);
            preparedStatement.execute();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Błąd: " + e.getMessage());
            return false;
        }
    }

     */
    public static boolean updateVehicle(int id, VehicleData vehicleData) {

        StringBuilder query = new StringBuilder("UPDATE vehicle SET ");

        if (vehicleData.getVehicleBrand() != null && !vehicleData.getVehicleBrand().isEmpty()) {
            query.append("marka = ?, ");
        }

        if (vehicleData.getVehicleModel() != null && !vehicleData.getVehicleModel().isEmpty()) {
            query.append("model = ?, ");
        }

        if (vehicleData.getVehiclePlates() != null && !vehicleData.getVehiclePlates().isEmpty()) {
            query.append("rejestracja = ?, ");
        }

        if (vehicleData.getVehicleVin() != null && !vehicleData.getVehicleVin().isEmpty()) {
            query.append("vin = ?, ");
        }

        if (vehicleData.getAvailableSeats() > 0) {
            query.append("liczba_miejsc = ?, ");
        }

        if (vehicleData.getInsuranceNumber() != null && !vehicleData.getInsuranceNumber().isEmpty()) {
            query.append("polisa = ?, ");
        }

        if (vehicleData.getInsuranceExpirationDate() != null) {
            query.append("data_wygasniecia_polisy = ?, ");
        }

        query.delete(query.length() - 2, query.length());

        query.append(" WHERE id_uzytkownika = ?");

        String sql = query.toString();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {


            int parameterIndex = 1;

            if (vehicleData.getVehicleBrand() != null && !vehicleData.getVehicleBrand().isEmpty()) {
                preparedStatement.setString(parameterIndex++, vehicleData.getVehicleBrand());
            }

            if (vehicleData.getVehicleModel() != null && !vehicleData.getVehicleModel().isEmpty()) {
                preparedStatement.setString(parameterIndex++, vehicleData.getVehicleModel());
            }

            if (vehicleData.getVehiclePlates() != null && !vehicleData.getVehiclePlates().isEmpty()) {
                preparedStatement.setString(parameterIndex++, vehicleData.getVehiclePlates());
            }

            if (vehicleData.getVehicleVin() != null && !vehicleData.getVehicleVin().isEmpty()) {
                preparedStatement.setString(parameterIndex++, vehicleData.getVehicleVin());
            }

            if (vehicleData.getAvailableSeats() > 0) {
                preparedStatement.setInt(parameterIndex++, vehicleData.getAvailableSeats());
            }

            if (vehicleData.getInsuranceNumber() != null && !vehicleData.getInsuranceNumber().isEmpty()) {
                preparedStatement.setString(parameterIndex++, vehicleData.getInsuranceNumber());
            }

            if (vehicleData.getInsuranceExpirationDate() != null) {
                preparedStatement.setDate(parameterIndex++, vehicleData.getInsuranceExpirationDate());
            }

            preparedStatement.setInt(parameterIndex, id);


            preparedStatement.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Błąd: " + e.getMessage());
            return false;
        }
    }
    /*public static boolean updateLicense(int id, LicenseData licenseData) {

        StringBuilder query = new StringBuilder("UPDATE ");

        query.append(table).append(" SET ");

        for (Map.Entry<String, Object> entry : fieldsToUpdate.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value != null ) {
                query.append(key).append(" = ?, ");
            }
        }

        if (!fieldsToUpdate.isEmpty()) {
            query.setLength(query.length() - 2); // Usunięcie ostatniej przecinki i spacji
            query.append(" WHERE id_uzytkownika = ?;");
        }

        String sql = query.toString();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            int i = 1;

            for (Map.Entry<String, Object> entry : fieldsToUpdate.entrySet()) {

                if(entry.getKey().startsWith("data_"))
                {
                    Date value = Date.valueOf(entry.getValue().toString());
                    preparedStatement.setDate(i,value);
                }
                else
                {
                    String value = entry.getValue().toString();
                    try {
                        Integer number = Integer.parseInt(value);
                        preparedStatement.setObject(i,number);
                    }
                    catch (Exception e)
                    {
                        preparedStatement.setObject(i,value);
                    }
                }
                i++;
            }
            preparedStatement.setInt(i, id);
            System.out.println(preparedStatement);
            preparedStatement.execute();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Błąd: " + e.getMessage());
            return false;
        }
    }

     */
}
