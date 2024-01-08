package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class DisconnectCommand implements Command{
    public static void execute(Socket socket, ObjectInputStream input, ObjectOutputStream out) throws IOException {
        input.close();
        out.close();
        socket.close();
    }
}
