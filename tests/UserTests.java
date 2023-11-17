import Server.PostgreSQL;
import Server.Register;
import Server.User;

import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

public class UserTests {
    private static final String[] FIRST_NAMES = {"abcdefghijklmnopqrstuvwxyz!", "ABCDEFGHIJKLMNOPQRSTUVWXYZ?"};
    private static final String[] LAST_NAMES = {"abcdefghijklmnopqrstuvwxyz!", "ABCDEFGHIJKLMNOPQRSTUVWXYZ?!"};
    private static final String[] EMAIL_DOMAINS = {"gmail.com", "yahoo.com", "outlook.com", "hotmail.com"};
    private static final String[] PASSWORD_CHARACTERS = {"abcdefghijklmnopqrstuvwxyz", "ABCDEFGHIJKLMNOPQRSTUVWXYZ", "0123456789", "!@#$%^&*()-_=+[]<>"};
    private static final Random RANDOM = new Random();

     static String generateRandomFirstName() {
        StringBuilder firstName = new StringBuilder();
        String allCharacters = String.join("", FIRST_NAMES);
        int maxLength = 40;
        int randomLength = RANDOM.nextInt(maxLength);
        for (int i = 0; i < randomLength; i++) {
            int randomIndex = RANDOM.nextInt(allCharacters.length());
            firstName.append(allCharacters.charAt(randomIndex));
        }
        return firstName.toString();
    }
    static String generateRandomLastName() {
        StringBuilder lastName = new StringBuilder();
        String allCharacters = String.join("", LAST_NAMES);
        int maxLength = 40;
        int randomLength = RANDOM.nextInt(maxLength);
        for (int i = 0; i < randomLength; i++) {
            int randomIndex = RANDOM.nextInt(allCharacters.length());
            lastName.append(allCharacters.charAt(randomIndex));
        }
        return lastName.toString();
    }

    static String getRandomElement(String[] array) {
        int randomIndex = RANDOM.nextInt(array.length);
        return array[randomIndex];
    }
    static String generateRandomEmail(String firstName, String lastName) {
        String domain = getRandomElement(EMAIL_DOMAINS);
        return firstName.toLowerCase() + "." + lastName.toLowerCase() + "@" + domain;
    }
    static int generateRandomAge() {
        return RANDOM.nextInt(120);
    }
    static String generateRandomPhoneNumber() {
        StringBuilder phoneNumber = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            phoneNumber.append(RANDOM.nextInt(9));
        }
        return phoneNumber.toString();
    }
    static Date generateRandomDateOfBirth() {
        Random random = new Random();
        int year = random.nextInt(50) + 1950; // Losowy rok od 1950 do 1999
        int month = random.nextInt(12) + 1; // Losowy miesiąc od 1 do 12
        int day = random.nextInt(28) + 1; // Losowy dzień od 1 do 28

        Calendar calendar = new GregorianCalendar(year, month - 1, day); // Miesiące w Calendar są indeksowane od 0
        return new Date(calendar.getTimeInMillis());
    }
    static String generateRandomPassword() {
        StringBuilder password = new StringBuilder();
        String allCharacters = String.join("", PASSWORD_CHARACTERS);
        int maxLength = 50;
        int randomLength = RANDOM.nextInt(maxLength) + 6;
        for (int i = 0; i < randomLength; i++) {
            int randomIndex = RANDOM.nextInt(allCharacters.length());
            password.append(allCharacters.charAt(randomIndex));
        }
        return password.toString();
    }
    public static void main (String[] args) {
        int addedUsers = 0;
        PostgreSQL postgreSQL = new PostgreSQL();
        postgreSQL.startConnection();
        postgreSQL.createTable();
        for(int i =0; i<1000; i++) {
            //postgreSQL.removeEveryUsers();
            User newUser = Register.CreateUser(generateRandomFirstName(), generateRandomLastName(), generateRandomEmail(generateRandomFirstName(), generateRandomLastName()),generateRandomAge(), generateRandomPhoneNumber(), "Mezczyzna", generateRandomDateOfBirth(), generateRandomPassword());
            if (newUser != null)
            {
                postgreSQL.addUser(newUser.getFirstName(), newUser.getLastName(), newUser.getEmail(), newUser.getAge(), newUser.getPhoneNumber(), newUser.getGender(), newUser.getDateOfBirth(), newUser.getPassword());
                addedUsers++;
            }
        }
        System.out.println("Added users: " + addedUsers);
    }
}
