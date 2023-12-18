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
        PostgreSQLInitialization postgreSQLInitialization = PostgreSQLInitialization.getInstance();
        postgreSQLInitialization.startConnection();
        postgreSQLInitialization.createTables();
        PostgreSQLUser postgreSQLUser = new PostgreSQLUser();
        PostgreSQLDriver postgreSQLDriver = new PostgreSQLDriver();
        //postgreSQLInitialization.removeEveryUsers();
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server on port: " + port);
            log.writeLog("Server started on port: " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New connection: " + clientSocket.getInetAddress().getHostAddress());
                log.writeLog("New connection: " + clientSocket.getInetAddress().getHostAddress());

                new Thread(()-> handleClient(clientSocket, postgreSQLInitialization,postgreSQLUser, postgreSQLDriver, log)).start();//obsługa nowego klienta w osobnym wątku

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
        Date sqlJoinDate = Date.valueOf(joinDate);
        Date sqlDate = Date.valueOf(birthDate);

        return Verification.CreateUser(name,lastName,mail,phoneNumber,null, sqlDate,password, sqlJoinDate);
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
    private static void handleClient(Socket clientSocket, PostgreSQLInitialization postgreSQLInitialization, PostgreSQLUser postgreSQLUser, PostgreSQLDriver postgreSQLDriver, Logs log) {
        try {

                Scanner scanner = new Scanner(clientSocket.getInputStream());
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),true);
                String hostAddress = clientSocket.getInetAddress().getHostAddress();

                while (scanner.hasNextLine())
                {
                    String type = scanner.nextLine();

                    switch (type) {
                        case "LOGIN" -> {
                            log.writeLog(hostAddress + " tries to login");
                            String mail = scanner.nextLine();
                            String password = scanner.nextLine();
                            if (postgreSQLUser.loginUser(mail, password)) {
                                out.println(true);
                                out.println(postgreSQLUser.id);
                                log.writeLog(hostAddress + " logged in");

                            } else {
                                out.println(false);
                                log.writeLog(hostAddress + " denied to login, not in database");
                            }
                        }
                        case "REGISTER" -> {
                            User newUser = TranslateConnection(scanner);
                            log.writeLog(hostAddress + " tries to register");
                            if (newUser != null) {
                                if (!postgreSQLUser.registerCheckMail(newUser.getEmail())) {
                                    postgreSQLUser.addUser(newUser.getFirstName(), newUser.getLastName(), newUser.getEmail(), newUser.getPhoneNumber(), newUser.getDateOfBirth(), newUser.getPassword(), newUser.getDateOfJoin());
                                    out.println(true);
                                    log.writeLog(hostAddress + " registered");
                                } else {
                                    out.println(false);
                                    log.writeLog(hostAddress + " denied to register, used email");
                                }
                            } else {
                                log.writeLog(hostAddress + " denied to register, server verification block");
                            }
                        }
                        case "NAME" ->
//jeżeli klient wysłał żądnie wysłania imienia
                        {
                            int id = scanner.nextInt();
                            out.println(postgreSQLUser.selectName(id));
                        }
                        case "PROFILE-INFO" -> {
                            int id = scanner.nextInt();
                            List<String> profileInfo;
                            profileInfo = postgreSQLUser.selectProfileInfo(id);
                            int isDriver = postgreSQLDriver.getDriverId(id);
                            out.println(profileInfo.get(0));
                            out.println(profileInfo.get(1));
                            out.println(profileInfo.get(2));
                            out.println(profileInfo.get(3));
                            out.println(profileInfo.get(4));
                            out.println(profileInfo.get(5));
                            out.println(profileInfo.get(6));
                            if(isDriver > 0)
                            {
                                out.println("Aktywny");
                            }
                            else
                            {
                                out.println("Nie aktywny");
                            }
                        }
                        case "DELETE" -> {
                            int id = scanner.nextInt();
                            postgreSQLUser.removeUser(id);
                        }
                        case "UPDATE" -> {

                            Map<String, String> fieldsToUpdate = new HashMap<>();

                            int id = Integer.parseInt(scanner.nextLine());
                            int size = Integer.parseInt(scanner.nextLine());

                            //System.out.println(size);

                            for (int i = 0; i < size; i++) {
                                String key = scanner.nextLine();
                                String value = scanner.nextLine();
                                fieldsToUpdate.put(key, value);
                                System.out.println(fieldsToUpdate);
                            }

                            //System.out.println("User w serverconnection " + newUser);
                            postgreSQLUser.updateUser(id, fieldsToUpdate);
                        }

                        case "CHECKIFDRIVER" -> {

                            int id = scanner.nextInt();
                            if(postgreSQLDriver.getDriverId(id) > 0)
                            {
                                out.print(true);
                            }
                            else {
                                out.println(false);
                            }

                        }

                        case "CHECKIFHAVELICESNE" -> {

                            int id = scanner.nextInt();
                            if(postgreSQLDriver.getLicenseId(id) > 0)
                            {
                                out.println(true);
                            }
                            else {
                                out.println(false);
                            }
                        }

                        case "ADDLICENSE" -> {
                            String licenseID = scanner.nextLine();
                            System.out.println(licenseID);
                            Date dateOfIssueOfTheLicense = Date.valueOf(scanner.nextLine());
                            Date expirationDateOfTheLicense = Date.valueOf(scanner.nextLine());
                            String licenseCategory = scanner.nextLine();

                            int id = scanner.nextInt();
                            System.out.println(id);

                            postgreSQLDriver.addLicense(licenseID, dateOfIssueOfTheLicense, expirationDateOfTheLicense, licenseCategory, id);
                        }

                        case "LICENSE_UPDATE" -> {

                            Map<String, String> fieldsToUpdate = new HashMap<>();

                            int id = Integer.parseInt(scanner.nextLine());
                            int size = Integer.parseInt(scanner.nextLine());

                            System.out.println(id);
                            System.out.println(size);

                            //System.out.println(size);

                            for (int i = 0; i < size; i++) {
                                String key = scanner.nextLine();
                                String value = scanner.nextLine();
                                fieldsToUpdate.put(key, value);
                                System.out.println(fieldsToUpdate);
                            }

                            postgreSQLDriver.updateLicense(id, fieldsToUpdate);
                        }

                        case "LICENSEREQUEST" ->
                        {
                            int id = scanner.nextInt();
                            List<String> licenseInfo;
                            try {
                                licenseInfo = postgreSQLDriver.selectLicenseInfo(id);
                                out.println(licenseInfo.get(0));
                                out.println(licenseInfo.get(1));
                                out.println(licenseInfo.get(2));
                                out.println(licenseInfo.get(3));
                            }catch (NullPointerException e)
                            {
                                out.println("Brak");
                                out.println("Brak");
                                out.println("Brak");
                                out.println("Brak");
                            }
                        }

                        case "CARREQUEST" ->
                        {
                            int id = scanner.nextInt();
                            List<String> carInfo;
                            try {
                                carInfo = postgreSQLDriver.selectCarInfo(id);
                                out.println(carInfo.get(0));
                                out.println(carInfo.get(1));
                                out.println(carInfo.get(2));
                                out.println(carInfo.get(3));
                                out.println(carInfo.get(4));
                                out.println(carInfo.get(5));
                                out.println(carInfo.get(6));
                            }catch (NullPointerException e)
                            {
                                out.println("Brak");
                                out.println("Brak");
                                out.println("Brak");
                                out.println("Brak");
                                out.println("Brak");
                                out.println("Brak");
                                out.println("Brak");
                            }
                        }

                        case "DELETELICENSE" ->
                        {
                            int id = scanner.nextInt();
                            postgreSQLDriver.removeLicense(id);
                        }

                        case "DELETECAR" ->
                        {
                            int id = scanner.nextInt();
                            postgreSQLDriver.removeCar(id);
                        }

                        case "CHECKIFHAVECAR" ->
                        {
                            int id = scanner.nextInt();
                            if(postgreSQLDriver.getCarId(id) > 0)
                            {
                                out.println(true);
                            }
                            else {
                                out.println(false);
                            }
                        }

                        case "CAR_UPDATE" ->
                        {
                            Map<String, Object> fieldsToUpdate = new HashMap<>();

                            int id = Integer.parseInt(scanner.nextLine());
                            int size = Integer.parseInt(scanner.nextLine());

                            System.out.println(id);
                            System.out.println(size);

                            //System.out.println(size);

                            for (int i = 0; i < size; i++) {
                                String key = scanner.nextLine();
                                Object value = scanner.nextLine();
                                fieldsToUpdate.put(key, value);
                                System.out.println(fieldsToUpdate);
                            }

                            postgreSQLDriver.updateCar(id, fieldsToUpdate);
                        }

                        case "ADDCAR" ->
                        {
                            String carBrand = scanner.nextLine();
                            String carModel = scanner.nextLine();
                            String carPlates = scanner.nextLine();
                            String carVIN = scanner.nextLine();
                            String carInsuranceNumber = scanner.nextLine();
                            Date insurancePolicyExpirationDate = Date.valueOf(scanner.nextLine());
                            Integer carSeatAvailable = scanner.nextInt();
                            //System.out.println(scanner.nextLine());

                            int id = scanner.nextInt();
                            System.out.println(id);

                            postgreSQLDriver.addCar(carBrand, carModel, carPlates, carVIN,carSeatAvailable,carInsuranceNumber,insurancePolicyExpirationDate, id);
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