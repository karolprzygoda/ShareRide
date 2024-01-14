package Data;

import java.io.Serializable;
import java.sql.Date;

public class VehicleData implements Serializable {
    private int id;
    private int userID;
    private String brand;
    private String model;
    private Date productionDate;
    private String platesNumber;
    private String vin;
    private int availableSeats;
    private String insuranceNumber;
    private Date insuranceExpirationDate;


    public void setId(int id) {
        this.id = id;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setProductionDate(Date productionDate) {
        this.productionDate = productionDate;
    }

    public void setPlatesNumber(String platesNumber) {
        this.platesNumber = platesNumber;
    }

    public void setVin(String vin) {
        this.vin = vin;
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

    public VehicleData(){}

    public VehicleData(int id, int userID, String brand, String model, Date productionDate, String platesNumber, String vin, int availableSeats, String insuranceNumber, Date insuranceExpirationDate) {
        this.id = id;
        this.userID = userID;
        this.brand = brand;
        this.model = model;
        this.productionDate = productionDate;
        this.platesNumber = platesNumber;
        this.vin = vin;
        this.availableSeats = availableSeats;
        this.insuranceNumber = insuranceNumber;
        this.insuranceExpirationDate = insuranceExpirationDate;
    }

    public int getId() {
        return id;
    }

    public int getUserID() {
        return userID;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public Date getProductionDate() {
        return productionDate;
    }

    public String getPlatesNumber() {
        return platesNumber;
    }

    public String getVin() {
        return vin;
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
}
