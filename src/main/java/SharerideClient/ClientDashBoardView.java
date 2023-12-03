package SharerideClient;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * Klasa odpowiedzialna za uruchomienie panelu zarządzania klienta w aplikacji Desktop Shop.
 * Zarządza inicjalizacją sceny, obsługą zdarzeń myszy oraz konfiguracją okna, wczytywania odpowiednich zakładek.
 * @author Karol Przygoda
 */
public class ClientDashBoardView {

    private double x = 0;
    private double y = 0;

    /**
     * Metoda uruchamiająca główny panel zarządzania klienta.
     * @param stage Obiekt Stage, na którym ma być wyświetlony panel.
     * @throws IOException W przypadku problemów z załadowaniem pliku FXML.
     * @author Karol Przygoda
     */
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(FormsContainer.class.getResource("ClientDashBoard.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.setFill(Color.TRANSPARENT);

        Image icon = new Image("icon.png");

        scene.setOnMousePressed((MouseEvent event) -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });

        scene.setOnMouseDragged((MouseEvent event) -> {
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        });

        stage.initStyle(StageStyle.TRANSPARENT);
        stage.getIcons().add(icon);
        stage.setTitle("ShareRide");
        stage.setScene(scene);
        stage.show();
    }
}