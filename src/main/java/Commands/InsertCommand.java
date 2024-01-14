package Commands;

import Data.DriverData;
import Data.LicenseData;
import Data.UserData;

import Data.VehicleData;
import DataManagers.DriverDataManager;
import DataManagers.LicenseDataManger;
import DataManagers.UserDataManager;
import DataManagers.VehicleDataManager;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class InsertCommand extends Command{

    public InsertCommand(Object objectToManage){
        super(objectToManage);
    }

    @Override
    public void execute(ObjectOutputStream output) throws IOException {

        boolean response;

         switch (objectToManage) {
            case UserData ignored       -> response = UserDataManager.registerUser((UserData) objectToManage);
            case VehicleData ignored    -> response = VehicleDataManager.insertVehicle((VehicleData) objectToManage);
            case LicenseData ignored    -> response = LicenseDataManger.insertLicense((LicenseData) objectToManage);
            case DriverData ignored     -> response = DriverDataManager.insertDriver((DriverData) objectToManage);
            default                     -> throw new IllegalStateException("Unexpected value: ");
        }
        output.writeBoolean(response);
        output.flush();
    }

}

