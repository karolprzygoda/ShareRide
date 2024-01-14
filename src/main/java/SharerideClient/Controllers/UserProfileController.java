package SharerideClient.Controllers;

import Data.DriverData;
import Data.UserData;
import SharerideClient.Alerts;
import SharerideClient.Views.ClientDashBoardView;
import SharerideClient.Views.DriverTabView;
import SharerideClient.Views.FormsContainer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * Klasa zarządzająca interfejsem graficznym zakładki profilu użytkownika
 * @see ClientDashBoardController
 * @author Karol Przygoda
 */
public class UserProfileController implements Initializable {

    /**
     * Pole wyświetlające średnią ocenę użytkownika
     */
    @FXML
    private Label avgRatingLabel;

    /**
     * Pole wyświetlające date urodzenia użytkownika
     */
    @FXML
    private Label birthDateLabel;

    /**
     * Pole wyświetlający status kierowcy użytkownika
     */
    @FXML
    private Label driverStatusLabel;

    /**
     * Pole wyświetlające płeć użytkownika
     */
    @FXML
    private Label genderLabel;

    /**
     * Pole wyświetlające date zarejestrowania konta przez użytkownika
     */
    @FXML
    private Label joinDateLabel;

    /**
     * Pole wyświetlające nazwisko użytkownika
     */
    @FXML
    private Label lastNameLabel;

    /**
     * Pole wyświetlające adres mailowy użytkownika
     */
    @FXML
    private Label mailLabel;

    /**
     * Pole wyświetlające imię użytkownika
     */
    @FXML
    private Label nameLabel;

    /**
     * Pole wyświetlające numer telefonu użytkownika
     */
    @FXML
    private Label phoneNumberLabel;

    /**
     * Przycisk odpowiedzialny za usunięcie konta
     * @see UserProfileController#deleteAccount()
     */
    @FXML
    private Button profile_deleteAccBtn;

    /**
     * Tekst wyświetlany w przypadku wprowadzenia nieprawidłowego formatu dla nazwiska
     * @see UserProfileController#checkName()
     */
    @FXML
    private Label profile_lastNameFieldValidationInfo;

    /**
     * Tekst wyświetlany w przypadku wprowadzenia nieprawidłowego formatu dla adresu mailowego
     * @see UserProfileController#checkName()
     */
    @FXML
    private Label profile_mailFieldValidationInfo;

    /**
     * Tekst wyświetlany w przypadku wprowadzenia nieprawidłowego formatu dla imienia
     * @see UserProfileController#checkName()
     */
    @FXML
    private Label profile_nameFieldValidationInfo;

    /**
     * Tekst wyświetlany w przypadku wprowadzenia nieprawidłowego formatu dla hasła
     * @see UserProfileController#checkName()
     */
    @FXML
    private Label profile_passwordFieldValidationInfo;

    /**
     * Tekst wyświetlany w przypadku wprowadzenia nieprawidłowego formatu dla numeru telefonu
     * @see UserProfileController#checkName()
     */
    @FXML
    private Label profile_phoneNumberFieldValidationInfo;

    /**
     * Pole potwierdzenia aktualizacji hasła
     * @see UserProfileController#update()
     */
    @FXML
    private PasswordField profile_updateConfirmPasswordField;

    /**
     * ComboBox zawierający wybór płci do aktualizacji
     * @see UserProfileController#update()
     * @see UserProfileController#initialize(URL, ResourceBundle)
     * @see UserProfileController#gender()
     */
    @FXML
    private ComboBox<String> profile_updateGenderComboBox;

    /**
     * Pole aktualizacji nazwiska
     * @see UserProfileController#update()
     * @see UserProfileController#checkLastName()
     */
    @FXML
    private TextField profile_updateLastNameField;

    /**
     * Pole aktualizacji adresu mailowego
     * @see UserProfileController#update()
     * @see UserProfileController#checkMail()
     */
    @FXML
    private TextField profile_updateMailField;

    /**
     * Pole aktualizacji imienia
     * @see UserProfileController#update()
     * @see UserProfileController#checkName()
     */
    @FXML
    private TextField profile_updateNameField;

    /**
     * Pole aktualizacji hasła
     * @see UserProfileController#update()
     * @see UserProfileController#checkPassword()
     */
    @FXML
    private PasswordField profile_updatePasswordField;

    /**
     * Pole aktualizacji numeru telefonu
     * @see UserProfileController#update()
     * @see UserProfileController#checkPhoneNumber()
     */
    @FXML
    private TextField profile_updatePhoneNumberField;

