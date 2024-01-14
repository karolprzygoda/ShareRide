package SharerideClient.Views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;

public class NotificationTabView {

    private final FXMLLoader fxmlLoader;

    public NotificationTabView()
    {
        fxmlLoader = new FXMLLoader(getClass().getResource("NotificationTab.fxml"));
    }

    public Node initialize() throws IOException
    {
        return fxmlLoader.load();
    }
}
