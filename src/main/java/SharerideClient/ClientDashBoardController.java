package SharerideClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;


/**
 * Kontroler interfejsu uzytkownika
 */
public class ClientDashBoardController implements Initializable {

    @FXML
    private Button announcementsBtn;

    @FXML
    private AnchorPane announcmentsPane;

    @FXML
    private Button chatBtn;

    @FXML
    private AnchorPane chatPane;

    @FXML
    private Button closeBtn;

    @FXML
    private Button contactBtn;

    @FXML
    private AnchorPane contactPane;

    @FXML
    private Button logOutBtn;

    @FXML
    private Button minBtn;

    @FXML
    private Label nameLabel;

    @FXML
    private Button notificationBtn;

    @FXML
    private AnchorPane notificationsPane;

    @FXML
    private Button profileBtn;

    @FXML
    private AnchorPane profilePane;

    @FXML
    private Button ridesBtn;

    @FXML
    private AnchorPane ridesPane;

    /**
     * Zamyka aplikację i kończy jej działanie.
     * Metoda jest wywoływana po kliknięciu przycisku "closeBtn". Zamyka okno aplikacji oraz kończy jej działanie.
     * Metoda uzyskuje dostęp do obiektu Stage, reprezentującego
     * aktualne okno aplikacji, za pomocą metody `getScene().getWindow()`. Następnie wywołuje metodę `close()` na tym obiekcie,
     * powodując zamknięcie okna i zakończenie działania aplikacji.
     */
    @FXML
    private void close() {
        Stage stage = (Stage) closeBtn.getScene().getWindow();
        stage.close();
    }

    /**
     * Minimalizuje okno aplikacji.
     * Metoda jest wywoływana po kliknięciu przycisku "minBtn". Minimalizuje aktualne okno aplikacji, zmniejszając je do ikony
     * na pasku zadań. Metoda uzyskuje dostęp do obiektu Stage, reprezentującego aktualne okno aplikacji, za pomocą metody
     * `getScene().getWindow()`. Następnie wywołuje metodę `setIconified(true)` na tym obiekcie, powodując minimalizację okna.
     */
    @FXML
    private void minimize() {
        Stage stage = (Stage) minBtn.getScene().getWindow();
        stage.setIconified(true);
    }

