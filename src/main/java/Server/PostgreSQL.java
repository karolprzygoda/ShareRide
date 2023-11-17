package Server;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
public class PostgreSQL {

    private final String url = "jdbc:postgresql://localhost:5432/wspolnedojazdy";
    private final String user = "postgres";
    private final String password = "Password123!";

    private Connection connection;
    public void startConnection() {
        try {
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Połączono z bazą danych." + connection);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Rozłączono z bazą danych.");
            } catch (SQLException e) {
                System.out.println("Błąd zamykania bazy danych: " + e.getMessage());
            }
        }
    }

    public void addUser(String imie, String nazwisko, String email,  String numerTelefonu
                                 , java.sql.Date dataUrodzenia, String haslo) {
        String sql = "INSERT INTO users(imie, nazwisko, email, numer_telefonu, data_urodzenia, haslo) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, imie);
            preparedStatement.setString(2, nazwisko);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, numerTelefonu);
            preparedStatement.setDate(5, dataUrodzenia);
            preparedStatement.setString(6, haslo);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Dodano użytkownika do bazy danych.");
            } else {
                System.out.println("Nie udało się dodać użytkownika do bazy danych.");
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
            } else {
                System.out.println("Nie znaleziono użytkownika o podanym ID.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Błąd SQL: " + e.getMessage());
        }
    }
    public void removeEveryUsers() {
        String sql = "DELETE FROM users";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.executeUpdate();

            System.out.println("Usunięto wszystkich użytkowników.");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Błąd SQL: " + e.getMessage());
        }
    }

    public void createTable(){
        String createTableSQL = "CREATE TABLE IF NOT EXISTS users ("
                + "id SERIAL PRIMARY KEY,"
                + "imie VARCHAR(50) NOT NULL,"
                + "nazwisko VARCHAR(50) NOT NULL,"
                + "email VARCHAR(100) UNIQUE NOT NULL,"
                + "numer_telefonu VARCHAR(15)  NOT NULL,"
                + "plec VARCHAR(10),"
                + "data_urodzenia DATE  NOT NULL,"
                + "haslo VARCHAR(255) NOT NULL"
                + ")";
        try (PreparedStatement preparedStatement = connection.prepareStatement(createTableSQL)) {
            preparedStatement.execute();
            System.out.println("Tabela uzytkownicy została utworzona (jeśli nie istniała).");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Błąd SQL: " + e.getMessage());
        }
    }

    public boolean loginUser(String email, String password) {
        String sql = "SELECT * FROM users WHERE email = ? AND haslo = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                boolean loggedIn = resultSet.next();
                if (loggedIn) {
                    System.out.println("Użytkownik zalogowany pomyślnie.");
                } else {
                    System.out.println("Błąd logowania. Nieprawidłowe dane.");
                }
                return loggedIn;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Błędne dane logowania: " + e.getMessage());
        }
        return false;
    }
    public boolean registerCheckMail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                boolean used = resultSet.next();
                if (used) {
                    System.out.println("Email w użyciu!.");
                } else {
                    System.out.println("Email wolny!");
                }
                return used;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Błędny email: " + e.getMessage());
        }
        return false;
    }
}