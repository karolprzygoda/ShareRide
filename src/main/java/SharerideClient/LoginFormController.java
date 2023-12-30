package SharerideClient;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Kontroler formularza logowania
 * @author Karol Przygoda
 */
public class LoginFormController {

    /**
     * Przycisk odpowiadający za wysłanie rządnie logowania do serwera
     * @see LoginFormController#login()
     * @see ServerController#sendLoginInfoToServer(String, String)
     */
    @FXML
    private Button loginBtn;

    /**
     * Pole tekstowe odpowiadające za wprowadzenie adresu mailowego
     * @see LoginFormController#login()
     */
    @FXML
    private TextField mailTextField;

    /**
     * Pole tekstowe zabezpieczone odpowiadające za wprowadzenie hasła
     * @see LoginFormController#login()
     */
    @FXML
    private PasswordField passwordTextField;


    /**
     * Metoda obsługująca proces logowania użytkownika.
     * <p>
     * Metoda `login()` jest odpowiedzialna za wysłanie do serwera danych logowania użytkownika. Na podstawie wprowadzonych
     * danych, metoda wysyła przechwycone dane do serwera za pomocą metody {@linkplain ServerController#sendLoginInfoToServer(String, String)}
     * , serwer sprawdza ich występowanie w bazie danych.
     * Następnie odbiera odpowiedź od serwera za pomocą metody {@linkplain  ServerController#getLoginFeedBackFromServer()}
     * Jeśli dopasowanie zostanie znalezione, metoda wyłącza okno logowania i przechodzi do okna głównego
     * W przeciwnym wypadku użytkownik zostaje poinformowany za pomocą okna alertu o wprowadzeniu niezgadzających się ze sobą danych.
     * Jeżeli serwer napotkał problem flaga {@linkplain ServerController#errorFlag} ustawiona jest na true użytkownik widzi wtedy odpowiedni komunikat
     * @author Karol Przygoda
     */
    @FXML
    private void login() throws IOException {

        if (mailTextField.getText().isEmpty() || passwordTextField.getText().isEmpty())
        {
            Alerts.failureAlert("Proszę uzupełnić wszystkie puste pola");
        }
        else
        {

            String mail = mailTextField.getText();
            String password = passwordTextField.getText();
            int response = ServerController.sendLoginRequest(mail, password);

            if(response == 1)
            {
                loginBtn.getScene().getWindow().hide();
                Stage stage = new Stage();
                ClientDashBoardView clientDashBoardView = new ClientDashBoardView();
                clientDashBoardView.start(stage);

            }
            else if(response == 0)
                Alerts.failureAlert("Podany adres mailowy oraz hasło nie zgadzają się");
        }
    }
}
