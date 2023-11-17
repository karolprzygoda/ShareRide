package SharerideClient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Główna klasa aplikacji klienta ShareRide.
 *<p>
 * Klasa `LoginView` jest klasą główną aplikacji. Rozszerzana jest przez interfejs
 * `Application` i zawiera metodę `main()`, która uruchamia aplikację. Klasa
 * inicjalizuje scenę i okno aplikacji, wczytuje plik FXML reprezentujący widok
 * logowania/rejestracji dodatkowo definiuje zachowanie dla przeciągania okna po ekranie.
 * @author Karol Przygoda
 */
public class LoginView extends Application {
    private double x = 0;
    private double y = 0;
    public static Socket socket;

    /**
     * Metoda start.
     *<p>
     * Metoda `start()` jest punktem wejscia dla aplikacji napisanych z wykorzystaniem JavaFX. Inicjalizuje scenę i okno aplikacji,
     * wczytuje plik FXML reprezentujący widok logowania/rejestracji, ustawia ikone aplikacji, tytuł aplikacji
     * oraz definiuje zachowanie dla przeciągania okna po ekranie. Następnie tworzy nowe połączenie z serwerem jeżeli się powiedzie to po zakończeniu
     * konfiguracji, wyświetla okno aplikacji. W przeciwnym wypadku aplikacja wyświetli komunikat o problemie serwera a następnie się wylączy
     *
     * @param stage Referencja do obiektu klasy Stage, reprezentującego okno aplikacji.
     * @throws IOException Wyjątek, który może zostać zgłoszony w przypadku błędu
     *                     podczas wczytywania pliku FXML.
     * @author Karol Przygoda
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginView.class.getResource("LoginView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 650);
        stage.initStyle(StageStyle.TRANSPARENT);//usuniecie domyslnego paska gornego aplikacji

        Image icon = new Image("icon.png");

        scene.setOnMousePressed((MouseEvent event) -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });

        scene.setOnMouseDragged((MouseEvent event) -> {
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        });

        stage.getIcons().add(icon);
        stage.setTitle("ShareRide");
        stage.setScene(scene);
        stage.show();


        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress("localhost", 5000));//tworzenie polaczenia z serwerem
        } catch (Exception e) {//jezeli serwer nie jest uruchomiony lub nie udalo sie utworzyc polaczenia
            Alert alert;
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd serwera");
            alert.setHeaderText(null);
            alert.setContentText("Serwer napotkał problem");
            alert.showAndWait();
            stage.close();
        }

    }

    /**
     * Metoda main.
     *<p>
     * Główna metoda aplikacji, która uruchamia aplikację klienta ShareRide.
     * Wywołuje metodę `launch()`, która inicjalizuje i uruchamia aplikację JavaFX.
     *
     * @param args Argumenty wiersza poleceń przekazane do metody main.
     * @author Karol Przygoda
     */
    public static void main(String[] args) {
        launch();
    }

}




