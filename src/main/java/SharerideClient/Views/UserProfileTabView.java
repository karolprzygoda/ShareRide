package SharerideClient.Views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;

public class UserProfileTabView {

    private final FXMLLoader fxmlLoader;

    public UserProfileTabView()
    {
        fxmlLoader = new FXMLLoader(getClass().getResource("UserProfileTab.fxml"));
    }

    public Node initialize() throws IOException
    {
        return fxmlLoader.load();
    }

}
