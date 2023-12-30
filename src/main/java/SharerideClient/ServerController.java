package SharerideClient;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.*;

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
            PrintWriter out = new PrintWriter(new OutputStreamWriter(FormsContainer.socket.getOutputStream()), true);
            Scanner scanner = new Scanner(FormsContainer.socket.getInputStream());
            out.println("LOGIN");
            out.println(mail);
            out.println(password);

            boolean serverFeedBack = scanner.nextBoolean();
            if(serverFeedBack) {
                id = scanner.nextInt();
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
            Alerts.failureAlert("Serwer napotkał problem");
            return -1;
        }
    }
    static protected List<String> sendSelectRequest(String field)
    {

        List<String> info = new ArrayList<>();
        try{

            PrintWriter out = new PrintWriter(new OutputStreamWriter(FormsContainer.socket.getOutputStream()), true);
            out.println("SELECT");
            out.println(field);
            out.println(id);
            Scanner scanner = new Scanner(FormsContainer.socket.getInputStream());

            if(scanner.hasNextLine())
            {
                int size = Integer.parseInt(scanner.nextLine());

                if(size < 1)
                {
                    return null;
                }
                else {

                    for (int i = 0; i < size; i++) {
                        info.add((scanner.nextLine()));
                    }

                    return info;
                }
            }
            else {
                Alerts.failureAlert("Serwer napotkał problem");
                return null;
            }
        }catch (IOException e )
        {
            Alerts.failureAlert("Serwer napotkał problem");
            return null;
        }
    }
    static protected int sendInsertRequest(String field,List<Object> values)
    {

        try{

            PrintWriter out = new PrintWriter(new OutputStreamWriter(FormsContainer.socket.getOutputStream()), true);
            Scanner scanner = new Scanner(FormsContainer.socket.getInputStream());
            out.println("INSERT");
            out.println(field);
            out.println(values.size());
            for (Object elements:values) {
                out.println(elements);
            }
            boolean response = scanner.nextBoolean();

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
    static protected int  sendUpdateRequest(String field,Map<String, Object> fieldsToUpdate)
    {
        try{
            PrintWriter out = new PrintWriter(new OutputStreamWriter(FormsContainer.socket.getOutputStream()), true);
            Scanner scanner = new Scanner(FormsContainer.socket.getInputStream());
            out.println("UPDATE");
            out.println(field);
            out.println(id);
            out.println(fieldsToUpdate.size());
            for(Map.Entry<String, Object> entry : fieldsToUpdate.entrySet())
            {
                out.println(entry.getKey());
                out.println(entry.getValue());
            }
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
}