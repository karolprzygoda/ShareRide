package SharerideClient.Views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;

public class ChatTabView {

    private final FXMLLoader fxmlLoader;

    public ChatTabView()
    {
        fxmlLoader = new FXMLLoader(getClass().getResource("ChatTab.fxml"));
    }

    public Node initialize() throws IOException
    {
        return fxmlLoader.load();
    }
}
