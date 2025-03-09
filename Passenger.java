public class Passenger extends User {
    private String passportNumber;

    public Passenger(String userId, String name, String email, String password, String passportNumber) {
        super(userId, name, email, password);
        this.passportNumber = passportNumber;
    }

    public String getPassportNumber() {
        return passportNumber;
    }
}
