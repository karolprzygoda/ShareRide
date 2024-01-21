package SharerideClient.Views;

import SharerideClient.Controllers.RidesController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;

public class RidesTabView {

    private final FXMLLoader fxmlLoader;
    public static RidesController ridesController;
    public RidesTabView()
    {
        fxmlLoader = new FXMLLoader(getClass().getResource("RidesTab.fxml"));
    }

    public Node initialize() throws IOException
    {
        Node node = fxmlLoader.load();
        ridesController = fxmlLoader.getController();
        return node;
    }
}
