module SharerideClient {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens SharerideClient to javafx.fxml;
    exports SharerideClient;
}