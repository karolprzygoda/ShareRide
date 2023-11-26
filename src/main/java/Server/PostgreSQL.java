package Server;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
public class PostgreSQL {
	
    protected Integer id;//pole przechowujace id uzytkownika ktory sie zalogowal

    private final String url = "jdbc:postgresql://localhost:5432/wspolnedojazdy";
    private final String user = "postgres";
    private final String password = "Password123!";
    private final Logs log = new Logs("Database");

    private Connection connection;
    public void startConnection() {
        try {
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Połączono z bazą danych." + connection);
            log.writeLog("Connected");
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
    public void removeEveryUsers() {
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
            log.writeLog("Users table created (if didn't exist)");
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
	 public String selectNamme(int id) {
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
	
}