    /**
     * Przycisk odpowiedzialny za wysłanie żądania aktualizacji danych użytkownika zalogowanego w aktualnej sesji
     * @see UserProfileController#update()
     */
    @FXML
    private Button profile_updateProfileBtn;

    /**
     * Pole wyświetlające liczbę wykonanych przejazdów
     */
    @FXML
    private Label ridesCountLabel;

    /**
     * Tekst wyświetlający się, jeżeli użytkownik wprowadzi za długi ciąg znaków do pola {@linkplain  UserProfileController#profile_updateNameField}
     * @see UserProfileController#update()
     * @see UserProfileController#checkName()
     */
    @FXML
    private Label nameFieldStringLengthValidationInfo;
    /**
     * Tekst wyświetlający się, jeżeli użytkownik wprowadzi za długi ciąg znaków do pola {@linkplain  UserProfileController#profile_updateLastNameField}
     * @see UserProfileController#update()
     * @see UserProfileController#checkLastName()
     */
    @FXML
    private Label lastNameFieldStringLengthValidationInfo;

    /**
     * Tekst wyświetlający się, jeżeli użytkownik wprowadzi za długi ciąg znaków do pola {@linkplain UserProfileController#profile_updateMailField}
     * @see UserProfileController#update()
     * @see UserProfileController#checkMail()
     */
    @FXML
    private Label mailFieldStringLengthValidationInfo;

    /**
     * Tekst wyświetlający się, jeżeli użytkownik wprowadzi za długi ciąg znaków do pola {@linkplain  UserProfileController#profile_updatePasswordField}
     * @see UserProfileController#update()
     * @see UserProfileController#checkPassword()
     */
    @FXML
    private Label passwordFieldStringLengthValidationInfo;


    /**
     * Tablica przechowująca możliwe wybory płci
     * @see UserProfileController#gender()
     */
    private final String[] genderList = {"Mężczyzna", "Kobieta", "Wole nie podawać"};

    /**
     * Flagi odpowiedzialne za sprawdzenie, czy konkretne pola formularza rejestracji są uzupełnione poprawnie
     * @see UserProfileController#update()
     */
    private boolean nameCheckFlag = true, lastNameCheckFlag = true, mailCheckFlag = true, phoneNumberCheckFlag = true, passwordCheckFlag = true;

    protected static UserData userDataBuffer = new UserData();


    /**
     * Sprawdza, czy użytkownik nie wprowadził znaku nieporządnego
     * <p>
     * Metoda nasłuchuje na zmiany wprowadzone w pole tekstowe {@linkplain UserProfileController#profile_updateNameField}.
     * Jeżeli użytkownik wprowadzi znak zakazany obramowanie pola tekstowego zmieni się na czerwone.
     * Aby uniemożliwić przejścia dalej użytkownikowi do momentu poprawienia wprowadzanego tekstu metoda aktualizuje flagę {@linkplain UserProfileController#nameCheckFlag}
     * która używana jest w instrukcji warunkowej if w metodzie {@linkplain UserProfileController#update()}
     * ponadto wyświetli się krótki komunikat informujący użytkownika o problemie dzieje się to do momentu, kiedy użytkownik nie poprawi pola tekstowego
     * @see UserProfileController#update()
     * @author Karol Przygoda
     */
    @FXML
    private void checkName() {
        profile_updateNameField.addEventFilter(KeyEvent.ANY, event -> {
            String currentText = profile_updateNameField.getText() + event.getCharacter();
            if (currentText.matches(".*[*!;@#$%^&()-=+{}|:',.<>/?_\\[\\]\"].*")) {
                profile_updateNameField.setStyle("-fx-border-color: red");
                profile_nameFieldValidationInfo.setVisible(true);
                nameFieldStringLengthValidationInfo.setVisible(false);
                nameCheckFlag = false;
            }
            else if(currentText.length() > 50)
            {
                profile_updateNameField.setStyle("-fx-border-color: red");
                nameFieldStringLengthValidationInfo.setVisible(true);
                profile_nameFieldValidationInfo.setVisible(false);
                nameCheckFlag = false;
            }
            else {
                profile_updateNameField.setStyle("");
                profile_nameFieldValidationInfo.setVisible(false);
                nameFieldStringLengthValidationInfo.setVisible(false);
                nameCheckFlag = true;
            }
        });
    }

