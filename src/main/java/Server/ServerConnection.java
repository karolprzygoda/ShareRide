package Server;

import ChainOfResponsibility.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerConnection {
    public ServerConnection(int port) {

        PostgreSQLInitialization databaseInit = PostgreSQLInitialization.getInstance();
        databaseInit.createTables();
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server on port: " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler();
                ObjectInputStream input = new ObjectInputStream(clientSocket.getInputStream());
                ObjectOutputStream output = new ObjectOutputStream(clientSocket.getOutputStream());

                new Thread(()-> clientHandler.handleClient(clientSocket,input,output)).start();//obsługa nowego klienta w osobnym wątku

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}