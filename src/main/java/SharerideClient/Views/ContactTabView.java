package SharerideClient.Views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;

public class ContactTabView {

    private final FXMLLoader fxmlLoader;

    public ContactTabView()
    {
        fxmlLoader = new FXMLLoader(getClass().getResource("ContactTab.fxml"));
    }

    public Node initialize() throws IOException
    {
        return fxmlLoader.load();
    }
}
