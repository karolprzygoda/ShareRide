package SharerideClient.Views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;

public class LoginFormView {

    private final FXMLLoader fxmlLoader;

    public LoginFormView()
    {
        fxmlLoader = new FXMLLoader(getClass().getResource("LoginForm.fxml"));
    }

    public Node initialize() throws IOException
    {
        return fxmlLoader.load();
    }
}
