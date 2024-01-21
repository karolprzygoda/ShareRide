package Commands;

import Data.AnnouncementsData;
import Data.DriverData;
import Data.LicenseData;
import Data.UserData;
import DataManagers.AnnouncementDataManager;
import DataManagers.DriverDataManager;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class SelectUserIncomingRidesCommand extends Command{
    public SelectUserIncomingRidesCommand(Object objectToManage){
        super(objectToManage);
    }

    public void execute(ObjectOutputStream output) throws IOException {

        UserData userData = (UserData) objectToManage;
        ArrayList<AnnouncementsData> response = AnnouncementDataManager.selectUserIncomingRides(userData);

        output.writeObject(response);
        output.flush();
        output.reset();
    }
}
