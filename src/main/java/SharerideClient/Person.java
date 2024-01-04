package SharerideClient;

public class Person {
    private String name;
    private String lastName;
    private String mail;
    private int age;
    private String phoneNumber;
    private int ridesCount;
    private double avgRating;

    public Person(String name, String lastName, String mail, int age, String phoneNumber, int ridesCount, double avgRating) {
        this.name = name;
        this.lastName = lastName;
        this.mail = mail;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.ridesCount = ridesCount;
        this.avgRating = avgRating;
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

    public int getAge() {
        return age;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getRidesCount() {
        return ridesCount;
    }

    public double getAvgRating() {
        return avgRating;
    }
}
