package Server;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SelectCommand implements Command{
    public static void execute(Scanner scanner, PrintWriter out)
    {
        String field = scanner.nextLine();
        int id = Integer.parseInt(scanner.nextLine());
        List<String> list = switch (field) {
            case "USER" -> PostgreSQL.select(id, "users", PostgreSQLInitialization.userColumns);
            case "LICENSE" -> PostgreSQL.select(id, "license", PostgreSQLInitialization.licenseColumns);
            case "CAR" -> PostgreSQL.select(id, "vehicle", PostgreSQLInitialization.vehicleColumns);
            case "DRIVER" -> PostgreSQL.select(id, "driver", PostgreSQLInitialization.driverColumns);
            default -> new ArrayList<>();
        };

        out.println(list.size());
        for (String result : list) {
            out.println(result);
        }
    }
}
