package SharerideClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;


/**
 * Kontroler interfejsu uzytkownika
 * @author Karol Przygoda
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
    private Label genderLabel;

    @FXML
    private Label gradeLabel;

    @FXML
    private Label joinDateLabel;

    @FXML
    private Label lastNameLabel;

    @FXML
    private Button logOutBtn;

    @FXML
    private Label mailLabel;

    @FXML
    private Button minBtn;

    @FXML
    private Label nameLabel;

    @FXML
    private Button notificationBtn;

    @FXML
    private AnchorPane notificationsPane;

    @FXML
    private Label phoneLabel;

    @FXML
    private Button profileBtn;

    @FXML
    private AnchorPane profilePane;

    @FXML
    private Button profile_deleteAccBtn;

    @FXML
    private Label profile_lastNameFieldValidationInfo;

    @FXML
    private Label profile_mailFieldValidationInfo;

    @FXML
    private Label profile_nameFieldValidationInfo;

    @FXML
    private Label profile_passwordFieldValidationInfo;

    @FXML
    private Label profile_phoneNumberFieldValidationInfo;

    @FXML
    private PasswordField profile_updateComfirmPasswordField;

    @FXML
    private ComboBox<String> profile_updateGenderComboBox;

    @FXML
    private TextField profile_updateLastNameField;

    @FXML
    private TextField profile_updateMailField;

    @FXML
    private TextField profile_updateNameField;

    @FXML
    private PasswordField profile_updatePasswordField;

    @FXML
    private TextField profile_updatePhoneNumberField;

    @FXML
    private Button profile_updateProfileBtn;

    @FXML
    private Button ridesBtn;

    @FXML
    private Label ridesCountLabel;

    @FXML
    private AnchorPane ridesPane;

    private boolean nameCheckFlag, lastNameCheckFlag, mailCheckFlag, phoneNumberCheckFlag, passwordCheckFlag;

    private final String[] genderList = {"Mężczyzna", "Kobieta", "Wole nie podawać"};

    /**
     * Zamyka aplikację i kończy jej działanie.
     * Metoda jest wywoływana po kliknięciu przycisku "closeBtn". Zamyka okno aplikacji oraz kończy jej działanie.
     * Metoda uzyskuje dostęp do obiektu Stage, reprezentującego
     * aktualne okno aplikacji, za pomocą metody `getScene().getWindow()`. Następnie wywołuje metodę `close()` na tym obiekcie,
     * powodując zamknięcie okna i zakończenie działania aplikacji.
     * @author Karol Przygoda
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
     * @author Karol Przygoda
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
     * @author Karol PRzygoda
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
     * @author Karol Przygoda
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
     * Sprawdza czy użytkownik wprowadził nie wprowadził znaku nieporządanego
     * <p>
     * Metoda nasłuchuje na zmiany wprowadzone w pole tesktowe odpowiedzialne za wprowadzenie imienia.
     * Jeżeli użytkownik wprowadzi znak zakazany obramowanie pola tekstowego odpowiedzialnego za imie zmieni się na czerwone
     * aby uniemożliwić przejścia dalej użytkownikowi do momentu poprawienia wprowadzanego tekstu metoda aktualizuje flage nameCheckFlag
     * ponadto wyświetli sie krótki komunikat informujący użytkownika o problemie dzieje się to do momentu kiedy użytkownik nie poprawi pola tekstowego
     * @author Karol Przygoda
     */
    @FXML
    private void checkName() {
        profile_updateNameField.addEventFilter(KeyEvent.ANY, event -> {
            String currentText = profile_updateNameField.getText() + event.getCharacter();
            if (currentText.matches(".*[*!;@#$%^&()-=+{}|:',.<>/?_\\[\\]\"].*")) {
                profile_updateNameField.setStyle("-fx-border-color: red");
                profile_nameFieldValidationInfo.setVisible(true);
                nameCheckFlag = false;
            } else {
                profile_updateNameField.setStyle("");
                profile_nameFieldValidationInfo.setVisible(false);
                nameCheckFlag = true;
            }
        });
    }

    /**
     * Sprawdza czy użytkownik wprowadził nie wprowadził znaku nieporządanego
     * <p>
     * Metoda nasłuchuje na zmiany wprowadzone w pole tesktowe odpowiedzialne za wprowadzenie nazwiska.
     * Jeżeli użytkownik wprowadzi znak zakazany obramowanie pola tekstowego odpowiedzialnego za nazwisko zmieni się na czerwone
     * aby uniemożliwić przejścia dalej użytkownikowi do momentu poprawienia wprowadzanego tekstu metoda aktualizuje flage lastNameCheckFlag
     * ponadto wyświetli sie krótki komunikat informujący użytkownika o problemie dzieje się to do momentu kiedy użytkownik nie poprawi pola tekstowego
     * @author Karol Przygoda
     */
    @FXML
    private void checkLastName() {
       profile_updateLastNameField.addEventFilter(KeyEvent.ANY, event -> {
            String currentText = profile_updateLastNameField.getText() + event.getCharacter();
            if (currentText.matches(".*[*!;@#$%^&()-=+{}|:',.<>/?_\\[\\]\"].*")) {
                profile_updateLastNameField.setStyle("-fx-border-color: red");
                profile_lastNameFieldValidationInfo.setVisible(true);
                lastNameCheckFlag = false;
            } else {
                profile_updateLastNameField.setStyle("");
                profile_lastNameFieldValidationInfo.setVisible(false);
                lastNameCheckFlag = true;
            }
        });
    }

    /**
     * Sprawdza czy użytkownik wprowadził poprawny format adresu mailowego
     * <p>
     * Metoda nasłuchuje na zmiany wprowadzone w pole tesktowe odpowiedzialne za wprowadzenie adresu mailowego.
     * Jeżeli użytkownik wprowadzi adres mailowy w nieprawidłowym formacie obramowanie pola tekstowego odpowiedzialnego za adres mailowy zmieni się na czerwone
     * aby uniemożliwić przejścia dalej użytkownikowi do momentu poprawienia wprowadzanego tekstu metoda aktualizuje flage mailCheckFlag która
     * ponadto wyświetli sie krótki komunikat informujący użytkownika o problemie dzieje się to do momentu kiedy użytkownik nie poprawi pola tekstowego
     * @author Karol Przygoda
     */
    @FXML
    private void checkMail() {
        profile_updateMailField.addEventFilter(KeyEvent.ANY, event -> {
            String currentText = profile_updateMailField.getText();
            if (!currentText.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}")) {
                profile_mailFieldValidationInfo.setVisible(true);
                profile_updateMailField.setStyle("-fx-border-color: red");
                mailCheckFlag = false;
            } else {
                profile_mailFieldValidationInfo.setVisible(false);
                profile_updateMailField.setStyle("");
                mailCheckFlag = true;
            }
        });

    }

    /**
     * Sprawdza czy użytkownik wprowadził prawidłowy format numeru komórkowego
     * <p>
     * Metoda nasłuchuje na zmiany wprowadzone w pole tesktowe odpowiedzialne za wprowadzenie numeru komorkowego.
     * Jeżeli użytkownik wprowadzi znak zakazany lub wporwadzi numer o nieprawidłowym formacie obramowanie pola tekstowego odpowiedzialnego za numer komórkowy zmieni się na czerwone
     * aby uniemożliwić przejścia dalej użytkownikowi do momentu poprawienia wprowadzanego tekstu metoda aktualizuje flage phoneNumerCheckFlag
     * ponadto wyświetli sie krótki komunikat informujący użytkownika o problemie dzieje się to do momentu kiedy użytkownik nie poprawi pola tekstowego
     * @author Karol Przygoda
     */
    @FXML
    private void checkPhoneNumber() {
        profile_updatePhoneNumberField.addEventFilter(KeyEvent.ANY, event -> {
            String currentText = profile_updatePhoneNumberField.getText();
            if (!currentText.matches("\\d{9}") ) {
                profile_phoneNumberFieldValidationInfo.setVisible(true);
                profile_updatePhoneNumberField.setStyle("-fx-border-color: red");
                phoneNumberCheckFlag = false;
            } else {
                profile_phoneNumberFieldValidationInfo.setVisible(false);
                profile_updatePhoneNumberField.setStyle("");
                phoneNumberCheckFlag = true;
            }
        });

    }

    /**
     * Sprawdza czy użytkownik wprowadził prawidłowy format hasła
     * <p>
     * Metoda nasłuchuje na zmiany wprowadzone w pole tesktowe odpowiedzialne za wprowadzenie hasła.
     * Jeżeli użytkownik nie wprowadzi hasła o prawidłowym formacie obramowanie pola tekstowego odpowiedzialnego za hasło zmieni się na czerwone
     * aby uniemożliwić przejścia dalej użytkownikowi do momentu poprawienia wprowadzanego tekstu metoda aktualizuje flage passwordCheckFlag
     * ponadto wyświetli sie krótki komunikat informujący użytkownika o problemie dzieje się to do momentu kiedy użytkownik nie poprawi pola tekstowego
     * @author Karol Przygoda
     */
    @FXML
    private void checkPassword() {
        profile_updatePasswordField.addEventFilter(KeyEvent.ANY, event -> {
            String currentText = profile_updatePasswordField.getText() + event.getCharacter();
            if (!currentText.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#!$%^&-+=()])(?=\\S+$).{8,20}$")) {
                profile_passwordFieldValidationInfo.setVisible(true);
                profile_updatePasswordField.setStyle("-fx-border-color: red");
                passwordCheckFlag = false;
            } else {
                profile_passwordFieldValidationInfo.setVisible(false);
                profile_updatePasswordField.setStyle("");
                passwordCheckFlag = true;
            }
        });

    }

    /**
     * Sprawdza czy wszytskie pola w formularzu rejestracji są uzupełnione
     * <p>
     * @return true jeżeli wszystkie pola w formularzu rejestracji są uzupełnione false jeżeli jakieś pole nie jest uzupełnione
     * @author Karol Przygoda
     */
    @FXML
    private boolean checkIfEmpty() {
        return profile_updateNameField.getText().isEmpty()    // sprawdzanie czy wszytskie pola formularza rejestracji zostaly wypelnione
                || profile_updateLastNameField.getText().isEmpty()
                || profile_updateMailField.getText().isEmpty()
                || profile_updatePhoneNumberField.getText().isEmpty()
                || profile_updatePasswordField.getText().isEmpty()
                || profile_updateGenderComboBox.getValue() == null;

    }

    /**
     * Przypisuje opcje wyboru Płci dla comboBox-a
     * @author Karol Przygoda
     */
    private void Gender() {
        List<String> listS = new ArrayList<>();

        for (String data : genderList) {
            listS.add(data);
        }

        ObservableList listD = FXCollections.observableArrayList(listS);
        profile_updateGenderComboBox.setItems(listD);
    }

    /**
     * Inicjalizuje kontroler widoku po załadowaniu sceny.
     * Metoda jest wywoływana automatycznie po załadowaniu sceny i inicjalizuje stan początkowy kontrolera widoku.
     * Ustawia styl tła przycisku "announcementsBtn" na przezroczysty. Wyświetla nazwę klienta zalogowanego użytkownika.
     * @param url             URL sceny, która została załadowana
     * @param resourceBundle zasoby powiązane z kontrolerem widoku
     * @Author Karol Przygoda
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        announcementsBtn.setStyle("-fx-background-color: rgba(255, 255, 255, 0.3)");

        Gender();

        try {
           ServerController.sendClientNameRequest(ServerController.id);
           String name = ServerController.getClientName();
           nameLabel.setText(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
