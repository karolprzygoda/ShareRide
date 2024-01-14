package Data;

import java.io.Serializable;

public class DriverData implements Serializable {
    private int id;
    private int userID;
    private int licenseID;
    private int vehicleID;

    public DriverData(){}

    public DriverData(int id, int userID, int licenseID, int vehicleID) {
        this.id = id;
        this.userID = userID;
        this.licenseID = licenseID;
        this.vehicleID = vehicleID;
    }

    public int getId() {
        return id;
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

    public void setId(int id) {
        this.id = id;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setLicenseID(int licenseID) {
        this.licenseID = licenseID;
    }

    public void setVehicleID(int vehicleID) {
        this.vehicleID = vehicleID;
    }
}
