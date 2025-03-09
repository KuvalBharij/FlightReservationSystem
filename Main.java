import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        FlightService flightService = new FlightService();
        BookingService bookingService = new BookingService();

        System.out.println("===================================");
        System.out.println("      ✈️ Flight Reservation System ✈️");
        System.out.println("===================================");

        while (true) {
            System.out.println("\n1️⃣  Login as Passenger");
            System.out.println("2️⃣  Login as Admin");
            System.out.println("3️⃣  Exit");
            System.out.print("👉 Enter your choice: ");

            if (!scanner.hasNextInt()) {
                System.out.println("❌ Invalid input! Please enter a number.");
                scanner.next();
                continue;
            }

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    loginAsPassenger(scanner, flightService, bookingService);
                    break;
                case 2:
                    loginAsAdmin(scanner, flightService);
                    break;
                case 3:
                    System.out.println("\nThank you for using the Flight Reservation System. Goodbye! ✈️");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("❌ Invalid choice! Try again.");
            }
        }
    }

    private static void loginAsPassenger(Scanner scanner, FlightService flightService, BookingService bookingService) {
        System.out.println("\n1️⃣ Login");
        System.out.println("2️⃣ Register");
        System.out.print("👉 Enter your choice: ");

        if (!scanner.hasNextInt()) {
            System.out.println("❌ Invalid input! Please enter a number.");
            scanner.next();
            return;
        }

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (choice == 1) { // **Login Flow**
            System.out.print("📧 Enter Email: ");
            String email = scanner.nextLine().trim();
            System.out.print("🔒 Enter Password: ");
            String password = scanner.nextLine().trim();

            Passenger passenger = PassengerService.login(email, password);
            if (passenger != null) {
                handlePassengerMenu(passenger, flightService, bookingService, scanner);
            } else {
                System.out.println("❌ Login failed! Invalid credentials.");
            }
        } else if (choice == 2) { // **Registration Flow**
            System.out.print("🆔 Enter User ID: ");
            String userId = scanner.nextLine().trim();
            System.out.print("👤 Enter Name: ");
            String name = scanner.nextLine().trim();
            System.out.print("📧 Enter Email: ");
            String email = scanner.nextLine().trim();
            System.out.print("🔒 Enter Password: ");
            String password = scanner.nextLine().trim();
            System.out.print("🛂 Enter Passport Number: ");
            String passport = scanner.nextLine().trim();

            Passenger newPassenger = PassengerService.register(userId, name, email, password, passport);
            if (newPassenger != null) {
                System.out.println("✅ Please log in now.");
            }
        } else {
            System.out.println("❌ Invalid choice!");
        }
    }

    private static void loginAsAdmin(Scanner scanner, FlightService flightService) {
        System.out.print("📧 Enter Admin Email: ");
        String adminEmail = scanner.nextLine().trim();
        System.out.print("🔒 Enter Password: ");
        String adminPassword = scanner.nextLine().trim();

        Admin admin = AdminService.login(adminEmail, adminPassword);
        if (admin != null) {
            System.out.println("✅ Admin login successful!");
            handleAdminMenu(admin, flightService, scanner);
        } else {
            System.out.println("❌ Login failed! Invalid credentials.");
        }
    }

    private static void handlePassengerMenu(Passenger passenger, FlightService flightService, BookingService bookingService, Scanner scanner) {
        while (true) {
            System.out.println("\n1️⃣  View Available Flights");
            System.out.println("2️⃣  Book a Flight");
            System.out.println("3️⃣  Logout");
            System.out.print("👉 Enter your choice: ");

            if (!scanner.hasNextInt()) {
                System.out.println("❌ Invalid input! Please enter a number.");
                scanner.next();
                continue;
            }

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    flightService.displayFlights();
                    break;
                case 2:
                    System.out.print("✈️ Enter Flight Number: ");
                    String flightNum = scanner.nextLine().trim();
                    Flight selectedFlight = flightService.getFlight(flightNum);

                    if (selectedFlight == null) {
                        System.out.println("❌ Flight not found!");
                        break;
                    }

                    // Show available seats before booking
                    selectedFlight.displayAvailableSeats();

                    String seatNum;
                    while (true) {
                        System.out.print("💺 Enter Seat Number (e.g., A12, B5, etc.): ");
                        seatNum = scanner.nextLine().trim();

                        // Validate seat format
                        if (!seatNum.matches("[A-J]\\d{1,2}")) {
                            System.out.println("❌ Invalid format! Please enter a valid seat (A1-J10).");
                        } else if (!selectedFlight.bookSeat(seatNum)) {
                            System.out.println("❌ Seat is already booked! Choose another.");
                        } else {
                            break; // Valid seat, exit loop
                        }
                    }

                    boolean bookingSuccess = bookingService.bookFlight(passenger.getUserId(), selectedFlight.getFlightNumber(), seatNum);
                    System.out.println(bookingSuccess ? "✅ Booking successful!" : "❌ Booking failed! Seat may be taken.");
                    break;
                case 3:
                    System.out.println("🔒 Logging out...");
                    return;
                default:
                    System.out.println("❌ Invalid choice!");
            }
        }
    }


    private static void handleAdminMenu(Admin admin, FlightService flightService, Scanner scanner) {
        while (true) {
            System.out.println("\n1️⃣  Add Flight");
            System.out.println("2️⃣  Remove Flight");
            System.out.println("3️⃣  Logout");
            System.out.print("👉 Enter your choice: ");

            if (!scanner.hasNextInt()) {
                System.out.println("❌ Invalid input! Please enter a number.");
                scanner.next();
                continue;
            }

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addFlight(scanner, flightService);
                    break;
                case 2:
                    removeFlight(scanner, flightService);
                    break;
                case 3:
                    System.out.println("🔒 Logging out...");
                    return;
                default:
                    System.out.println("❌ Invalid choice!");
            }
        }
    }

    private static void addFlight(Scanner scanner, FlightService flightService) {
        System.out.print("✈️ Enter Flight Number: ");
        String flightNum = scanner.nextLine().trim();
        System.out.print("🛫 Enter Airline: ");
        String airline = scanner.nextLine().trim();
        System.out.print("🌍 Enter Source: ");
        String source = scanner.nextLine().trim();
        System.out.print("🌎 Enter Destination: ");
        String destination = scanner.nextLine().trim();
        System.out.print("💺 Enter Total Seats: ");
        int totalSeats = scanner.nextInt();
        System.out.print("💰 Enter Price: ");
        double price = scanner.nextDouble();
        scanner.nextLine();

        Flight newFlight = new Flight(flightNum, airline, source, destination, totalSeats, price);

        flightService.addFlight(newFlight);
        System.out.println("✅ Flight added successfully!");
    }

    private static void removeFlight(Scanner scanner, FlightService flightService) {
        System.out.print("🗑️ Enter Flight Number to remove: ");
        String removeFlightNum = scanner.nextLine().trim();
        boolean removed = flightService.removeFlight(removeFlightNum);
        System.out.println(removed ? "✅ Flight removed successfully!" : "❌ Flight not found!");
    }
}
