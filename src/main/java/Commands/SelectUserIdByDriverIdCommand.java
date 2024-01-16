package Commands;

import Data.AnnouncementsData;
import Data.DriverData;
import Data.UserData;
import DataManagers.AnnouncementDataManager;
import DataManagers.DriverDataManager;
import DataManagers.UserDataManager;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class SelectUserIdByDriverIdCommand extends Command {

    public SelectUserIdByDriverIdCommand(Object objectToManage){
        super(objectToManage);
    }

    public void execute(ObjectOutputStream output) throws IOException {

        DriverData driverData = (DriverData) objectToManage;
        DriverData response = DriverDataManager.selectUserIdByDriverId(driverData);

        output.writeObject(response);
        output.flush();
        output.reset();
    }
}
