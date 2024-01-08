package SharerideClient;

import java.io.*;
import java.util.*;

import static SharerideClient.FormsContainer.*;

/**
 * Kontroler zarządzający połączeniem z serwerem
 * @author Karol Przygoda
 */
public class ServerController {

    /**
     * Zmienna przechowująca id użytkownika zalogowanego w aktualnej sesji
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

    static protected int sendLoginRequest(String mail, String password)
    {
        try{
            out.writeObject("LOGIN");
            out.writeObject(mail);
            out.writeObject(password);
            out.flush();

            boolean serverFeedBack = input.readBoolean();
            if(serverFeedBack) {
                id = input.readInt();
            }

            if(serverFeedBack)
            {
                return 1;
            }
            else
            {
                return 0;
            }


        }catch (IOException  | RuntimeException e)
        {
            e.printStackTrace();
            Alerts.failureAlert("Serwer napotkał problem");
            return -1;
        }
    }

    static protected Object sendSelectRequest(String field)
    {

        try{

            out.writeObject("SELECT");
            out.writeObject(field);
            out.writeInt(id);
            out.flush();

            return input.readObject();


        }catch (IOException | ClassNotFoundException e )
        {
            e.printStackTrace();
            Alerts.failureAlert("Serwer napotkał problem");
            return null;
        }
    }
    static protected int sendInsertRequest(String field,Object object)
    {

        try{

            out.writeObject("INSERT");
            out.writeObject(field);
            out.writeObject(object);
            out.flush();

            boolean response = input.readBoolean();

            if(response)
            {
                return 1;
            }
            else
            {
                return 0;
            }

        }catch (IOException  | RuntimeException e)
        {
            Alerts.failureAlert("Serwer napotkał problem");
            return -1;
        }
    }
    static protected int  sendUpdateRequest(String field,Object object)
    {
        try{

            out.writeObject("UPDATE");
            out.writeObject(field);
            out.writeInt(id);
            out.writeObject(object);

            boolean response = input.readBoolean();
            if(response)
            {
                return 1;
            }
            else
            {
                return 0;
            }

        }catch (IOException | RuntimeException e)
        {
            Alerts.failureAlert("Serwer napotkał problem");
            return -1;
        }
    }
    static protected int  sendDeleteRequest(String field)
    {
        try {

            PrintWriter out = new PrintWriter(new OutputStreamWriter(FormsContainer.socket.getOutputStream()), true);
            Scanner scanner = new Scanner(FormsContainer.socket.getInputStream());
            out.println("DELETE");
            out.println(field);
            out.println(id);
            boolean response = scanner.nextBoolean();
            if(response)
            {
                return 1;
            }
            else
            {
                return 0;
            }
        }catch (IOException | RuntimeException e)
        {
            Alerts.failureAlert("Serwer napotkał problem");
            return -1;
        }
    }

    static protected void sendDisconnectRequest() {
        try {
            if (out != null) {
                out.writeObject("DISCONNECT");
                out.flush();
                out.close();
            }
            if (input != null) {
                input.close();
            }
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}