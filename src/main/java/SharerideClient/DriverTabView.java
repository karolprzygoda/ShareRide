package SharerideClient;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;

public class DriverTabView {

    private final FXMLLoader fxmlLoader;

    DriverTabView()
    {
        fxmlLoader = new FXMLLoader(getClass().getResource("DriverTab.fxml"));
    }

    protected Node initialize() throws IOException
    {
        return fxmlLoader.load();
    }
}
