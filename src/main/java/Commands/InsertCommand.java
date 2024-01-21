package Commands;

import Data.*;

import DataManagers.*;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

public class InsertCommand extends Command{

    public InsertCommand(Object objectToManage){
        super(objectToManage);
    }

    @Override
    public void execute(ObjectOutputStream output) throws IOException {

        boolean response;

         switch (objectToManage) {
            case UserData ignored               -> response = UserDataManager.registerUser((UserData) objectToManage);
            case VehicleData ignored            -> response = VehicleDataManager.insertVehicle((VehicleData) objectToManage);
            case LicenseData ignored            -> response = LicenseDataManger.insertLicense((LicenseData) objectToManage);
            case DriverData ignored             -> response = DriverDataManager.insertDriver((DriverData) objectToManage);
            case AnnouncementsData ignored      -> response = AnnouncementDataManager.insertAnnouncement((AnnouncementsData) objectToManage);
            case PassengersData ignored         -> response = PassengersDataManager.insertPassenger((PassengersData) objectToManage);
            case List ignored                   -> response = RatingDataManager.insertRatings((List<RatingData>) objectToManage);
            default                             -> throw new IllegalStateException("Unexpected value: ");
        }
        output.writeBoolean(response);
        output.flush();
        output.reset();
    }

}

