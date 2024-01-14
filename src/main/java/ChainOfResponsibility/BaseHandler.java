package ChainOfResponsibility;

import java.io.IOException;
import java.io.ObjectOutputStream;

public abstract class BaseHandler implements RequestHandler {
    private RequestHandler nextHandler;

    @Override
    public void setNextHandler(RequestHandler handler) {
        this.nextHandler = handler;
    }

    @Override
    public void handleRequest(Request request, ObjectOutputStream output) throws IOException {
        if (nextHandler != null) {
            nextHandler.handleRequest(request,output);
        }
    }
}
