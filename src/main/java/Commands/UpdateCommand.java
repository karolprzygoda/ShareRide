package Commands;


import Data.LicenseData;
import Data.UserData;
import Data.VehicleData;
import DataManagers.LicenseDataManger;
import DataManagers.UserDataManager;
import DataManagers.VehicleDataManager;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class UpdateCommand extends Command{

    public UpdateCommand(Object objectToManage){
        super(objectToManage);
    }

    public void execute(ObjectOutputStream output) throws IOException
    {
        boolean response;

        switch (objectToManage) {
            case UserData ignored       -> response = UserDataManager.updateUserData((UserData) objectToManage);
            case VehicleData ignored    -> response = VehicleDataManager.updateVehicle((VehicleData) objectToManage);
            case LicenseData ignored    -> response = LicenseDataManger.updateLicense((LicenseData) objectToManage);
            default                     -> throw new IllegalArgumentException("Unrecognized object");
        }

        output.writeBoolean(response);
        output.flush();
    }
}
