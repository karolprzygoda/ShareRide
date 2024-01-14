package SharerideClient.Controllers;

import Data.UserData;
import SharerideClient.Alerts;
import SharerideClient.Views.FormsContainer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

/**
 * Kontroler formularza rejestracji
 * @see FormsContainerController
 * @author Karol Przygoda
 */
public class RegisterFormController {

    /**
     * Tekst wyświetlany w przypadku wprowadzenia nieprawidłowego formatu dla nazwiska
     * @see RegisterFormController#register()
     * @see RegisterFormController#checkLastName()
     */
    @FXML
    private Label lastNameFieldValidationInfo;

    /**
     * Tekst wyświetlany w przypadku wprowadzenia nieprawidłowego formatu dla adresu mailowego
     * @see RegisterFormController#register()
     * @see RegisterFormController#checkMail()
     */
    @FXML
    private Label mailFieldValidationInfo;

    /**
     * Tekst wyświetlany w przypadku wprowadzenia nieprawidłowego formatu dla imienia
     * @see RegisterFormController#register()
     * @see RegisterFormController#checkName()
     */
    @FXML
    private Label nameFieldValidationInfo;

    /**
     * Tekst wyświetlany w przypadku wprowadzenia nieprawidłowego formatu dla hasła
     * @see RegisterFormController#register()
     * @see RegisterFormController#checkPassword()
     */
    @FXML
    private Label passwordFieldValidationInfo;

    /**
     * Tekst wyświetlany w przypadku wprowadzenia nieprawidłowego formatu dla numeru telefonu
     * @see RegisterFormController#register()
     * @see RegisterFormController#checkPhoneNumber()
     */
    @FXML
    private Label phoneNumberFieldValidationInfo;

    /**
     * Pole wyboru daty urodzenia
     * @see DatePicker
     */
    @FXML
    private DatePicker register_datePickerTextField;

    /**
     * Pole odpowiedzialne za wprowadzenie nazwiska w formularzu rejestracji
     * @see RegisterFormController#register()
     * @see RegisterFormController#checkLastName()
     * @see RegisterFormController#checkIfEmpty()
     */
    @FXML
    private TextField register_lastNameTextField;

    /**
     * Pole odpowiedzialne za wprowadzenie adresu mailowego w formularzu rejestracji
     * @see RegisterFormController#register()
     * @see RegisterFormController#checkMail()
     * @see RegisterFormController#checkIfEmpty()
     */
    @FXML
    private TextField register_mailTextField;

    /**
     * Pole odpowiedzialne za wprowadzenie imienia w formularzu rejestracji
     * @see RegisterFormController#register()
     * @see RegisterFormController#checkName()
     * @see RegisterFormController#checkIfEmpty()
     */
    @FXML
    private TextField register_nameTextField;

    /**
     * Pole odpowiedzialne za wprowadzenie potwierdzenia hasła w formularzu rejestracji
     * @see RegisterFormController#register()
     * @see RegisterFormController#checkIfEmpty()
     */
    @FXML
    private PasswordField register_passwordConfirmTextField;

    /**
     * Pole odpowiedzialne za wprowadzenie hasła w formularzu rejestracji
     * @see RegisterFormController#register()
     * @see RegisterFormController#checkPassword()
     * @see RegisterFormController#checkIfEmpty()
     */
    @FXML
    private PasswordField register_passwordTextField;

    /**
     * Pole odpowiedzialne za wprowadzenie potwierdzenia numeru telefonu w formularzu rejestracji
     * @see RegisterFormController#register()
     * @see RegisterFormController#checkLastName()
     * @see RegisterFormController#checkIfEmpty()
     */
    @FXML
    private TextField register_phoneNumberTextField;

    /**
     * Przycisk odpowiedzialny za wysłanie żądania rejestracji do serwera
     * @see RegisterFormController#register()
     */
    @FXML
    private Button register_registerBtn;

    /**
     * Tekst wyświetlający się, jeżeli użytkownik wprowadzi za długi ciąg znaków do pola {@linkplain  RegisterFormController#register_nameTextField}
     * @see RegisterFormController#register()
     * @see RegisterFormController#checkName()
     */
    @FXML
    private Label nameFieldStringLengthValidationInfo;
    /**
     * Tekst wyświetlający się, jeżeli użytkownik wprowadzi za długi ciąg znaków do pola {@linkplain  RegisterFormController#register_lastNameTextField}
     * @see RegisterFormController#register()
     * @see RegisterFormController#checkLastName()
     */
    @FXML
    private Label lastNameFieldStringLengthValidationInfo;

