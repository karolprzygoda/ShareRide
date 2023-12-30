package Server;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class UpdateCommand implements Command{
    public static void execute(Scanner scanner, PrintWriter out)
    {
        Map<String, Object> fieldsToUpdate = new HashMap<>();

        String field = scanner.nextLine();
        int id = Integer.parseInt(scanner.nextLine());
        int size = Integer.parseInt(scanner.nextLine());
        boolean response;

        for (int i = 0; i < size; i++) {
            String key = scanner.nextLine();
            String value = scanner.nextLine();
            fieldsToUpdate.put(key, value);
        }

        switch (field) {
            case "CAR" -> response = PostgreSQL.update(id, "vehicle", fieldsToUpdate);
            case "USER" -> response = PostgreSQL.update(id, "users", fieldsToUpdate);
            case "LICENSE" -> response = PostgreSQL.update(id, "license", fieldsToUpdate);
            default -> throw new IllegalStateException("Unexpected value: " + field);
        }

        out.println(response);
    }
}
