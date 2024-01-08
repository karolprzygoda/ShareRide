package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class LoginCommand implements Command{
    public static void execute(ObjectInputStream scanner, ObjectOutputStream out) throws IOException, ClassNotFoundException {
        String mail = (String) scanner.readObject();
        String password = (String) scanner.readObject();
        int id = PostgreSQL.login(mail,password);
        if (id >= 0) {
            out.writeBoolean(true);
            out.writeInt(id);
            out.flush();
            //out.println(true);
            //out.println(id);
        } else {
            out.writeBoolean(false);
            out.flush();
            //out.println(false);
        }
    }
}
