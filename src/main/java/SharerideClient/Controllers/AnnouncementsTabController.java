package SharerideClient.Controllers;

import Data.UserData;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class AnnouncementsTabController  implements Initializable {

    @FXML
    private TableColumn<Object, Object> AVGRatingColumn;

    @FXML
    private TableColumn<Object, Object> AgeColumn;

    @FXML
    private TableView<UserData> PassengersTable;

    @FXML
    private TableColumn<Object, Object> LastNameColumn;

    @FXML
    private TableColumn<Object, Object> MailColumn;

    @FXML
    private TableColumn<Object, Object> NameColumn;

    @FXML
    private TableColumn<Object, Object> PhoneNumberColumn;

    @FXML
    private TableColumn<Object, Object> RidesCountColumn;

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
