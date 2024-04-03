package ChainOfResponsibility;


import Commands.*;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class SelectRequestHandler extends BaseHandler {
    @Override
    public void handleRequest(Request request, ObjectOutputStream output) throws IOException {
        if (request.getType() == Request.RequestType.SELECT) {
            SelectCommand selectCommand = new SelectCommand(request.getDataToManage());
            selectCommand.execute(output);
        }else if(request.getType() == Request.RequestType.SELECT_ALL){
            SelectAllCommand selectAllCommand = new SelectAllCommand(request.getDataToManage());
            selectAllCommand.execute(output);
        }else if(request.getType() == Request.RequestType.SELECT_ID) {
            SelectUserIdByDriverIdCommand selectUserIdByDriverIdCommand = new SelectUserIdByDriverIdCommand(request.getDataToManage());
            selectUserIdByDriverIdCommand.execute(output);
        }else if(request.getType() == Request.RequestType.CHECK_IF_ALREADY_IN_RIDE){
            CheckIfAlreadyInRideCommand checkIfAlreadyInRideCommand = new CheckIfAlreadyInRideCommand(request.getDataToManage());
            checkIfAlreadyInRideCommand.execute(output);
        }
        else if(request.getType() == Request.RequestType.USER_INCOMING_RIDES){
            SelectUserIncomingRidesCommand selectUserIncomingRidesCommand = new SelectUserIncomingRidesCommand(request.getDataToManage());
            selectUserIncomingRidesCommand.execute(output);
        } else if (request.getType() == Request.RequestType.USER_FINISHED_RIDES) {
            SelectFinishedRidesCommand selectFinishedRidesCommand = new SelectFinishedRidesCommand(request.getDataToManage());
            selectFinishedRidesCommand.execute(output);
        } else {
            super.handleRequest(request,output);
        }
    }
}