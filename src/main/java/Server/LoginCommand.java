package Server;

import java.io.PrintWriter;
import java.util.Scanner;

public class LoginCommand implements Command{
    public static void execute(Scanner scanner, PrintWriter out)
    {
        String mail = scanner.nextLine();
        String password = scanner.nextLine();
        int id = PostgreSQL.login(mail,password);
        if (id > 0) {
            out.println(true);
            out.println(id);
        } else {
            out.println(false);
        }
    }
}
