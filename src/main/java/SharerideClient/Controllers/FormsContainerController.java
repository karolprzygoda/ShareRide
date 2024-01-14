package SharerideClient.Controllers;

import SharerideClient.Views.FormsContainer;
import SharerideClient.Views.LoginFormView;
import SharerideClient.Views.RegisterFormView;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static SharerideClient.Views.FormsContainer.*;

/**
 * Kontroler interfejsu graficznego kontenera dla formularzy logowania i rejestracji
 * @see FormsContainer
 * @author Karol Przygoda
 */
public class FormsContainerController implements Initializable {

    /**
     * Pole przechowujące formularz logowania
     * @see FormsContainerController#initialize(URL, ResourceBundle)
     */
    @FXML
    private AnchorPane loginForm;

    /**
     * Pole przechowujące formularz rejestracji
     * @see FormsContainerController#initialize(URL, ResourceBundle)
     */
    @FXML
    private AnchorPane registerForm;

    /**
     * Pole przechowujące grafikę przesuwającą po oknie w zależności od wyboru formularza
     * @see FormsContainerController#switchForm(ActionEvent)
     */
    @FXML
    private AnchorPane sidePane;

    /**
     * Przycisk odpowiadający za zmienienie formularza na formularz rejestracji
     * @see FormsContainerController#switchForm(ActionEvent)
     */
    @FXML
    private Button registerBtn;

    /**
     * Przycisk odpowiadający za zmienienie formularza na formularz logowania
     * @see FormsContainerController#switchForm(ActionEvent)
     */
    @FXML
    private Button register_loginBtn;

    /**
     * Przycisk odpowiadający za minimalizację okienka do paska zadań
     * @see FormsContainerController#minimize()
     */
    @FXML
    private Button minBtn;

    /**
     * Przycisk odpowiadający za zamknięcie aplikacji z poziomu formularzy
     * @see FormsContainerController#close()
     */
    @FXML
    private Button closeBtn;

    /**
     * Metoda odpowiedzialna za przełączanie formularzy rejestracji i logowania.
     * <p>
     * Obsługuje zdarzenie akcji, które występuje po kliknięciu
     * przycisku rejestracji {@linkplain FormsContainerController#registerBtn} lub przycisku powrotu do logowania {@linkplain FormsContainerController#register_loginBtn}.
     * Metoda animuje przesunięcie formularza na bocznym panelu w celu wyświetlenia odpowiedniego formularza. Przesunięcie
     * wykonywane jest za pomocą metody {@linkplain TranslateTransition#play()}.
     * @param event Zdarzenie akcji wywołane przez kliknięcie przycisku rejestracji lub powrotu do logowania.
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
     * Zamyka aplikację i kończy jej działanie.
     * <p>
     * Metoda jest wywoływana po kliknięciu przycisku {@linkplain FormsContainerController#closeBtn}. Zamyka połączenie z serwerem, zamyka okno aplikacji oraz kończy jej działanie.
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
     * Metoda jest wywoływana po kliknięciu przycisku {@linkplain FormsContainerController#minBtn}. Minimalizuje aktualne okno aplikacji, zmniejszając je do ikony
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
     * Inicjalizuje formularze logowania oraz rejestracji.
     * <p>
     * Metoda jest wywoływana automatycznie po załadowaniu sceny i inicjalizuje formularze logowania oraz rejestracji.
     * Wczytuje plik FXML zawierający formularz rejestracji do pola {@linkplain FormsContainerController#registerForm}
     * Wczytuje plik FXML zawierający formularz logowania do pola {@linkplain FormsContainerController#loginForm}
     * @param url             URL sceny, która została załadowana
     * @param resourceBundle zasoby powiązane z kontrolerem widoku
     * @author Karol Przygoda
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            LoginFormView loginFormView = new LoginFormView();
            RegisterFormView registerFormView = new RegisterFormView();
            registerForm.getChildren().add(registerFormView.initialize());
            loginForm.getChildren().add(loginFormView.initialize());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}