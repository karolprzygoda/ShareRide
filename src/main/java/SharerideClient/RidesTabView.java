package SharerideClient;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;

public class RidesTabView {

    private final FXMLLoader fxmlLoader;

    RidesTabView()
    {
        fxmlLoader = new FXMLLoader(getClass().getResource("RidesTab.fxml"));
    }

    protected Node initialize() throws IOException
    {
        return fxmlLoader.load();
    }
}
