package SharerideClient;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;

/**
 * Kontroler interfejsu logowania i rejestracji konta
 *
 * @author Karol Przygoda
 */
public class LoginController {

    @FXML
    private Button closeBtn;

    @FXML
    private Button loginBtn;

    @FXML
    private TextField mailTextField;

    @FXML
    private Button minBtn;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Button registerBtn;

    @FXML
    private DatePicker register_datePickerTextField;

    @FXML
    private TextField register_lastNameTextField;

    @FXML
    private Button register_loginBtn;

    @FXML
    private TextField register_mailTextField;

    @FXML
    private TextField register_nameTextField;

    @FXML
    private PasswordField register_passwordConfirmTextField;

    @FXML
    private PasswordField register_passwordTextField;

    @FXML
    private TextField register_phoneNumberTextField;

    @FXML
    private AnchorPane sidePane;

    @FXML
    private Label lastNameFieldValidationInfo;
    @FXML
    private Label mailFieldValidationInfo;

    @FXML
    private Label nameFieldValidationInfo;

    @FXML
    private Label passwordFieldValidationInfo;

    @FXML
    private Label phoneNumberFieldValidationInfo;

    private boolean nameCheckFlag, lastNameCheckFlag, mailCheckFlag, phoneNumberCheckFlag, passwordCheckFlag;


    /**
     * Metoda odpowiedzialna za przełączanie formularzy rejestracji i logowania.
     * <p>
     * Metoda `switchForm()` obsługuje zdarzenie akcji, które występuje po kliknięciu
     * przycisku rejestracji lub przycisku powrotu do logowania. Metoda animuje przesunięcie
     * formularza na bocznym panelu w celu wyświetlenia odpowiedniego formularza. Przesunięcie
     * wykonywane jest za pomocą efektu TranslateTransition.
     *
     * @param event Zdarzenie akcji wywołane przez kliknięcie przycisku rejestracji lub powrotu do logowania.
     *
     * @author Karol Przygoda
     */
    @FXML
    private void switchForm(ActionEvent event) {

        TranslateTransition slider = new TranslateTransition();

        if (event.getSource() == registerBtn) {
            slider.setNode(sidePane);
            slider.setToX(450);
            slider.setDuration(Duration.seconds(.5));
            slider.play();
        } else if (event.getSource() == register_loginBtn) {
            slider.setNode(sidePane);
            slider.setToX(0);
            slider.setDuration(Duration.seconds(.5));
            slider.play();
        }
    }

