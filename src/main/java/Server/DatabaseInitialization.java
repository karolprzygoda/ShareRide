package Server;

public interface DatabaseInitialization {
    void startConnection(); //connect to database
    void closeConnection(); //close connection with database
    void createTables();

}
