package SharerideClient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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
 * Klasa `FormsContainer` jest klasą główną aplikacji. Rozszerzana jest przez interfejs
 * `Application` i zawiera metodę `main()`, która uruchamia aplikację. Klasa
 * inicjalizuje scenę i okno aplikacji wczytuje plik FXML reprezentujący widok
 * okna będącego kontenerem dla formularzy logowania oraz rejestracji dodatkowo definiuje zachowanie dla przeciągania okna po ekranie.
 * @see Application
 * @author Karol Przygoda
 */
public class FormsContainer extends Application {
    private double x = 0;
    private double y = 0;
    public static Socket socket;

    /**
     * Metoda start.
     *<p>
     * Metoda `start()` jest punktem wejścia dla aplikacji napisanych z wykorzystaniem JavaFX. Inicjalizuje scenę i okno aplikacji,
     * wczytuje plik FXML reprezentujący widok kontenera dla formularzy logowania/rejestracji, ustawia ikonę aplikacji, tytuł aplikacji
     * oraz definiuje zachowanie dla przeciągania okna po ekranie. Następnie tworzy nowe połączenie z serwerem, jeżeli operacja ta się powiedzie to po zakończeniu
     * konfiguracji, wyświetla okno aplikacji. W przeciwnym wypadku aplikacja wyświetli komunikat o problemie serwera, a następnie się wyłączy
     * @see Application#start(Stage)
     * @param stage Referencja do obiektu klasy Stage, reprezentującego okno aplikacji.
     * @throws IOException Wyjątek, który może zostać zgłoszony w przypadku błędu
     *                     podczas wczytywania pliku FXML.
     * @author Karol Przygoda
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FormsContainer.class.getResource("FormsContainer.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 650);
        stage.initStyle(StageStyle.TRANSPARENT);//usuniecie domyślnego paska górnego aplikacji

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
            socket.connect(new InetSocketAddress("localhost", 5000));//tworzenie połączenia z serwerem
        } catch (Exception e) {//jeżeli serwer nie jest uruchomiony lub nie udało się utworzyć połączenia
            Alerts.failureAlert("Serwer napotkał problem");
            stage.close();
        }
    }

    /**
     * Metoda main.
     *<p>
     * Główna metoda aplikacji, która uruchamia aplikację klienta ShareRide.
     * Wywołuje metodę `launch()`, która inicjalizuje i uruchamia aplikację JavaFX.
     * @see Application#launch(String...)
     * @param args Argumenty wiersza poleceń przekazane do metody main.
     * @author Karol Przygoda
     */
    public static void main(String[] args) {
        launch();
    }

}




