import java.util.HashMap;
import java.util.Map;

public class PassengerService {
    private static final Map<String, Passenger> passengerDatabase = new HashMap<>();

    public static Passenger login(String email, String password) {
        for (Passenger passenger : passengerDatabase.values()) {
            if (passenger.getEmail().equals(email) && passenger.getPassword().equals(password)) {
                return passenger; // Login successful
            }
        }
        System.out.println("❌ Invalid email or password!");
        return null;
    }

    public static Passenger register(String userId, String name, String email, String password, String passportNumber) {
        if (passengerDatabase.containsKey(userId)) {
            System.out.println("❌ User ID already exists! Try a different one.");
            return null;
        }

        Passenger newPassenger = new Passenger(userId, name, email, password, passportNumber);
        passengerDatabase.put(userId, newPassenger);
        System.out.println("✅ Registration successful! You can now log in.");
        return newPassenger;
    }
}
