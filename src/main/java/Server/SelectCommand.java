package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class SelectCommand implements Command{

    public static void execute(ObjectInputStream scanner, ObjectOutputStream out) throws IOException, ClassNotFoundException {
        String field = (String) scanner.readObject();
        int id = scanner.readInt();
        Object object = switch (field) {
            case "USER" -> SelectHandler.selectUserData(id);
            case "LICENSE" -> SelectHandler.selectLicenceData(id);
            case "CAR" -> SelectHandler.selectVehicleData(id);
            case "DRIVER" -> SelectHandler.selectDriverData(id);
            default -> null;
        };

        out.writeObject(object);
        out.flush();
    }
}