    /**
     * Tekst wyświetlający się, jeżeli użytkownik wprowadzi za długi ciąg znaków do pola {@linkplain  RegisterFormController#register_mailTextField}
     * @see RegisterFormController#register()
     * @see RegisterFormController#checkMail()
     */
    @FXML
    private Label mailFieldStringLengthValidationInfo;

    /**
     * Tekst wyświetlający się, jeżeli użytkownik wprowadzi za długi ciąg znaków do pola {@linkplain  RegisterFormController#register_passwordTextField}
     * @see RegisterFormController#register()
     * @see RegisterFormController#checkPassword()
     */
    @FXML
    private Label passwordFieldStringLengthValidationInfo;

    /**
     * Flagi odpowiedzialne za sprawdzenie, czy konkretne pola formularza rejestracji są uzupełnione poprawnie
     * @see RegisterFormController#register()
     */
    private boolean nameCheckFlag, lastNameCheckFlag, mailCheckFlag, phoneNumberCheckFlag, passwordCheckFlag;

    /**
     * Metoda obsługująca proces rejestracji użytkownika.
     * @author Karol Przygoda
     */
    @FXML
    private void register() throws IOException {

        LocalDate today = LocalDate.now();

        if (checkIfEmpty())
            Alerts.failureAlert("Prosze uzupełnić wszystkie puste pola");
        else if(!nameCheckFlag || !lastNameCheckFlag || !mailCheckFlag || !phoneNumberCheckFlag || !passwordCheckFlag)
            Alerts.failureAlert("Proszę poprawić pola zaznaczone na czerwono");
        else if(Period.between(register_datePickerTextField.getValue(), today).getYears() < 18)
            Alerts.failureAlert("Aby mieć możliwość rejestracji trzeba być pełnoletnim !");
        else if(!(register_passwordTextField.getText().equals(register_passwordConfirmTextField.getText())))
            Alerts.failureAlert("Hasła się różnią !");
        else if(checkIfRegulationsAccepted())
        {
            int response = ServerController.sendInsertRequest(getRegisterData());

            if(response == 1)
                Alerts.successAlert("Zarejestrowano pomyślnie");
            else if (response == 0)
                Alerts.failureAlert("Przekazany adres mailowy jest już zarejestrowany w systemie");
            clearRegisterFields();
        }
    }

    private UserData getRegisterData()
    {
        Date currentDate = new Date();
        java.sql.Date sqlDate = new java.sql.Date(currentDate.getTime());

        UserData userData = new UserData();
        userData.setName(register_nameTextField.getText());
        userData.setLastName(register_lastNameTextField.getText());
        userData.setEmail(register_mailTextField.getText());
        userData.setGender("Wole nie podawać");
        userData.setPassword(register_passwordTextField.getText());
        userData.setPhoneNumber(register_phoneNumberTextField.getText());
        userData.setBirthDate(java.sql.Date.valueOf( register_datePickerTextField.getValue()));
        userData.setRegisterDate(sqlDate);

        return userData;

    }

    /**
     * Sprawdza, czy użytkownik nie wprowadził znaku nieporządnego
     * <p>
     * Metoda nasłuchuje na zmiany wprowadzone w pole tekstowe {@linkplain RegisterFormController#register_nameTextField}.
     * Jeżeli użytkownik wprowadzi znak zakazany obramowanie pola tekstowego zmieni się na czerwone.
     * Aby uniemożliwić przejścia dalej użytkownikowi do momentu poprawienia wprowadzanego tekstu metoda aktualizuje flagę {@linkplain RegisterFormController#nameCheckFlag}
     * która używana jest w instrukcji warunkowej if w metodzie {@linkplain RegisterFormController#register()}
     * ponadto wyświetli się krótki komunikat informujący użytkownika o problemie dzieje się to do momentu, kiedy użytkownik nie poprawi pola tekstowego
     * @see RegisterFormController#register()
     * @author Karol Przygoda
     */
    @FXML
    private void checkName() {
        register_nameTextField.addEventFilter(KeyEvent.ANY, event -> {
            String currentText = register_nameTextField.getText() + event.getCharacter();
            if (currentText.matches(".*[*!;@#$%^&()-=+{}|:',.<>/?_\\[\\]\"].*")) {
                register_nameTextField.setStyle("-fx-border-color: red");
                nameFieldValidationInfo.setVisible(true);
                nameFieldStringLengthValidationInfo.setVisible(false);
                nameCheckFlag = false;
            } else if(currentText.length() > 50)
            {
                register_nameTextField.setStyle("-fx-border-color: red");
                nameFieldStringLengthValidationInfo.setVisible(true);
                nameFieldValidationInfo.setVisible(false);
                nameCheckFlag = false;
            }
            else {
                register_nameTextField.setStyle("");
                nameFieldValidationInfo.setVisible(false);
                nameFieldStringLengthValidationInfo.setVisible(false);
                nameCheckFlag = true;
            }
        });
    }

