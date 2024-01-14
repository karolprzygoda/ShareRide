package SharerideClient.Views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;

public class AnnouncementsTabView {

    private final FXMLLoader fxmlLoader;

    public AnnouncementsTabView()
    {
        fxmlLoader = new FXMLLoader(getClass().getResource("AnnouncementsTab.fxml"));
    }

    public Node initialize() throws IOException
    {
        return fxmlLoader.load();
    }
}
