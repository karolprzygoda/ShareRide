package Data;

import java.io.Serializable;

public class RatingData implements Serializable {

    private int id;
    private int user_id;
    private int rater_id;
    private int announcement_id;
    private int rating;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getRater_id() {
        return rater_id;
    }

    public void setRater_id(int rater_id) {
        this.rater_id = rater_id;
    }

    public int getAnnouncement_id() {
        return announcement_id;
    }

    public void setAnnouncement_id(int announcement_id) {
        this.announcement_id = announcement_id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
