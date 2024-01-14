module SharerideClient {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens SharerideClient to javafx.fxml;
    exports SharerideClient;
    exports Data;
    opens Data to javafx.fxml;
    exports SharerideClient.Controllers;
    opens SharerideClient.Controllers to javafx.fxml;
    exports SharerideClient.Views;
    opens SharerideClient.Views to javafx.fxml;
}