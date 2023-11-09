package com.example.sharerideclient;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Kontroler okna akceptacji warunkow uzytkowania
 */
public class RegulationsController {

    @FXML
    private Button acceptBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    private CheckBox checkBox;
    @FXML
    private Label infoLabel;

    protected static boolean accepted = false;//flga oznaczajaca czy uzytkownik zaakceptowal warunki korzystania z systemu

    /**
     * Rezygnacja z akceptacji warunków użytkowania.
     * <p>
     * Metoda jest wywoływana po kliknięciu przycisku "cancelBtn". Zamyka okno akceptacji warunków użytkowania bez rejestrowania użytkownika
     */
    public void cancel() {
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    /**
     * Zaakceptowanie warunkow uzytkowania.
     * <p>
     * Metoda jest wywoływana po kliknięciu przycisku "acceptBtn" oraz zaznaczeniu checkBoxa "checkBox".
     * Ustawia flage akceptacji warunków na prawde a następnie zamyka okno akceptacji warunków użytkowania
     * Jeżeli użytkownik nie zaznaczy check boxa wyświetla tekst informujący o tym ze konieczne jest jego zaznaczenie
     */
    public void accept() {
        if (checkBox.isSelected()) {
            accepted = true;
            Stage stage = (Stage) acceptBtn.getScene().getWindow();
            stage.close();

        } else {
            infoLabel.setVisible(true);

        }
    }


}
