package Server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class ServerConnection {
    private final Logs log = new Logs("Server");

    public ServerConnection(int port) {
        PostgreSQLInitialization postgreSQLInitialization = PostgreSQLInitialization.getInstance();
        postgreSQLInitialization.startConnection();
        postgreSQLInitialization.createTables();
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server on port: " + port);
            log.writeLog("Server started on port: " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                log.writeLog("New connection: " + clientSocket.getInetAddress().getHostAddress());

                new Thread(()-> handleClient(clientSocket)).start();//obsługa nowego klienta w osobnym wątku

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metoda obsługi klienta.
     *<p>
     * Metoda ta tworzy nowy skaner odpowiedzialny za odczyt wiadomości wysłanych przez klienta oraz nowy PrintWriter odpowiedzialny za wysyłanie odpowiedzi do Klienta.
     * Jeżeli skaner posiada wiadomości do odczytu sprwadza, jaką komendę wysłał klient, a następnie według odczytanej komendy komunikuję się z bazą danych w odpowiednim celu,
     * jeżeli operację się powiodą wysyła do klienta odpowiedzi
     * @param clientSocket gniazdo, z którego połączył się klient
     * @param postgreSQLInitialization obiekt klasy {@linkplain PostgreSQLInitialization} używany do wywołania odpowiedniej metody rejestracji lub logowania
     * @author Karol Przygoda
     */
    private static void handleClient(Socket clientSocket) {
        try {

                Scanner scanner = new Scanner(clientSocket.getInputStream());
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),true);

                while (scanner.hasNextLine()) {
                    String type = scanner.nextLine();

                    switch (type) {
                        case "LOGIN" -> LoginCommand.execute(scanner,out);
                        case "SELECT" -> SelectCommand.execute(scanner, out);
                        case "UPDATE" -> UpdateCommand.execute(scanner, out);
                        case "DELETE" -> DeleteCommand.execute(scanner, out);
                        case "INSERT" -> InsertCommand.execute(scanner, out);
                    }
                }

                scanner.close();
                out.close();
                clientSocket.close();

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}