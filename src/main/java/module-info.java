module com.example.sharerideclient {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.sharerideclient to javafx.fxml;
    exports com.example.sharerideclient;
}