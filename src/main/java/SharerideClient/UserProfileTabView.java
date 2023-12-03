package SharerideClient;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;

public class UserProfileTabView {

    private final FXMLLoader fxmlLoader;

    UserProfileTabView()
    {
        fxmlLoader = new FXMLLoader(getClass().getResource("UserProfileTab.fxml"));
    }

    protected Node initialize() throws IOException
    {
        return fxmlLoader.load();
    }

}
