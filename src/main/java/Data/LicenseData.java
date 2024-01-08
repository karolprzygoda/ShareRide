package Data;

import java.io.Serializable;
import java.sql.Date;

public class LicenseData implements Serializable {

    private int userID;
    private String licenseNumber;
    private Date dateOfIssueOfTheLicense;
    private Date expirationDateOfTheLicense;
    private String licenseCategory;
    int id;

    public LicenseData(int userID, String licenseNumber, Date dateOfIssueOfTheLicense, Date expirationDateOfTheLicense, String licenseCategory) {
        this.userID = userID;
        this.licenseNumber = licenseNumber;
        this.dateOfIssueOfTheLicense = dateOfIssueOfTheLicense;
        this.expirationDateOfTheLicense = expirationDateOfTheLicense;
        this.licenseCategory = licenseCategory;
    }

    public int getId() {
        return id;
    }

    public int getUserID() {
        return userID;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public Date getDateOfIssueOfTheLicense() {
        return dateOfIssueOfTheLicense;
    }

    public Date getExpirationDateOfTheLicense() {
        return expirationDateOfTheLicense;
    }

    public String getLicenseCategory() {
        return licenseCategory;
    }

    public void setId(int id) {
        this.id = id;
    }

}
