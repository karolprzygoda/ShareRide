package Server;

import ChainOfResponsibility.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerConnection {
    private static final Logger logger = LogManager.getLogger(ServerConnection.class);
    public ServerConnection(int port) {

        PostgreSQLInitialization databaseInit = PostgreSQLInitialization.getInstance();
        databaseInit.createTables();
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("Server starts on port: " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                logger.info("New Client connect from  port: " + clientSocket.getPort());
                ClientHandler clientHandler = new ClientHandler();
                ObjectInputStream input = new ObjectInputStream(clientSocket.getInputStream());
                ObjectOutputStream output = new ObjectOutputStream(clientSocket.getOutputStream());

                new Thread(()-> clientHandler.handleClient(clientSocket,input,output)).start();//obsługa nowego klienta w osobnym wątku

            }
        } catch (IOException e) {
            logger.error("Server caught an error: " + e.getCause());
            e.printStackTrace();
        }
    }
}