package SharerideClient.Controllers;

import Data.UserData;
import SharerideClient.Alerts;
import SharerideClient.Views.ClientDashBoardView;
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

            UserData userData = new UserData();
            userData.setEmail(mailTextField.getText());
            userData.setPassword(passwordTextField.getText());

            int response = ServerController.sendLoginRequest(userData);

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
