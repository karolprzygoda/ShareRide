package Data;

import java.io.Serializable;
import java.sql.Date;

public class AnnouncementsData implements Serializable {
    private int id;

    private int driverId;
    private String startingStation;
    private String destination;
    private Date DateOfAddAnnouncement;
    private Date departureDate;
    private int seatsAvailable;

    public AnnouncementsData(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStartingStation() {
        return startingStation;
    }

    public void setStartingStation(String startingStation) {
        this.startingStation = startingStation;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Date getDateOfAddAnnouncement() {
        return DateOfAddAnnouncement;
    }

    public void setDateOfAddAnnouncement(Date dateOfAddAnnouncement) {
        DateOfAddAnnouncement = dateOfAddAnnouncement;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public int getSeatsAvailable() {
        return seatsAvailable;
    }

    public void setSeatsAvailable(int seatsAvailable) {
        this.seatsAvailable = seatsAvailable;
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

}
