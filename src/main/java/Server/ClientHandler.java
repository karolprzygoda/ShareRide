package Server;

import ChainOfResponsibility.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler {

    private static RequestHandler configureChainOfResponsibility()
    {
        RequestHandler loginHandler = new LoginRequestHandler();
        RequestHandler selectHandler = new SelectRequestHandler();
        RequestHandler updateHandler = new UpdateRequestHandler();
        RequestHandler deleteHandler = new DeleteRequestHandler();
        RequestHandler insertHandler = new InsertRequestHandler();

        loginHandler.setNextHandler(selectHandler);
        selectHandler.setNextHandler(updateHandler);
        updateHandler.setNextHandler(deleteHandler);
        deleteHandler.setNextHandler(insertHandler);

        return loginHandler;
    }

    public void handleClient(Socket clientSocket,ObjectInputStream input,ObjectOutputStream output) {
        try {

            RequestHandler head = configureChainOfResponsibility();
            Request request;

            while (!clientSocket.isClosed()) {
                request =  (Request) input.readObject();
                head.handleRequest(request,output);
            }

        } catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
        } finally {
            try {
                input.close();
                output.close();
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
