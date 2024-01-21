package Commands;

import Data.*;
import DataManagers.*;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class DeleteCommand extends Command {

    public DeleteCommand(Object objectToManage){
        super(objectToManage);
    }

    public void  execute(ObjectOutputStream output) throws IOException{

        boolean response;

        switch (objectToManage) {
            case UserData ignored           -> response = UserDataManager.deleteUser((UserData) objectToManage);
            case VehicleData ignored        -> response = VehicleDataManager.deleteVehicle((VehicleData) objectToManage);
            case LicenseData ignored        -> response = LicenseDataManger.deleteLicense((LicenseData) objectToManage);
            case DriverData ignored         -> response = DriverDataManager.deleteDriver((DriverData) objectToManage);
            case PassengersData ignored     -> response = PassengersDataManager.deletePassenger((PassengersData) objectToManage);
            case AnnouncementsData ignored  -> response = AnnouncementDataManager.deleteAnnouncement((AnnouncementsData) objectToManage);
            default                     -> throw new IllegalArgumentException("Unrecognized object");
        }

        output.writeBoolean(response);
        output.flush();
        output.reset();
    }
}
