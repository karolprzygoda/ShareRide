package Commands;

import Data.*;
import DataManagers.*;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class SelectAllCommand extends Command{

    public SelectAllCommand(Object objectToManage){
        super(objectToManage);
    }

    public void execute(ObjectOutputStream output) throws IOException {


        objectToManage = switch (objectToManage) {
            case AnnouncementsData ignored  -> AnnouncementDataManager.selectAllAnnouncements();
            default                         -> throw new IllegalArgumentException("Unrecognized object");
        };

        output.writeObject(objectToManage);
        output.flush();
        output.reset();

    }
}
