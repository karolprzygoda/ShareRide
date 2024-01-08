package Data;

import java.io.Serializable;
import java.sql.Date;

public class VehicleData implements Serializable {
    int userID;
    String vehicleBrand;
    String vehicleModel;
    String vehiclePlates;
    String vehicleVin;
    int availableSeats;
    String insuranceNumber;
    Date insuranceExpirationDate;
    int id;

    public VehicleData(){}

    public VehicleData( int userID, String vehicleBrand, String vehicleModel, String vehiclePlates, String vehicleVin, int availableSeats, String insuranceNumber, Date insuranceExpirationDate) {
        this.userID = userID;
        this.vehicleBrand = vehicleBrand;
        this.vehicleModel = vehicleModel;
        this.vehiclePlates = vehiclePlates;
        this.vehicleVin = vehicleVin;
        this.availableSeats = availableSeats;
        this.insuranceNumber = insuranceNumber;
        this.insuranceExpirationDate = insuranceExpirationDate;
    }

    public int getId(){return id;}
    public int getUserID() {
        return userID;
    }

    public String getVehicleBrand() {
        return vehicleBrand;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public String getVehiclePlates() {
        return vehiclePlates;
    }

    public String getVehicleVin() {
        return vehicleVin;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public String getInsuranceNumber() {
        return insuranceNumber;
    }

    public Date getInsuranceExpirationDate() {
        return insuranceExpirationDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setVehicleBrand(String vehicleBrand) {
        this.vehicleBrand = vehicleBrand;
    }

    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    public void setVehiclePlates(String vehiclePlates) {
        this.vehiclePlates = vehiclePlates;
    }

    public void setVehicleVin(String vehicleVin) {
        this.vehicleVin = vehicleVin;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public void setInsuranceNumber(String insuranceNumber) {
        this.insuranceNumber = insuranceNumber;
    }

    public void setInsuranceExpirationDate(Date insuranceExpirationDate) {
        this.insuranceExpirationDate = insuranceExpirationDate;
    }
}
