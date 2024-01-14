package Server;

import java.sql.Connection;

public interface DatabaseInitialization {
    Connection startConnection(); //connect to database
    void closeConnection(); //close connection with database
    void createTables();

}
