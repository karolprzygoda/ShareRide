package Server;
import java.io.PrintWriter;
import java.util.Scanner;

public class DeleteCommand implements Command{
    public static void execute(Scanner scanner, PrintWriter out)
    {
        String field = scanner.nextLine();
        int id = scanner.nextInt();
        boolean response;
        switch (field) {
            case "USER" -> response = PostgreSQL.delete("users", id);
            case "CAR" -> response = PostgreSQL.delete("vehicle", id);
            case "LICENSE" -> response = PostgreSQL.delete("license", id);
            default -> throw new IllegalStateException("Unexpected value: " + field);
        }

        out.println(response);
    }
}
