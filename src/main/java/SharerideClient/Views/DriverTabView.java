package SharerideClient.Views;

import SharerideClient.Controllers.ClientDashBoardController;
import SharerideClient.Controllers.DriverTabController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;

public class DriverTabView {

    private final FXMLLoader fxmlLoader;

    public static DriverTabController driverTabController;
    public DriverTabView()
    {
        fxmlLoader = new FXMLLoader(getClass().getResource("DriverTab.fxml"));
    }

    public Node initialize() throws IOException
    {
        Node node = fxmlLoader.load();
        driverTabController = fxmlLoader.getController();
        return node;
    }

}