    /**
     * Sprawdza, czy użytkownik wprowadził nie wprowadził znaku nieporządnego
     * <p>
     * Metoda nasłuchuje na zmiany wprowadzone w pole tekstowe {@linkplain RegisterFormController#register_lastNameTextField}.
     * Jeżeli użytkownik wprowadzi znak zakazany obramowanie pola tekstowego zmieni się na czerwone.
     * Aby uniemożliwić przejścia dalej użytkownikowi do momentu poprawienia wprowadzanego tekstu metoda aktualizuje flagę {@linkplain RegisterFormController#lastNameCheckFlag} która używana jest
     * w instrukcji warunkowej if w metodzie {@linkplain RegisterFormController#register()}
     * ponadto wyświetli się krótki komunikat informujący użytkownika o problemie dzieje się to do momentu, kiedy użytkownik nie poprawi pola tekstowego
     * @see RegisterFormController#register()
     * @author Karol Przygoda
     */
    @FXML
    private void checkLastName() {
        register_lastNameTextField.addEventFilter(KeyEvent.ANY, event -> {
            String currentText = register_lastNameTextField.getText() + event.getCharacter();
            if (currentText.matches(".*[*!;@#$%^&()-=+{}|:',.<>/?_\\[\\]\"].*")) {
                register_lastNameTextField.setStyle("-fx-border-color: red");
                lastNameFieldValidationInfo.setVisible(true);
                lastNameFieldStringLengthValidationInfo.setVisible(false);
                lastNameCheckFlag = false;
            }
            else if(currentText.length() > 50)
            {
                register_lastNameTextField.setStyle("-fx-border-color: red");
                lastNameFieldStringLengthValidationInfo.setVisible(true);
                lastNameFieldValidationInfo.setVisible(false);
                lastNameCheckFlag = false;
            }
            else {
                register_lastNameTextField.setStyle("");
                lastNameFieldValidationInfo.setVisible(false);
                lastNameFieldStringLengthValidationInfo.setVisible(false);
                lastNameCheckFlag = true;
            }
        });
    }

