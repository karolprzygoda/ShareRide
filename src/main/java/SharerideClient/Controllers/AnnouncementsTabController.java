package SharerideClient.Controllers;

import Data.DriverData;
import SharerideClient.Alerts;
import SharerideClient.Views.FormsContainer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AnnouncementsTabController  implements Initializable {

    @FXML
    private TableColumn<?, ?> AVGRatingColumn;

    @FXML
    private TableColumn<?, ?> AgeColumn;

    @FXML
    private TableView<?> AnnoucementsTableView;

    @FXML
    private TableColumn<?, ?> LastNameColumn;

    @FXML
    private TableColumn<?, ?> MailColumn;

    @FXML
    private TableColumn<?, ?> NameColumn;

    @FXML
    private TableView<?> PassengersTable;

    @FXML
    private TableColumn<?, ?> PhoneNumberColumn;

    @FXML
    private TableColumn<?, ?> RidesCountColumn;

    @FXML
    private Button addNewAnnouncementBtn;

    @FXML
    private TableColumn<?, ?> announcementDateOfAddColumn;

    @FXML
    private TableColumn<?, ?> announcementIdColumn;

    @FXML
    private TableColumn<?, ?> departureDateColumn;

    @FXML
    private DatePicker departureDateDatePicker;

    @FXML
    private TableColumn<?, ?> destinationColumn;

    @FXML
    private TextField destinationTextField;

    @FXML
    private TableColumn<?, ?> joinBtnColumn;

    @FXML
    private Label lastNameLabel;

    @FXML
    private Label mailLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Button searchAnnouncementsBtn;

    @FXML
    private TableColumn<?, ?> seatAvailableColumn;

    @FXML
    private TextField seatsAvailableTextField;

    @FXML
    private TableColumn<?, ?> startingStationColumn;

    @FXML
    private TextField startingStationTextField;

    @FXML
    private void addNewAnnouncement() throws IOException {
        DriverData driverData = new DriverData();

        if(ServerController.sendSelectRequest(driverData) != null)
        {
            Stage modalStage = new Stage();
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.initStyle(StageStyle.UTILITY);
            modalStage.setTitle("Dodaj nowe ogłoszenie");

            FXMLLoader fxmlLoader = new FXMLLoader(FormsContainer.class.getResource("AddNewAnnouncementFormView.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            modalStage.initStyle(StageStyle.TRANSPARENT);
            modalStage.setScene(scene);
            modalStage.showAndWait();

        }else{
            Alerts.failureAlert("Aby mieć możliwość dodania ogłoszenia trzeba być zarejestrowanym kierowcą, przejdź do zakładki kierowca");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {

        /*NameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        LastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        MailColumn.setCellValueFactory(new PropertyValueFactory<>("mail"));
        AgeColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        PhoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        RidesCountColumn.setCellValueFactory(new PropertyValueFactory<>("ridesCount"));
        AVGRatingColumn.setCellValueFactory(new PropertyValueFactory<>("avgRating"));

        //UserData samplePerson = new UserData(1,"John", "Doe", "john@example.com", 30, "123456789", "Male",10, 4.5);

        // Tworzymy listę zawierającą ten rekord
        ObservableList<UserData> data = FXCollections.observableArrayList();
        //data.add(samplePerson);

        // Ustawiamy dane w TableView
        PassengersTable.setItems(data);

         */
    }

}
