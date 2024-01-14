package ChainOfResponsibility;

import java.io.IOException;
import java.io.ObjectOutputStream;

public interface RequestHandler {
    void setNextHandler(RequestHandler handler);
    void handleRequest(Request request, ObjectOutputStream output) throws IOException;
}
