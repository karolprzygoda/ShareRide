package Server;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logs {

    private static final String FILE_NAME = "logs.txt";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final String name;
    Logs(String name)
    {
        this.name = name;
        if (!Files.exists(Paths.get(FILE_NAME))) {
            try {
                Files.createFile(Paths.get(FILE_NAME));
                System.out.println("Log file created: " + FILE_NAME);
            } catch (IOException e) {
                System.err.println("Error creating the log file: " + e.getMessage());
            }

        }
    }
    public void writeLog(String message) {
      try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            String timestamp = LocalDateTime.now().format(DATE_TIME_FORMATTER);

            String logEntry = "[" + name + "]" + "[" + timestamp + "] " + message;

            writer.write(logEntry);
            writer.newLine();

        } catch (IOException e) {
            System.err.println("Error writing to the log file: " + e.getMessage());
        }
    }
    public static void writeLog(String name, String message)
    {
        if (!Files.exists(Paths.get(FILE_NAME))) {
            try {
                Files.createFile(Paths.get(FILE_NAME));
                System.out.println("Log file created: " + FILE_NAME);
            } catch (IOException e) {
                System.err.println("Error creating the log file: " + e.getMessage());
            }

        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            String timestamp = LocalDateTime.now().format(DATE_TIME_FORMATTER);

            String logEntry = "[" + name + "]" + "[" + timestamp + "] " + message;

            writer.write(logEntry);
            writer.newLine();

        } catch (IOException e) {
            System.err.println("Error writing to the log file: " + e.getMessage());
        }
    }

}
