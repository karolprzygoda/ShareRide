package SharerideClient;

import Data.DriverData;
import Data.LicenseData;
import Data.UserData;
import Data.VehicleData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import java.net.URL;
import java.sql.Date;
import java.util.*;

public class DriverTabController implements Initializable {


    @FXML
    private Label carBrandFieldStringLengthValidationInfo;

    @FXML
    private Label carBrandLabel;

    @FXML
    private Label carInsuranceLabel;

    @FXML
    private Label carInsurancePolicyExpirationDate;

    @FXML
    private Label carModelFieldStringLengthValidationInfo;

    @FXML
    private Label carModelLabel;

    @FXML
    private Label carPlateFieldStringLengthValidationInfo;

    @FXML
    private Label carPlatesLabel;

    @FXML
    private Label carSeatsAvailableLabel;

    @FXML
    private Label carVinFieldStringLengthValidationInfo;

    @FXML
    private Label carVinLabel;

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
    private Label driver_dateOfIssueLabel;

    @FXML
    private DatePicker driver_dateOfIssueOfTheLicense;

    @FXML
    private Button driver_deleteCarData;

    @FXML
    private Button driver_deleteLicenseBtn;

    @FXML
    private Button driver_editDriverData;

    @FXML
    private DatePicker driver_expirationDateOfTheLicense;

    @FXML
    private Label driver_expirationDateOfTheLicenseLabel;

    @FXML
    private TextField driver_insuranceNumber;

    @FXML
    private DatePicker driver_insurancePolicyExpirationDate;

    @FXML
    private Label driver_lastNameLabel;

    @FXML
    private ComboBox<String> driver_licenseCategory;

    @FXML
    private Label driver_licenseCategoryLabel;

    @FXML
    private Label driver_licenseIDLabel;

    @FXML
    private Label driver_nameLabel;

    @FXML
    private Spinner<Integer> driver_seatsAvailable;

    @FXML
    private Button driver_seeActualData;

    @FXML
    private Button driver_updateLicenseBtn;

    @FXML
    private AnchorPane editDriverDataPane;

    @FXML
    private Label insuranceNumberFieldStringLengthValidationInfo;

    @FXML
    private Label licenseNumberStringLengthValidationInfo;

    @FXML
    private Label seatsAvailableFieldStringLengthValidationInfo;

    @FXML
    private AnchorPane seeActualDataPane;

    private boolean licenseCheckFlag = true, brandCheckFlag=true,modelCheckFlag=true,carPlatesCheckFlag=true,carVinCheckFlag=true,carInsuranceNumberCheckFlag=true;

    private final String[] licenseType = {"AM", "A1", "A2", "A", "B1", "B", "C1", "C", "D1", "D", "BE", "C1E", "CE", "D1E", "DE", "T"};

