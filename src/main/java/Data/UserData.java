package Data;

import java.io.Serializable;
import java.sql.Date;

public class UserData implements Serializable{
    private String name;
    private String lastName;
    private String mail;
    private Date birthDate;
    private Date registerDate;
    private String password;
    private String phoneNumber;
    private String gender;

    public UserData( String name, String lastName, String mail, Date birthDate, Date registerDate, String password, String phoneNumber, String gender) {
        this.name = name;
        this.lastName = lastName;
        this.mail = mail;
        this.birthDate = birthDate;
        this.registerDate = registerDate;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMail() {
        return mail;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public Date getRegisterDate(){return registerDate;}

    public String getPassword(){return password;}
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getGender(){return gender;}

    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
