package SharerideClient;
import javafx.scene.control.Alert;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Kontroler zarządzający połączeniem z serwerem
 * @author Karol Przygoda
 */
public class ServerController {

    /**
     * Flaga sprawdzająca, czy serwer nie napotkał problemu
     * @see ServerController#displayRegistrationServerFeedback(boolean) 
     */
    static protected boolean errorFlag = false;
    
    /**
     * Zmienna przechowująca id użytkownika zalogowanego w aktualnej sesji
     * @see ServerController#getLoginFeedBackFromServer()
     * @see ServerController#sendClientRequest(int, String)
     */
    static protected int id;


    /**
     * Wysyła informacje wprowadzone przez użytkownika w przypadku logowania
     * <p>
     * Tworzony jest nowy obiekt PrintWritera, za pomocą którego
     * metoda wysyła komendę do serwera informując go, że użytkownik próbuje się zalogować, następnie wysyła dane wprowadzone przez użytkownika
     * w celu sprawdzenia ich występowania
     * @param mail adres mailowy wprowadzony przez użytkownika
     * @param password hasło wprowadzone przez użytkownika
     * @author Karol Przygoda
     */
    static protected void sendLoginInfoToServer(String mail, String password) {
        try{
            PrintWriter out = new PrintWriter(new OutputStreamWriter(FormsContainer.socket.getOutputStream()), true);
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
     * Tworzony jest nowy obiekt PrintWritera, za pomocą którego
     * metoda wysyła komendę do serwera informując go, że użytkownik próbuje się zarejestrować, następnie wysyła dane wprowadzone przez użytkownika
     * w celu sprawdzenia ich występowania oraz utworzenia konta
     * @param name imię wprowadzone przez użytkownika
     * @param lastName nazwisko wprowadzone przez użytkownika
     * @param mail adres mailowy wprowadzony przez użytkownika
     * @param phoneNumber numer telefonu wprowadzony przez użytkownika
     * @param birthDate data urodzenia wprowadzona przez użytkownika
     * @param password hasło wprowadzone przez użytkownika
     * @param date data zarejestrowania konta
     * @author Karol Przygoda
     */
    static protected void sendRegisterInfoToServer(String name, String lastName, String mail, String phoneNumber, LocalDate birthDate, String password, java.sql.Date date) {
        try{
            PrintWriter out = new PrintWriter(new OutputStreamWriter(FormsContainer.socket.getOutputStream()), true);
            out.println("REGISTER");
            out.println(name);
            out.println(lastName);
            out.println(mail);
            out.println(phoneNumber);
            out.println(birthDate);
            out.println(password);
            out.println(date);
        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Odbiera informację zwrotną wysłaną przez serwer
     * <p>
     * Metoda odbiera odpowiedzi od serwera o tym, czy rejestracja się powiodła,
     * jeżeli serwer napotkał problem i nie wysłał wiadomości użytkownik jest o tym informowany flaga {@linkplain ServerController#errorFlag} jest ustawiana
     * na true, aby komunikat wyświetlił się poprawnie
     * @return true, jeżeli klient otrzymał informacje od serwera, false, jeżeli klient nie otrzymał informacji od serwera
     * @author Karol Przygoda
     */
    static protected boolean getRegisterFeedBackFromServer() {

        try{
            Scanner scanner = new Scanner(FormsContainer.socket.getInputStream());

            if(scanner.hasNextLine())
            {
                boolean serverFeedBack;
                serverFeedBack = scanner.nextBoolean();
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
     * Odbiera informację zwrotną wysłaną przez serwer
     * <p>
     * Metoda odbiera odpowiedzi od serwera o tym, czy logowanie się powiodło,
     * jeżeli serwer napotkał problem i nie wysłał wiadomości użytkownik jest o tym informowany flaga {@linkplain ServerController#errorFlag} jest ustawiana
     * na true, aby komunikat wyświetlił się poprawnie
     * dodatkowo metoda pobiera id użytkownika, który się zalogował w celu wykonania poprawnie późniejszych operacji związanych z tym użytkownikiem
     * @return true, jeżeli klient otrzymał informacje od serwera, false, jeżeli klient nie otrzymał informacji od serwera
     * @author Karol Przygoda
     */
    static protected boolean getLoginFeedBackFromServer() {

        try{
            Scanner scanner = new Scanner(FormsContainer.socket.getInputStream());

            if(scanner.hasNextLine())
            {
                boolean serverFeedBack = scanner.nextBoolean();
                if(serverFeedBack) {
                    id = scanner.nextInt();
                }

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
     * Wyświetla odpowiedni komunikat na podstawie informacji zwrotnej od serwera
     * <p>
     * Metoda wyświetla komunikat o powodzeniu rejestracji, jeżeli metoda {@linkplain ServerController#getRegisterFeedBackFromServer()} zwróci true
     * w przeciwnym wypadku, jeżeli nie zaszedł błąd serwera metoda wyświetli komunikat o zarejestrowanym adresie mailowym w bazie danych
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

    /**
     * Wysyła żądanie pobrania imienia klienta, który się zalogował
     * @param id id użytkownika, który jest zalogowany w bieżącej sesji
     * @author Karol Przygoda
     */
    static protected void sendClientNameRequest(int id) {
        try {
            PrintWriter out = new PrintWriter(new OutputStreamWriter(FormsContainer.socket.getOutputStream()), true);
            out.println("NAME");
            out.println(id);
        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Odbiera odpowiedz od serwera w postaci imienia zalogowanego użytkownika w bieżącej sesji
     * @return Imię użytkownika zalogowanego w bieżącej sesji
     * @author Karol Przygoda
     */
    static protected String getClientName() {
        try{
            Scanner scanner = new Scanner(FormsContainer.socket.getInputStream());

            if(scanner.hasNextLine())
            {
                String name;
                name = scanner.nextLine();
                return name;
            }
            else {
                Alert alert;
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Błąd serwera");
                alert.setHeaderText(null);
                alert.setContentText("Serwer napotkał problem");
                alert.showAndWait();
                errorFlag = true;
                return null;
            }

        }catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Pobiera dane osobowe użytkownika zalogowanego w bieżącej sesji.
     * @return Imię użytkownika zalogowanego w bieżącej sesji
     * @author Karol Przygoda, Radosław Jasinski, Jakub Kotwica
     */
    static protected List<String> getProfileInfo() {
        List<String> profileInfo = new ArrayList<>();
        try{
            Scanner scanner = new Scanner(FormsContainer.socket.getInputStream());
            if(scanner.hasNextLine())
            {
                profileInfo.add(scanner.nextLine());
                profileInfo.add(scanner.nextLine());
                profileInfo.add(scanner.nextLine());
                profileInfo.add(scanner.nextLine());
                profileInfo.add(scanner.nextLine());
                profileInfo.add(scanner.nextLine());
                profileInfo.add(scanner.nextLine());
                return profileInfo;
            }
            else {
                Alert alert;
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Błąd serwera");
                alert.setHeaderText(null);
                alert.setContentText("Serwer napotkał problem");
                alert.showAndWait();
                errorFlag = true;
                return null;
            }
        }catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Wysyła żądanie pobrania informacji z bazy danych
     * @param id id użytkownika, który jest zalogowany w bieżącej sesji
     * @param request Wiadomość opisująca nature informacji np. NAME, PROFILE-INFO, DELETE
     * @author Karol Przygoda, Radosław Jasiński
     */
    static protected void sendClientRequest(int id, String request) {
        try {
            PrintWriter out = new PrintWriter(new OutputStreamWriter(FormsContainer.socket.getOutputStream()), true);
            out.println(request);
            out.println(id);
        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Wysyła żądanie aktualizacji danych osobowych użytkownika zalogowanego w aktualnej sesji
     * @param id id użytkownika, który jest zalogowany w bieżącej sesji
     * @param fieldsToUpdate Mapa, której kluczem jest kolumna w tabeli, którą użytkownik chce zaktualizować, a wartością wartość, na którą użytkownik chce zaktualizować daną
     * @author Karol Przygoda
     */
    static protected void sendUpdateInfoToServer(int id, Map<String, String> fieldsToUpdate) {
        try{
            PrintWriter out = new PrintWriter(new OutputStreamWriter(FormsContainer.socket.getOutputStream()), true);
            out.println("UPDATE");
            out.println(id);
            out.println(fieldsToUpdate.size());
            for(Map.Entry<String, String> entry : fieldsToUpdate.entrySet())
            {
                out.println(entry.getKey());
                out.println(entry.getValue());
                System.out.println(entry.getKey());
                System.out.println(entry.getValue());
            }
        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
