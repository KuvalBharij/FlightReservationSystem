import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/FlightReservationDB";
    private static final String USER = "root"; // Change to your MySQL username
    private static final String PASSWORD = "KUVALBHARIJ24"; // Change to your MySQL password

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Database connection failed!");
        }
    }
}
