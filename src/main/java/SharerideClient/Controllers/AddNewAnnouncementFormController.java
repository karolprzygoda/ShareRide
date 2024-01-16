package SharerideClient.Controllers;

import Data.AnnouncementsData;
import Data.DriverData;
import Data.PassengersData;
import Data.VehicleData;
import SharerideClient.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class AddNewAnnouncementFormController implements Initializable {
    @FXML
    private Button addNewAnnouncementBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    private DatePicker departureDatePicker;

    @FXML
    private TextField destinationTextField;

    @FXML
    private Spinner<Integer> seatsAvailableSpinner;

    @FXML
    private TextField startingStationTextField;

    @FXML
    private void cancelAddingNewAnnouncement(){
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    private boolean checkIfEmpty() {
        return startingStationTextField.getText().isEmpty()
                || destinationTextField.getText().isEmpty()
                || departureDatePicker.getValue() == null;

    }

    public void seatsAvailableSpinner() {
        VehicleData vehicleData = new VehicleData();
        vehicleData.setUserID(ServerController.currentSessionUser.getId());
        vehicleData = ServerController.sendSelectRequest(vehicleData);
        SpinnerValueFactory<Integer> spinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, vehicleData.getAvailableSeats(), 1);
        seatsAvailableSpinner.setValueFactory(spinner);
    }
    @FXML
    private void addNewAnnouncement(){
        AnnouncementsData announcementsData = new AnnouncementsData();
        PassengersData passengersData = new PassengersData();
        DriverData driverData = new DriverData();
        driverData.setUserID(ServerController.currentSessionUser.getId());
        if(! checkIfEmpty())
        {
            java.util.Date currentDate = new java.util.Date();
            java.sql.Date sqlDate = new java.sql.Date(currentDate.getTime());
            announcementsData.setStartingStation(startingStationTextField.getText());
            announcementsData.setDestination(destinationTextField.getText());
            announcementsData.setDepartureDate(Date.valueOf(departureDatePicker.getValue()));
            announcementsData.setDateOfAddAnnouncement(sqlDate);
            announcementsData.setSeatsAvailable(seatsAvailableSpinner.getValue());
            DriverData upToDateDriverData = ServerController.sendSelectRequest(driverData);
            announcementsData.setDriverId(upToDateDriverData.getId());

            ServerController.sendInsertRequest(announcementsData);

            AnnouncementsData  upToDateAnnouncementData = ServerController.sendSelectRequest(announcementsData);

            passengersData.setAnnouncementId(upToDateAnnouncementData.getId());
            passengersData.setUserId(upToDateDriverData.getUserID());

            ServerController.sendInsertRequest(passengersData);

            Alerts.successAlert("Pomyślnie dodano nowe ogłoszenie !");

            addNewAnnouncementBtn.getScene().getWindow().hide();

        }else
            Alerts.failureAlert("Proszę uzupełnić wszystkie pola formularza");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        seatsAvailableSpinner();
    }
}
