package Server;

import Data.DriverData;
import Data.LicenseData;
import Data.UserData;
import Data.VehicleData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertHandler {

    private static final Connection connection = PostgreSQLInitialization.getInstance().connection;
    public static boolean insertUser(UserData userData) {

        String query = "INSERT INTO users (imie, nazwisko, email, numer_telefonu, plec, data_urodzenia, haslo, data_dolaczenia) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";


        try (PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {
                preparedStatement.setString(1,userData.getName());
                preparedStatement.setString(2,userData.getLastName());
                preparedStatement.setString(3,userData.getMail());
                preparedStatement.setString(4,userData.getPhoneNumber());
                preparedStatement.setString(5,userData.getGender());
                preparedStatement.setDate(6,userData.getBirthDate());
                preparedStatement.setString(7,userData.getPassword());
                preparedStatement.setDate(8,userData.getRegisterDate());


            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Błąd: " + e.getMessage());
            return false;
        }
    }
    public static boolean insertLicense(LicenseData licenseData) {

        String query = "INSERT INTO license (id_uzytkownika,numer,data_wydania,data_waznosci,kategoria) VALUES ( ?, ?, ?, ?, ?);";


        try (PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {
                preparedStatement.setInt(1,licenseData.getUserID());
                preparedStatement.setString(2,licenseData.getLicenseNumber());
                preparedStatement.setDate(3,licenseData.getDateOfIssueOfTheLicense());
                preparedStatement.setDate(4,licenseData.getExpirationDateOfTheLicense());
                preparedStatement.setString(5,licenseData.getLicenseCategory());


            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Błąd: " + e.getMessage());
            return false;
        }
    }
    public static boolean insertVehicle(VehicleData vehicleData) {

        String query = "INSERT INTO vehicle (id_uzytkownika,marka,model,rejestracja,vin,liczba_miejsc,polisa,data_wygasniecia_polisy) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?);";


        try (PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {
                preparedStatement.setInt(1,vehicleData.getUserID());
                preparedStatement.setString(2,vehicleData.getVehicleBrand());
                preparedStatement.setString(3,vehicleData.getVehicleModel());
                preparedStatement.setString(4,vehicleData.getVehiclePlates());
                preparedStatement.setString(5,vehicleData.getVehicleVin());
                preparedStatement.setInt(6,vehicleData.getAvailableSeats());
                preparedStatement.setString(7,vehicleData.getInsuranceNumber());
                preparedStatement.setDate(8,vehicleData.getInsuranceExpirationDate());


            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Błąd: " + e.getMessage());
            return false;
        }
    }
    public static boolean insertDriver(DriverData driverData) {

        String query = "INSERT INTO driver (id_uzytkownika,id_prawa_jazdy,id_danych_pojazdu) VALUES ( ?, ?, ?);";


        try (PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {
                preparedStatement.setInt(1,driverData.getUserID());
                preparedStatement.setInt(2,driverData.getLicenseID());
                preparedStatement.setInt(3,driverData.getVehicleID());



            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Błąd: " + e.getMessage());
            return false;
        }
    }
}
