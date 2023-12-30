package Server;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InsertCommand implements Command{
    public static  void execute(Scanner scanner, PrintWriter out)
    {
        String field = scanner.nextLine();
        int size = Integer.parseInt(scanner.nextLine());
        boolean response;
        List<Object> data = new ArrayList<>();
        PasswordEncoder passwordEncoder = PasswordEncoderFactory.createPasswordEncoder();

        for (int i = 0; i < size; i++) {
            String value = scanner.nextLine();
            data.add(value);
        }

        switch (field) {
            case "USER" -> {
                if (Verification.verify(data)) {
                    String encodePassword = passwordEncoder.encodePassword(data.get(6).toString());
                    data.set(6, encodePassword);
                    response = PostgreSQL.insert("users", PostgreSQLInitialization.userColumnsToInsert, data);
                } else {
                    response = false;
                }
            }
            case "CAR" -> response = PostgreSQL.insert("vehicle", PostgreSQLInitialization.vehicleColumns, data);
            case "LICENSE" -> response = PostgreSQL.insert("license", PostgreSQLInitialization.licenseColumns, data);
            default -> throw new IllegalStateException("Unexpected value: " + field);
        }
        
        out.println(response);
    }
}

