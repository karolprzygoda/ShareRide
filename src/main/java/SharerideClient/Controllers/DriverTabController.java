package SharerideClient.Controllers;

import Data.DriverData;
import Data.LicenseData;
import Data.VehicleData;
import SharerideClient.Alerts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.sql.Date;
import java.util.*;

public class DriverTabController implements Initializable {


    @FXML
    private Label insuranceLabel;

    @FXML
    private Label VINLabel;

    @FXML
    private TextField ViNTextField;

    @FXML
    public Label lastNameLabel;

    @FXML
    private Button becomeDriverBtn;

    @FXML
    private Label brandLabel;

    @FXML
    private TextField brandTextField;

    @FXML
    private Label vehicleBrandFieldStringLengthValidationInfo;

    @FXML
    private Label vehicleModelFieldStringLengthValidationInfo;

    @FXML
    private Label vehiclePlateFieldStringLengthValidationInfo;

    @FXML
    private Label vehicleVinFieldStringLengthValidationInfo;

    @FXML
    private DatePicker dateOfIssueDatePicker;

    @FXML
    private Label dateOfIssueLabel;

    @FXML
    private Button deleteLicenseBtn;

    @FXML
    private Button deleteVehicleDataBtn;

    @FXML
    private DatePicker expirationDateOfTheLicenseDatePicker;

    @FXML
    private Label expirationDateOfTheLicenseLabel;

    @FXML
    private Label licenseCategoryLabel;

    @FXML
    private Label insuranceNumberFieldStringLengthValidationInfo;

    @FXML
    private TextField insuranceNumberTextField;

    @FXML
    private Label insurancePolicyExpirationDateLabel;

    @FXML
    private DatePicker insurancePolicyExpirationDateDatePicker;

    @FXML
    private TextField licensceNumberTextField;

    @FXML
    private ComboBox<String> licenseCategoryComboBox;

    @FXML
    private Label licenseIDLabel;

    @FXML
    private Label licenseNumberStringLengthValidationInfo;

    @FXML
    private Label modelLabel;

    @FXML
    private TextField modelTextField;

    @FXML
    public Label nameLabel;

    @FXML
    private Label platesNumberLabel;

    @FXML
    private TextField platesNumberTextField;

    @FXML
    private DatePicker productionDateDatePicker;

    @FXML
    private Label productionDateLabel;

    @FXML
    private Label seatsAvailableFieldStringLengthValidationInfo;

    @FXML
    private Label seatsAvailableLabel;

    @FXML
    private Spinner<Integer> seatsAvailableSpinner;

    @FXML
    private Button updateVehicleDataBtn;

    @FXML
    private Button updateLicenseBtn;

    private boolean licenseCheckFlag = true, brandCheckFlag=true,modelCheckFlag=true,vehiclePlatesCheckFlag=true,vehicleVinCheckFlag=true,vehicleInsuranceNumberCheckFlag=true;

    private final String[] licenseType = {"AM", "A1", "A2", "A", "B1", "B", "C1", "C", "D1", "D", "BE", "C1E", "CE", "D1E", "DE", "T"};

    private static LicenseData licenseDataBuffer = new LicenseData();

    private static VehicleData vehicleDataBuffer = new VehicleData();

    private static DriverData driverDataBuffer = new DriverData();


