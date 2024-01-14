package SharerideClient.Views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;

public class RegisterFormView {

    private final FXMLLoader fxmlLoader;

    public RegisterFormView()
    {
        fxmlLoader = new FXMLLoader(getClass().getResource("RegisterForm.fxml"));
    }

    public Node initialize() throws IOException
    {
        return fxmlLoader.load();
    }
}
