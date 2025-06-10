import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


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
        String query = "INSERT INTO passengers ( passenger_id, name , email, password, passport_number) VALUES (?, ?, ?, ?,?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // 🔹 Generate unique booking ID
            String bookingId = UUID.randomUUID().toString().substring(0, 8);

            stmt.setString(1, userId);
            stmt.setString(2, name);
            stmt.setString(3, email);
            stmt.setString(4, password);
            stmt.setString(5, passportNumber);


            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("✅ user  successfully registered ! Seat " + userId + " is confirmed.");
                //updateAvailableSeats(flightNumber);  // Update available seats after booking
                //return true;
            }
            System.out.println("✅ Registration successful! You can now log in.");
            return newPassenger;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }}
    }
