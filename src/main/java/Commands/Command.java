package Commands;

import java.io.IOException;
import java.io.ObjectOutputStream;

public abstract class Command {
        public Object objectToManage;

        Command(Object objectToManage) {
            this.objectToManage = objectToManage;
        }
        public abstract void execute(ObjectOutputStream output) throws IOException;
}

