package Server;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class PostgreSQLInitialization implements DatabaseInitialization {

    private static PostgreSQLInitialization instance;
    private final String configurationPath = "serverConfig.properties";
    protected String url;
    protected String user;
    protected String password;
    protected final Logs log = new Logs("DatabaseInitialization");
    protected static final String[] userColumns = {"imie", "nazwisko", "email", "numer_telefonu", "plec", "data_urodzenia", "data_dolaczenia"};
    protected static final String[] userColumnsToInsert = {"imie", "nazwisko", "email", "numer_telefonu", "plec", "data_urodzenia","haslo", "data_dolaczenia"};
    protected static final String[] licenseColumns = {"id_uzytkownika","numer", "data_wydania", "data_waznosci", "kategoria"};
    protected static final String[] vehicleColumns = {"id_uzytkownika","marka", "model", "rejestracja", "vin", "liczba_miejsc", "polisa", "data_wygasniecia_polisy"};
    protected static final String[] driverColumns = {"id_uzytkownika","id_prawa_jazdy", "id_danych_pojazdu"};
    protected Connection connection;
    private PostgreSQLInitialization()
    {
        Properties properties = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream(configurationPath)) {
            properties.load(fileInputStream);

            this.url = properties.getProperty("url");
            this.user = properties.getProperty("user");
            this.password = properties.getProperty("password");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static PostgreSQLInitialization getInstance()
    {
        if (instance == null)
        {
            instance = new PostgreSQLInitialization();
        }
        return instance;
    }

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
    public void createTables()
    {
        createTableUser();
        createTableLicense();
        createTableVehicle();
        createTableDriver();
    }
    private void createTableDriver() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS driver ("
                + "id SERIAL PRIMARY KEY,"
                + "id_uzytkownika INT REFERENCES users(id),"
                + "id_prawa_jazdy INT REFERENCES license(id),"
                + "id_danych_pojazdu INT REFERENCES vehicle(id)"
                + ")";
        executeCreateTable(createTableSQL, "Kierowca");
    }

    private void createTableLicense() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS license ("
                + "id SERIAL PRIMARY KEY,"
                + "id_uzytkownika INT REFERENCES users(id),"
                + "numer VARCHAR(20) NOT NULL,"
                + "data_wydania DATE NOT NULL,"
                + "data_waznosci DATE NOT NULL,"
                + "kategoria VARCHAR(10) NOT NULL"
                + ")";
        executeCreateTable(createTableSQL, "PrawoJazdy");
    }

    private void createTableVehicle() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS vehicle ("
                + "id SERIAL PRIMARY KEY,"
                + "id_uzytkownika INT REFERENCES users(id),"
                + "marka VARCHAR(50) NOT NULL,"
                + "model VARCHAR(50) NOT NULL,"
                + "rejestracja VARCHAR(15) NOT NULL,"
                + "vin VARCHAR(17) NOT NULL,"
                + "liczba_miejsc INT NOT NULL,"
                + "polisa VARCHAR(100) NOT NULL,"
                + "data_wygasniecia_polisy DATE NOT NULL"
                + ")";
        executeCreateTable(createTableSQL, "DanePojazdu");
    }

    private void createTableUser(){
        String createTableSQL = "CREATE TABLE IF NOT EXISTS users ("
                + "id_uzytkownika SERIAL PRIMARY KEY,"
                + "imie VARCHAR(50) NOT NULL,"
                + "nazwisko VARCHAR(50) NOT NULL,"
                + "email VARCHAR(100) UNIQUE NOT NULL,"
                + "numer_telefonu VARCHAR(15)  NOT NULL,"
                + "plec VARCHAR(16),"
                + "data_urodzenia DATE  NOT NULL,"
                + "haslo VARCHAR(255) NOT NULL,"
                + "data_dolaczenia DATE NOT NULL"
                + ")";
        executeCreateTable(createTableSQL, "Użytkownik");
    }

    private void executeCreateTable(String createTableSQL, String tableName) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(createTableSQL)) {
            preparedStatement.execute();
            System.out.println("Tabela " + tableName + " została utworzona (jeśli nie istniała).");
            log.writeLog(tableName + " table created (if didn't exist)");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Błąd SQL przy tworzeniu tabeli " + tableName + ": " + e.getMessage());
        }
    }
}
