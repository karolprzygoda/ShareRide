package Data;

import java.io.Serializable;
import java.sql.Date;

public class LicenseData implements Serializable {
    private int id;
    private int userID;
    private String serialNumber;
    private String category;
    private Date dateOfIssueOfTheLicense;
    private Date expirationDateOfTheLicense;

    public void setId(int id) {
        this.id = id;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDateOfIssueOfTheLicense(Date dateOfIssueOfTheLicense) {
        this.dateOfIssueOfTheLicense = dateOfIssueOfTheLicense;
    }

    public void setExpirationDateOfTheLicense(Date expirationDateOfTheLicense) {
        this.expirationDateOfTheLicense = expirationDateOfTheLicense;
    }

    public LicenseData(){}

    public LicenseData(int id, int userID, String serialNumber, String category, Date dateOfIssueOfTheLicense, Date expirationDateOfTheLicense) {
        this.id = id;
        this.userID = userID;
        this.serialNumber = serialNumber;
        this.category = category;
        this.dateOfIssueOfTheLicense = dateOfIssueOfTheLicense;
        this.expirationDateOfTheLicense = expirationDateOfTheLicense;
    }

    public int getId() {
        return id;
    }

    public int getUserID() {
        return userID;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public String getCategory() {
        return category;
    }

    public Date getDateOfIssueOfTheLicense() {
        return dateOfIssueOfTheLicense;
    }

    public Date getExpirationDateOfTheLicense() {
        return expirationDateOfTheLicense;
    }
}