    /**
     * Sprawdza, czy użytkownik wprowadził poprawny format adresu mailowego
     * <p>
     * Metoda nasłuchuje na zmiany wprowadzone w pole tekstowe {@linkplain RegisterFormController#register_mailTextField}.
     * Jeżeli użytkownik wprowadzi adres mailowy w nieprawidłowym formacie obramowanie pola tekstowego zmieni się na czerwone.
     * Aby uniemożliwić przejścia dalej użytkownikowi do momentu poprawienia wprowadzanego tekstu metoda aktualizuje flagę {@linkplain RegisterFormController#mailCheckFlag} która używana jest
     * w instrukcji warunkowej if w metodzie {@linkplain RegisterFormController#register()}
     * ponadto wyświetli się krótki komunikat informujący użytkownika o problemie dzieje się to do momentu, kiedy użytkownik nie poprawi pola tekstowego
     * @see RegisterFormController#register()
     * @author Karol Przygoda
     */
    @FXML
    private void checkMail() {
        register_mailTextField.addEventFilter(KeyEvent.ANY, event -> {
            String currentText = register_mailTextField.getText();
            if (!currentText.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}")) {
                mailFieldValidationInfo.setVisible(true);
                mailFieldStringLengthValidationInfo.setVisible(false);
                register_mailTextField.setStyle("-fx-border-color: red");
                mailCheckFlag = false;
            }
            else if(currentText.length() > 100)
            {
                register_mailTextField.setStyle("-fx-border-color: red");
                mailFieldStringLengthValidationInfo.setVisible(true);
                mailFieldValidationInfo.setVisible(false);
                mailCheckFlag = false;
            }
            else {
                mailFieldValidationInfo.setVisible(false);
                mailFieldStringLengthValidationInfo.setVisible(false);
                register_mailTextField.setStyle("");
                mailCheckFlag = true;
            }
        });

    }

    /**
     * Sprawdza, czy użytkownik wprowadził prawidłowy format numeru komórkowego
     * <p>
     * Metoda nasłuchuje na zmiany wprowadzone w pole tekstowe {@linkplain RegisterFormController#register_phoneNumberTextField}.
     * Jeżeli użytkownik wprowadzi znak zakazany lub wprowadzi numer o nieprawidłowym formacie obramowanie pola tekstowego zmieni się na czerwone.
     * Aby uniemożliwić przejścia dalej użytkownikowi do momentu poprawienia wprowadzanego tekstu metoda aktualizuje flagę {@linkplain RegisterFormController#phoneNumberCheckFlag} która używana jest
     * w instrukcji warunkowej if w metodzie {@linkplain RegisterFormController#register()}
     * ponadto wyświetli się krótki komunikat informujący użytkownika o problemie dzieje się to do momentu, kiedy użytkownik nie poprawi pola tekstowego
     * @see RegisterFormController#register()
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
     * Sprawdza, czy użytkownik wprowadził prawidłowy format hasła
     * <p>
     * Metoda nasłuchuje na zmiany wprowadzone w pole tekstowe {@linkplain RegisterFormController#register_passwordTextField}.
     * Jeżeli użytkownik nie wprowadzi hasła o prawidłowym formacie obramowanie pola tekstowego zmieni się na czerwone.
     * Aby uniemożliwić przejścia dalej użytkownikowi do momentu poprawienia wprowadzanego tekstu metoda aktualizuje flagę {@linkplain RegisterFormController#passwordCheckFlag} która używana jest
     * w instrukcji warunkowej if w metodzie {@linkplain RegisterFormController#register()}
     * ponadto wyświetli się krótki komunikat informujący użytkownika o problemie dzieje się to do momentu, kiedy użytkownik nie poprawi pola tekstowego
     * @see RegisterFormController#register()
     * @author Karol Przygoda
     */
    @FXML
    private void checkPassword() {
        register_passwordTextField.addEventFilter(KeyEvent.ANY, event -> {
            String currentText = register_passwordTextField.getText() + event.getCharacter();
            if (!currentText.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#!$%^&-+=()])(?=\\S+$).{8,}$")) {
                passwordFieldValidationInfo.setVisible(true);
                passwordFieldStringLengthValidationInfo.setVisible(false);
                register_passwordTextField.setStyle("-fx-border-color: red");
                passwordCheckFlag = false;
            }
            else if(currentText.length() > 255)
            {
                register_passwordTextField.setStyle("-fx-border-color: red");
                passwordFieldStringLengthValidationInfo.setVisible(true);
                passwordFieldValidationInfo.setVisible(false);
                passwordCheckFlag = false;
            }
            else {
                passwordFieldValidationInfo.setVisible(false);
                passwordFieldStringLengthValidationInfo.setVisible(false);
                register_passwordTextField.setStyle("");
                passwordCheckFlag = true;
            }
        });

    }

    /**
     * Sprawdza, czy wszystkie pola w formularzu rejestracji są uzupełnione
     * <p>
     * Sprawdza, czy wszystkie pola w formularzu rejestracji są uzupełnione w metodzie {@linkplain RegisterFormController#register()} aby uniemożliwić
     * przejścia dalej użytkownikowi, jeżeli pominął co najmniej jedno pole
     * @return true, jeżeli wszystkie pola w formularzu rejestracji są uzupełnione false, jeżeli jakieś pole nie jest uzupełnione
     * @see RegisterFormController#register()
     * @author Karol Przygoda
     */
    @FXML
    private boolean checkIfEmpty() {
        return register_nameTextField.getText().isEmpty()
                || register_lastNameTextField.getText().isEmpty()
                || register_mailTextField.getText().isEmpty()
                || register_passwordTextField.getText().isEmpty()
                || register_passwordConfirmTextField.getText().isEmpty()
                || register_phoneNumberTextField.getText().isEmpty()
                || register_datePickerTextField.getValue() == null;

    }

    /**
     * Sprawdza, czy użytkownik zaakceptował warunki użytkownika
     * @author Karol Przygoda
     */
    private boolean checkIfRegulationsAccepted() throws IOException {
        Stage modalStage = new Stage();
        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.initStyle(StageStyle.UTILITY);
        modalStage.setTitle("Warunki użytkowania");

        FXMLLoader fxmlLoader = new FXMLLoader(FormsContainer.class.getResource("RegulationsView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        modalStage.initStyle(StageStyle.TRANSPARENT);
        modalStage.setScene(scene);
        modalStage.showAndWait();//wyświetl okno zawierające informacje dotyczące warunków użytkowania

        return RegulationsController.accepted;
    }

    /**
     * Czyści pola tekstowe w formularzu rejestracji po udanej, jak i nieudanej operacji rejestracji
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
