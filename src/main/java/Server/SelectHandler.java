package Server;

import Data.DriverData;
import Data.LicenseData;
import Data.UserData;
import Data.VehicleData;

import java.sql.*;

public class SelectHandler {

    private static final Connection connection = PostgreSQLInitialization.getInstance().connection;

    public static UserData selectUserData(int id) {

        UserData userData = null;
        String query = "SELECT imie,nazwisko,email,numer_telefonu,plec,data_urodzenia,data_dolaczenia FROM users WHERE id_uzytkownika = ?;";


        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next())
                {
                    userData = new UserData(resultSet.getString("imie"),
                                            resultSet.getString("nazwisko"),
                                            resultSet.getString("email"),
                                            resultSet.getDate("data_urodzenia"),
                                            resultSet.getDate("data_dolaczenia"),
                                            null,
                                            resultSet.getString("numer_telefonu"),
                                            resultSet.getString("plec"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Błąd: " + e.getMessage());
        }
        return userData;
    }

    public static LicenseData selectLicenceData(int id) {

        LicenseData licenseData = null;
        String query = "SELECT id, id_uzytkownika, numer, data_wydania, data_waznosci, kategoria FROM license WHERE id_uzytkownika = ?;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next())
                {
                    licenseData = new LicenseData(resultSet.getInt("id_uzytkownika"),
                                                  resultSet.getString("numer"),
                                                  resultSet.getDate("data_wydania"),
                                                  resultSet.getDate("data_waznosci"),
                                                  resultSet.getString("kategoria"));
                    licenseData.setId(resultSet.getInt("id"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Błąd: " + e.getMessage());
        }

        return licenseData;
    }

    public static VehicleData selectVehicleData(int id)
    {
        VehicleData vehicleData = null;
        String query = "SELECT id, id_uzytkownika, marka, model, rejestracja, vin, liczba_miejsc, polisa, data_wygasniecia_polisy FROM vehicle WHERE id_uzytkownika = ?;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next())
                {
                    vehicleData = new VehicleData(resultSet.getInt("id_uzytkownika"),
                            resultSet.getString("marka"),
                            resultSet.getString("model"),
                            resultSet.getString("rejestracja"),
                            resultSet.getString("vin"),
                            resultSet.getInt("liczba_miejsc"),
                            resultSet.getString("polisa"),
                            resultSet.getDate("data_wygasniecia_polisy"));

                    vehicleData.setId(resultSet.getInt("id"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Błąd: " + e.getMessage());
        }

        return vehicleData;
    }

    public static DriverData selectDriverData(int id)
    {
        DriverData driverData = null;
        String query = "SELECT id_uzytkownika, id_prawa_jazdy, id_danych_pojazdu FROM driver WHERE id_uzytkownika = ?;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next())
                {
                    driverData = new DriverData(resultSet.getInt("id_uzytkownika"),
                                                resultSet.getInt("id_prawa_jazdy"),
                                                resultSet.getInt("id_danych_pojazdu"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Błąd: " + e.getMessage());
        }

        return driverData;
    }
}
