package Server;

import java.io.PrintWriter;
import java.util.Scanner;

public interface Command {
    static void execute(Scanner scanner, PrintWriter out) {}
}
