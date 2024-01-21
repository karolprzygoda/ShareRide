package Server;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    private String url;
    private String user;
    private String password;
    private  Connection connection;

    private static final Logger logger = LogManager.getLogger(PostgreSQLInitialization.class);
    private PostgreSQLInitialization()
    {
        Properties properties = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream(configurationPath)) {
            properties.load(fileInputStream);

            this.url = properties.getProperty("url");
            this.user = properties.getProperty("user");
            this.password = properties.getProperty("password");

        } catch (IOException e) {
            logger.error("Server caught an error: " + e.getCause());
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

    public Connection startConnection() {
        try {
            System.out.println("Połączono z bazą danych." + connection);
            return connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            logger.error("Server caught an error: " + e.getCause());
            System.out.println(e.getMessage());
            return null;
        }
    }
    public  void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Rozłączono z bazą danych.");
            } catch (SQLException e) {
                logger.error("Server caught an error: " + e.getCause());
                System.out.println("Błąd zamykania bazy danych: " + e.getMessage());
            }
        }
    }
    private  void createTableDriver() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS driver ("
                + "id SERIAL PRIMARY KEY,"
                + "user_id INT REFERENCES users(id) ON DELETE CASCADE,"
                + "license_id INT REFERENCES license(id) ON DELETE CASCADE,"
                + "vehicle_id INT REFERENCES vehicle(id) ON DELETE CASCADE"
                + ")";
        executeCreateTable(createTableSQL, "Driver");
    }

    private  void createTableLicense() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS license ("
                + "id SERIAL PRIMARY KEY,"
                + "user_id INT REFERENCES users(id) ON DELETE CASCADE,"
                + "serial_number VARCHAR(20) NOT NULL,"
                + "category VARCHAR(10) NOT NULL,"
                + "date_of_issue DATE NOT NULL,"
                + "expiration_date DATE NOT NULL"
                + ")";
        executeCreateTable(createTableSQL, "License");
    }

    private  void createTableVehicle() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS vehicle ("
                + "id SERIAL PRIMARY KEY,"
                + "user_id INT REFERENCES users(id) ON DELETE CASCADE,"
                + "brand VARCHAR(50) NOT NULL,"
                + "model VARCHAR(50) NOT NULL,"
                + "production_date DATE NOT NULL,"
                + "plates_number VARCHAR(15) NOT NULL,"
                + "vin VARCHAR(17) NOT NULL,"
                + "seats_available INT NOT NULL,"
                + "insurance_number VARCHAR(100) NOT NULL,"
                + "expiration_date DATE NOT NULL"
                + ")";
        executeCreateTable(createTableSQL, "Vehicle");
    }

    private  void createTableUser(){
        String createTableSQL = "CREATE TABLE IF NOT EXISTS users ("
                + "id SERIAL PRIMARY KEY,"
                + "name VARCHAR(50) NOT NULL,"
                + "last_name VARCHAR(50) NOT NULL,"
                + "email VARCHAR(100) UNIQUE NOT NULL,"
                + "password VARCHAR(255) NOT NULL,"
                + "phone_number VARCHAR(15)  NOT NULL,"
                + "gender VARCHAR(16),"
                + "birth_date DATE  NOT NULL,"
                + "registration_date DATE NOT NULL,"
                + "rating DOUBLE PRECISION "
                + ")";
        executeCreateTable(createTableSQL, "Users");
    }

    private  void createTableAnnouncements(){
        String createTableSQL = "CREATE TABLE IF NOT EXISTS announcements ("
                + "id SERIAL PRIMARY KEY,"
                + "driver_id INT REFERENCES driver(id) ON DELETE CASCADE,"
                + "starting_station VARCHAR(60) NOT NULL,"
                + "destination VARCHAR(60) NOT NULL,"
                + "date_of_add_announcement DATE NOT NULL,"
                + "departure_date DATE NOT NULL,"
                + "seats_available INT NOT NULL"
                + ")";
        executeCreateTable(createTableSQL, "Announcements");
    }

    private  void createTablePassengers(){
        String createTableSQL = "CREATE TABLE IF NOT EXISTS passengers ("
                + "id SERIAL PRIMARY KEY,"
                + "announcements_id INT REFERENCES announcements(id) ON DELETE CASCADE,"
                + "user_id INT REFERENCES users(id) ON DELETE CASCADE"
                + ")";
        executeCreateTable(createTableSQL, "Passengers");
    }

    private  void createTableRatings(){
        String createTableSQL = "CREATE TABLE IF NOT EXISTS ratings ("
                + "id SERIAL PRIMARY KEY,"
                + "announcements_id INT REFERENCES announcements(id) ON DELETE CASCADE,"
                + "user_id INT REFERENCES users(id) ON DELETE CASCADE,"
                + "rater_id INT REFERENCES users(id) ON DELETE CASCADE,"
                + "rating INT NOT NULL"
                + ")";
        executeCreateTable(createTableSQL, "Ratings");
    }


    public  void createTables()
    {
        createTableUser();
        createTableLicense();
        createTableVehicle();
        createTableDriver();
        createTableAnnouncements();
        createTablePassengers();
        createTableRatings();
    }

    private void executeCreateTable(String createTableSQL, String tableName) {

        startConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(createTableSQL)) {
            preparedStatement.execute();
        } catch (SQLException e) {

            closeConnection();
            e.printStackTrace();
            logger.error("Server caught an error: " + e.getCause());
        }finally {
            closeConnection();
        }
    }
}