    public void seatsAvailableSpinner() {
        SpinnerValueFactory<Integer> spinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 55, 1);
        seatsAvailableSpinner.setValueFactory(spinner);
    }


    private void licenceCategory() {
        List<String> listS = new ArrayList<>();

        Collections.addAll(listS, licenseType);

        ObservableList<String> listD = FXCollections.observableArrayList(listS);
        licenseCategoryComboBox.setItems(listD);
    }


    private LicenseData getLicenseData()
    {

        LicenseData licenseData = new LicenseData();

        licenseData.setUserID(licenseDataBuffer.getUserID());
        licenseData.setSerialNumber(licensceNumberTextField.getText());
        licenseData.setDateOfIssueOfTheLicense(Date.valueOf(dateOfIssueDatePicker.getValue()));
        licenseData.setExpirationDateOfTheLicense(Date.valueOf(expirationDateOfTheLicenseDatePicker.getValue()));
        licenseData.setCategory(licenseCategoryComboBox.getValue());

        return licenseData;
    }

    private VehicleData getVehicleData()
    {

        VehicleData vehicleData = new VehicleData();

        vehicleData.setUserID(vehicleDataBuffer.getUserID());
        vehicleData.setBrand(brandTextField.getText());
        vehicleData.setModel(modelTextField.getText());
        vehicleData.setProductionDate(Date.valueOf(productionDateDatePicker.getValue()));
        vehicleData.setPlatesNumber(platesNumberTextField.getText());
        vehicleData.setVin(ViNTextField.getText());
        vehicleData.setAvailableSeats(seatsAvailableSpinner.getValue());
        vehicleData.setInsuranceNumber(insuranceNumberTextField.getText());
        vehicleData.setInsuranceExpirationDate(Date.valueOf(insurancePolicyExpirationDateDatePicker.getValue()));

        return vehicleData;
    }

    private LicenseData getLicenseDataToUpdate()
    {

        if(!licensceNumberTextField.getText().isEmpty())
            licenseDataBuffer.setSerialNumber(licensceNumberTextField.getText());
        if(dateOfIssueDatePicker.getValue() != null)
            licenseDataBuffer.setDateOfIssueOfTheLicense(Date.valueOf(dateOfIssueDatePicker.getValue()));
        if(expirationDateOfTheLicenseDatePicker.getValue() != null)
            licenseDataBuffer.setExpirationDateOfTheLicense(Date.valueOf(expirationDateOfTheLicenseDatePicker.getValue()));
        if(licenseCategoryComboBox.getSelectionModel().getSelectedItem() != null)
            licenseDataBuffer.setCategory(licenseCategoryComboBox.getSelectionModel().getSelectedItem());

        return licenseDataBuffer;
    }

    private VehicleData getVehicleDataToUpdate()
    {

        if(!brandTextField.getText().isEmpty())
            vehicleDataBuffer.setBrand(brandTextField.getText());
        if(!modelTextField.getText().isEmpty())
            vehicleDataBuffer.setModel(modelTextField.getText());
        if(productionDateDatePicker.getValue() != null)
            vehicleDataBuffer.setProductionDate(Date.valueOf(productionDateDatePicker.getValue()));
        if(!platesNumberTextField.getText().isEmpty())
            vehicleDataBuffer.setPlatesNumber(platesNumberTextField.getText());
        if(!ViNTextField.getText().isEmpty())
            vehicleDataBuffer.setVin(ViNTextField.getText());
        if(seatsAvailableSpinner.getValue() != null)
            vehicleDataBuffer.setAvailableSeats(seatsAvailableSpinner.getValue());
        if(!insuranceNumberTextField.getText().isEmpty())
            vehicleDataBuffer.setInsuranceNumber(insuranceNumberTextField.getText());
        if(insurancePolicyExpirationDateDatePicker.getValue() != null)
            vehicleDataBuffer.setInsuranceExpirationDate(Date.valueOf(insurancePolicyExpirationDateDatePicker.getValue()));

        return vehicleDataBuffer;
    }

    @FXML
    private void editDriverVehicleData()
    {
        if(vehicleInsuranceNumberCheckFlag || vehicleVinCheckFlag || vehiclePlatesCheckFlag || modelCheckFlag || brandCheckFlag) {

            if (ServerController.sendSelectRequest(vehicleDataBuffer) != null) {
                if(!checkIfEmptyVehicleUpdate()) {
                    int response = ServerController.sendUpdateRequest(getVehicleDataToUpdate());

                    if(response == 1) {
                        Alerts.successAlert("Pomyślnie zaktualizowano samochód");
                        seeActualDriverData();
                    }
                    else if (response == 0)
                        Alerts.failureAlert("Operacja aktualizacji danych samochodu zakończona niepowodzeniem");
                }
                else
                    Alerts.failureAlert("Wszystkie pola są puste");
            } else {
                if(!checkIfEmptyVehicleAdd()) {
                    int response = ServerController.sendInsertRequest(getVehicleData());

                    if(response == 1){
                        Alerts.successAlert("Pomyślnie dodano samochód");
                        seeActualDriverData();
                    }
                    else if (response == 0)
                        Alerts.failureAlert("Operacja dodania informacji o pojeździe zakończona niepowodzeniem");
                }
                else
                    Alerts.failureAlert("Proszę uzupełnić wszystkie pola");
            }

            clearVehicleFields();
        }
        else
            Alerts.failureAlert("Proszę poprawić pola zaznaczone na czerwono");
    }


    @FXML
    private void editDriverLicenceData()
    {

        if(licenseCheckFlag) {

            if (ServerController.sendSelectRequest(licenseDataBuffer) != null) {
                if(!checkIfEmptyLicenseUpdate()) {
                    int response = ServerController.sendUpdateRequest(getLicenseDataToUpdate() );

                    if(response == 1) {
                        Alerts.successAlert("Pomyślnie zaktualizowano prawo jazdy");
                        seeActualDriverData();
                    }
                    else if (response == 0)
                        Alerts.failureAlert("Operacja aktualizacji danych prawa jazdy zakończona niepowodzeniem");
                }
                else
                {
                    Alerts.failureAlert("Wszystkie pola są puste");

                }
            } else {
                if(!checkIfEmptyLicenseAdd()) {
                    int response = ServerController.sendInsertRequest(getLicenseData());

                    if(response == 1) {
                        Alerts.successAlert("Pomyślnie dodano prawo jazdy");
                        seeActualDriverData();
                    }
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
            int response = ServerController.sendDeleteRequest(licenseDataBuffer);

            if(response == 1) {
                Alerts.successAlert("Pomyślnie usunięto prawo jazdy");
                seeActualDriverData();
            }
            else if(response == 0)
                Alerts.failureAlert("Operacja usunięcia prawa jazdy zakończona niepowodzeniem");

        }
    }

    @FXML
    private void deleteVehicle()
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Potwierdź Operacje");
        alert.setHeaderText(null);
        alert.setContentText("Na pewno chcesz usunąć pojazd?");

        Optional<ButtonType> option = alert.showAndWait();

        if (option.get().equals(ButtonType.OK)) {
            int response = ServerController.sendDeleteRequest(vehicleDataBuffer);

            if(response == 1) {
                Alerts.successAlert("Pomyślnie usunięto pojazd");
                seeActualDriverData();
            }
            else if(response == 0)
                Alerts.failureAlert("Operacja usunięcia pojazdu zakończona niepowodzeniem ");

        }
    }


    private void clearLicenseFields() {
        licensceNumberTextField.setText("");
        dateOfIssueDatePicker.setValue(null);
        expirationDateOfTheLicenseDatePicker.setValue(null);
        dateOfIssueDatePicker.setPromptText("Wybierz date wydania prawa jazdy");
        expirationDateOfTheLicenseDatePicker.setPromptText("Wybierz date ważności prawa jazdy");
    }

    private void clearVehicleFields() {
        brandTextField.setText("");
        modelTextField.setText("");
        platesNumberTextField.setText("");
        insuranceNumberTextField.setText("");
        ViNTextField.setText("");
        seatsAvailableSpinner.getValueFactory().setValue(1);
        platesNumberTextField.setText("");
        insurancePolicyExpirationDateDatePicker.setValue(null);
        insurancePolicyExpirationDateDatePicker.setPromptText("Wybierz date wygaśnięcia polisy ubezpieczeniowej");
    }


    @FXML
    private void seeActualDriverData(){

        Label[] licenseData = {licenseIDLabel, dateOfIssueLabel, expirationDateOfTheLicenseLabel, licenseCategoryLabel};
        Label[] vehicleData = {brandLabel, modelLabel, productionDateLabel, platesNumberLabel, VINLabel, seatsAvailableLabel, insuranceLabel, insurancePolicyExpirationDateLabel};

        LicenseData license = ServerController.sendSelectRequest(licenseDataBuffer);
        VehicleData vehicle = ServerController.sendSelectRequest(vehicleDataBuffer);

        if(license == null)
        {
            for (Label label: licenseData) {
                label.setText("Brak");
            }
        }
        else {
            licenseIDLabel.setText(String.valueOf(license.getSerialNumber()));
            dateOfIssueLabel.setText(String.valueOf(license.getDateOfIssueOfTheLicense()));
            expirationDateOfTheLicenseLabel.setText(String.valueOf(license.getExpirationDateOfTheLicense()));
            licenseCategoryLabel.setText(license.getCategory());
            licenseDataBuffer = license;
        }

        if(vehicle == null)
        {
            for (Label label: vehicleData) {
                label.setText("Brak");
            }
        }
        else
        {
            brandLabel.setText(vehicle.getBrand());
            modelLabel.setText(vehicle.getModel());
            productionDateLabel.setText(String.valueOf(vehicle.getProductionDate()));
            platesNumberLabel.setText(vehicle.getPlatesNumber());
            VINLabel.setText(vehicle.getVin());
            seatsAvailableLabel.setText(String.valueOf(vehicle.getAvailableSeats()));
            insuranceLabel.setText(vehicle.getInsuranceNumber());
            insurancePolicyExpirationDateLabel.setText(String.valueOf(vehicle.getInsuranceExpirationDate()));
            vehicleDataBuffer = vehicle;
        }

    }

    private DriverData getDriverData()
    {
        DriverData driverData = new DriverData();

        driverData.setUserID(driverDataBuffer.getUserID());
        driverData.setLicenseID(licenseDataBuffer.getId());
        driverData.setVehicleID(vehicleDataBuffer.getId());

        driverDataBuffer = driverData;

        return driverData;

    }

    @FXML
    private void becomeDriver()
    {
        DriverData driverData = ServerController.sendSelectRequest(driverDataBuffer);

        if(driverData == null)
        {
            if(ServerController.sendSelectRequest(licenseDataBuffer) == null)
            {
                Alerts.failureAlert("Proszę uzupełnić informacje o prawie jazdy !");
            }
            else if(ServerController.sendSelectRequest(vehicleDataBuffer) == null)
            {
                Alerts.failureAlert("Proszę uzupełnić informacje o pojeździe !");
            }
            else
            {
                ServerController.sendInsertRequest(getDriverData());
                Alerts.successAlert("Operacja zarejestrowania konta jako kierowca zakończona sukcesem !");
            }
        }else
        {
            Alerts.failureAlert("Jesteś już zarejestrowany jako kierowca !");
        }
    }

    @FXML
    private void checkLicense() {
        licensceNumberTextField.addEventFilter(KeyEvent.ANY, event -> {
            String currentText = licensceNumberTextField.getText() + event.getCharacter();
            if (currentText.length() > 20) {
                licensceNumberTextField.setStyle("-fx-border-color: red");
                licenseNumberStringLengthValidationInfo.setVisible(true);
                licenseCheckFlag = false;
            }
            else {
                licensceNumberTextField.setStyle("");
                licenseNumberStringLengthValidationInfo.setVisible(false);
                licenseCheckFlag = true;
            }
        });
    }

    @FXML
    private void checkVehicleBrand() {
        brandTextField.addEventFilter(KeyEvent.ANY, event -> {
            String currentText = brandTextField.getText() + event.getCharacter();
            if (currentText.length() > 50) {
                brandTextField.setStyle("-fx-border-color: red");
                vehicleBrandFieldStringLengthValidationInfo.setVisible(true);
                brandCheckFlag = false;
            }
            else {
                brandTextField.setStyle("");
                vehicleBrandFieldStringLengthValidationInfo.setVisible(false);
                brandCheckFlag = true;
            }
        });
    }

    @FXML
    private void checkVehicleModel() {
        modelTextField.addEventFilter(KeyEvent.ANY, event -> {
            String currentText = modelTextField.getText() + event.getCharacter();
            if (currentText.length() > 50) {
                modelTextField.setStyle("-fx-border-color: red");
                vehicleModelFieldStringLengthValidationInfo.setVisible(true);
                modelCheckFlag = false;
            }
            else {
                modelTextField.setStyle("");
                vehicleModelFieldStringLengthValidationInfo.setVisible(false);
                modelCheckFlag = true;
            }
        });
    }

    @FXML
    private void checkPlates() {
        platesNumberTextField.addEventFilter(KeyEvent.ANY, event -> {
            String currentText = platesNumberTextField.getText() + event.getCharacter();
            if (currentText.length() > 50) {
                platesNumberTextField.setStyle("-fx-border-color: red");
                vehiclePlateFieldStringLengthValidationInfo.setVisible(true);
                vehiclePlatesCheckFlag = false;
            }
            else {
                platesNumberTextField.setStyle("");
                vehiclePlateFieldStringLengthValidationInfo.setVisible(false);
                vehiclePlatesCheckFlag = true;
            }
        });
    }

    @FXML
    private void checkVIN() {
        ViNTextField.addEventFilter(KeyEvent.ANY, event -> {
            String currentText = ViNTextField.getText() + event.getCharacter();
            if (currentText.length() > 50) {
                ViNTextField.setStyle("-fx-border-color: red");
                vehicleVinFieldStringLengthValidationInfo.setVisible(true);
                vehicleVinCheckFlag = false;
            }
            else {
                ViNTextField.setStyle("");
                vehicleVinFieldStringLengthValidationInfo.setVisible(false);
                vehicleVinCheckFlag = true;
            }
        });
    }

    @FXML
    private void checkInsurance() {
        insuranceNumberTextField.addEventFilter(KeyEvent.ANY, event -> {
            String currentText = insuranceNumberTextField.getText() + event.getCharacter();
            if (currentText.length() > 50) {
                insuranceNumberTextField.setStyle("-fx-border-color: red");
                insuranceNumberFieldStringLengthValidationInfo.setVisible(true);
                vehicleInsuranceNumberCheckFlag = false;
            }
            else {
                insuranceNumberTextField.setStyle("");
                insuranceNumberFieldStringLengthValidationInfo.setVisible(false);
                vehicleInsuranceNumberCheckFlag = true;
            }
        });
    }

    private boolean checkIfEmptyLicenseAdd() {
        return licensceNumberTextField.getText().isEmpty()
                || dateOfIssueDatePicker.getValue() == null
                || expirationDateOfTheLicenseDatePicker.getValue() == null
                || licenseCategoryComboBox.getValue() == null;
    }

    private boolean checkIfEmptyLicenseUpdate() {
        return licensceNumberTextField.getText().isEmpty()
                && dateOfIssueDatePicker.getValue() == null
                && expirationDateOfTheLicenseDatePicker.getValue() == null
                && licenseCategoryComboBox.getValue() == null;
    }

    private boolean checkIfEmptyVehicleAdd() {
        return brandTextField.getText().isEmpty()
                || modelTextField.getText().isEmpty()
                || platesNumberTextField.getText().isEmpty()
                || ViNTextField.getText().isEmpty()
                || seatsAvailableSpinner.getValue() == null
                || insuranceNumberTextField.getText().isEmpty()
                || insurancePolicyExpirationDateDatePicker.getValue() == null;
    }

    private boolean checkIfEmptyVehicleUpdate() {
        return brandTextField.getText().isEmpty()
                && modelTextField.getText().isEmpty()
                && platesNumberTextField.getText().isEmpty()
                && ViNTextField.getText().isEmpty()
                && seatsAvailableSpinner.getValue() == null
                && insuranceNumberTextField.getText().isEmpty()
                && insurancePolicyExpirationDateDatePicker.getValue() == null;
    }





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        licenseDataBuffer.setUserID(ServerController.currentSessionUser.getId());
        vehicleDataBuffer.setUserID(ServerController.currentSessionUser.getId());
        driverDataBuffer.setUserID(ServerController.currentSessionUser.getId());

        licenceCategory();
        seatsAvailableSpinner();
        seeActualDriverData();
        nameLabel.setText(ClientDashBoardController.userData.getName());
        lastNameLabel.setText(ClientDashBoardController.userData.getLastName());
    }
}
