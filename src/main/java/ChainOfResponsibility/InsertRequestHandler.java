package ChainOfResponsibility;

import Commands.InsertCommand;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class InsertRequestHandler extends BaseHandler {
    @Override
    public void handleRequest (Request request, ObjectOutputStream output) throws IOException {
        if (request.getType() == Request.RequestType.INSERT) {
            InsertCommand insertCommand = new InsertCommand(request.getDataToManage());
            insertCommand.execute(output);
        } else {
            super.handleRequest(request, output);
        }
    }
}