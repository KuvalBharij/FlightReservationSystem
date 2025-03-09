import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class FlightService {

    // ✅ Add a new flight to the database
    public void addFlight(Flight flight) {
        String query = "INSERT INTO flights (flight_number, airline, source, destination, total_seats, price) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, flight.getFlightNumber());
            stmt.setString(2, flight.getAirline());
            stmt.setString(3, flight.getSource());
            stmt.setString(4, flight.getDestination());
            stmt.setInt(5, flight.getTotalSeats());
            stmt.setDouble(6, flight.getPrice());

            int rowsInserted = stmt.executeUpdate();
            System.out.println(rowsInserted > 0 ? "✅ Flight " + flight.getFlightNumber() + " added successfully!" : "❌ Failed to add flight.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ✅ Remove a flight from the database
    public boolean removeFlight(String flightNumber) {
        String query = "DELETE FROM flights WHERE flight_number = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, flightNumber);
            int rowsDeleted = stmt.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("✅ Flight " + flightNumber + " removed successfully.");
                return true;
            } else {
                System.out.println("❌ Flight not found!");
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ✅ Get flight details from the database
    public Flight getFlight(String flightNumber) {
        String query = "SELECT * FROM flights WHERE flight_number = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, flightNumber);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Flight(
                        rs.getString("flight_number"),
                        rs.getString("airline"),
                        rs.getString("source"),
                        rs.getString("destination"),
                        rs.getInt("total_seats"),
                        rs.getDouble("price")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null; // Flight not found
    }

    // ✅ Display all flights in the database
    public void displayFlights() {
        String query = "SELECT * FROM flights";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("\n===============================================");
            System.out.println("✈️  Available Flights");
            System.out.println("===============================================");
            System.out.printf("%-10s %-15s %-10s %-10s %-5s %-7s\n",
                    "Flight#", "Airline", "Source", "Destination", "Seats", "Price");
            System.out.println("------------------------------------------------");

            while (rs.next()) {
                System.out.printf("%-10s %-15s %-10s %-10s %-5d $%-7.2f\n",
                        rs.getString("flight_number"),
                        rs.getString("airline"),
                        rs.getString("source"),
                        rs.getString("destination"),
                        rs.getInt("total_seats"),
                        rs.getDouble("price"));
            }
            System.out.println("===============================================\n");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ✅ Get all flights as a list
    public List<Flight> getAllFlights() {
        List<Flight> flights = new ArrayList<>();
        String query = "SELECT * FROM flights";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                flights.add(new Flight(
                        rs.getString("flight_number"),
                        rs.getString("airline"),
                        rs.getString("source"),
                        rs.getString("destination"),
                        rs.getInt("total_seats"),
                        rs.getDouble("price")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flights;
    }
}
