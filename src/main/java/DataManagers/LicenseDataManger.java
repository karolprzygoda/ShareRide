package DataManagers;

import Data.LicenseData;
import Data.VehicleData;
import Server.PostgreSQLInitialization;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LicenseDataManger {

    private static final PostgreSQLInitialization databaseConnection = PostgreSQLInitialization.getInstance();

    public static boolean insertLicense(LicenseData licenseData) {

        String query = "INSERT INTO license (user_id,serial_number,category,date_of_issue,expiration_date) VALUES ( ?, ?, ?, ?, ?);";

        Connection connection = databaseConnection.startConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1,UserDataManager.userId);
            preparedStatement.setString(2,licenseData.getSerialNumber());
            preparedStatement.setString(3,licenseData.getCategory());
            preparedStatement.setDate(4,licenseData.getDateOfIssueOfTheLicense());
            preparedStatement.setDate(5,licenseData.getExpirationDateOfTheLicense());


            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Błąd: " + e.getMessage());
            return false;
        }
    }

    public static LicenseData selectLicenceData() {

        String query = "SELECT id, user_id, serial_number, category, date_of_issue, expiration_date FROM license WHERE user_id = ?;";

        Connection connection = databaseConnection.startConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, UserDataManager.userId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next())
                {
                    return new LicenseData(resultSet.getInt("id"),
                            resultSet.getInt("user_id"),
                            resultSet.getString("serial_number"),
                            resultSet.getString("category"),
                            resultSet.getDate("date_of_issue"),
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

    public static boolean updateLicense(LicenseData licenseData) {

        String query = "UPDATE license SET serial_number = ?, category = ?, date_of_issue = ?, expiration_date = ? WHERE user_id = ?";

        if (licenseData.getSerialNumber() != null && licenseData.getCategory() != null &&
                licenseData.getDateOfIssueOfTheLicense() != null && licenseData.getExpirationDateOfTheLicense() !=null) {

            Connection connection = databaseConnection.startConnection();

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, licenseData.getSerialNumber());
                preparedStatement.setString(2, licenseData.getCategory());
                preparedStatement.setDate(3, licenseData.getDateOfIssueOfTheLicense());
                preparedStatement.setDate(4, licenseData.getExpirationDateOfTheLicense());
                preparedStatement.setInt(5, UserDataManager.userId);

                preparedStatement.executeUpdate();
                return true;

            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Błąd: " + e.getMessage());
                return false;
            } finally {
                databaseConnection.closeConnection();
            }
        }else {
            return false;
        }
    }

    public static boolean deleteLicense() {

        String query= "DELETE FROM license WHERE user_id = ?";

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
