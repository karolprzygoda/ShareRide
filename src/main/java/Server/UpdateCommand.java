package Server;
import Data.VehicleData;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class UpdateCommand implements Command{
    public static void execute(ObjectInputStream scanner, ObjectOutputStream out) throws IOException, ClassNotFoundException {

        String field = (String) scanner.readObject();
        int id = scanner.readInt();
        Object object = scanner.readObject();
        boolean response;


        switch (field) {
            case "CAR" -> response = UpdateHandler.updateVehicle(id, (VehicleData) object);
            //case "USER" -> response = PostgreSQL.update(id, "users", fieldsToUpdate);
            //case "LICENSE" -> response = PostgreSQL.update(id, "license", fieldsToUpdate);
            default -> throw new IllegalStateException("Unexpected value: " + field);
        }

        out.writeBoolean(response);
        out.flush();
    }
}
