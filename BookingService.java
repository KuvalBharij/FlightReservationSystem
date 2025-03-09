import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class BookingService {

    public boolean bookFlight(String passengerId, String flightNumber, String seatNumber) {
        // ✅ Step 1: Check if seat is already booked
        if (isSeatBooked(flightNumber, seatNumber)) {
            System.out.println("❌ Seat " + seatNumber + " is already booked! Choose another.");
            return false;
        }

        // ✅ Step 2: Check if there are available seats
        if (!isSeatAvailable(flightNumber)) {
            System.out.println("❌ Booking failed! No available seats on this flight.");
            return false;
        }

        // ✅ Step 3: Insert booking into database
        String query = "INSERT INTO bookings (booking_id, passenger_id, flight_number, seat_number) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // 🔹 Generate unique booking ID
            String bookingId = UUID.randomUUID().toString().substring(0, 8);

            stmt.setString(1, bookingId);
            stmt.setString(2, passengerId);
            stmt.setString(3, flightNumber);
            stmt.setString(4, seatNumber);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("✅ Booking successful! Seat " + seatNumber + " is confirmed.");
                updateAvailableSeats(flightNumber);  // Update available seats after booking
                return true;
            }
        } catch (SQLException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
        return false;
    }

    // ✅ Check if a specific seat is already booked
    private boolean isSeatBooked(String flightNumber, String seatNumber) {
        String query = "SELECT * FROM bookings WHERE flight_number = ? AND seat_number = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, flightNumber);
            stmt.setString(2, seatNumber);

            ResultSet rs = stmt.executeQuery();
            return rs.next();  // If a record exists, seat is already booked

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // ✅ Check if there are available seats on the flight
    private boolean isSeatAvailable(String flightNumber) {
        String query = "SELECT available_seats FROM flights WHERE flight_number = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, flightNumber);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("available_seats") > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // ✅ Update available seats count after successful booking
    private void updateAvailableSeats(String flightNumber) {
        String query = "UPDATE flights SET available_seats = available_seats - 1 WHERE flight_number = ? AND available_seats > 0";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, flightNumber);
            stmt.executeUpdate();  // Reduce available seats by 1

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
