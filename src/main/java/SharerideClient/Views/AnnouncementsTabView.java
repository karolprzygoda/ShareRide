package SharerideClient.Views;

import SharerideClient.Controllers.AnnouncementsTabController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;

public class AnnouncementsTabView {

    private final FXMLLoader fxmlLoader;

    public static AnnouncementsTabController announcementsTabController;
    public AnnouncementsTabView()
    {
        fxmlLoader = new FXMLLoader(getClass().getResource("AnnouncementsTab.fxml"));
    }

    public Node initialize() throws IOException
    {
        Node node = fxmlLoader.load();
        announcementsTabController = fxmlLoader.getController();
        return node;
    }
}
