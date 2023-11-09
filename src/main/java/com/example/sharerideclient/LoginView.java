package com.example.sharerideclient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * Główna klasa aplikacji klienta ShareRide.
 *<p>
 * Klasa `LoginView` jest klasą główną aplikacji. Rozszerzana jest przez interfejs
 * `Application` i zawiera metodę `main()`, która uruchamia aplikację. Klasa
 * inicjalizuje scenę i okno aplikacji, wczytuje plik FXML reprezentujący widok
 * logowania/rejestracji dodatkowo definiuje zachowanie dla przeciągania okna po ekranie.
 */
public class LoginView extends Application {
    private double x = 0;
    private double y = 0;

    /**
     * Metoda start.
     *<p>
     * Metoda `start()` jest punktem wejscia dla aplikacji napisanych z wykorzystaniem JavaFX. Inicjalizuje scenę i okno aplikacji,
     * wczytuje plik FXML reprezentujący widok logowania/rejestracji, ustawia ikone aplikacji, tytuł aplikacji
     * oraz definiuje zachowanie dla przeciągania okna po ekranie. Po zakończeniu
     * konfiguracji, wyświetla okno aplikacji.
     *
     * @param stage Referencja do obiektu klasy Stage, reprezentującego okno aplikacji.
     * @throws IOException Wyjątek, który może zostać zgłoszony w przypadku błędu
     *                     podczas wczytywania pliku FXML.
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
    }

    /**
     * Metoda main.
     *<p>
     * Główna metoda aplikacji, która uruchamia aplikację klienta ShareRide.
     * Wywołuje metodę `launch()`, która inicjalizuje i uruchamia aplikację JavaFX.
     *
     * @param args Argumenty wiersza poleceń przekazane do metody main.
     */
    public static void main(String[] args) {
        launch();
    }
}