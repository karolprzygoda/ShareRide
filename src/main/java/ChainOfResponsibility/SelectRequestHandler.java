package ChainOfResponsibility;


import Commands.SelectCommand;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class SelectRequestHandler extends BaseHandler {
    @Override
    public void handleRequest(Request request, ObjectOutputStream output) throws IOException {
        if (request.getType() == Request.RequestType.SELECT) {
            SelectCommand selectCommand = new SelectCommand(request.getDataToManage());
            selectCommand.execute(output);
        } else {
            super.handleRequest(request,output);
        }
    }
}
