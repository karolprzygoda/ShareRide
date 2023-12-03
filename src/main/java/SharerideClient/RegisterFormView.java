package SharerideClient;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;

public class RegisterFormView {

    private final FXMLLoader fxmlLoader;

    RegisterFormView()
    {
        fxmlLoader = new FXMLLoader(getClass().getResource("RegisterForm.fxml"));
    }

    protected Node initialize() throws IOException
    {
        return fxmlLoader.load();
    }
}
