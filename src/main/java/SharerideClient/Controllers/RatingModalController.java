package SharerideClient.Controllers;

import Data.PassengersData;
import Data.RatingData;
import Data.UserData;
import SharerideClient.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class RatingModalController implements Initializable {
    @FXML
    private Button cancelBtn;

    @FXML
    private Button rateBtn;

    @FXML
    private Spinner<Integer> ratingSpinner;

    private int announcementId;

    public int getAnnouncementId() {
        return announcementId;
    }

    public void setAnnouncementId(int announcementId) {
        this.announcementId = announcementId;
    }


    private void ratingSpinner(){
        SpinnerValueFactory<Integer> spinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 5, 1);
        ratingSpinner.setValueFactory(spinner);
    }

    @FXML
    private void cancelAddingNewAnnouncement(){
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void sendRating(){
        PassengersData passengersData = new PassengersData();
        passengersData.setAnnouncementId(announcementId);
        List<RatingData> ratings = new ArrayList<>();

        List<UserData> records = (List<UserData>) ServerController.sendSelectRequest(passengersData);

        for (UserData record : records) {

            RatingData ratingData = new RatingData();

            ratingData.setAnnouncement_id(announcementId);
            ratingData.setUser_id(record.getId());
            ratingData.setRater_id(ServerController.currentSessionUser.getId());
            ratingData.setRating(ratingSpinner.getValue());

            ratings.add(ratingData);

        }


        int response = ServerController.sendInsertRequest(ratings);

        if(response == 1){
            Alerts.successAlert("Poprawnie oceniono przejazd!");
        }else if( response == 0){
            Alerts.failureAlert("Wystawiłeś już ocenę dla tego przejazdu");
        }

        cancelAddingNewAnnouncement();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        ratingSpinner();
    }

}
