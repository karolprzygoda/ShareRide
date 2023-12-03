package SharerideClient;

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
    private Label avgRaingLabel;

    /**
     * Pole wyświetlające date urodzenia użytkownika
     * @see UserProfileController#updateInfo()
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
     * @see UserProfileController#updateInfo()
     */
    @FXML
    private Label genderLabel;

    /**
     * Pole wyświetlające date zarejestrowania konta przez użytkownika
     * @see UserProfileController#updateInfo()
     */
    @FXML
    private Label joinDateLabel;

    /**
     * Pole wyświetlające nazwisko użytkownika
     * @see UserProfileController#updateInfo()
     */
    @FXML
    private Label lastNameLabel;

    /**
     * Pole wyświetlające adres mailowy użytkownika
     * @see UserProfileController#updateInfo()
     */
    @FXML
    private Label mailLabel;

    /**
     * Pole wyświetlające imię użytkownika
     * @see UserProfileController#updateInfo()
     */
    @FXML
    private Label nameLabel;

    /**
     * Pole wyświetlające numer telefonu użytkownika
     * @see UserProfileController#updateInfo()
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
    private PasswordField profile_updateComfirmPasswordField;

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
     * Tablica przechowująca możliwe wybory płci
     * @see UserProfileController#gender()
     */
    private final String[] genderList = {"Mężczyzna", "Kobieta", "Wole nie podawać"};

    /**
     * Flagi odpowiedzialne za sprawdzenie, czy konkretne pola formularza rejestracji są uzupełnione poprawnie
     * @see UserProfileController#update()
     */
    private boolean nameCheckFlag = true, lastNameCheckFlag = true, mailCheckFlag = true, phoneNumberCheckFlag = true, passwordCheckFlag = true;


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
                nameCheckFlag = false;
            } else {
                profile_updateNameField.setStyle("");
                profile_nameFieldValidationInfo.setVisible(false);
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
                lastNameCheckFlag = false;
            } else {
                profile_updateLastNameField.setStyle("");
                profile_lastNameFieldValidationInfo.setVisible(false);
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
     * @author Karol Przygoda, Radosław Jasiński, Jakub Kotwica
     */
    public void updateInfo()
    {
        gender();
        try {

            ServerController.sendClientRequest(ServerController.id, "PROFILE-INFO");
            List<String> profileInfo;
            profileInfo = ServerController.getProfileInfo();
            assert profileInfo != null;
            nameLabel.setText(profileInfo.get(0));
            lastNameLabel.setText(profileInfo.get(1));
            genderLabel.setText(profileInfo.get(2));
            mailLabel.setText(profileInfo.get(3));
            phoneNumberLabel.setText(profileInfo.get(4));
            birthDateLabel.setText(profileInfo.get(5));
            joinDateLabel.setText(profileInfo.get(6));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Metoda obsługująca proces aktualizacji danych osobowych użytkownika.
     * <p>
     * Metoda `update()` jest odpowiedzialna za pośrednie wysłanie wypełnionych danych wybranych do aktualizacji przez użytkownika zalogowanego w aktualnej sesji
     * za pomocą metody: {@linkplain ServerController#sendUpdateInfoToServer(int, Map )} do serwera.
     * Metoda sprawdza, czy wszystkie wymagane pola rejestracyjne zostały uzupełnione za pomocą metody {@linkplain  UserProfileController#checkIfEmpty()}. Następnie
     * sprawdzane jest, czy poszczególne pola spełniają określone warunki takie jak odpowiedni format tekstu lub brak znaków zakazanych
     * za pomocą metod: {@linkplain UserProfileController#checkMail()}, {@linkplain UserProfileController#checkName()}, {@linkplain UserProfileController#checkPassword()}
     * {@linkplain UserProfileController#checkLastName()} {@linkplain UserProfileController#checkPhoneNumber()}, jeżeli
     * wszystkie warunki zostaną spełnione metoda tworzy mapę kolumn, które użytkownik chce zaktualizować oraz wartości, na jakie chce je zaktualizować
     * Ostatnim etapem rejestracji jest wysłanie danych do serwera oraz odebranie z niego informacji zwrotnej
     * do wyświetlenia zaktualizowanych danych służy metoda {@linkplain UserProfileController#updateInfo()}.
     * @see ServerController
     * @see ServerController#sendUpdateInfoToServer(int, Map )
     * @author Karol Przygoda
     */
    @FXML
    private void update(){

        Alert alert;

        if (checkIfEmpty())
        {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd");
            alert.setHeaderText(null);
            alert.setContentText("Prosze uzupełnić pole które chcesz zaktualizować"); // jeżeli przynajmniej 1 pole pozostało puste użytkownik zostaje o tym poinformowany
            alert.showAndWait();
        }
        else if(!nameCheckFlag || !lastNameCheckFlag || !mailCheckFlag || !phoneNumberCheckFlag || !passwordCheckFlag) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd");
            alert.setHeaderText(null);
            alert.setContentText("Proszę poprawić pola zaznaczone na czerwono");
            alert.showAndWait();
        }
        else if(!(profile_updatePasswordField.getText().equals(profile_updateComfirmPasswordField.getText())))
        {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd");
            alert.setHeaderText(null);
            alert.setContentText("Hasła się różnią !");
            alert.showAndWait();
        }
        else
        {
            Map<String, String> fieldsToUpdate = new HashMap<>();

            if(!profile_updateNameField.getText().isEmpty())
                fieldsToUpdate.put("imie", profile_updateNameField.getText());
            if(!profile_updateLastNameField.getText().isEmpty())
                fieldsToUpdate.put("nazwisko", profile_updateLastNameField.getText());
            if(!profile_updateMailField.getText().isEmpty())
                fieldsToUpdate.put("email", profile_updateMailField.getText());
            if(!profile_updatePhoneNumberField.getText().isEmpty())
                fieldsToUpdate.put("numer_telefonu", profile_updatePhoneNumberField.getText());
            if(!profile_updatePasswordField.getText().isEmpty())
                fieldsToUpdate.put("haslo", profile_updatePasswordField.getText());
            if(profile_updateGenderComboBox.getSelectionModel().getSelectedItem() != null)
                fieldsToUpdate.put("plec", profile_updateGenderComboBox.getValue());

            ServerController.sendUpdateInfoToServer(ServerController.id, fieldsToUpdate);

            updateInfo();
            clearUpdateFields();
        }
    }

    /**
     * Metoda odpowiedzialna za wysłanie żądania usunięcia konta do serwera
     * @see ServerController#sendClientRequest(int, String)
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
                ServerController.sendClientRequest(ServerController.id, "DELETE");
                profile_deleteAccBtn.getScene().getWindow().hide();
                FormsContainer formsContainer = new FormsContainer();
                Stage stage = new Stage();
                formsContainer.start(stage);
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
        profile_updateComfirmPasswordField.setText("");
        profile_updateGenderComboBox.setPromptText("Wybierz Płeć");
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
        updateInfo();
    }

}
