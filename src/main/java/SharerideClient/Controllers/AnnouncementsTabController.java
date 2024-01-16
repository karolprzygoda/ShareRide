package SharerideClient.Controllers;

import Data.*;
import SharerideClient.Alerts;
import SharerideClient.Views.FormsContainer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class AnnouncementsTabController  implements Initializable {

    @FXML
    private TableColumn<UserData, Double> avgRatingColumn;

    @FXML
    private TableColumn<UserData, Integer> ageColumn;

    @FXML
    private TableView<AnnouncementsData> annoucementsTableView;

    @FXML
    private TableColumn<UserData, String> lastNameColumn;

    @FXML
    private TableColumn<UserData, String> mailColumn;

    @FXML
    private TableColumn<UserData, String> nameColumn;

    @FXML
    private TableView<UserData> passengersTable;

    @FXML
    private TableColumn<UserData, String> phoneNumberColumn;

    @FXML
    private TableColumn<UserData, Integer> ridesCountColumn;

    @FXML
    private Button addNewAnnouncementBtn;

    @FXML
    private TableColumn<AnnouncementsData, Date> announcementDateOfAddColumn;

    @FXML
    private TableColumn<AnnouncementsData, Integer> announcementIdColumn;

    @FXML
    private TableColumn<AnnouncementsData, Date> departureDateColumn;

    @FXML
    private DatePicker departureDateDatePicker;

    @FXML
    private TableColumn<AnnouncementsData, String> destinationColumn;

    @FXML
    private TextField destinationTextField;

    @FXML
    private TableColumn<AnnouncementsData, Integer> joinBtnColumn;

    @FXML
    private Label lastNameLabel;

    @FXML
    private Label mailLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Button searchAnnouncementsBtn;

    @FXML
    private TableColumn<AnnouncementsData, Integer> seatAvailableColumn;

    @FXML
    private TextField seatsAvailableTextField;

    @FXML
    private TableColumn<AnnouncementsData, String> startingStationColumn;

    @FXML
    private TextField startingStationTextField;

    @FXML
    private Label licenseCategoryLabel;

    @FXML
    private Label licenseDateOfIssueLabel;

    @FXML
    private Label licenseExpirationDateLabel;

    @FXML
    private Label vehicleBrankLabel;

    @FXML
    private Label vehicleInsuranceExpirationDateLabel;

    @FXML
    private Label vehicleModelLabel;

    @FXML
    private Label vehiclePlatesLabel;

    @FXML
    private Label vehicleProductionDateLabel;

    @FXML
    private Label vehicleVinLabel;

    private ObservableList<AnnouncementsData> announcementsDataObservableList;

    private ObservableList<UserData> passengersDataObservableList;

    @FXML
    private void addNewAnnouncement() throws IOException {
        DriverData driverData = new DriverData();
        driverData.setUserID(ServerController.currentSessionUser.getId());

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

    private void announcementsShowListData(){
        AnnouncementsData announcementsData = new AnnouncementsData();

        List<AnnouncementsData> records = (List<AnnouncementsData>) ServerController.sendSelectAllRequest(announcementsData);

        announcementsDataObservableList = FXCollections.observableArrayList(records);

        announcementIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        startingStationColumn.setCellValueFactory(new PropertyValueFactory<>("startingStation"));
        destinationColumn.setCellValueFactory(new PropertyValueFactory<>("destination"));
        departureDateColumn.setCellValueFactory(new PropertyValueFactory<>("departureDate"));
        seatAvailableColumn.setCellValueFactory(new PropertyValueFactory<>("seatsAvailable"));
        announcementDateOfAddColumn.setCellValueFactory(new PropertyValueFactory<>("DateOfAddAnnouncement"));
        joinBtnColumn.setCellFactory(new Callback<TableColumn<AnnouncementsData, Integer>, TableCell<AnnouncementsData, Integer>>() {
            @Override
            public TableCell<AnnouncementsData, Integer> call(TableColumn<AnnouncementsData, Integer> param) {
                return new TableCell<AnnouncementsData, Integer>() {
                    private final Button joinButton = new Button("Dołącz");

                    {
                        joinButton.getStylesheets().add("dashBoardDesign.css");
                        joinButton.getStyleClass().add("join-btn");

                        joinButton.setOnAction(event -> {
                            AnnouncementsData announcement = getTableView().getItems().get(getIndex());
                            joinToRide(announcement.getId());
                        });
                    }

                    @Override
                    protected void updateItem(Integer item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(joinButton);
                        }
                    }
                };
            }
        });



        annoucementsTableView.setItems(announcementsDataObservableList);
    }

    public static void joinToRide(int announcementId){
        PassengersData passengersData = new PassengersData();
        passengersData.setAnnouncementId(announcementId);
        passengersData.setUserId(ServerController.currentSessionUser.getId());
        int response = ServerController.sendCheckIfAlreadyInRideRequest(passengersData);
        if(response == 0)
        {
            int response2 = ServerController.sendInsertRequest(passengersData);

            if(response2 == 1)
            {
                Alerts.successAlert("Pomyślnie dołączono do przejazdu");
            } else if (response2 == 0) {
                Alerts.failureAlert("Dołączenie do przejazdu nie powiodło się");
            }

        }else if(response == 1){
            Alerts.failureAlert("Nie możesz dołączyć do przejazdu w którym już jesteś zarejestrowany");
        }
    }

    private void passengersShowListData(){
        PassengersData passengersData = new PassengersData();

        AnnouncementsData announcementsData = annoucementsTableView.getSelectionModel().getSelectedItem();

        passengersData.setAnnouncementId(announcementsData.getId());

        List<UserData> records = (List<UserData>) ServerController.sendSelectRequest(passengersData);

        passengersDataObservableList = FXCollections.observableArrayList(records);

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        mailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        passengersTable.setItems(passengersDataObservableList);

    }

    @FXML
    private void showAnnouncementDetails(){

        AnnouncementsData announcementsData = annoucementsTableView.getSelectionModel().getSelectedItem();

        DriverData driverData = new DriverData();
        driverData.setId(announcementsData.getDriverId());
        UserData userData = new UserData();
        DriverData receivedDriverData = ServerController.sendSelectUserIdByDriverIdRequest(driverData);
        userData.setId(receivedDriverData.getUserID());
        VehicleData vehicleData = new VehicleData();
        vehicleData.setUserID(receivedDriverData.getUserID());
        LicenseData licenseData = new LicenseData();
        licenseData.setUserID(receivedDriverData.getUserID());

        VehicleData receivedVehicleData = ServerController.sendSelectRequest(vehicleData);
        LicenseData receivedLicenseData = ServerController.sendSelectRequest(licenseData);

        UserData recivedUserData = ServerController.sendSelectRequest(userData);

        nameLabel.setText(recivedUserData.getName());
        lastNameLabel.setText(recivedUserData.getLastName());
        mailLabel.setText(recivedUserData.getEmail());
        licenseCategoryLabel.setText(receivedLicenseData.getCategory());
        licenseDateOfIssueLabel.setText(String.valueOf(receivedLicenseData.getDateOfIssueOfTheLicense()));
        licenseExpirationDateLabel.setText(String.valueOf(receivedLicenseData.getExpirationDateOfTheLicense()));
        vehicleBrankLabel.setText(receivedVehicleData.getBrand());
        vehicleModelLabel.setText(receivedVehicleData.getModel());
        vehicleProductionDateLabel.setText(String.valueOf(receivedVehicleData.getProductionDate()));
        vehiclePlatesLabel.setText(receivedVehicleData.getPlatesNumber());
        vehicleVinLabel.setText(receivedVehicleData.getVin());
        vehicleInsuranceExpirationDateLabel.setText(String.valueOf(receivedVehicleData.getInsuranceExpirationDate()));

        passengersShowListData();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        announcementsShowListData();
    }

}
