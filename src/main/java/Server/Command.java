package Server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public interface Command {
    static void execute(Scanner scanner, PrintWriter out) {}
    static void execute(ObjectInputStream input, ObjectOutputStream out) {}
}
