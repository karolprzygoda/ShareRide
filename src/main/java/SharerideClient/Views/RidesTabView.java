package SharerideClient.Views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;

public class RidesTabView {

    private final FXMLLoader fxmlLoader;

    public RidesTabView()
    {
        fxmlLoader = new FXMLLoader(getClass().getResource("RidesTab.fxml"));
    }

    public Node initialize() throws IOException
    {
        return fxmlLoader.load();
    }
}
