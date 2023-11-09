package com.example.sharerideclient;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
 */
public class LoginController {

    @FXML
    private Button closeBtn;

    @FXML
    private Button loginBtn;

    @FXML
    private TextField mailLabel;

    @FXML
    private Button minBtn;

    @FXML
    private PasswordField passwordLabel;

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


    /**
     * Metoda odpowiedzialna za przełączanie formularzy rejestracji i logowania.
     * <p>
     * Metoda `switchForm()` obsługuje zdarzenie akcji, które występuje po kliknięciu
     * przycisku rejestracji lub przycisku powrotu do logowania. Metoda animuje przesunięcie
     * formularza na bocznym panelu w celu wyświetlenia odpowiedniego formularza. Przesunięcie
     * wykonywane jest za pomocą efektu TranslateTransition.
     *
     * @param event Zdarzenie akcji wywołane przez kliknięcie przycisku rejestracji lub powrotu do logowania.
     */
    public void switchForm(ActionEvent event) {

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
     * danych, metoda sprawdza, czy istnieje dopasowanie w tabeli administrator
     * lub w tabeli uzytkownik. Jeśli dopasowanie zostanie znalezione, otwierane
     * są odpowiednie panele:
     *
     */
    public void login() {

        Alert alert;
        if (mailLabel.getText().isEmpty() || passwordLabel.getText().isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd Logowania");
            alert.setHeaderText(null);
            alert.setContentText("Proszę uzupełnić wszystkie puste pola");
            alert.showAndWait();
        } else {
            loginBtn.getScene().getWindow().hide();
        }
    }


    /**
     * Metoda obsługująca proces rejestracji użytkownika.
     * <p>
     * Metoda `register()` jest odpowiedzialna za weryfikację danych
     * nowo rejestrującego się użytkownika. Metoda sprawdza, czy
     * wszystkie wymagane pola rejestracyjne zostały uzupełnione. Następnie
     * sprawdzane jest, czy poszczególne pola spełniają określone warunki takie jak odpowiedni format tekstu lub brak znaków zakazanych
     *
     */
    public void register() throws IOException {


        Alert alert;

        if (register_nameTextField.getText().isEmpty()    // sprawdzanie czy wszytskie pola formularza rejestracji zostaly wypelnione
                || register_lastNameTextField.getText().isEmpty()
                || register_mailTextField.getText().isEmpty()
                || register_passwordTextField.getText().isEmpty()
                || register_passwordConfirmTextField.getText().isEmpty()
                || register_phoneNumberTextField.getText().isEmpty()
                || register_datePickerTextField.getValue() == null) {

            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd Rejestracji");
            alert.setHeaderText(null);
            alert.setContentText("Prosze uzupełnić wszystkie puste pola"); // jezeli przynajmniej 1 pole pozostalo puste uzytkownik zostaje o tym poinformowany
            alert.showAndWait();
        } else {

            if (register_nameTextField.getText().matches(".*[*!;@#$%^&()-=+{}|:',.<>/?].*") // sprawdzanie czy pole imienia oraz nazwiska zawiera znaki niedozwolone
                    || register_lastNameTextField.getText().matches(".*[*!;@#$%^&()-=+{}|:',<>/?].*")) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Wprowadzono niedozwolone znaki");
                alert.setHeaderText(null);
                alert.setContentText("Wprowadzono niedozwolone znaki takie jak: * ! ; @ # $ % ^ & ( )- = + { } | : ' , . < > / ?"); // jezeli pola imienia lub naziwksa zawieraja znaki niedozowlone uzytkownik zostaje o tym poinformowany
                alert.showAndWait();
            } else if (!(register_mailTextField.getText().matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}"))) { // sorawdzanie czy adres mailowy zaiwera znaki charakterystyczne takie jak @ i kropka

                if (register_mailTextField.getText().matches(".*[*!;#$%^&()=+{}|:',<>/?].*")) { // sprawdzanie czy adres mailowy zawiera inne nieporzadane znaki specjalne
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Wprowadzono niedozwolone znaki");
                    alert.setHeaderText(null);
                    alert.setContentText("Wprowadzono niedozwolone znaki takie jak: * ! ; @ # $ % ^ & ( )- = + { } | : ' , . < > / ?");// jezeli aders zawiera nieporzadne znaki specjalne uzytkownik zostaje o tym poinformowany
                    alert.showAndWait();
                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Nieprawidłowy adres mailowy");
                    alert.setHeaderText(null);
                    alert.setContentText("Adres mailowy powinien zawierać znak @ oraz .");// jezeli adres nie zawiera zadnych znakow specjalnych uzytkownik zostaje o tym poinformowany
                    alert.showAndWait();
                }
            } else if (!(register_phoneNumberTextField.getText().matches("\\d{0,9}"))) {// sprawdzanie czy wprowadzony numer telefonu sklada sie z 9 cyfr

                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Nieprawidłowy mumer telefonu");
                alert.setHeaderText(null);
                alert.setContentText("Numer komórkowy powinien składać się tylko z cyfr od 0 do 9 i powinno ich być 9");// jezeli numer telefonu nie sklada sie tylko z 9 cyfr uzytkownik zostaje o tym poinformowany
                alert.showAndWait();
            } else if (!register_passwordTextField.getText().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#!$%^&-+=()])(?=\\S+$).{8,20}$")) {//sprawdzanie czy wprowadzone haslo posiada od 8 do 20 znakow w tym 1 duza litera, 1 mala litera, 1 cyfra, jeden znak specjalny
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Niebezpieczne hasło");
                alert.setHeaderText(null);
                alert.setContentText("Hasło powinno zawierać od 8 do 20 znaków w tym przynajmniej 1 znak specjalny i przynajmniej 1 cyfre");// jezeli haslo zaproponowane przez uzytkownika nie spelnia warunkow uzytkownik zostaje o tym poinformowany
                alert.showAndWait();
            } else {

                LocalDate today = LocalDate.now();
                Period age = Period.between(register_datePickerTextField.getValue(), today);//obliczanie wieku uzytkownika na podstawie daty terazniejszej oraz wybranej przez uztykownika

                if (age.getYears() < 18) { //jezeli uzytkownik nie posiada 18 lat zostaje poinformowany o koniecznosci bycia pelnoletnim aby moc korzystac z uslug
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Błąd Rejestracji");
                    alert.setHeaderText(null);
                    alert.setContentText("Aby mieć możliwość rejestracji trzeba być pełnoletnim !");
                    alert.showAndWait();
                } else if (register_passwordTextField.getText().equals(register_passwordConfirmTextField.getText())) { // jezeli haslo i haslo pitwierdzajace sa takie same to

                    Stage modalStage = new Stage();
                    modalStage.initModality(Modality.APPLICATION_MODAL);
                    modalStage.initStyle(StageStyle.UTILITY);
                    modalStage.setTitle("Warunki użytkowania");

                    FXMLLoader fxmlLoader = new FXMLLoader(LoginView.class.getResource("regulationsView.fxml"));
                    Scene scene = new Scene(fxmlLoader.load(), 600, 400);
                    modalStage.initStyle(StageStyle.TRANSPARENT);
                    modalStage.setScene(scene);
                    modalStage.showAndWait();//wyswietl okno zawierajace informacje dotyczace warunkow uzytkowania

                    if (RegulationsController.accepted) { // jezeli uzytkownik zaakceptowal warunki uzytkowania to wyczysc wszytskie pola i poinformuj o prawidlowej rejestracji

                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information Message");
                        alert.setHeaderText(null);
                        register_nameTextField.setText("");
                        register_lastNameTextField.setText("");
                        register_phoneNumberTextField.setText("");
                        register_mailTextField.setText("");
                        register_passwordTextField.setText("");
                        register_passwordConfirmTextField.setText("");
                        register_datePickerTextField.setValue(null);
                        alert.setContentText("Zarejestrowano pomyślnie");
                        alert.showAndWait();
                        RegulationsController.accepted = false;
                    }

                } else {//jezeli cos poszlo nie tak oznacza to ze hasla sie roznia
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Błąd Rejestracji");
                    alert.setHeaderText(null);
                    alert.setContentText("Hasła się różnią !");
                    alert.showAndWait();
                }
            }
        }
    }


