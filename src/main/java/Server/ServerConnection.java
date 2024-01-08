package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

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
                ObjectInputStream input = new ObjectInputStream(clientSocket.getInputStream());
                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                log.writeLog("New connection: " + clientSocket.getInetAddress().getHostAddress());

                new Thread(()-> handleClient(clientSocket,input,out)).start();//obsługa nowego klienta w osobnym wątku

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
    private static void handleClient(Socket clientSocket,ObjectInputStream input, ObjectOutputStream out) {
        try {

                while (!clientSocket.isClosed()) {
                    String type = (String) input.readObject();

                    switch (type) {
                        case "LOGIN" -> LoginCommand.execute(input,out);
                        case "SELECT" -> SelectCommand.execute(input, out);
                        case "UPDATE" -> UpdateCommand.execute(input, out);
                        case "DELETE" -> DeleteCommand.execute(input, out);
                        case "INSERT" -> InsertCommand.execute(input, out);
                        case "DISCONNECT" -> DisconnectCommand.execute(clientSocket,input,out);
                    }
                }

        } catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        finally {
            try {
                if (input != null) {
                    input.close();
                }
                if (out != null) {
                    out.close();
                }
                if (clientSocket != null && !clientSocket.isClosed()) {
                    clientSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}