module SharerideClient {
    requires javafx.controls;
    requires javafx.fxml;


    opens SharerideClient to javafx.fxml;
    exports SharerideClient;
}