    /**
     * Wylogowuje użytkownika z aplikacji.
     * Metoda jest wywoływana po kliknięciu przycisku "logOutBtn". Wyświetla potwierdzenie operacji za pomocą okna dialogowego
     * typu CONFIRMATION. Jeśli użytkownik potwierdzi operację, zostaje zarejestrowane wylogowanie w dzienniku zdarzeń,
     * aktualne okno aplikacji zostaje ukryte, a aplikacja powraca do ekranu logowania. W przypadku błędu podczas wylogowywania
     * zostaje zgłoszony wyjątek typu RuntimeException.
     *
     * @throws RuntimeException jeśli wystąpił błąd podczas wylogowywania użytkownika
     */
    @FXML
    private void logout() {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Potwierdź Operacje");
            alert.setHeaderText(null);
            alert.setContentText("Napewno chcesz się wylogować ?");

            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {
                logOutBtn.getScene().getWindow().hide();
                LoginView loginView = new LoginView();
                Stage stage = new Stage();
                loginView.start(stage);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Przełącza widoki między layoutami w aplikacji.
     * Metoda jest wywoływana po kliknięciu na przyciski nawigacji (announcementsBtn, profileBtn, ridesBtn, chatBtn, notificationBtn, contactBtn).
     * Na podstawie źródła zdarzenia (event.getSource()), metoda zmienia widoczność odpowiednich paneli oraz
     * dostosowuje styl tła przycisków nawigacji.
     * @param event zdarzenie, które wywołało metodę (kliknięcie na przycisk nawigacji)
     */
    @FXML
    private void switchForm(ActionEvent event) {
        if (event.getSource() == announcementsBtn) {
            announcmentsPane.setVisible(true);
            profilePane.setVisible(false);
            ridesPane.setVisible(false);
            chatPane.setVisible(false);
            notificationsPane.setVisible(false);
            contactPane.setVisible(false);


            announcementsBtn.setStyle("-fx-background-color: rgba(255, 255, 255, 0.3)");
            profileBtn.setStyle("-fx-background-color: transparent");
            ridesBtn.setStyle("-fx-background-color: transparent");
            chatBtn.setStyle("-fx-background-color: transparent");
            notificationBtn.setStyle("-fx-background-color: transparent");
            contactBtn.setStyle("-fx-background-color: transparent");
            

        } else if (event.getSource() == profileBtn) {
            announcmentsPane.setVisible(false);
            profilePane.setVisible(true);
            ridesPane.setVisible(false);
            chatPane.setVisible(false);
            notificationsPane.setVisible(false);
            contactPane.setVisible(false);

            announcementsBtn.setStyle("-fx-background-color: transparent");
            profileBtn.setStyle("-fx-background-color: rgba(255, 255, 255, 0.3)");
            ridesBtn.setStyle("-fx-background-color: transparent");
            chatBtn.setStyle("-fx-background-color: transparent");
            notificationBtn.setStyle("-fx-background-color: transparent");
            contactBtn.setStyle("-fx-background-color: transparent");


        } else if (event.getSource() == ridesBtn) {
            announcmentsPane.setVisible(false);
            profilePane.setVisible(false);
            ridesPane.setVisible(true);
            chatPane.setVisible(false);
            notificationsPane.setVisible(false);
            contactPane.setVisible(false);

            announcementsBtn.setStyle("-fx-background-color: transparent");
            profileBtn.setStyle("-fx-background-color: transparent");
            ridesBtn.setStyle("-fx-background-color: rgba(255, 255, 255, 0.3)");
            chatBtn.setStyle("-fx-background-color: transparent");
            notificationBtn.setStyle("-fx-background-color: transparent");
            contactBtn.setStyle("-fx-background-color: transparent");

        } else if (event.getSource() == chatBtn) {
            announcmentsPane.setVisible(false);
            profilePane.setVisible(false);
            ridesPane.setVisible(false);
            chatPane.setVisible(true);
            notificationsPane.setVisible(false);
            contactPane.setVisible(false);

            announcementsBtn.setStyle("-fx-background-color: transparent");
            profileBtn.setStyle("-fx-background-color: transparent");
            ridesBtn.setStyle("-fx-background-color: transparent");
            chatBtn.setStyle("-fx-background-color: rgba(255, 255, 255, 0.3)");
            notificationBtn.setStyle("-fx-background-color: transparent");
            contactBtn.setStyle("-fx-background-color: transparent");

        } else if (event.getSource() == notificationBtn) {
            announcmentsPane.setVisible(false);
            profilePane.setVisible(false);
            ridesPane.setVisible(false);
            chatPane.setVisible(false);
            notificationsPane.setVisible(true);
            contactPane.setVisible(false);

            announcementsBtn.setStyle("-fx-background-color: transparent");
            profileBtn.setStyle("-fx-background-color: transparent");
            ridesBtn.setStyle("-fx-background-color: transparent");
            chatBtn.setStyle("-fx-background-color: transparent");
            notificationBtn.setStyle("-fx-background-color: rgba(255, 255, 255, 0.3)");
            contactBtn.setStyle("-fx-background-color: transparent");

        } else if (event.getSource() == contactBtn) {
            announcmentsPane.setVisible(false);
            profilePane.setVisible(false);
            ridesPane.setVisible(false);
            chatPane.setVisible(false);
            notificationsPane.setVisible(false);
            contactPane.setVisible(true);

            announcementsBtn.setStyle("-fx-background-color: transparent");
            profileBtn.setStyle("-fx-background-color: transparent");
            ridesBtn.setStyle("-fx-background-color: transparent");
            chatBtn.setStyle("-fx-background-color: transparent");
            notificationBtn.setStyle("-fx-background-color: transparent");
            contactBtn.setStyle("-fx-background-color: rgba(255, 255, 255, 0.3)");
        }
    }

    /**
     * Inicjalizuje kontroler widoku po załadowaniu sceny.
     * Metoda jest wywoływana automatycznie po załadowaniu sceny i inicjalizuje stan początkowy kontrolera widoku.
     * Ustawia styl tła przycisku "announcementsBtn" na przezroczysty. Wyświetla nazwę klienta zalogowanego użytkownika.
     * @param url             URL sceny, która została załadowana
     * @param resourceBundle zasoby powiązane z kontrolerem widoku
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        announcementsBtn.setStyle("-fx-background-color: rgba(255, 255, 255, 0.3)");

        try {
           ServerController.sendClientNameRequest(ServerController.id);
           String name = ServerController.getClientName();
           nameLabel.setText(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
