package SharerideClient;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import java.net.URL;
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
    private TextField driver_seatsAvailable;

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

    private boolean licenseCheckFlag = true, brandCheckFlag=true,modelCheckFlag=true,carPlatesCheckFlag=true,carVinCheckFlag=true,carSeatsAvailableCheckFlag=true,carInsuranceNumberCheckFlag=true;

    private final String[] licenseType = {"AM", "A1", "A2", "A", "B1", "B", "C1", "C", "D1", "D", "BE", "C1E", "CE", "D1E", "DE", "T"};


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

    private ArrayList<Object> getLicenseData()
    {
        ArrayList<Object> list = new ArrayList<>();
        list.add(ServerController.id);
        list.add(driver_LicenceNumber.getText());
        list.add(driver_dateOfIssueOfTheLicense.getValue());
        list.add(driver_expirationDateOfTheLicense.getValue());
        list.add(driver_licenseCategory.getValue());
        return list;
    }

    private ArrayList<Object> getCarData()
    {
        ArrayList<Object> list = new ArrayList<>();
        list.add(ServerController.id);
        list.add(driver_carBrand.getText());
        list.add(driver_carModel.getText());
        list.add(driver_carPlatesNumber.getText());
        list.add(driver_VIN.getText());
        list.add(driver_seatsAvailable.getText());
        list.add(driver_insuranceNumber.getText());
        list.add(driver_insurancePolicyExpirationDate.getValue());
        return list;
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

    private Map<String, Object> getCarDataToUpdate()
    {
        Map<String, Object> fieldsToUpdate = new HashMap<>();

        if(!driver_carBrand.getText().isEmpty())
            fieldsToUpdate.put("marka", driver_carBrand.getText());
        if(!driver_carModel.getText().isEmpty())
            fieldsToUpdate.put("model", driver_carModel.getText());
        if(!driver_carPlatesNumber.getText().isEmpty())
            fieldsToUpdate.put("rejestracja", driver_carPlatesNumber.getText());
        if(!driver_VIN.getText().isEmpty())
            fieldsToUpdate.put("vin", driver_VIN.getText());
        if(!driver_seatsAvailable.getText().isEmpty())
            fieldsToUpdate.put("liczba_miejsc", driver_seatsAvailable.getText());
        if(!driver_insuranceNumber.getText().isEmpty())
            fieldsToUpdate.put("polisa", driver_insuranceNumber.getText());
        if(driver_insurancePolicyExpirationDate.getValue() != null)
            fieldsToUpdate.put("data_wygasniecia_polisy", driver_insurancePolicyExpirationDate.getValue());

        return fieldsToUpdate;
    }

    @FXML
    private void editDriverCarData()
    {
        if(carInsuranceNumberCheckFlag || carVinCheckFlag || carPlatesCheckFlag || modelCheckFlag || carSeatsAvailableCheckFlag || brandCheckFlag) {

            if (ServerController.sendSelectRequest("CAR") != null) {
                if(!checkIfEmptyCarUpdate()) {
                    int response = ServerController.sendUpdateRequest("CAR",getCarDataToUpdate());
                    if(response == 1) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Udało się");
                        alert.setHeaderText(null);
                        alert.setContentText("Pomyślnie zaktualizowano samochód");
                        alert.showAndWait();
                    } else if (response == 0) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Błąd");
                        alert.setHeaderText(null);
                        alert.setContentText("Operacja aktualizacji danych samochodu zakończona niepowodzeniem");
                        alert.showAndWait();
                    }
                }
                else
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Błąd");
                    alert.setHeaderText(null);
                    alert.setContentText("Wszystkie pola są puste");
                    alert.showAndWait();
                }
            } else {
                if(!checkIfEmptyCarAdd()) {
                    int response = ServerController.sendInsertRequest("CAR",getCarData());
                    if(response == 1) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Udało się");
                        alert.setHeaderText(null);
                        alert.setContentText("Pomyślnie dodano samochód");
                        alert.showAndWait();
                    } else if (response == 0) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Błąd");
                        alert.setHeaderText(null);
                        alert.setContentText("Operacja dodania informacji o pojeździe zakończona niepowodzeniem");
                        alert.showAndWait();
                    }
                }
                else
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Błąd");
                    alert.setHeaderText(null);
                    alert.setContentText("Proszę uzupełnić wszystkie pola");
                    alert.showAndWait();
                }
            }

            clearCarFields();
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd");
            alert.setHeaderText(null);
            alert.setContentText("Proszę poprawić pola zaznaczone na czerwono");
            alert.showAndWait();
        }
    }


    @FXML
    private void editDriverLicenceData()
    {

        if(licenseCheckFlag) {

            if (ServerController.sendSelectRequest("LICENSE") != null) {
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

            if(response == 1)
                Alerts.successAlert("Pomyślnie usunięto prawo jazdy");
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

            if(response == 1)
                Alerts.successAlert("Pomyślnie usunięto pojazd");
            else if(response == 0)
                Alerts.failureAlert("Operacja usunięcia pojazdu zakończona niepowodzeniem ");

        }
    }


    private void clearLicenseFields() {
        driver_LicenceNumber.setText("");
        driver_dateOfIssueOfTheLicense.setValue(null);
        driver_expirationDateOfTheLicense.setValue(null);
        driver_licenseCategory.getSelectionModel().clearSelection();
        driver_dateOfIssueOfTheLicense.setPromptText("Wybierz date wydania prawa jazdy");
        driver_expirationDateOfTheLicense.setPromptText("Wybierz date ważności prawa jazdy");
    }

    private void clearCarFields() {
        driver_carBrand.setText("");
        driver_carModel.setText("");
        driver_carPlatesNumber.setText("");
        driver_insuranceNumber.setText("");
        driver_VIN.setText("");
        driver_seatsAvailable.setText("");
        driver_carPlatesNumber.setText("");
        driver_insurancePolicyExpirationDate.setValue(null);
        driver_insurancePolicyExpirationDate.setPromptText("Wybierz date wygaśnięcia polisy ubezpieczeniowej");
    }


    @FXML
    private void seeActualDriverData(){

        List<String> profileInfo = ServerController.sendSelectRequest("USER");
        List<String> licenseInfo = ServerController.sendSelectRequest("LICENSE");
        List<String> carInfo = ServerController.sendSelectRequest("CAR");
        Label[] licenseData = {driver_licenseIDLabel, driver_dateOfIssueLabel, driver_expirationDateOfTheLicenseLabel, driver_licenseCategoryLabel};
        Label[] carData = {carBrandLabel, carModelLabel, carPlatesLabel, carVinLabel, carSeatsAvailableLabel, carInsuranceLabel, carInsurancePolicyExpirationDate};
        assert profileInfo != null;
        assert licenseInfo != null;
        assert carInfo != null;
        driver_nameLabel.setText(profileInfo.get(0));
        driver_lastNameLabel.setText(profileInfo.get(1));

        for(int i = 0; i<licenseData.length; i++)
        {
            try {
                licenseData[i].setText(licenseInfo.get(i + 1));
            }catch (RuntimeException e)
            {
                licenseData[i].setText("Brak");
            }
        }
        for(int i = 0; i<carData.length; i++)
        {
            try {
                carData[i].setText(carInfo.get(i + 1));
            }catch (RuntimeException e)
            {
                carData[i].setText("Brak");
            }
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
    private void checkSeats() {
        driver_seatsAvailable.addEventFilter(KeyEvent.ANY, event -> {
            String currentText = driver_seatsAvailable.getText() + event.getCharacter();
            if (currentText.length() > 50) {
                driver_seatsAvailable.setStyle("-fx-border-color: red");
                seatsAvailableFieldStringLengthValidationInfo.setVisible(true);
                carSeatsAvailableCheckFlag = false;
            }
            else {
                driver_seatsAvailable.setStyle("");
                seatsAvailableFieldStringLengthValidationInfo.setVisible(false);
                carSeatsAvailableCheckFlag = true;
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
                || driver_seatsAvailable.getText().isEmpty()
                || driver_insuranceNumber.getText().isEmpty()
                || driver_insurancePolicyExpirationDate.getValue() == null;
    }

    private boolean checkIfEmptyCarUpdate() {
        return driver_carBrand.getText().isEmpty()
                && driver_carModel.getText().isEmpty()
                && driver_carPlatesNumber.getText().isEmpty()
                && driver_VIN.getText().isEmpty()
                && driver_seatsAvailable.getText().isEmpty()
                && driver_insuranceNumber.getText().isEmpty()
                && driver_insurancePolicyExpirationDate.getValue() == null;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        licenceCategory();
    }
}
