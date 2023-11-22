package SharerideClient;
import javafx.scene.control.Alert;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Scanner;

/**
 * Kontroler zarządzający połączeniem z serwerem
 *
 * @author Karol Przygoda
 */
public class ServerController {

    static protected boolean errorFlag = false;

    /**
     * Wysyła informacje wprowadzone przez użytkownika w przypadku logowania
     * <p>
     * Metoda jest wywoływana po kliknięciu przycisku "loginBtn". Tworzony jest nowy obiekt PrintWritera za pomocą którego
     * metoda wysyła komende do serwera informując go ze użytkownik próbuje się zalogować, następnie wysyła dane wprowadzone przez użytkownika
     * w celu sprawdzenia ich występowania
     * @param mail adres mailowy wprowadzony przez użytkownika
     * @param password hasło wprowadzone przez użytkownika
     * @author Karol Przygoda
     */
    static protected void sendLoginInfoToServer(String mail, String password) {
        try{
            PrintWriter out = new PrintWriter(new OutputStreamWriter(LoginView.socket.getOutputStream()), true);
            out.println("LOGIN");
            out.println(mail);
            out.println(password);
        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Wysyła informacje wprowadzone przez użytkownika w przypadku rejestracji
     * <p>
     * Metoda jest wywoływana po kliknięciu przycisku "loginBtn". Tworzony jest nowy obiekt PrintWritera za pomocą którego
     * metoda wysyła komende do serwera informując go ze użytkownik próbuje się zalogować, następnie wysyła dane wprowadzone przez użytkownika
     * w celu sprawdzenia ich występowania
     * @param name imie wprowadzone przez uzytkownika
     * @param lastName nazwisko wprowadzone przez uzytkownika
     * @param mail adres mailowy wprowdzony przez uzytkownika
     * @param phoneNumber numer telefonu wprowadzony przez uzytkowniak
     * @param birthDate data urodzenia wprowadzona przez uzytkownika
     * @param password haslo wprowadzone przez uzytkownika
     * @author Karol Przygoda
     */
    static protected void sendRegisterInfoToServer(String name, String lastName, String mail, String phoneNumber, LocalDate birthDate, String password) {
        try{
            PrintWriter out = new PrintWriter(new OutputStreamWriter(LoginView.socket.getOutputStream()), true);
            out.println("REGISTER");
            out.println(name);
            out.println(lastName);
            out.println(mail);
            out.println(phoneNumber);
            out.println(birthDate);
            out.println(password);
        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Odbiera informacje zwrotną wysłaną przez serwer
     * <p>
     * Metoda odbiera odpowiedzi od serwera o tym czy rejestracja lub logowanie się powiodło
     * jeżeli serwer napotkał problem i nie wysłał wiadomości użytkownik jest o tym informowany flaga errorFlag jest ustawiana na true aby komunikat wyswietlil sie poprawnie
     * @return true jeżeli klient otrzymal informacje od serwera, false jeżeli klient nie otrzymał infromacji od serwera
     * @author Karol Przygoda
     */
    static protected boolean getFeedBackFromServer() {

        try{
            Scanner scanner = new Scanner(LoginView.socket.getInputStream());

            if(scanner.hasNextLine())
            {
                boolean serverFeedBack = scanner.nextBoolean();

                return serverFeedBack;
            }
            else {
                Alert alert;
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Błąd serwera");
                alert.setHeaderText(null);
                alert.setContentText("Serwer napotkał problem");
                alert.showAndWait();
                errorFlag = true;
                return false;
            }

        }catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * Wyświetla odpowiedni komunikat na podstawie innformacji zwrotnej od serwera
     * <p>
     * Metoda wyświetla kouminat o powodzeniu rejestracji jeżeli metoda {@linkplain ServerController#getFeedBackFromServer()} zwróci true
     * w przeciwnym wypadku jeżeli nie zaszedł błąd serwera metoda wyświetli komunikat o zarejestrowanym adresie mailowym w bazie danych
     * @author Karol Przygoda
     */
    static protected void displayRegistrationServerFeedback(boolean feedback) {
        Alert alert;

        if(feedback)
        {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Rejestracja powiodła się");
            alert.setHeaderText(null);
            alert.setContentText("Zarejestrowano pomyślnie");
            alert.showAndWait();
            RegulationsController.accepted = false;
        }
        else
        {
            if(!errorFlag)
            {
                RegulationsController.accepted = false;
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Błąd rejestracji");
                alert.setHeaderText(null);
                alert.setContentText("Wprowadzony adres mailowy już jest zarejestrowany");
                alert.showAndWait();
            }
        }
    }

}
