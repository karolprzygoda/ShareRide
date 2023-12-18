package Server;

import java.sql.Date;
import java.util.Map;

public interface DatabaseDriver {

    void addCar(String carBrand, String carModel, String carPlates, String carVIN, int carSeatAvailable,String carInsuranceNumber,Date insurancePolicyExpirationDate, int id);
    void addLicense(String licenseID, Date dateOfIssueOfTheLicense, Date expirationDateOfTheLicense, String licenseCategory, int userID);
    void addDriver(Map<String, Integer> driverMap);


}
