package SharerideClient.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Kontroler okna akceptacji warunków użytkowania
 * @see RegisterFormController
 * @author Karol Przygoda
 */
public class RegulationsController {

    /**
     * Przycisk odpowiedzialny za akceptacje warunków użytkowania
     * @see RegulationsController#accept()
     */
    @FXML
    private Button acceptBtn;

    /**
     * Przycisk odpowiedzialny za anulowanie akceptacji warunków użytkowania
     * @see RegulationsController#cancel()
     */
    @FXML
    private Button cancelBtn;

    /**
     * CheckBox odpowiedzialny potwierdzenie akceptacji warunków użytkowania
     * @see RegulationsController#accept()
     * @see RegulationsController#cancel()
     */
    @FXML
    private CheckBox checkBox;

    /**
     * Napis wyświetlany, jeżeli użytkownik nie zaznaczy {@linkplain RegulationsController#checkBox}
     * @see RegulationsController#accept()
     * @see RegulationsController#cancel()
     */
    @FXML
    private Label infoLabel;

    /**
     * Flaga sprawdzająca, czy użytkownik zaakceptował warunki korzystania z systemu
     * @see RegulationsController#accept()
     */
    protected static boolean accepted = false;//flaga sprawdzająca, czy użytkownik zaakceptował warunki korzystania z systemu

    /**
     * Rezygnacja z akceptacji warunków użytkowania.
     * <p>
     * Metoda jest wywoływana po kliknięciu przycisku {@linkplain RegulationsController#cancelBtn}.
     * Zamyka okno akceptacji warunków użytkowania bez rejestrowania użytkownika
     * @author Karol Przygoda
     */
    @FXML
    private void cancel() {
        accepted = false;
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    /**
     * Zaakceptowanie warunków użytkowania.
     * <p>
     * Metoda jest wywoływana po kliknięciu przycisku {@linkplain RegulationsController#acceptBtn} oraz zaznaczeniu {@linkplain RegulationsController#checkBox}.
     * Ustawia flagę akceptacji warunków na, true, a następnie zamyka okno akceptacji warunków użytkowania,
     * Jeżeli użytkownik nie zaznaczy {@linkplain RegulationsController#checkBox} wyświetla tekst informujący o tym, że konieczne jest jego zaznaczenie
     * @author Karol Przygoda
     */
    @FXML
    private void accept() {
        if (checkBox.isSelected()) {
            accepted = true;
            Stage stage = (Stage) acceptBtn.getScene().getWindow();
            stage.close();

        } else {
            infoLabel.setVisible(true);

        }
    }
}
