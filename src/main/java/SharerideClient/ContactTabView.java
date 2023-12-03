package SharerideClient;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;

public class ContactTabView {

    private final FXMLLoader fxmlLoader;

    ContactTabView()
    {
        fxmlLoader = new FXMLLoader(getClass().getResource("ContactTab.fxml"));
    }

    protected Node initialize() throws IOException
    {
        return fxmlLoader.load();
    }
}