    public void seatsAvailableSpinner() {
        SpinnerValueFactory<Integer> spinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 55, 1);
        driver_seatsAvailable.setValueFactory(spinner);
    }


    private void licenceCategory() {
        List<String> listS = new ArrayList<>();

        Collections.addAll(listS, licenseType);

        ObservableList<String> listD = FXCollections.observableArrayList(listS);
        driver_licenseCategory.setItems(listD);
    }

    @FXML
    private void seeDriverActualData()
    {
        editDriverDataPane.setVisible(false);
        seeActualDataPane.setVisible(true);
        seeActualDriverData();
    }

    @FXML
    private void seeEditDriverData()
    {
        seeActualDataPane.setVisible(false);
        editDriverDataPane.setVisible(true);
    }

    private LicenseData getLicenseData()
    {

        return new LicenseData(ServerController.id,
                             driver_LicenceNumber.getText(),
                             Date.valueOf(driver_dateOfIssueOfTheLicense.getValue()),
                             Date.valueOf(driver_expirationDateOfTheLicense.getValue()),
                             driver_licenseCategory.getValue());
    }

    private VehicleData getCarData()
    {


        return new VehicleData(ServerController.id,
                driver_carBrand.getText(),
                driver_carModel.getText(),
                driver_carPlatesNumber.getText(),
                driver_VIN.getText(),
                driver_seatsAvailable.getValue(),
                driver_insuranceNumber.getText(),
                Date.valueOf(driver_insurancePolicyExpirationDate.getValue()));
    }

    private Map<String, Object> getLicenseDataToUpdate()
    {
        Map<String, Object> fieldsToUpdate = new HashMap<>();

        if(!driver_LicenceNumber.getText().isEmpty())
            fieldsToUpdate.put("numer", driver_LicenceNumber.getText());
        if(driver_dateOfIssueOfTheLicense.getValue() != null)
            fieldsToUpdate.put("data_wydania", driver_dateOfIssueOfTheLicense.getValue());
        if(driver_expirationDateOfTheLicense.getValue() != null)
            fieldsToUpdate.put("data_waznosci", driver_expirationDateOfTheLicense.getValue());
        if(driver_licenseCategory.getSelectionModel().getSelectedItem() != null)
            fieldsToUpdate.put("kategoria", driver_licenseCategory.getValue());

        return fieldsToUpdate;
    }

    private VehicleData getCarDataToUpdate()
    {
        VehicleData vehicleData = new VehicleData();

        if(!driver_carBrand.getText().isEmpty())
            vehicleData.setVehicleBrand(driver_carBrand.getText());
        if(!driver_carModel.getText().isEmpty())
            vehicleData.setVehicleModel(driver_carModel.getText());
        if(!driver_carPlatesNumber.getText().isEmpty())
            vehicleData.setVehiclePlates(driver_carPlatesNumber.getText());
        if(!driver_VIN.getText().isEmpty())
             vehicleData.setVehicleVin(driver_VIN.getText());
        if(driver_seatsAvailable.getValue() != null)
            vehicleData.setAvailableSeats(driver_seatsAvailable.getValue());
        if(!driver_insuranceNumber.getText().isEmpty())
            vehicleData.setInsuranceNumber(driver_insuranceNumber.getText());
        if(driver_insurancePolicyExpirationDate.getValue() != null)
            vehicleData.setInsuranceExpirationDate(Date.valueOf(driver_insurancePolicyExpirationDate.getValue()));

        return vehicleData;
    }

    @FXML
    private void editDriverCarData()
    {
        if(carInsuranceNumberCheckFlag || carVinCheckFlag || carPlatesCheckFlag || modelCheckFlag || brandCheckFlag) {

            VehicleData vehicleData = (VehicleData) ServerController.sendSelectRequest("CAR");

            if (vehicleData != null) {
                if(!checkIfEmptyCarUpdate()) {
                    int response = ServerController.sendUpdateRequest("CAR",getCarDataToUpdate());

                    if(response == 1)
                        Alerts.successAlert("Pomyślnie zaktualizowano samochód");
                    else if (response == 0)
                        Alerts.failureAlert("Operacja aktualizacji danych samochodu zakończona niepowodzeniem");
                }
                else
                    Alerts.failureAlert("Wszystkie pola są puste");
            } else {
                if(!checkIfEmptyCarAdd()) {
                    int response = ServerController.sendInsertRequest("CAR",getCarData());

                    if(response == 1)
                        Alerts.successAlert("Pomyślnie dodano samochód");
                    else if (response == 0)
                        Alerts.failureAlert("Operacja dodania informacji o pojeździe zakończona niepowodzeniem");
                }
                else
                    Alerts.failureAlert("Proszę uzupełnić wszystkie pola");
            }

            clearCarFields();
        }
        else
            Alerts.failureAlert("Proszę poprawić pola zaznaczone na czerwono");
    }


    @FXML
    private void editDriverLicenceData()
    {

        if(licenseCheckFlag) {

            LicenseData licenseData = (LicenseData) ServerController.sendSelectRequest("LICENSE");

            if (licenseData != null) {
                if(!checkIfEmptyLicenseUpdate()) {
                    int response = ServerController.sendUpdateRequest("LICENSE",getLicenseDataToUpdate() );

                    if(response == 1)
                        Alerts.successAlert("Pomyślnie zaktualizowano prawo jazdy");
                    else if (response == 0)
                        Alerts.failureAlert("Operacja aktualizacji danych prawa jazdy zakończona niepowodzeniem");
                }
                else
                {
                    Alerts.failureAlert("Wszystkie pola są puste");

                }
            } else {
                if(!checkIfEmptyLicenseAdd()) {
                    int response = ServerController.sendInsertRequest("LICENSE",getLicenseData());

                    if(response == 1)
                        Alerts.successAlert("Pomyślnie dodano prawo jazdy");
                    else if (response == 0)
                        Alerts.failureAlert("Operacja dodania informacji o prawie jazdy zakończona niepowodzeniem");
                }
                else
                {
                    Alerts.failureAlert("Proszę uzupełnić wszystkie pola");
                }
            }

            clearLicenseFields();
        }
        else
        {
            Alerts.failureAlert("Proszę poprawić pola zaznaczone na czerwono");
        }
    }

    @FXML
    private void deleteLicense()
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Potwierdź Operacje");
        alert.setHeaderText(null);
        alert.setContentText("Na pewno chcesz usunąć prawo jazdy?");

        Optional<ButtonType> option = alert.showAndWait();

        if (option.get().equals(ButtonType.OK)) {
            int response = ServerController.sendDeleteRequest("LICENSE");

            if(response == 1) {
                Alerts.successAlert("Pomyślnie usunięto prawo jazdy");
                seeActualDriverData();
            }
            else if(response == 0)
                Alerts.failureAlert("Operacja usunięcia prawa jazdy zakończona niepowodzeniem");

        }
    }

    @FXML
    private void deleteCar()
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Potwierdź Operacje");
        alert.setHeaderText(null);
        alert.setContentText("Na pewno chcesz usunąć samochód?");

        Optional<ButtonType> option = alert.showAndWait();

        if (option.get().equals(ButtonType.OK)) {
            int response = ServerController.sendDeleteRequest("CAR");

            if(response == 1) {
                Alerts.successAlert("Pomyślnie usunięto pojazd");
                seeActualDriverData();
            }
            else if(response == 0)
                Alerts.failureAlert("Operacja usunięcia pojazdu zakończona niepowodzeniem ");

        }
    }


    private void clearLicenseFields() {
        driver_LicenceNumber.setText("");
        driver_dateOfIssueOfTheLicense.setValue(null);
        driver_expirationDateOfTheLicense.setValue(null);
        driver_dateOfIssueOfTheLicense.setPromptText("Wybierz date wydania prawa jazdy");
        driver_expirationDateOfTheLicense.setPromptText("Wybierz date ważności prawa jazdy");
    }

    private void clearCarFields() {
        driver_carBrand.setText("");
        driver_carModel.setText("");
        driver_carPlatesNumber.setText("");
        driver_insuranceNumber.setText("");
        driver_VIN.setText("");
        driver_seatsAvailable.getValueFactory().setValue(1);
        driver_carPlatesNumber.setText("");
        driver_insurancePolicyExpirationDate.setValue(null);
        driver_insurancePolicyExpirationDate.setPromptText("Wybierz date wygaśnięcia polisy ubezpieczeniowej");
    }


    @FXML
    private void seeActualDriverData(){

        Label[] licenseData = {driver_licenseIDLabel, driver_dateOfIssueLabel, driver_expirationDateOfTheLicenseLabel, driver_licenseCategoryLabel};
        Label[] carData = {carBrandLabel, carModelLabel, carPlatesLabel, carVinLabel, carSeatsAvailableLabel, carInsuranceLabel, carInsurancePolicyExpirationDate};

        LicenseData license = (LicenseData) ServerController.sendSelectRequest("LICENSE");
        VehicleData vehicle = (VehicleData) ServerController.sendSelectRequest("CAR");
        UserData user = (UserData) ServerController.sendSelectRequest("USER");

        assert user != null;
        driver_nameLabel.setText(user.getName());
        driver_lastNameLabel.setText(user.getLastName());

        if(license == null)
        {
            for (Label label: licenseData) {
                label.setText("Brak");
            }
        }
        else {
            driver_licenseIDLabel.setText(String.valueOf(license.getLicenseNumber()));
            driver_dateOfIssueLabel.setText(String.valueOf(license.getDateOfIssueOfTheLicense()));
            driver_expirationDateOfTheLicenseLabel.setText(String.valueOf(license.getExpirationDateOfTheLicense()));
            driver_licenseCategoryLabel.setText(license.getLicenseCategory());
        }

        if(vehicle == null)
        {
            for (Label label: carData) {
                label.setText("Brak");
            }
        }
        else
        {
            carBrandLabel.setText(vehicle.getVehicleBrand());
            carModelLabel.setText(vehicle.getVehicleModel());
            carPlatesLabel.setText(vehicle.getVehiclePlates());
            carVinLabel.setText(vehicle.getVehicleVin());
            carSeatsAvailableLabel.setText(String.valueOf(vehicle.getAvailableSeats()));
            carInsuranceLabel.setText(vehicle.getInsuranceNumber());
            carInsurancePolicyExpirationDate.setText(String.valueOf(vehicle.getInsuranceExpirationDate()));
        }

    }

    private DriverData getDriverData()
    {
        VehicleData vehicleData = (VehicleData) ServerController.sendSelectRequest("CAR");
        LicenseData licenseData = (LicenseData) ServerController.sendSelectRequest("LICENSE");

        assert licenseData != null;
        assert vehicleData != null;
        return new DriverData(ServerController.id,
                                               licenseData.getId(),
                                                vehicleData.getId());

    }

    @FXML
    private void becomeDriver()
    {
        DriverData driverData = (DriverData) ServerController.sendSelectRequest("DRIVER");
        LicenseData licenseData = (LicenseData) ServerController.sendSelectRequest("LICENSE");
        VehicleData vehicleData = (VehicleData) ServerController.sendSelectRequest("CAR");

        if(driverData == null)
        {
            if(licenseData == null)
            {
                Alerts.failureAlert("Proszę uzupełnić informacje o prawie jazdy !");
            }
            else if(vehicleData == null)
            {
                Alerts.failureAlert("Proszę uzupełnić informacje o pojeździe !");
            }
            else
            {
                ServerController.sendInsertRequest("DRIVER",getDriverData());
                Alerts.successAlert("Operacja zarejestrowania konta jako kierowca zakończona sukcesem !");
            }
        }else
        {
            Alerts.failureAlert("Jesteś już zarejestrowany jako kierowca !");
        }
    }

    @FXML
    private void checkLicense() {
        driver_LicenceNumber.addEventFilter(KeyEvent.ANY, event -> {
            String currentText = driver_LicenceNumber.getText() + event.getCharacter();
            if (currentText.length() > 20) {
                driver_LicenceNumber.setStyle("-fx-border-color: red");
                licenseNumberStringLengthValidationInfo.setVisible(true);
                licenseCheckFlag = false;
            }
            else {
                driver_LicenceNumber.setStyle("");
                licenseNumberStringLengthValidationInfo.setVisible(false);
                licenseCheckFlag = true;
            }
        });
    }

    @FXML
    private void checkCarBrand() {
        driver_carBrand.addEventFilter(KeyEvent.ANY, event -> {
            String currentText = driver_carBrand.getText() + event.getCharacter();
            if (currentText.length() > 50) {
                driver_carBrand.setStyle("-fx-border-color: red");
                carBrandFieldStringLengthValidationInfo.setVisible(true);
                brandCheckFlag = false;
            }
            else {
                driver_carBrand.setStyle("");
                carBrandFieldStringLengthValidationInfo.setVisible(false);
                brandCheckFlag = true;
            }
        });
    }

    @FXML
    private void checkCarModel() {
        driver_carModel.addEventFilter(KeyEvent.ANY, event -> {
            String currentText = driver_carModel.getText() + event.getCharacter();
            if (currentText.length() > 50) {
                driver_carModel.setStyle("-fx-border-color: red");
                carModelFieldStringLengthValidationInfo.setVisible(true);
                modelCheckFlag = false;
            }
            else {
                driver_carModel.setStyle("");
                carModelFieldStringLengthValidationInfo.setVisible(false);
                modelCheckFlag = true;
            }
        });
    }

    @FXML
    private void checkPlates() {
        driver_carPlatesNumber.addEventFilter(KeyEvent.ANY, event -> {
            String currentText = driver_carPlatesNumber.getText() + event.getCharacter();
            if (currentText.length() > 50) {
                driver_carPlatesNumber.setStyle("-fx-border-color: red");
                carPlateFieldStringLengthValidationInfo.setVisible(true);
                carPlatesCheckFlag = false;
            }
            else {
                driver_carPlatesNumber.setStyle("");
                carPlateFieldStringLengthValidationInfo.setVisible(false);
                carPlatesCheckFlag = true;
            }
        });
    }

    @FXML
    private void checkVIN() {
        driver_VIN.addEventFilter(KeyEvent.ANY, event -> {
            String currentText = driver_VIN.getText() + event.getCharacter();
            if (currentText.length() > 50) {
                driver_VIN.setStyle("-fx-border-color: red");
                carVinFieldStringLengthValidationInfo.setVisible(true);
                carVinCheckFlag = false;
            }
            else {
                driver_VIN.setStyle("");
                carVinFieldStringLengthValidationInfo.setVisible(false);
                carVinCheckFlag = true;
            }
        });
    }

    @FXML
    private void checkInsurance() {
        driver_insuranceNumber.addEventFilter(KeyEvent.ANY, event -> {
            String currentText = driver_insuranceNumber.getText() + event.getCharacter();
            if (currentText.length() > 50) {
                driver_insuranceNumber.setStyle("-fx-border-color: red");
                insuranceNumberFieldStringLengthValidationInfo.setVisible(true);
                carInsuranceNumberCheckFlag = false;
            }
            else {
                driver_insuranceNumber.setStyle("");
                insuranceNumberFieldStringLengthValidationInfo.setVisible(false);
                carInsuranceNumberCheckFlag = true;
            }
        });
    }

    private boolean checkIfEmptyLicenseAdd() {
        return driver_LicenceNumber.getText().isEmpty()
                || driver_dateOfIssueOfTheLicense.getValue() == null
                || driver_expirationDateOfTheLicense.getValue() == null
                || driver_licenseCategory.getValue() == null;
    }

    private boolean checkIfEmptyLicenseUpdate() {
        return driver_LicenceNumber.getText().isEmpty()
                && driver_dateOfIssueOfTheLicense.getValue() == null
                && driver_expirationDateOfTheLicense.getValue() == null
                && driver_licenseCategory.getValue() == null;
    }

    private boolean checkIfEmptyCarAdd() {
        return driver_carBrand.getText().isEmpty()
                || driver_carModel.getText().isEmpty()
                || driver_carPlatesNumber.getText().isEmpty()
                || driver_VIN.getText().isEmpty()
                || driver_seatsAvailable.getValue() == null
                || driver_insuranceNumber.getText().isEmpty()
                || driver_insurancePolicyExpirationDate.getValue() == null;
    }

    private boolean checkIfEmptyCarUpdate() {
        return driver_carBrand.getText().isEmpty()
                && driver_carModel.getText().isEmpty()
                && driver_carPlatesNumber.getText().isEmpty()
                && driver_VIN.getText().isEmpty()
                && driver_seatsAvailable.getValue() == null
                && driver_insuranceNumber.getText().isEmpty()
                && driver_insurancePolicyExpirationDate.getValue() == null;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        licenceCategory();
        seatsAvailableSpinner();
    }
}