    /**
     * Metoda obsługująca proces logowania użytkownika.
     * <p>
     * Metoda `login()` jest odpowiedzialna za weryfikację danych logowania
     * użytkownika w bazie danych. Na podstawie wprowadzonych
     * danych, metoda wysyła przechywcone dane do serwera za pomocą metody {@linkplain ServerController#sendLoginInfoToServer(String, String)}
     * , serwer sprawdza ich występowanie w bazie danych.
     * Następnie odbiera odpowiedź od serwera za pomocą metody {@linkplain  ServerController#getLoginFeedBackFromServer()}
     * Jeśli dopasowanie zostanie znalezione, metoda wylacza okno logowania i przechodzi do okna głównego
     * W przeciwnym wypadku użytkownik zostaje poinformowany za pomocą okna alertu o wprowadzeniu nie zgadzających się ze sobą danych
     *
     * @author Karol Przygoda
     */
    @FXML
    private void login() throws IOException{

        Alert alert;
        if (mailTextField.getText().isEmpty() || passwordTextField.getText().isEmpty())
        {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd Logowania");
            alert.setHeaderText(null);
            alert.setContentText("Proszę uzupełnić wszystkie puste pola");
            alert.showAndWait();
        }
        else
        {

            String mail = mailTextField.getText();
            String password = passwordTextField.getText();

            ServerController.sendLoginInfoToServer(mail,password);

            if(ServerController.getLoginFeedBackFromServer())
            {
                loginBtn.getScene().getWindow().hide();
                Stage stage = new Stage();
                ClientDashBoardView clientDashBoardView = new ClientDashBoardView();
                clientDashBoardView.start(stage);

            }
            else
            {
                if(!ServerController.errorFlag) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Błąd Logowania");
                    alert.setHeaderText(null);
                    alert.setContentText("Podany adres mailowy oraz hasło nie zgadzają się");
                    alert.showAndWait();
                }
            }
        }
    }


    /**
     * Metoda obsługująca proces rejestracji użytkownika.
     * <p>
     * Metoda `register()` jest odpowiedzialna za pośrednie wysłanie wypełnionych danych
     * nowo rejestrującego się użytkownika za pomocą metody: {@linkplain ServerController#sendRegisterInfoToServer(String, String, String, String, LocalDate, String)} do serwera.
     * Metoda sprawdza, czy wszystkie wymagane pola rejestracyjne zostały uzupełnione za pomocą metody {@linkplain  LoginController#checkIfEmpty()}. Następnie
     * sprawdzane jest, czy poszczególne pola spełniają określone warunki takie jak odpowiedni format tekstu lub brak znaków zakazanych
     * za pomocą metod: {@linkplain LoginController#checkMail()}, {@linkplain LoginController#checkName()}, {@linkplain LoginController#checkPassword()} {@linkplain LoginController#checkLastName()}
     * {@linkplain LoginController#checkPhoneNumber()} kolejnym krokiem jest sprawdzenie czy użytkownik posiada 18 lat jeżeli wszystkie warunki zostaną spełnione wyświetla się okno
     * modalne które jest którym zarządza klasa {@linkplain RegulationsController}. Ostatnim etapem rejestracji jest wysłanie danych do serwera oraz odebranie z niego informacji zwrotnej
     * do wyświetlenia informacji zwrotnej służy metoda {@linkplain ServerController#displayRegistrationServerFeedback(boolean)}.
     *
     * @author Karol Przygoda
     */
    @FXML
    private void register() throws IOException {

        Alert alert;

        LocalDate today = LocalDate.now();

        if (checkIfEmpty())
        {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd Rejestracji");
            alert.setHeaderText(null);
            alert.setContentText("Prosze uzupełnić wszystkie puste pola"); // jezeli przynajmniej 1 pole pozostalo puste uzytkownik zostaje o tym poinformowany
            alert.showAndWait();
        }
        else if(!nameCheckFlag || !lastNameCheckFlag || !mailCheckFlag || !phoneNumberCheckFlag || !passwordCheckFlag) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd Rejestracji");
            alert.setHeaderText(null);
            alert.setContentText("Proszę poprawić pola zaznaczone na czerwono");
            alert.showAndWait();
        }
        else if(Period.between(register_datePickerTextField.getValue(), today).getYears() < 18)
        {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd Rejestracji");
            alert.setHeaderText(null);
            alert.setContentText("Aby mieć możliwość rejestracji trzeba być pełnoletnim !");
            alert.showAndWait();
        }
        else if(!(register_passwordTextField.getText().equals(register_passwordConfirmTextField.getText())))
        {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd Rejestracji");
            alert.setHeaderText(null);
            alert.setContentText("Hasła się różnią !");
            alert.showAndWait();
        }
        else if(checkIfRegulationsAccepted())
        {
            ServerController.sendRegisterInfoToServer(register_nameTextField.getText(),register_lastNameTextField.getText(),
                                    register_mailTextField.getText(), register_phoneNumberTextField.getText(),
                                    register_datePickerTextField.getValue(), register_passwordTextField.getText());

            ServerController.displayRegistrationServerFeedback(ServerController.getRegisterFeedBackFromServer());
            clearRegisterFields();
        }
    }


    /**
     * Zamyka aplikację i kończy jej działanie.
     * <p>
     * Metoda jest wywoływana po kliknięciu przycisku "closeBtn".Zamyka połączenie z serwerem, zamyka okno aplikacji oraz kończy jej działanie.
     * Metoda uzyskuje dostęp do obiektu Stage, reprezentującego
     * aktualne okno aplikacji, za pomocą metody `getScene().getWindow()`. Następnie wywołuje metodę `close()` na tym obiekcie,
     * powodując zamknięcie okna i zakończenie działania aplikacji.
     *
     * @author Karol Przygoda
     */
    @FXML
    private void close() throws IOException {
        LoginView.socket.close();
        Stage stage = (Stage) closeBtn.getScene().getWindow();
        stage.close();
    }

    /**
     * Minimalizuje okno aplikacji.
     * <p>
     * Metoda jest wywoływana po kliknięciu przycisku "minBtn". Minimalizuje aktualne okno aplikacji, zmniejszając je do ikony
     * na pasku zadań. Metoda uzyskuje dostęp do obiektu Stage, reprezentującego aktualne okno aplikacji, za pomocą metody
     * `getScene().getWindow()`. Następnie wywołuje metodę `setIconified(true)` na tym obiekcie, powodując minimalizację okna.
     *
     * @author Karol Przygoda
     */
    @FXML
    private void minimize() {
        Stage stage = (Stage) minBtn.getScene().getWindow();
        stage.setIconified(true);
    }

    /**
     * Sprawdza czy użytkownik wprowadził nie wprowadził znaku nieporządanego
     * <p>
     * Metoda nasłuchuje na zmiany wprowadzone w pole tesktowe odpowiedzialne za wprowadzenie imienia.
     * Jeżeli użytkownik wprowadzi znak zakazany obramowanie pola tekstowego odpowiedzialnego za imie zmieni się na czerwone
     * aby uniemożliwić przejścia dalej użytkownikowi do momentu poprawienia wprowadzanego tekstu metoda aktualizuje flage nameCheckFlag która używana jest
     * w instrukcji warunkowej if w metodzie {@linkplain LoginController#register()}
     * ponadto wyświetli sie krótki komunikat informujący użytkownika o problemie dzieje się to do momentu kiedy użytkownik nie poprawi pola tekstowego
     * @author Karol Przygoda
     */
    @FXML
    private void checkName() {
        register_nameTextField.addEventFilter(KeyEvent.ANY, event -> {
            String currentText = register_nameTextField.getText() + event.getCharacter();
            if (currentText.matches(".*[*!;@#$%^&()-=+{}|:',.<>/?_\\[\\]\"].*")) {
                register_nameTextField.setStyle("-fx-border-color: red");
                nameFieldValidationInfo.setVisible(true);
                nameCheckFlag = false;
            } else {
                register_nameTextField.setStyle("");
                nameFieldValidationInfo.setVisible(false);
                nameCheckFlag = true;
            }
        });
    }

    /**
     * Sprawdza czy użytkownik wprowadził nie wprowadził znaku nieporządanego
     * <p>
     * Metoda nasłuchuje na zmiany wprowadzone w pole tesktowe odpowiedzialne za wprowadzenie nazwiska.
     * Jeżeli użytkownik wprowadzi znak zakazany obramowanie pola tekstowego odpowiedzialnego za nazwisko zmieni się na czerwone
     * aby uniemożliwić przejścia dalej użytkownikowi do momentu poprawienia wprowadzanego tekstu metoda aktualizuje flage lastNameCheckFlag która używana jest
     * w instrukcji warunkowej if w metodzie {@linkplain LoginController#register()}
     * ponadto wyświetli sie krótki komunikat informujący użytkownika o problemie dzieje się to do momentu kiedy użytkownik nie poprawi pola tekstowego
     * @author Karol Przygoda
     */
    @FXML
    private void checkLastName() {
        register_lastNameTextField.addEventFilter(KeyEvent.ANY, event -> {
            String currentText = register_lastNameTextField.getText() + event.getCharacter();
            if (currentText.matches(".*[*!;@#$%^&()-=+{}|:',.<>/?_\\[\\]\"].*")) {
                register_lastNameTextField.setStyle("-fx-border-color: red");
                lastNameFieldValidationInfo.setVisible(true);
                lastNameCheckFlag = false;
            } else {
                register_lastNameTextField.setStyle("");
                lastNameFieldValidationInfo.setVisible(false);
                lastNameCheckFlag = true;
            }
        });
    }

    /**
     * Sprawdza czy użytkownik wprowadził poprawny format adresu mailowego
     * <p>
     * Metoda nasłuchuje na zmiany wprowadzone w pole tesktowe odpowiedzialne za wprowadzenie adresu mailowego.
     * Jeżeli użytkownik wprowadzi adres mailowy w nieprawidłowym formacie obramowanie pola tekstowego odpowiedzialnego za adres mailowy zmieni się na czerwone
     * aby uniemożliwić przejścia dalej użytkownikowi do momentu poprawienia wprowadzanego tekstu metoda aktualizuje flage mailCheckFlag która używana jest
     * w instrukcji warunkowej if w metodzie {@linkplain LoginController#register()}
     * ponadto wyświetli sie krótki komunikat informujący użytkownika o problemie dzieje się to do momentu kiedy użytkownik nie poprawi pola tekstowego
     * @author Karol Przygoda
     */
    @FXML
    private void checkMail() {
        register_mailTextField.addEventFilter(KeyEvent.ANY, event -> {
            String currentText = register_mailTextField.getText();
            if (!currentText.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}")) {
                mailFieldValidationInfo.setVisible(true);
                register_mailTextField.setStyle("-fx-border-color: red");
                mailCheckFlag = false;
            } else {
                mailFieldValidationInfo.setVisible(false);
                register_mailTextField.setStyle("");
                mailCheckFlag = true;
            }
        });

    }

    /**
     * Sprawdza czy użytkownik wprowadził prawidłowy format numeru komórkowego
     * <p>
     * Metoda nasłuchuje na zmiany wprowadzone w pole tesktowe odpowiedzialne za wprowadzenie numeru komorkowego.
     * Jeżeli użytkownik wprowadzi znak zakazany lub wporwadzi numer o nieprawidłowym formacie obramowanie pola tekstowego odpowiedzialnego za numer komórkowy zmieni się na czerwone
     * aby uniemożliwić przejścia dalej użytkownikowi do momentu poprawienia wprowadzanego tekstu metoda aktualizuje flage phoneNumerCheckFlag która używana jest
     * w instrukcji warunkowej if w metodzie {@linkplain LoginController#register()}
     * ponadto wyświetli sie krótki komunikat informujący użytkownika o problemie dzieje się to do momentu kiedy użytkownik nie poprawi pola tekstowego
     * @author Karol Przygoda
     */
    @FXML
    private void checkPhoneNumber() {
        register_phoneNumberTextField.addEventFilter(KeyEvent.ANY, event -> {
            String currentText = register_phoneNumberTextField.getText();
            if (!currentText.matches("\\d{9}") ) {
                phoneNumberFieldValidationInfo.setVisible(true);
                register_phoneNumberTextField.setStyle("-fx-border-color: red");
                phoneNumberCheckFlag = false;
            } else {
                phoneNumberFieldValidationInfo.setVisible(false);
                register_phoneNumberTextField.setStyle("");
                phoneNumberCheckFlag = true;
            }
        });

    }

    /**
     * Sprawdza czy użytkownik wprowadził prawidłowy format hasła
     * <p>
     * Metoda nasłuchuje na zmiany wprowadzone w pole tesktowe odpowiedzialne za wprowadzenie hasła.
     * Jeżeli użytkownik nie wprowadzi hasła o prawidłowym formacie obramowanie pola tekstowego odpowiedzialnego za hasło zmieni się na czerwone
     * aby uniemożliwić przejścia dalej użytkownikowi do momentu poprawienia wprowadzanego tekstu metoda aktualizuje flage passwordCheckFlag która używana jest
     * w instrukcji warunkowej if w metodzie {@linkplain LoginController#register()}
     * ponadto wyświetli sie krótki komunikat informujący użytkownika o problemie dzieje się to do momentu kiedy użytkownik nie poprawi pola tekstowego
     * @author Karol Przygoda
     */
    @FXML
    private void checkPassword() {
        register_passwordTextField.addEventFilter(KeyEvent.ANY, event -> {
            String currentText = register_passwordTextField.getText() + event.getCharacter();
            if (!currentText.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#!$%^&-+=()])(?=\\S+$).{8,20}$")) {
                passwordFieldValidationInfo.setVisible(true);
                register_passwordTextField.setStyle("-fx-border-color: red");
                passwordCheckFlag = false;
            } else {
                passwordFieldValidationInfo.setVisible(false);
                register_passwordTextField.setStyle("");
                passwordCheckFlag = true;
            }
        });

    }

    /**
     * Sprawdza czy wszytskie pola w formularzu rejestracji są uzupełnione
     * <p>
     * Sprawdza czy wszytskie pola w formularzu rejestracji są uzupełnione w metodzie {@linkplain LoginController#register()} aby uniemożliwić
     * przejścia dalej użytkownikowi jeżeli pominął jakies pole
     * @return true jeżeli wszystkie pola w formularzu rejestracji są uzupełnione false jeżeli jakieś pole nie jest uzupełnione
     * @author Karol Przygoda
     */
    @FXML
    private boolean checkIfEmpty() {
        return register_nameTextField.getText().isEmpty()    // sprawdzanie czy wszytskie pola formularza rejestracji zostaly wypelnione
                || register_lastNameTextField.getText().isEmpty()
                || register_mailTextField.getText().isEmpty()
                || register_passwordTextField.getText().isEmpty()
                || register_passwordConfirmTextField.getText().isEmpty()
                || register_phoneNumberTextField.getText().isEmpty()
                || register_datePickerTextField.getValue() == null;

    }

    /**
     * Sprawdza czy użytkownik zaakceptowal warunki użytkownia
     * <p>
     * Metoda ta tworzy nowa Scene jako okno modalne do której przekazywany jest plik fxml z zawartością warunków użytkowania checkboxem potwierdzenia
     * przyciskiem potwierdzenia oraz anulowania. Metoda ta jest ostanim etapem rejstracji oraz ostatnim etapem funckji {@linkplain LoginController#register()} jeżeli użytkownik
     * zaakceptował warunki użytkownia metoda pobiera wprowadzone dane przez użytkownika a następnie wysyła je do serwera za pomocą metody {@linkplain ServerController#sendLoginInfoToServer(String, String)}
     * nastepnie metoda obiera informacje zwrotną od serwera jeżeli serwer zaakceptował wprowadzone dane użytkownik informowany jest o prawidłowej rejestracji w przeciwnym wypadku jeżeli
     * nie nastąpiło zerwanie połączenia użytkownik informowany jest o zarejestowanym już adresie mailowym
     * @author Karol Przygoda
     */
    private boolean checkIfRegulationsAccepted() throws IOException {
        Stage modalStage = new Stage();
        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.initStyle(StageStyle.UTILITY);
        modalStage.setTitle("Warunki użytkowania");

        FXMLLoader fxmlLoader = new FXMLLoader(LoginView.class.getResource("regulationsView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        modalStage.initStyle(StageStyle.TRANSPARENT);
        modalStage.setScene(scene);
        modalStage.showAndWait();//wyswietl okno zawierajace informacje dotyczace warunkow uzytkowania

        // jezeli uzytkownik zaakceptowal warunki uzytkowania to zwroc true
        return RegulationsController.accepted;
    }

    /**
     * Czyści pola tekstowe w formularzu rejestracji
     * @author Karol Przygoda
     */
    private void clearRegisterFields() {
        register_nameTextField.setText("");
        register_lastNameTextField.setText("");
        register_phoneNumberTextField.setText("");
        register_mailTextField.setText("");
        register_passwordTextField.setText("");
        register_passwordConfirmTextField.setText("");
        register_datePickerTextField.setValue(null);
    }

}