package Server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PostgreSQLUser implements DatabaseUser {
    protected int id;//pole przechowujace id uzytkownika ktory sie zalogowal
    private final Connection connection;
    private final Logs log = new Logs("DatabaseUser");
    public PostgreSQLUser()
    {
        this.connection = PostgreSQLInitialization.getInstance().connection;
    }
    public void addUser(String imie, String nazwisko, String email,  String numerTelefonu
            , java.sql.Date dataUrodzenia, String haslo, java.sql.Date dataDolaczenia) {
        String sql = "INSERT INTO users(imie, nazwisko, email, numer_telefonu, data_urodzenia, haslo, plec, data_dolaczenia) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, imie);
            preparedStatement.setString(2, nazwisko);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, numerTelefonu);
            preparedStatement.setDate(5, dataUrodzenia);
            preparedStatement.setString(6, haslo);
            preparedStatement.setString(7, "Wole nie podawać");
            preparedStatement.setDate(8, dataDolaczenia);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Dodano użytkownika do bazy danych.");
                log.writeLog("New user added");
            } else {
                System.out.println("Nie udało się dodać użytkownika do bazy danych.");
                log.writeLog("Isert user error");
            }


        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Błąd SQL: " + e.getMessage());
        }

    }
    public void removeUser(int id) {
        String sql = "DELETE FROM users WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Usunięto użytkownika");
                log.writeLog("User removed");
            } else {
                System.out.println("Nie znaleziono użytkownika o podanym ID.");
                log.writeLog("User removing error, ID not found");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Błąd SQL: " + e.getMessage());
        }
    }
    public boolean loginUser(String email, String password) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, email);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String storedPassword = resultSet.getString("haslo");

                    boolean passwordMatch = Password.matchPassword(password, storedPassword);

                    if (passwordMatch) {
                        System.out.println("Użytkownik zalogowany pomyślnie.");
                        id = resultSet.getInt("id");//przeslanie do klienta id zalogowanego uzytkownika
                        return true;
                    } else {
                        System.out.println("Błąd logowania. Nieprawidłowe dane.");
                        return false;
                    }
                } else {
                    System.out.println("Błąd logowania. Nieprawidłowe dane.");
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Błędne dane logowania: " + e.getMessage());
            return false;
        }
    }
    public String selectName(int id) {
        String sql = "SELECT imie FROM users WHERE id = ?";
        String name;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                name = resultSet.getString("imie");
                System.out.println("Wysłano imie");
                return name;
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
    public boolean registerCheckMail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                boolean used = resultSet.next();
                if (used) {
                    System.out.println("Email w użyciu!.");
                    log.writeLog("Error, registering mail found in database");

                } else {
                    System.out.println("Email wolny!");
                    log.writeLog("Registering mail not found in database");

                }
                return used;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Błędny email: " + e.getMessage());
        }
        return false;
    }
    /**
     * Pobiera imie aktualnie użytkownika zalogowanego w aktualnej sesji
     * @param id użytkownika zalogowanego w aktualnej sesji
     * @return Imię użytkownika, jeżeli wszystko poszło pomyślnie. Null, jeżeli coś poszło nie tak
     */

    public List<String> selectProfileInfo(int id) {
        String sql = "SELECT imie,nazwisko,email,numer_telefonu,plec,data_urodzenia,data_dolaczenia FROM users WHERE id = ?";
        List<String> profileInfo = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                profileInfo.add(resultSet.getString("imie"));
                profileInfo.add(resultSet.getString("nazwisko"));
                profileInfo.add(resultSet.getString("plec"));
                profileInfo.add(resultSet.getString("email"));
                profileInfo.add(resultSet.getString("numer_telefonu"));
                profileInfo.add(resultSet.getString("data_urodzenia"));
                profileInfo.add(resultSet.getString("data_dolaczenia"));
                System.out.println("Wysłano dane");
                return profileInfo;
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
    /**
     * Aktualizuje dane użytkownika w bazie danych na podstawie podanego identyfikatora (id)
     * zgodnie z przekazanymi polami i ich nowymi wartościami przekazanymi w mapie.
     *
     * @param id             Identyfikator użytkownika, którego dane mają być zaktualizowane.
     * @param fieldsToUpdate Mapa pól do zaktualizowania w formacie <NazwaPola, NowaWartość>.
     * @author Karol Przygoda
     */
    public void updateUser(int id,  Map<String, String> fieldsToUpdate) {

        StringBuilder query = new StringBuilder("UPDATE users SET ");

        for (Map.Entry<String, String> entry : fieldsToUpdate.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (value != null && !value.isEmpty()) {
                query.append(key).append(" = ?, ");
            }
        }

        if (!fieldsToUpdate.isEmpty()) {
            query.setLength(query.length() - 2); // Usunięcie ostatniej przecinki i spacji
            query.append(" WHERE id = ?;");
        }

        String sql = query.toString();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            int i = 1;

            for (Map.Entry<String, String> entry : fieldsToUpdate.entrySet()) {
                String value = entry.getValue();
                preparedStatement.setString(i,value);
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
    public void removeEveryUser() {
        String sql = "DELETE FROM users";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.executeUpdate();

            System.out.println("Usunięto wszystkich użytkowników.");
            log.writeLog("All users removed");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Błąd SQL: " + e.getMessage());
        }
    }

}
