package Commands;

import Data.PassengersData;
import DataManagers.PassengersDataManager;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class CheckIfAlreadyInRideCommand extends Command{
    public CheckIfAlreadyInRideCommand(Object objectToManage){
        super(objectToManage);
    }

    public void execute(ObjectOutputStream output) throws IOException {
        PassengersData passengersData = (PassengersData) objectToManage;
        boolean response = PassengersDataManager.checkIfPassengerHasAlreadyJoinedTheRide(passengersData);

        output.writeBoolean(response);
        output.flush();
        output.reset();
    }
}
