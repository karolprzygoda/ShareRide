package Server;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class DeleteCommand implements Command{
    public static void execute(ObjectInputStream scanner, ObjectOutputStream out) throws IOException, ClassNotFoundException {
        String field = (String) scanner.readObject();
        int id = scanner.readInt();
        boolean response;
        switch (field) {
            case "USER" -> response = PostgreSQL.delete("users", id);
            case "CAR" -> response = PostgreSQL.delete("vehicle", id);
            case "LICENSE" -> response = PostgreSQL.delete("license", id);
            default -> throw new IllegalStateException("Unexpected value: " + field);
        }

        out.writeObject(response);
        //out.println(response);
    }
}
