package Server;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.Scanner;

public class ServerConnection {
    // constructor with port
    public ServerConnection(int port) {
        PostgreSQL postgreSQL = new PostgreSQL();
        postgreSQL.startConnection();
        postgreSQL.createTable();
        //postgreSQL.removeEveryUsers();
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server on port: " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New connection: " + clientSocket.getInetAddress().getHostAddress());
                new Thread(()-> handleClient(clientSocket,postgreSQL)).start();//obsługa nowego klienta w osobnym wątku

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static User TranslateConnection(Scanner scanner) {

        String name = scanner.nextLine();
        String lastName = scanner.nextLine();
        String mail = scanner.nextLine();
        String phoneNumber = scanner.nextLine();
        LocalDate birthDate = LocalDate.parse(scanner.nextLine());
        String password = scanner.nextLine();

        java.sql.Date sqlDate = java.sql.Date.valueOf(birthDate);

        return Register.CreateUser(name,lastName,mail,phoneNumber,null, sqlDate,password);
    }

    /**
     * Metoda obsługi klienta.
     *<p>
     * Metoda ta tworzy nowy skaner odpowiedzielny za odczyt wiadomosci wysłanych przez kilenta oraz nowy PrintWriter odpowiedzialny za wysyłanie odpowiedzi do Klienta
     * Jeżeli skaner posiada wiadomośći do odczytu sprwadza jaką komende wysłał klient a następnie według odczytanej komendy komunikuję się z bazą danych w celu rejestracji
     * lub zalogowania użytkownika korzystającego z aplikacji klienckiej jeżeli operację się powiodą wysyła do klienta true w przeciwnym wypadku false
     * @param clientSocket gniazdo z którego połączył się klient
     * @param postgreSQL obiekt klasy {@linkplain PostgreSQL} używany do wywołania odpowiedniej metody rejestracji lub logowania
     * @author Karol Przygoda
     */
    private static void handleClient(Socket clientSocket, PostgreSQL postgreSQL) {
        try {
                Scanner scanner = new Scanner(clientSocket.getInputStream());
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),true);

                while (scanner.hasNextLine())
                {
                    String type = scanner.nextLine();

                    if (type.equals("LOGIN"))
                    {
                        String mail = scanner.nextLine();
                        String password = scanner.nextLine();

                        if (postgreSQL.loginUser(mail, password))
                        {
                            out.println(true);
                        } else
                        {
                            out.println(false);
                        }
                    } else if (type.equals("REGISTER"))
                    {
                        User newUser = TranslateConnection(scanner);

                        if (newUser != null)
                        {
                            if (!postgreSQL.registerCheckMail(newUser.getEmail()))
                            {
                                postgreSQL.addUser(newUser.getFirstName(), newUser.getLastName(), newUser.getEmail(), newUser.getPhoneNumber(),
                                                    newUser.getDateOfBirth(), newUser.getPassword());
                                out.println(true);
                            } else
                            {
                                out.println(false);
                            }
                        }
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