package Server;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class ServerConnection {
    private final Logs log = new Logs("Server");
    public ServerConnection(int port) {
        PostgreSQL postgreSQL = new PostgreSQL();
        postgreSQL.startConnection();
        postgreSQL.createTable();
        //postgreSQL.removeEveryUsers();
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server on port: " + port);
            log.writeLog("Server started on port: " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New connection: " + clientSocket.getInetAddress().getHostAddress());
                log.writeLog("New connection: " + clientSocket.getInetAddress().getHostAddress());

                new Thread(()-> handleClient(clientSocket, postgreSQL, log)).start();//obsługa nowego klienta w osobnym wątku

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
        LocalDate joinDate = LocalDate.parse(scanner.nextLine());
        java.sql.Date sqlJoinDate = Date.valueOf(joinDate);
        java.sql.Date sqlDate = java.sql.Date.valueOf(birthDate);

        return Verification.CreateUser(name,lastName,mail,phoneNumber,null, sqlDate,password, sqlJoinDate);
    }

    /**
     * Metoda obsługi klienta.
     *<p>
     * Metoda ta tworzy nowy skaner odpowiedzialny za odczyt wiadomości wysłanych przez klienta oraz nowy PrintWriter odpowiedzialny za wysyłanie odpowiedzi do Klienta.
     * Jeżeli skaner posiada wiadomości do odczytu sprwadza, jaką komendę wysłał klient, a następnie według odczytanej komendy komunikuję się z bazą danych w odpowiednim celu,
     * jeżeli operację się powiodą wysyła do klienta odpowiedzi
     * @param clientSocket gniazdo, z którego połączył się klient
     * @param postgreSQL obiekt klasy {@linkplain PostgreSQL} używany do wywołania odpowiedniej metody rejestracji lub logowania
     * @author Karol Przygoda
     */
    private static void handleClient(Socket clientSocket, PostgreSQL postgreSQL, Logs log) {
        try {
                Scanner scanner = new Scanner(clientSocket.getInputStream());
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),true);
                String hostAddress = clientSocket.getInetAddress().getHostAddress();

                while (scanner.hasNextLine())
                {
                    String type = scanner.nextLine();

                    if (type.equals("LOGIN"))
                    {
                        log.writeLog(hostAddress + " tries to login");

                        String mail = scanner.nextLine();
                        String password = scanner.nextLine();

                        if (postgreSQL.loginUser(mail, password))
                        {
                            out.println(true);
							out.println(postgreSQL.id);
                            log.writeLog(hostAddress + " logged in");

                        } else
                        {
                            out.println(false);
                            log.writeLog(hostAddress + " denied to login, not in database");
                        }
                    } else if (type.equals("REGISTER"))
                    {
                        User newUser = TranslateConnection(scanner);
                        log.writeLog(hostAddress + " tries to register");

                        if (newUser != null)
                        {
                            if (!postgreSQL.registerCheckMail(newUser.getEmail()))
                            {
                                postgreSQL.addUser(newUser.getFirstName(), newUser.getLastName(), newUser.getEmail(), newUser.getPhoneNumber(), newUser.getDateOfBirth(), newUser.getPassword(), newUser.getDateOfJoin());
                                out.println(true);
                                log.writeLog(hostAddress + " registered");
                            } else
                            {
                                out.println(false);
                                log.writeLog(hostAddress + " denied to register, used email");
                            }
                        }
                        else {
                            log.writeLog(hostAddress + " denied to register, server verification block");
                        }
                     }else if (type.equals("NAME"))//jeżeli klient wysłał żądnie wysłania imienia
                    {
                        int id = scanner.nextInt();
                        out.println(postgreSQL.selectName(id));
                    }
                    else if (type.equals("PROFILE-INFO"))
                    {
                        int id = scanner.nextInt();
                        List<String> profileInfo;
                        profileInfo = postgreSQL.selectProfileInfo(id);
                        out.println(profileInfo.get(0));
                        out.println(profileInfo.get(1));
                        out.println(profileInfo.get(2));
                        out.println(profileInfo.get(3));
                        out.println(profileInfo.get(4));
                        out.println(profileInfo.get(5));
                        out.println(profileInfo.get(6));
                    }
                    else if (type.equals("DELETE"))
                    {
                        int id = scanner.nextInt();
                        postgreSQL.removeUser(id);
                    }
                    else if (type.equals("UPDATE"))
                    {

                        Map<String, String> fieldsToUpdate = new HashMap<>();

                        int id = Integer.parseInt(scanner.nextLine());
                        int size = Integer.parseInt(scanner.nextLine());

                        //System.out.println(size);

                        for(int i = 0; i < size; i++)
                        {
                            String key = scanner.nextLine();
                            String value = scanner.nextLine();
                            fieldsToUpdate.put(key,value);
                            System.out.println(fieldsToUpdate);
                        }

                        //System.out.println("User w serverconnection " + newUser);
                        postgreSQL.updateUser(id, fieldsToUpdate);
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