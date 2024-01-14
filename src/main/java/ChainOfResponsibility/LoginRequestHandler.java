package ChainOfResponsibility;

import Commands.LoginCommand;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class LoginRequestHandler extends BaseHandler {
    @Override
    public void handleRequest(Request request, ObjectOutputStream output) throws IOException {
        if (request.getType() == Request.RequestType.LOGIN) {
            LoginCommand loginCommand = new LoginCommand(request.getDataToManage());
            loginCommand.execute(output);
        } else {
            super.handleRequest(request, output);
        }
    }
}