package SharerideClient.Controllers;

import Data.UserData;
import SharerideClient.Views.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.*;

import javafx.fxml.Initializable;

import static SharerideClient.Views.FormsContainer.*;

/**
 * Kontroler interfejsu graficznego głównego panelu użytkownika
 * @see ClientDashBoardView
 * @author Karol Przygoda
 */
public class ClientDashBoardController implements Initializable {

    /**
     * Przycisk odpowiedzialny za przejście do zakładni powiadomień
     * @see ClientDashBoardController#switchToAnnouncements() 
     * @see ClientDashBoardController#setButtonStyle(Button) 
     */
    @FXML
    private Button announcementsBtn;

    /**
     * Zakładka powiadomień
     * @see ClientDashBoardController#switchToAnnouncements()
     * @see ClientDashBoardController#setVisibility(Pane)  
     */
    @FXML
    private AnchorPane announcementsPane;

    /**
     * Pole przechowujące zawartość wczytywaną do zakładki powiadomień
     * @see ClientDashBoardController#switchToAnnouncements()
     * @see ClientDashBoardController#initialize(URL, ResourceBundle)
     */
    @FXML
    private VBox announcementsTabContainer;

    /**
     * Przycisk przekierowujący do zakładki z chatem
     * @see ClientDashBoardController#switchToChat()
     * @see ClientDashBoardController#setButtonStyle(Button)
     */
    @FXML
    private Button chatBtn;

    @FXML
    private ScrollPane driverPaneContainer;

    /**
     * Zakładka powiadomień
     * @see ClientDashBoardController#switchToChat()
     * @see ClientDashBoardController#setVisibility(Pane)
     */
    @FXML
    private AnchorPane chatPane;

    /**
     * Pole przechowujące zawartość wczytywaną do zakładki powiadomień
     * @see ClientDashBoardController#switchToChat()
     * @see ClientDashBoardController#initialize(URL, ResourceBundle)
     */
    @FXML
    private VBox chatTabContainer;

    /**
     * Przycisk odpowiedzialny za zamknięcie aplikacji
     * @see ClientDashBoardController#close()
     */
    @FXML
    private Button closeBtn;

    /**
     * Pole przechowujące zawartość wczytywaną do zakładki informacji kontaktowych z administratorami
     * @see ClientDashBoardController#switchToContact()
     * @see ClientDashBoardController#initialize(URL, ResourceBundle)
     */
    @FXML
    private VBox contactTabContainer;

    /**
     * Przycisk przekierowujący do zakładki z informacjami kontaktowymi z administratorami
     * @see ClientDashBoardController#switchToContact()
     * @see ClientDashBoardController#setButtonStyle(Button)
     */
    @FXML
    private Button contactBtn;

    /**
     * Zakładka z informacjami kontaktowymi z administratorami
     * @see ClientDashBoardController#switchToContact()
     * @see ClientDashBoardController#setVisibility(Pane)
     */
    @FXML
    private AnchorPane contactPane;

    /**
     * Przycisk przekierowujący do zakładki kierowcy
     * @see ClientDashBoardController#switchToDriver()
     * @see ClientDashBoardController#setButtonStyle(Button)
     */
    @FXML
    private Button driverBtn;

    /**
     * Zakładka kierowcy
     * @see ClientDashBoardController#switchToDriver()
     * @see ClientDashBoardController#setVisibility(Pane)
     */
    @FXML
    private AnchorPane driverPane;


    /**
     * Imię użytkownika zalogowanego w bieżącej sesji
     * @see ClientDashBoardController#initialize(URL, ResourceBundle)
     */
    @FXML
    public Label helloClientNameLabel;

    /**
     * Przycisk odpowiedzialny za wylogowanie użytkownika
     * @see ClientDashBoardController#logout()
     */
    @FXML
    private Button logOutBtn;

    /**
     * Przycisk odpowiedzialny za minimalizację okna
     * @see ClientDashBoardController#minimize()
     */
    @FXML
    private Button minBtn;

    /**
     * Przycisk przekierowujący do zakładki powiadomień
     * @see ClientDashBoardController#switchToNotifications()
     * @see ClientDashBoardController#setButtonStyle(Button)
     */
    @FXML
    private Button notificationBtn;

    /**
     * Pole przechowujące zawartość wczytywaną do zakładki powiadomień
     * @see ClientDashBoardController#switchToNotifications()
     * @see ClientDashBoardController#initialize(URL, ResourceBundle)
     */
    @FXML
    private VBox notificationTabContainer;

