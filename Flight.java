import java.util.HashSet;
import java.util.Set;

public class Flight {
    private String flightNumber;
    private String airline;
    private String source;
    private String destination;
    private int totalSeats;
    private int availableSeats;
    private double price;
    private Set<String> bookedSeats;

    // ✅ Constructor
    public Flight(String flightNumber, String airline, String source, String destination, int totalSeats, double price) {
        this.flightNumber = flightNumber;
        this.airline = airline;
        this.source = source;
        this.destination = destination;
        this.totalSeats = totalSeats;
        this.availableSeats = totalSeats; // Initially, all seats are available
        this.price = price;
        this.bookedSeats = new HashSet<>();
    }

    // ✅ Getters
    public String getFlightNumber() {
        return flightNumber;
    }

    public String getAirline() {
        return airline;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public double getPrice() {
        return price;
    }

    // ✅ Display available seats
    public void displayAvailableSeats() {
        System.out.println("\n===================================");
        System.out.println("💺 Available Seats for Flight " + flightNumber);
        System.out.println("===================================");
        System.out.println("Total Seats    : " + totalSeats);
        System.out.println("Available Seats: " + availableSeats);
        System.out.println("===================================\n");
    }

    // ✅ Book a seat
    public boolean bookSeat(String seatNumber) {
        if (bookedSeats.contains(seatNumber)) {
            return false; // Seat is already booked
        }
        bookedSeats.add(seatNumber);
        availableSeats--; // Reduce available seats
        return true;
    }

    // ✅ Cancel a booked seat
    public boolean cancelBooking(String seatNumber) {
        if (bookedSeats.contains(seatNumber)) {
            bookedSeats.remove(seatNumber);
            availableSeats++; // Increase available seats
            return true;
        }
        return false; // Seat wasn't booked
    }
}