    /**
     * Sprawdza, czy użytkownik wprowadził nie wprowadził znaku nieporządnego
     * <p>
     * Metoda nasłuchuje na zmiany wprowadzone w pole tekstowe {@linkplain UserProfileController#profile_updateLastNameField}.
     * Jeżeli użytkownik wprowadzi znak zakazany obramowanie pola tekstowego zmieni się na czerwone.
     * Aby uniemożliwić przejścia dalej użytkownikowi do momentu poprawienia wprowadzanego tekstu metoda aktualizuje flagę {@linkplain UserProfileController#lastNameCheckFlag} która używana jest
     * w instrukcji warunkowej if w metodzie {@linkplain UserProfileController#update()}
     * ponadto wyświetli się krótki komunikat informujący użytkownika o problemie dzieje się to do momentu, kiedy użytkownik nie poprawi pola tekstowego
     * @see UserProfileController#update()
     * @author Karol Przygoda
     */
    @FXML
    private void checkLastName() {
        profile_updateLastNameField.addEventFilter(KeyEvent.ANY, event -> {
            String currentText = profile_updateLastNameField.getText() + event.getCharacter();
            if (currentText.matches(".*[*!;@#$%^&()-=+{}|:',.<>/?_\\[\\]\"].*")) {
                profile_updateLastNameField.setStyle("-fx-border-color: red");
                profile_lastNameFieldValidationInfo.setVisible(true);
                lastNameFieldStringLengthValidationInfo.setVisible(false);
                lastNameCheckFlag = false;
            }
            else if(currentText.length() > 50)
            {
                profile_updateLastNameField.setStyle("-fx-border-color: red");
                lastNameFieldStringLengthValidationInfo.setVisible(true);
                profile_lastNameFieldValidationInfo.setVisible(false);
                lastNameCheckFlag = false;
            }
            else {
                profile_updateLastNameField.setStyle("");
                profile_lastNameFieldValidationInfo.setVisible(false);
                lastNameFieldStringLengthValidationInfo.setVisible(false);
                lastNameCheckFlag = true;
            }
        });
    }