    /**
     * Zamyka aplikację i kończy jej działanie.
     * <p>
     * Metoda jest wywoływana po kliknięciu przycisku "closeBtn". Zamyka okno aplikacji oraz kończy jej działanie.
     * Metoda uzyskuje dostęp do obiektu Stage, reprezentującego
     * aktualne okno aplikacji, za pomocą metody `getScene().getWindow()`. Następnie wywołuje metodę `close()` na tym obiekcie,
     * powodując zamknięcie okna i zakończenie działania aplikacji.
     */
    @FXML
    public void close() {
        Stage stage = (Stage) closeBtn.getScene().getWindow();
        stage.close();
    }

    /**
     * Minimalizuje okno aplikacji.
     * <p>
     * Metoda jest wywoływana po kliknięciu przycisku "minBtn". Minimalizuje aktualne okno aplikacji, zmniejszając je do ikony
     * na pasku zadań. Metoda uzyskuje dostęp do obiektu Stage, reprezentującego aktualne okno aplikacji, za pomocą metody
     * `getScene().getWindow()`. Następnie wywołuje metodę `setIconified(true)` na tym obiekcie, powodując minimalizację okna.
     */
    @FXML
    public void minimize() {
        Stage stage = (Stage) minBtn.getScene().getWindow();
        stage.setIconified(true);
    }
}