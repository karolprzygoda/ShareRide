package Commands;

import Data.UserData;
import DataManagers.UserDataManager;
import Server.ClientHandler;

import java.io.IOException;
import java.io.ObjectOutputStream;


public class LoginCommand extends Command{

    public LoginCommand(Object objectToManage){
        super(objectToManage);
    }

    public void execute(ObjectOutputStream output) throws IOException {
        UserData userData = (UserData)objectToManage;
        boolean response = UserDataManager.loginUser(userData);

        output.writeBoolean(response);
        output.flush();
    }
}
