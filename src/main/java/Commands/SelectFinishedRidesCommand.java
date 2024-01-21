package Commands;

import Data.AnnouncementsData;
import Data.UserData;
import DataManagers.AnnouncementDataManager;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class SelectFinishedRidesCommand extends Command{
    public SelectFinishedRidesCommand(Object objectToManage){
        super(objectToManage);
    }

    public void execute(ObjectOutputStream output) throws IOException {

        UserData userData = (UserData) objectToManage;
        ArrayList<AnnouncementsData> response = AnnouncementDataManager.selectUserFinishedRides(userData);

        output.writeObject(response);
        output.flush();
        output.reset();
    }
}