    /**
     * Sprawdza, czy użytkownik wprowadził poprawny format adresu mailowego
     * <p>
     * Metoda nasłuchuje na zmiany wprowadzone w pole tekstowe {@linkplain UserProfileController#profile_updateMailField}.
     * Jeżeli użytkownik wprowadzi adres mailowy w nieprawidłowym formacie obramowanie pola tekstowego zmieni się na czerwone.
     * Aby uniemożliwić przejścia dalej użytkownikowi do momentu poprawienia wprowadzanego tekstu metoda aktualizuje flagę {@linkplain UserProfileController#mailCheckFlag} która używana jest
     * w instrukcji warunkowej if w metodzie {@linkplain UserProfileController#update()}
     * ponadto wyświetli się krótki komunikat informujący użytkownika o problemie dzieje się to do momentu, kiedy użytkownik nie poprawi pola tekstowego
     * @see UserProfileController#update()
     * @author Karol Przygoda
     */
    @FXML
    private void checkMail() {
        profile_updateMailField.addEventFilter(KeyEvent.ANY, event -> {
            String currentText = profile_updateMailField.getText();
            if (!currentText.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}")) {
                profile_mailFieldValidationInfo.setVisible(true);
                mailFieldStringLengthValidationInfo.setVisible(false);
                profile_updateMailField.setStyle("-fx-border-color: red");
                mailCheckFlag = false;
            }
            else if(currentText.length() > 100)
            {
                profile_updateMailField.setStyle("-fx-border-color: red");
                mailFieldStringLengthValidationInfo.setVisible(true);
                profile_lastNameFieldValidationInfo.setVisible(false);
                mailCheckFlag = false;
            }
            else {
                profile_mailFieldValidationInfo.setVisible(false);
                mailFieldStringLengthValidationInfo.setVisible(false);
                profile_updateMailField.setStyle("");
                mailCheckFlag = true;
            }
        });

    }

    /**
     * Sprawdza, czy użytkownik wprowadził prawidłowy format numeru komórkowego
     * <p>
     * Metoda nasłuchuje na zmiany wprowadzone w pole tekstowe {@linkplain UserProfileController#profile_updatePhoneNumberField}.
     * Jeżeli użytkownik wprowadzi znak zakazany lub wprowadzi numer o nieprawidłowym formacie obramowanie pola tekstowego zmieni się na czerwone.
     * Aby uniemożliwić przejścia dalej użytkownikowi do momentu poprawienia wprowadzanego tekstu metoda aktualizuje flagę {@linkplain UserProfileController#phoneNumberCheckFlag} która używana jest
     * w instrukcji warunkowej if w metodzie {@linkplain UserProfileController#update()}
     * ponadto wyświetli się krótki komunikat informujący użytkownika o problemie dzieje się to do momentu, kiedy użytkownik nie poprawi pola tekstowego
     * @see UserProfileController#update()
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
     * Sprawdza, czy użytkownik wprowadził prawidłowy format hasła
     * <p>
     * Metoda nasłuchuje na zmiany wprowadzone w pole tekstowe {@linkplain UserProfileController#profile_updatePhoneNumberField}.
     * Jeżeli użytkownik nie wprowadzi hasła o prawidłowym formacie obramowanie pola tekstowego zmieni się na czerwone.
     * Aby uniemożliwić przejścia dalej użytkownikowi do momentu poprawienia wprowadzanego tekstu metoda aktualizuje flagę {@linkplain UserProfileController#passwordCheckFlag} która używana jest
     * w instrukcji warunkowej if w metodzie {@linkplain UserProfileController#update()}
     * ponadto wyświetli się krótki komunikat informujący użytkownika o problemie dzieje się to do momentu, kiedy użytkownik nie poprawi pola tekstowego
     * @see UserProfileController#update()
     * @author Karol Przygoda
     */
    @FXML
    private void checkPassword() {
        profile_updatePasswordField.addEventFilter(KeyEvent.ANY, event -> {
            String currentText = profile_updatePasswordField.getText() + event.getCharacter();
            if (!currentText.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#!$%^&-+=()])(?=\\S+$).{8,}$")) {
                profile_passwordFieldValidationInfo.setVisible(true);
                passwordFieldStringLengthValidationInfo.setVisible(false);
                profile_updatePasswordField.setStyle("-fx-border-color: red");
                passwordCheckFlag = false;
            }
            else if(currentText.length() > 255)
            {
                profile_updatePasswordField.setStyle("-fx-border-color: red");
                passwordFieldStringLengthValidationInfo.setVisible(true);
                profile_passwordFieldValidationInfo.setVisible(false);
                passwordCheckFlag = false;
            }
            else {
                profile_passwordFieldValidationInfo.setVisible(false);
                passwordFieldStringLengthValidationInfo.setVisible(false);
                profile_updatePasswordField.setStyle("");
                passwordCheckFlag = true;
            }
        });

    }

    /**
     * Sprawdza, czy wszystkie pola w formularzu rejestracji są puste
     * <p>
     * Sprawdza, czy wszystkie pola w formularzu rejestracji są puste w metodzie {@linkplain UserProfileController#update()} aby uniemożliwić
     * przejścia dalej użytkownikowi, jeżeli pominął wszystkie pola
     * @return true, jeżeli chociaż jedno pole w formularzu aktualizacji danych jest uzupełnione, false, jeżeli jakieś pole nie jest uzupełnione
     * @see UserProfileController#update()
     * @author Karol Przygoda
     */
    @FXML
    private boolean checkIfEmpty() {
        return profile_updateNameField.getText().isEmpty()
                && profile_updateLastNameField.getText().isEmpty()
                && profile_updateMailField.getText().isEmpty()
                && profile_updatePhoneNumberField.getText().isEmpty()
                && profile_updatePasswordField.getText().isEmpty()
                && profile_updateGenderComboBox.getValue() == null;

    }

    /**
     * Przypisuje opcje wyboru Płci dla comboBox-a
     * @see UserProfileController#initialize(URL, ResourceBundle)
     * @author Karol Przygoda
     */
    private void gender() {
        List<String> listS = new ArrayList<>();

        Collections.addAll(listS, genderList);

        ObservableList<String> listD = FXCollections.observableArrayList(listS);
        profile_updateGenderComboBox.setItems(listD);
    }

    /**
     * Wysyła żądanie otrzymania danych użytkownika zalogowanego w aktualnej sesji do serwera
     * @see UserProfileController#update()
     * @see UserProfileController#initialize(URL, ResourceBundle)
     * @author Karol Przygoda
     */
    private void selectUserData(UserData user)
    {

        try {

            DriverData driverData = new DriverData();

            nameLabel.setText(user.getName());
            lastNameLabel.setText(user.getLastName());
            genderLabel.setText(user.getGender());
            mailLabel.setText(user.getEmail());
            phoneNumberLabel.setText(user.getPhoneNumber());
            birthDateLabel.setText(user.getBirthDate().toString());
            joinDateLabel.setText(user.getRegisterDate().toString());
            if(ServerController.sendSelectRequest(driverData) != null)
            {
                driverStatusLabel.setText("Aktywny");
            }
            else
            {
                driverStatusLabel.setText("Nie Aktywny");
            }

            userDataBuffer = user;

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Metoda obsługująca proces aktualizacji danych osobowych użytkownika.
     * <p>
     * @author Karol Przygoda
     */
    @FXML
    private void update(){

        if (checkIfEmpty())
            Alerts.failureAlert("Prosze uzupełnić pole które chcesz zaktualizować"); // jeżeli przynajmniej 1 pole pozostało puste użytkownik zostaje o tym poinformowany
        else if(!nameCheckFlag || !lastNameCheckFlag || !mailCheckFlag || !phoneNumberCheckFlag || !passwordCheckFlag)
            Alerts.failureAlert("Proszę poprawić pola zaznaczone na czerwono");
        else if(!(profile_updatePasswordField.getText().equals(profile_updateConfirmPasswordField.getText())))
            Alerts.failureAlert("Hasła się różnią !");
        else
        {
            userDataBuffer = ServerController.sendSelectRequest(ClientDashBoardController.userData);

            if(!profile_updateNameField.getText().isEmpty())
                userDataBuffer.setName(profile_updateNameField.getText());
            if(!profile_updateLastNameField.getText().isEmpty())
                userDataBuffer.setLastName(profile_updateLastNameField.getText());
            if(!profile_updateMailField.getText().isEmpty())
                userDataBuffer.setEmail(profile_updateMailField.getText());
            if(!profile_updatePhoneNumberField.getText().isEmpty())
                userDataBuffer.setPhoneNumber(profile_updatePhoneNumberField.getText());
            if(!profile_updatePasswordField.getText().isEmpty())
                userDataBuffer.setPassword(profile_updatePasswordField.getText());
            if(profile_updateGenderComboBox.getSelectionModel().getSelectedItem() != null)
                userDataBuffer.setGender(profile_updateGenderComboBox.getValue());

            int response = ServerController.sendUpdateRequest(userDataBuffer);
            if (response == 1) {


                selectUserData(userDataBuffer);

                ClientDashBoardView.clientDashBoardController.helloClientNameLabel.setText(userDataBuffer.getName());
                DriverTabView.driverTabController.nameLabel.setText(userDataBuffer.getName());
                DriverTabView.driverTabController.lastNameLabel.setText(userDataBuffer.getLastName());
                Alerts.successAlert("Pomyślnie zaktualizowano dane użytkownika");
            } else if (response == 0)
                Alerts.failureAlert("Operacja aktualizacji danych użytkownika zakończona niepowodzeniem");

            clearUpdateFields();
        }
    }

    /**
     * Metoda odpowiedzialna za wysłanie żądania usunięcia konta do serwera
     * @author Karol Przygoda, Radosław Jasiński, Jakub Kotwica
     */
    @FXML
    private void deleteAccount() {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Potwierdź Operacje");
            alert.setHeaderText(null);
            alert.setContentText("Na pewno chcesz usunąć konto?");

            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {
                int response = ServerController.sendDeleteRequest(ClientDashBoardController.userData);
                if(response == 1) {
                    profile_deleteAccBtn.getScene().getWindow().hide();
                    FormsContainer formsContainer = new FormsContainer();
                    Stage stage = new Stage();
                    formsContainer.start(stage);
                }
                else if(response == 0)
                    Alerts.failureAlert("Operacja usunięcia konta zakończona niepowodzeniem");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Czyści pola tekstowe w formularzu aktualizacji danych po udanej operacji aktualizacji
     * @author Karol Przygoda
     */
    private void clearUpdateFields() {
        profile_updateNameField.setText("");
        profile_updateLastNameField.setText("");
        profile_updatePhoneNumberField.setText("");
        profile_updateMailField.setText("");
        profile_updatePasswordField.setText("");
        profile_updateConfirmPasswordField.setText("");
    }


    /**
     * Dodaje możliwości wyboru płci do ComboBoxa oraz wyświetla zaktualizowane dane użytkownika zalogowanego w bieżącej sesji
     * @param url             URL sceny, która została załadowana
     * @param resourceBundle zasoby powiązane z kontrolerem widoku
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        gender();
        selectUserData(ClientDashBoardController.userData);
    }

}
