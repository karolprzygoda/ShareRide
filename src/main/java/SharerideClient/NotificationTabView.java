package SharerideClient;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;

public class NotificationTabView {

    private final FXMLLoader fxmlLoader;

    NotificationTabView()
    {
        fxmlLoader = new FXMLLoader(getClass().getResource("NotificationTab.fxml"));
    }

    protected Node initialize() throws IOException
    {
        return fxmlLoader.load();
    }
}
