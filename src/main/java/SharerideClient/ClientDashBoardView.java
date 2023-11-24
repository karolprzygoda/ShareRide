package SharerideClient;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * Klasa odpowiedzialna za uruchomienie panelu zarządzania klienta w aplikacji Desktop Shop.
 * Zarządza inicjalizacją sceny, obsługą zdarzeń myszy oraz konfiguracją okna Stage.
 */
public class ClientDashBoardView {

    private double x = 0;
    private double y = 0;

    /**
     * Metoda uruchamiająca panel zarządzania klienta.
     *
     * @param stage Obiekt Stage, na którym ma być wyświetlony panel.
     * @throws IOException W przypadku problemów z załadowaniem pliku FXML.
     */
    public void start(Stage stage) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("ClientDashBoard.fxml"));
        Scene scene = new Scene(root);
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
        stage.setTitle("CYBERZONE");
        stage.setScene(scene);
        stage.show();
    }
}