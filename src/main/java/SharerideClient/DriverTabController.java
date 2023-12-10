package SharerideClient;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class DriverTabController implements Initializable {

    @FXML
    private Label carBrandFieldStringLengthValidationInfo;

    @FXML
    private Label carModelFieldStringLengthValidationInfo;

    @FXML
    private Label carPlateFieldStringLengthValidationInfo;

    @FXML
    private Label carVinFieldStringLengthValidationInfo;

    @FXML
    private Button diver_updateCarData;

    @FXML
    private TextField driver_LicenceNumber;

    @FXML
    private TextField driver_VIN;

    @FXML
    private TextField driver_carBrand;

    @FXML
    private TextField driver_carModel;

    @FXML
    private TextField driver_carPlatesNumber;

    @FXML
    private Button driver_deleteCarData;

    @FXML
    private Button driver_deleteLicenseBtn;

    @FXML
    private TextField driver_insuranceNumber;

    @FXML
    private DatePicker driver_ldateOfIssueOfTheLicense;

    @FXML
    private DatePicker driver_licenceValidityDate;

    @FXML
    private DatePicker driver_licenceValidityDate1;

    @FXML
    private ComboBox<String> driver_licenseCategory;

    @FXML
    private TextField driver_seatsAvailable;

    @FXML
    private Button driver_seeActualData;

    @FXML
    private Button driver_updateLicencseBtn;

    @FXML
    private Label insuranceNumberFieldStringLengthValidationInfo;

    @FXML
    private Label profile_nameFieldValidationInfo1;

    @FXML
    private Label seatsAvailableFieldStringLengthValidationInfo;


    private final String[] licenseType = {"AM", "A1", "A2", "A", "B1", "B", "C1", "C", "D1", "D", "BE", "C1E", "CE", "D1E", "DE", "T"};


    private void licenceCategory() {
        List<String> listS = new ArrayList<>();

        Collections.addAll(listS, licenseType);

        ObservableList<String> listD = FXCollections.observableArrayList(listS);
        driver_licenseCategory.setItems(listD);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        licenceCategory();
    }
}
