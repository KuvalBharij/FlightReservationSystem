import java.util.HashMap;
import java.util.Map;

public class AdminService {
    private static final Map<String, String> adminDatabase = new HashMap<>();

    static {
        // Admin userId → [name, email, password]
        adminDatabase.put("A001", "Admin One,admin@example.com,admin123");
        adminDatabase.put("A002", "Manager One,manager@example.com,manager123");
    }

    public static Admin login(String email, String password) {
        for (Map.Entry<String, String> entry : adminDatabase.entrySet()) {
            String[] adminData = entry.getValue().split(",");
            String name = adminData[0];
            String adminEmail = adminData[1];
            String adminPassword = adminData[2];

            if (adminEmail.equals(email) && adminPassword.equals(password)) {
                return new Admin(entry.getKey(), name, adminEmail, adminPassword);
            }
        }
        return null; // Login failed
    }
}
