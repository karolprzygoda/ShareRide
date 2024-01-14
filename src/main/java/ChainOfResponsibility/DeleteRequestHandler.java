package ChainOfResponsibility;

import Commands.DeleteCommand;
import Commands.InsertCommand;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class DeleteRequestHandler extends BaseHandler{
    @Override
    public void handleRequest (Request request, ObjectOutputStream output) throws IOException {
        if (request.getType() == Request.RequestType.DELETE) {
            DeleteCommand deleteCommand = new DeleteCommand(request.getDataToManage());
            deleteCommand.execute(output);
        } else {
            super.handleRequest(request, output);
        }
    }
}
