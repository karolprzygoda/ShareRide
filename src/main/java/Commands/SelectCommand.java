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
            case UserData ignored           -> UserDataManager.selectUserData((UserData)objectToManage);
            case LicenseData ignored        -> LicenseDataManger.selectLicenceData((LicenseData) objectToManage);
            case VehicleData ignored        -> VehicleDataManager.selectVehicleData((VehicleData) objectToManage);
            case DriverData ignored         -> DriverDataManager.selectDriverData((DriverData)objectToManage);
            case AnnouncementsData ignored  -> AnnouncementDataManager.selectAnnouncement((AnnouncementsData)objectToManage);
            case PassengersData ignored     -> PassengersDataManager.selectPassengers((PassengersData) objectToManage);
            default                         -> throw new IllegalArgumentException("Unrecognized object");
        };

        output.writeObject(objectToManage);
        output.flush();
        output.reset();

    }
}
