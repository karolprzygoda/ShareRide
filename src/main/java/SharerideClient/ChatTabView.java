package SharerideClient;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;

public class ChatTabView {

    private final FXMLLoader fxmlLoader;

    ChatTabView()
    {
        fxmlLoader = new FXMLLoader(getClass().getResource("ChatTab.fxml"));
    }

    protected Node initialize() throws IOException
    {
        return fxmlLoader.load();
    }
}
