package Commands;

import Data.*;
import DataManagers.*;
import Server.ClientHandler;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class SelectCommand extends Command {

    public SelectCommand(Object objectToManage){
        super(objectToManage);
    }

    public void execute(ObjectOutputStream output) throws IOException {


        objectToManage = switch (objectToManage) {
            case UserData ignored           -> UserDataManager.selectUserData();
            case LicenseData ignored        -> LicenseDataManger.selectLicenceData();
            case VehicleData ignored        -> VehicleDataManager.selectVehicleData();
            case DriverData ignored         -> DriverDataManager.selectDriverData();
            case AnnouncementsData ignored  -> AnnouncementDataManager.selectAnnouncement();
            //case PassengersData ignored   -> PassengersDataManager.selectPassenger();TODO
            default                         -> throw new IllegalArgumentException("Unrecognized object");
        };

        output.writeObject(objectToManage);
        output.flush();
        output.reset();

    }
}
