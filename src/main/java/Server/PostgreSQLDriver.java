package Server;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostgreSQLDriver implements DatabaseDriver {
    private final Connection connection;
    private final Logs log = new Logs("DatabaseDriver");
    public PostgreSQLDriver()
    {
        this.connection = PostgreSQLInitialization.getInstance().connection;
    }
    public void addLicense(String licenseID, Date dateOfIssueOfTheLicense, Date expirationDateOfTheLicense, String licenseCategory, int userID) {
        String sql = "INSERT INTO license(numer, data_wydania, data_waznosci, kategoria,id_uzytkownika) " +
                "VALUES (?,?,?,?,?)";

        System.out.println(userID);

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, licenseID);
            preparedStatement.setDate(2, dateOfIssueOfTheLicense);
            preparedStatement.setDate(3, expirationDateOfTheLicense);
            preparedStatement.setString(4, licenseCategory);
            preparedStatement.setInt(5,userID);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Dodano licencję do bazy danych.");
                log.writeLog("New license added");
            } else {
                System.out.println("Nie udało się dodać licencji do bazy danych.");
                log.writeLog("Insert license error");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Błąd SQL: " + e.getMessage());
        }
    }
    public void addDriver(Map<String, Integer> driverMap) {
        String sql = "INSERT INTO driver(id_uzytkownika, id_prawa_jazdy, id_danych_pojazdu) " +
                "VALUES (?,?,?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, driverMap.get("id_uzytkownika"));
            preparedStatement.setInt(2, driverMap.get("id_prawa_jazdy"));
            preparedStatement.setInt(3, driverMap.get("id_danych_pojazdu"));

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Dodano kierowcę do bazy danych.");
                log.writeLog("New driver added");
            } else {
                System.out.println("Nie udało się dodać kierowcy do bazy danych.");
                log.writeLog("Insert driver error");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Błąd SQL: " + e.getMessage());
        }
    }
    //jakie id kierowcy ma podany uzytkownik
    public int getDriverId(int id) {
        String sql = "SELECT id FROM driver WHERE id_uzytkownika = ?";
        int driverId;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                driverId = resultSet.getInt("id_uzytkownika");
                System.out.println("Wysłano imie");
                return driverId;
            } else {
                System.out.println("Coś poszło nie tak!");
                return -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Błędny email: " + e.getMessage());
            return -1;
        }
    }

    public int getLicenseId(int id) {
        String sql = "SELECT id FROM license WHERE id_uzytkownika = ?";
        int licenseId;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                licenseId = resultSet.getInt("id");
                System.out.println("Wysłano id");
                return licenseId;
            } else {
                System.out.println("Coś poszło nie tak!");
                return -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Błędny email: " + e.getMessage());
            return -1;
        }
    }

    public int getCarId(int id) {
        String sql = "SELECT id FROM vehicle WHERE id_uzytkownika = ?";
        int licenseId;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                licenseId = resultSet.getInt("id");
                System.out.println("Wysłano id");
                return licenseId;
            } else {
                System.out.println("Coś poszło nie tak!");
                return -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Błędny email: " + e.getMessage());
            return -1;
        }
    }

    /**
     * Aktualizuje dane użytkownika w bazie danych na podstawie podanego identyfikatora (id)
     * zgodnie z przekazanymi polami i ich nowymi wartościami przekazanymi w mapie.
     *
     * @param id             Identyfikator użytkownika, którego dane mają być zaktualizowane.
     * @param fieldsToUpdate Mapa pól do zaktualizowania w formacie <NazwaPola, NowaWartość>.
     * @author Karol Przygoda
     */
    public void updateLicense(int id,  Map<String, String> fieldsToUpdate) {

        StringBuilder query = new StringBuilder("INSERT INTO driver(id_uzytkownika, id_prawa_jazdy, id_danych_pojazdu) \" +\n" +
                "                \"VALUES (?,?,?)\" ");

        for (Map.Entry<String, String> entry : fieldsToUpdate.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (value != null && !value.isEmpty()) {
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

            for (Map.Entry<String, String> entry : fieldsToUpdate.entrySet()) {

                if(entry.getKey().startsWith("data_"))
                {
                    Date value = Date.valueOf(entry.getValue());
                    preparedStatement.setDate(i,value);
                }
                else
                {
                    String value = entry.getValue();
                    preparedStatement.setObject(i,value);
                }
                i++;
            }
            preparedStatement.setInt(i, id);
            System.out.println(preparedStatement);
            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Błąd: " + e.getMessage());
        }
    }

    public void updateCar(int id,  Map<String, Object> fieldsToUpdate) {

        StringBuilder query = new StringBuilder("UPDATE vehicle SET ");

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

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Błąd: " + e.getMessage());
        }
    }

    public void addCar(String carBrand, String carModel, String carPlates, String carVIN, int carSeatAvailable,String carInsuranceNumber,Date insurancePolicyExpirationDate, int id) {

        String sql = "INSERT INTO vehicle(marka, model, rejestracja, vin,liczba_miejsc,polisa,data_wygasniecia_polisy,id_uzytkownika) " +
                "VALUES (?,?,?,?,?,?,?,?)";


        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, carBrand);
            preparedStatement.setString( 2, carModel);
            preparedStatement.setString(3, carPlates);
            preparedStatement.setString(4, carVIN);
            preparedStatement.setInt(5,carSeatAvailable);
            preparedStatement.setString(6,carInsuranceNumber);
            preparedStatement.setDate(7,insurancePolicyExpirationDate);
            preparedStatement.setInt(8,id);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Dodano samochód do bazy danych.");
                log.writeLog("New car added");
            } else {
                System.out.println("Nie udało się dodać samochodu do bazy danych.");
                log.writeLog("Insert car error");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Błąd SQL: " + e.getMessage());
        }
    }

    public List<String> selectLicenseInfo(int id) {
        String sql = "SELECT numer,data_wydania,data_waznosci,kategoria FROM license WHERE id_uzytkownika = ?";
        List<String> licenseInfo = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                licenseInfo.add(resultSet.getString("numer"));
                licenseInfo.add(resultSet.getString("data_wydania"));
                licenseInfo.add(resultSet.getString("data_waznosci"));
                licenseInfo.add(resultSet.getString("kategoria"));
                System.out.println("Wysłano dane");
                return licenseInfo;
            } else {
                System.out.println("Coś poszło nie tak!");
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Błędny email: " + e.getMessage());
            return null;
        }
    }

    public List<String> selectCarInfo(int id) {
        String sql = "SELECT marka,model,rejestracja,vin,liczba_miejsc,polisa,data_wygasniecia_polisy FROM vehicle WHERE id_uzytkownika = ?";
        List<String> licenseInfo = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                licenseInfo.add(resultSet.getString("marka"));
                licenseInfo.add(resultSet.getString("model"));
                licenseInfo.add(resultSet.getString("rejestracja"));
                licenseInfo.add(resultSet.getString("vin"));
                licenseInfo.add(resultSet.getString("liczba_miejsc"));
                licenseInfo.add(resultSet.getString("polisa"));
                licenseInfo.add(resultSet.getString("data_wygasniecia_polisy"));
                System.out.println("Wysłano dane");
                return licenseInfo;
            } else {
                System.out.println("Coś poszło nie tak!");
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Błędny email: " + e.getMessage());
            return null;
        }
    }

    public void removeLicense(int id) {
        String sql = "DELETE FROM license WHERE id_uzytkownika = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Usunięto licencje");
                log.writeLog("License removed");
            } else {
                System.out.println("Nie znaleziono licencji użytkownika o podanym ID.");
                log.writeLog("License removing error, ID not found");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Błąd SQL: " + e.getMessage());
        }
    }

    public void removeCar(int id) {
        String sql = "DELETE FROM vehicle WHERE id_uzytkownika = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Usunięto samochód");
                log.writeLog("Car removed");
            } else {
                System.out.println("Nie znaleziono samochodu użytkownika o podanym ID.");
                log.writeLog("Car removing error, ID not found");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Błąd SQL: " + e.getMessage());
        }
    }

}
