package Data;

import java.io.Serializable;

public class DriverData implements Serializable {
    int userID;
    int licenseID;
    int vehicleID;


    public DriverData(int userID, int licenseID, int vehicleID) {
        this.userID = userID;
        this.licenseID = licenseID;
        this.vehicleID = vehicleID;
    }

    public int getUserID() {
        return userID;
    }

    public int getLicenseID() {
        return licenseID;
    }

    public int getVehicleID() {
        return vehicleID;
    }
}