    /**
     * Zakładka powiadomień
     * @see ClientDashBoardController#switchToNotifications()
     * @see ClientDashBoardController#setVisibility(Pane)
     */
    @FXML
    private AnchorPane notificationsPane;

    /**
     * Przycisk przekierowujący do zakładki profilu użytkownika
     * @see ClientDashBoardController#switchToProfile()
     * @see ClientDashBoardController#setButtonStyle(Button)
     */
    @FXML
    private Button profileBtn;

    /**
     * Zakładka profilu użytkownika
     * @see ClientDashBoardController#switchToProfile()
     * @see ClientDashBoardController#setVisibility(Pane)
     */
    @FXML
    private AnchorPane profilePane;

    /**
     * Przycisk przekierowujący do zakładki przejazdów
     * @see ClientDashBoardController#switchToRides()
     * @see ClientDashBoardController#setButtonStyle(Button)
     */
    @FXML
    private Button ridesBtn;

    /**
     * Zakładka przejazdów
     * @see ClientDashBoardController#switchToRides()
     * @see ClientDashBoardController#setVisibility(Pane)
     */
    @FXML
    private AnchorPane ridesPane;

    /**
     * Pole przechowujące zawartość wczytywaną do zakładki przejazdów
     * @see ClientDashBoardController#switchToRides()
     * @see ClientDashBoardController#initialize(URL, ResourceBundle)
     */
    @FXML
    private VBox ridesTabContainer;

    protected static UserData userData;


    /**
     * Zamyka aplikację i kończy jej działanie.
     * <p>
     * Metoda jest wywoływana po kliknięciu przycisku {@linkplain ClientDashBoardController#closeBtn}. Zamyka połączenie z serwerem, zamyka okno aplikacji oraz kończy jej działanie.
     * Metoda uzyskuje dostęp do obiektu Stage, reprezentującego
     * aktualne okno aplikacji, za pomocą metody {@linkplain Stage#getScene()} . Następnie wywołuje metodę {@linkplain Stage#close()} na tym obiekcie,
     * powodując zamknięcie okna i zakończenie działania aplikacji.
     * @throws IOException jeżeli nastąpił błąd podczas rozłączenia z serwerem
     * @author Karol Przygoda
     */
    @FXML
    private void close() throws IOException {
        out.close();
        input.close();
        socket.close();
        Stage stage = (Stage) closeBtn.getScene().getWindow();
        stage.close();
    }

    /**
     * Minimalizuje okno aplikacji.
     * <p>
     * Metoda jest wywoływana po kliknięciu przycisku {@linkplain ClientDashBoardController#minBtn}. Minimalizuje aktualne okno aplikacji, zmniejszając je do ikony
     * na pasku zadań. Metoda uzyskuje dostęp do obiektu Stage, reprezentującego aktualne okno aplikacji, za pomocą metody {@linkplain Stage#getScene()}
     * . Następnie wywołuje metodę {@linkplain Stage#setIconified(boolean)} na tym obiekcie, powodując minimalizację okna.
     * @author Karol Przygoda
     */
    @FXML
    private void minimize() {
        Stage stage = (Stage) minBtn.getScene().getWindow();
        stage.setIconified(true);
    }

