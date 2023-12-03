package SharerideClient;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;

public class LoginFormView {

    private final FXMLLoader fxmlLoader;

    LoginFormView()
    {
        fxmlLoader = new FXMLLoader(getClass().getResource("LoginForm.fxml"));
    }

    protected Node initialize() throws IOException
    {
        return fxmlLoader.load();
    }
}
