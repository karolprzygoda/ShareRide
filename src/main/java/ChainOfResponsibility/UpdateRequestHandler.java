package ChainOfResponsibility;

import Commands.SelectCommand;
import Commands.UpdateCommand;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class UpdateRequestHandler extends BaseHandler {
    @Override
    public void handleRequest(Request request, ObjectOutputStream output) throws IOException {
        if (request.getType() == Request.RequestType.UPDATE) {
            UpdateCommand updateCommand = new UpdateCommand(request.getDataToManage());
            updateCommand.execute(output);
        } else {
            super.handleRequest(request,output);
        }
    }
}
