public class Booking {
    private String bookingId;
    private Passenger passenger;
    private Flight flight;
    private String seatNumber;

    public Booking(String bookingId, Passenger passenger, Flight flight, String seatNumber) {
        this.bookingId = bookingId;
        this.passenger = passenger;
        this.flight = flight;
        this.seatNumber = seatNumber;
    }

    public String getBookingId() {
        return bookingId;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public Flight getFlight() {
        return flight;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    @Override
    public String toString() {
        return "Booking ID: " + bookingId +
                "\nPassenger: " + passenger.getName() +
                "\nFlight: " + flight.getFlightNumber() + " (" + flight.getAirline() + ")" +
                "\nSeat: " + seatNumber;
    }
}
