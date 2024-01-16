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

public class DeleteCommand extends Command {

    public DeleteCommand(Object objectToManage){
        super(objectToManage);
    }

    public void  execute(ObjectOutputStream output) throws IOException{

        boolean response;

        switch (objectToManage) {
            case UserData ignored       -> response = UserDataManager.deleteUser((UserData) objectToManage);
            case VehicleData ignored    -> response = VehicleDataManager.deleteVehicle((VehicleData) objectToManage);
            case LicenseData ignored    -> response = LicenseDataManger.deleteLicense((LicenseData) objectToManage);
            case DriverData ignored     -> response = DriverDataManager.deleteDriver((DriverData) objectToManage);
            default                     -> throw new IllegalArgumentException("Unrecognized object");
        }

        output.writeBoolean(response);
        output.flush();
        output.reset();
    }
}
