package Commands;

import Data.DriverData;
import Data.LicenseData;
import Data.UserData;
import Data.VehicleData;
import DataManagers.DriverDataManager;
import DataManagers.LicenseDataManger;
import DataManagers.UserDataManager;
import DataManagers.VehicleDataManager;
import Server.ClientHandler;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class SelectCommand extends Command {

    public SelectCommand(Object objectToManage){
        super(objectToManage);
    }

    public void execute(ObjectOutputStream output) throws IOException {


        objectToManage = switch (objectToManage) {
            case UserData ignored    -> UserDataManager.selectUserData();
            case LicenseData ignored -> LicenseDataManger.selectLicenceData();
            case VehicleData ignored -> VehicleDataManager.selectVehicleData();
            case DriverData ignored  -> DriverDataManager.selectDriverData();
            default                  -> throw new IllegalArgumentException("Unrecognized object");
        };

        output.writeObject(objectToManage);
        output.flush();

    }
}
