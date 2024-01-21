package SharerideClient.Controllers;

import Data.*;
import SharerideClient.Alerts;
import SharerideClient.Views.FormsContainer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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

public class RidesController implements Initializable {
    @FXML
    private TableColumn<AnnouncementsData, Date> finishedRidesDateOfAddColumn;

    @FXML
    private TableColumn<AnnouncementsData, Date> finishedRidesDepartureDateColumn;

    @FXML
    private TableColumn<AnnouncementsData, String> finishedRidesDestinationColumn;

    @FXML
    private TableColumn<AnnouncementsData, Integer> finishedRidesIdColumn;

    @FXML
    private TableColumn<AnnouncementsData, Integer> finishedRidesSeatAvailableColumn;

    @FXML
    private TableColumn<AnnouncementsData, String> finishedRidesStartingStationColumn;

    @FXML
    private TableView<AnnouncementsData> finishedRidesTableView;

    @FXML
    private TableColumn<AnnouncementsData, String> incomingRidesStartingStationColumn;

    @FXML
    private TableColumn<AnnouncementsData, Date> incomingRidesDateOfAddColumn;

    @FXML
    private TableColumn<AnnouncementsData, Integer> incomingRidesCancelRideBtnsColumn;

    @FXML
    private TableColumn<AnnouncementsData, Date> incomingRidesDepartureDateColumn;

    @FXML
    private TableColumn<AnnouncementsData, String> incomingRidesDestinationColumn;

    @FXML
    private TableColumn<AnnouncementsData, Integer> incomingRidesIdColumn;

    @FXML
    private TableColumn<AnnouncementsData, Integer> incomingRidesSeatAvailableColumn;

    @FXML
    private TableView<AnnouncementsData> incomingRidesTableView;

    @FXML
    private TableColumn<AnnouncementsData, Integer> rateRideBtnsColumn;

    private ObservableList<AnnouncementsData> incomingRidesDataObservableList;

    private ObservableList<AnnouncementsData> finishedRidesDataObservableList;

    public void cancelRide(int id){
        PassengersData passengersData = new PassengersData();
        DriverData driverData = new DriverData();
        driverData.setUserID(ServerController.currentSessionUser.getId());
        passengersData.setUserId(ServerController.currentSessionUser.getId());
        passengersData.setAnnouncementId(id);
        DriverData isDriverOfThisRide = new DriverData();
        isDriverOfThisRide = ServerController.sendSelectRequest(driverData);

        int response;

        if(isDriverOfThisRide.getId() > 0){
            AnnouncementsData announcementsData = new AnnouncementsData();
            announcementsData.setId(id);
            response = ServerController.sendDeleteRequest(announcementsData);
        }else {

            response = ServerController.sendDeleteRequest(passengersData);
        }
        if(response == 1)
        {
            Alerts.successAlert("Pomyślnie udało sie anulować przejazd");
            incomingRidesShowListData();
        } else if (response == 0) {
            Alerts.failureAlert("Nie udało się anulować przejazdu");
        }
    }

    public void rateAnnouncement(int id) throws IOException {

        Stage modalStage = new Stage();
        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.initStyle(StageStyle.UTILITY);
        modalStage.setTitle("Oceń przejazd");

        FXMLLoader fxmlLoader = new FXMLLoader(FormsContainer.class.getResource("RatingModal.fxml"));
        Parent root = fxmlLoader.load();

        RatingModalController controller = fxmlLoader.getController();

        controller.setAnnouncementId(id);

        Scene scene = new Scene(root, 450, 250);
        modalStage.initStyle(StageStyle.TRANSPARENT);
        modalStage.setScene(scene);
        modalStage.showAndWait();





    }

    public void  incomingRidesShowListData(){
        AnnouncementsData announcementsData = new AnnouncementsData();

        UserData userData = new UserData();
        userData.setId(ServerController.currentSessionUser.getId());

        List<AnnouncementsData> records = (List<AnnouncementsData>) ServerController.sendSelectUserIncomingRidesRequest(userData);

        incomingRidesDataObservableList = FXCollections.observableArrayList(records);

        incomingRidesIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        incomingRidesStartingStationColumn.setCellValueFactory(new PropertyValueFactory<>("startingStation"));
        incomingRidesDestinationColumn.setCellValueFactory(new PropertyValueFactory<>("destination"));
        incomingRidesDepartureDateColumn.setCellValueFactory(new PropertyValueFactory<>("departureDate"));
        incomingRidesSeatAvailableColumn.setCellValueFactory(new PropertyValueFactory<>("seatsAvailable"));
        incomingRidesDateOfAddColumn.setCellValueFactory(new PropertyValueFactory<>("DateOfAddAnnouncement"));
        incomingRidesCancelRideBtnsColumn.setCellFactory(new Callback<TableColumn<AnnouncementsData, Integer>, TableCell<AnnouncementsData, Integer>>() {
            @Override
            public TableCell<AnnouncementsData, Integer> call(TableColumn<AnnouncementsData, Integer> param) {
                return new TableCell<AnnouncementsData, Integer>() {
                    private final Button cancelBtn = new Button("Anuluj");

                    {
                        cancelBtn.getStylesheets().add("dashBoardDesign.css");
                        cancelBtn.getStyleClass().add("join-btn");

                        cancelBtn.setOnAction(event -> {
                            AnnouncementsData announcement = getTableView().getItems().get(getIndex());
                            cancelRide(announcement.getId());
                        });
                    }

                    @Override
                    protected void updateItem(Integer item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(cancelBtn);
                        }
                    }
                };
            }
        });



        incomingRidesTableView.setItems(incomingRidesDataObservableList);
    }

    public void  finishedRidesShowListData(){
        AnnouncementsData announcementsData = new AnnouncementsData();

        UserData userData = new UserData();
        userData.setId(ServerController.currentSessionUser.getId());

        List<AnnouncementsData> records = (List<AnnouncementsData>) ServerController.sendSelectUserFinishedRidesRequest(userData);

        finishedRidesDataObservableList = FXCollections.observableArrayList(records);

        finishedRidesIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        finishedRidesStartingStationColumn.setCellValueFactory(new PropertyValueFactory<>("startingStation"));
        finishedRidesDestinationColumn.setCellValueFactory(new PropertyValueFactory<>("destination"));
        finishedRidesDepartureDateColumn.setCellValueFactory(new PropertyValueFactory<>("departureDate"));
        finishedRidesSeatAvailableColumn.setCellValueFactory(new PropertyValueFactory<>("seatsAvailable"));
        finishedRidesDateOfAddColumn.setCellValueFactory(new PropertyValueFactory<>("DateOfAddAnnouncement"));
        rateRideBtnsColumn.setCellFactory(new Callback<TableColumn<AnnouncementsData, Integer>, TableCell<AnnouncementsData, Integer>>() {
            @Override
            public TableCell<AnnouncementsData, Integer> call(TableColumn<AnnouncementsData, Integer> param) {
                return new TableCell<AnnouncementsData, Integer>() {
                    private final Button rateBtn = new Button("Oceń");

                    {
                        rateBtn.getStylesheets().add("dashBoardDesign.css");
                        rateBtn.getStyleClass().add("join-btn");

                        rateBtn.setOnAction(event -> {
                            AnnouncementsData announcement = getTableView().getItems().get(getIndex());
                            try {
                                rateAnnouncement(announcement.getId());
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });
                    }

                    @Override
                    protected void updateItem(Integer item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(rateBtn);
                        }
                    }
                };
            }
        });



       finishedRidesTableView.setItems(finishedRidesDataObservableList);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        incomingRidesShowListData();
        finishedRidesShowListData();
    }
}