    /**
     * Wylogowuje użytkownika z aplikacji.
     * Metoda jest wywoływana po kliknięciu przycisku {@linkplain ClientDashBoardController#logOutBtn}. Wyświetla potwierdzenie operacji za pomocą okna dialogowego
     * typu CONFIRMATION. Jeśli użytkownik potwierdzi operację, zostaje zarejestrowane wylogowanie w dzienniku zdarzeń,
     * aktualne okno aplikacji zostaje ukryte, a aplikacja powraca do ekranu logowania.
     * @throws RuntimeException jeśli wystąpił błąd podczas wylogowywania użytkownika
     * @author Karol Przygoda
     */
    @FXML
    private void logout() {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Potwierdź Operacje");
            alert.setHeaderText(null);
            alert.setContentText("Na pewno chcesz się wylogować ?");

            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {
                logOutBtn.getScene().getWindow().hide();
                FormsContainer loginView = new FormsContainer();
                Stage stage = new Stage();
                loginView.start(stage);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Przełącza widoki na zakładkę ogłoszeń.
     * Metoda jest wywoływana po kliknięciu przyciski nawigacji {@linkplain ClientDashBoardController#announcementsBtn}.
     * Wywołuję ona dwie inne metody {@linkplain ClientDashBoardController#setVisibility(Pane)}, {@linkplain ClientDashBoardController#setButtonStyle(Button)}
     * @see ClientDashBoardController#setVisibility(Pane)
     * @see ClientDashBoardController#setButtonStyle(Button)
     * @author Karol Przygoda
     */
    @FXML
    private void switchToAnnouncements() {
        setVisibility(announcementsPane);
        setButtonStyle(announcementsBtn);
    }

    /**
     * Przełącza widoki na zakładkę profilu użytkownika.
     * Metoda jest wywoływana po kliknięciu przyciski nawigacji {@linkplain ClientDashBoardController#profileBtn}.
     * Wywołuję ona dwie inne metody {@linkplain ClientDashBoardController#setVisibility(Pane)}, {@linkplain ClientDashBoardController#setButtonStyle(Button)}
     * @see ClientDashBoardController#setVisibility(Pane)
     * @see ClientDashBoardController#setButtonStyle(Button)
     * @author Karol Przygoda
     */
    @FXML
    private void switchToProfile() {
        setVisibility(profilePane);
        setButtonStyle(profileBtn);
    }

    /**
     * Przełącza widoki na zakładkę ogłoszeń.
     * Metoda jest wywoływana po kliknięciu przyciski nawigacji {@linkplain ClientDashBoardController#driverBtn}.
     * Wywołuję ona dwie inne metody {@linkplain ClientDashBoardController#setVisibility(Pane)}, {@linkplain ClientDashBoardController#setButtonStyle(Button)}
     * @see ClientDashBoardController#setVisibility(Pane)
     * @see ClientDashBoardController#setButtonStyle(Button)
     * @author Karol Przygoda
     */
    @FXML
    private void switchToDriver() {
        setVisibility(driverPaneContainer);
        setButtonStyle(driverBtn);
    }

    /**
     * Przełącza widoki na zakładkę ogłoszeń.
     * Metoda jest wywoływana po kliknięciu przyciski nawigacji {@linkplain ClientDashBoardController#ridesBtn}.
     * Wywołuję ona dwie inne metody {@linkplain ClientDashBoardController#setVisibility(Pane)}, {@linkplain ClientDashBoardController#setButtonStyle(Button)}
     * @see ClientDashBoardController#setVisibility(Pane)
     * @see ClientDashBoardController#setButtonStyle(Button)
     * @author Karol Przygoda
     */
    @FXML
    private void switchToRides() {
        setVisibility(ridesPane);
        setButtonStyle(ridesBtn);
    }

    /**
     * Przełącza widoki na zakładkę ogłoszeń.
     * Metoda jest wywoływana po kliknięciu przyciski nawigacji {@linkplain ClientDashBoardController#closeBtn}.
     * Wywołuję ona dwie inne metody {@linkplain ClientDashBoardController#setVisibility(Pane)}, {@linkplain ClientDashBoardController#setButtonStyle(Button)}
     * @see ClientDashBoardController#setVisibility(Pane)
     * @see ClientDashBoardController#setButtonStyle(Button)
     * @author Karol Przygoda
     */
    @FXML
    private void switchToChat() {
        setVisibility(chatPane);
        setButtonStyle(chatBtn);
    }

    /**
     * Przełącza widoki na zakładkę ogłoszeń.
     * Metoda jest wywoływana po kliknięciu przyciski nawigacji {@linkplain ClientDashBoardController#notificationBtn}.
     * Wywołuję ona dwie inne metody {@linkplain ClientDashBoardController#setVisibility(Pane)}, {@linkplain ClientDashBoardController#setButtonStyle(Button)}
     * @see ClientDashBoardController#setVisibility(Pane)
     * @see ClientDashBoardController#setButtonStyle(Button)
     * @author Karol Przygoda
     */
    @FXML
    private void switchToNotifications() {
        setVisibility(notificationsPane);
        setButtonStyle(notificationBtn);
    }

    /**
     * Przełącza widoki na zakładkę ogłoszeń.
     * Metoda jest wywoływana po kliknięciu przyciski nawigacji {@linkplain ClientDashBoardController#closeBtn}.
     * Wywołuję ona dwie inne metody {@linkplain ClientDashBoardController#setVisibility(Pane)}, {@linkplain ClientDashBoardController#setButtonStyle(Button)}
     * @see ClientDashBoardController#setVisibility(Pane)
     * @see ClientDashBoardController#setButtonStyle(Button)
     * @author Karol Przygoda
     */
    @FXML
    private void switchToContact() {
        setVisibility(contactPane);
        setButtonStyle(contactBtn);
    }

    /**
     * Ustawia aktualnie używaną zakładkę na widoczną
     * @param pane aktualnie używana zakładka, która ma być widoczna
     * @see ClientDashBoardController#switchToRides()
     * @see ClientDashBoardController#switchToProfile()
     * @see ClientDashBoardController#switchToNotifications()
     * @see ClientDashBoardController#switchToContact()
     * @see ClientDashBoardController#switchToAnnouncements()
     * @see ClientDashBoardController#switchToChat()
     * @see ClientDashBoardController#switchToDriver()
     * @author Karol Przygoda
     */
    private void setVisibility(Pane pane) {
        announcementsPane.setVisible(false);
        driverPaneContainer.setVisible(false);
        profilePane.setVisible(false);
        ridesPane.setVisible(false);
        chatPane.setVisible(false);
        notificationsPane.setVisible(false);
        contactPane.setVisible(false);

        pane.setVisible(true);
    }

    private void setVisibility(ScrollPane pane) {
        announcementsPane.setVisible(false);
        driverPaneContainer.setVisible(false);
        profilePane.setVisible(false);
        ridesPane.setVisible(false);
        chatPane.setVisible(false);
        notificationsPane.setVisible(false);
        contactPane.setVisible(false);

        pane.setVisible(true);
    }


    /**
     * Ustawia styl ostatnio naciśniętego przycisku w celu zobrazowania, w której zakładce użytkownik aktualnie się znajduję
     * @param selectedButton ostatnio naciśnięty przycisk, którego styl ma być aktualizowany
     * @author Karol Przygoda
     */
    private void setButtonStyle(Button selectedButton) {
        announcementsBtn.setStyle("-fx-background-color: transparent");
        profileBtn.setStyle("-fx-background-color: transparent");
        driverBtn.setStyle("-fx-background-color: transparent");
        ridesBtn.setStyle("-fx-background-color: transparent");
        chatBtn.setStyle("-fx-background-color: transparent");
        notificationBtn.setStyle("-fx-background-color: transparent");
        contactBtn.setStyle("-fx-background-color: transparent");

        selectedButton.setStyle("-fx-background-color: rgba(255, 255, 255, 0.3)");
    }


    /**
     * Inicjalizuje kontroler widoku po załadowaniu sceny.
     * Metoda jest wywoływana automatycznie po załadowaniu sceny i inicjalizuje stan początkowy kontrolera widoku.
     * Ustawia styl tła przycisku {@linkplain ClientDashBoardController#announcementsBtn} na przezroczysty, ponieważ jest to zakładka główna.
     * Wyświetla nazwę klienta zalogowanego użytkownika.
     * Wczytuję pliki fxml zawierające interfejs graficzny zakładek w odpowiednie miejsca
     * @param url             URL sceny, która została załadowana
     * @param resourceBundle zasoby powiązane z kontrolerem widoku
     * @author Karol Przygoda
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        announcementsBtn.setStyle("-fx-background-color: rgba(255, 255, 255, 0.3)");

        try {

            userData = new UserData();

            userData = ServerController.sendSelectRequest(userData);

            helloClientNameLabel.setText(userData.getName());

            UserProfileTabView userProfileTabView = new UserProfileTabView();
            AnnouncementsTabView announcementsTabView = new AnnouncementsTabView();
            DriverTabView driverTabView = new DriverTabView();
            RidesTabView ridesTabView = new RidesTabView();
            ChatTabView chatTabView = new ChatTabView();
            NotificationTabView notificationTabView = new NotificationTabView();
            ContactTabView contactTabView = new ContactTabView();
            profilePane.getChildren().add(userProfileTabView.initialize());
            announcementsTabContainer.getChildren().add(announcementsTabView.initialize());
            driverPane.getChildren().add(driverTabView.initialize());
            ridesTabContainer.getChildren().add(ridesTabView.initialize());
            chatTabContainer.getChildren().add(chatTabView.initialize());
            notificationTabContainer.getChildren().add(notificationTabView.initialize());
            contactTabContainer.getChildren().add(contactTabView.initialize());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
