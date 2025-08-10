package src;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe"; // or use your DB service name
    private static final String USER = "your_username"; // e.g., "system"
    private static final String PASSWORD = "your_password";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // ✅ Test method
    public static void main(String[] args) {
        try (Connection conn = getConnection()) {
            System.out.println("✅ Connected to Oracle DB!");
        } catch (SQLException e) {
            System.out.println("❌ Failed to connect:");
            e.printStackTrace();
        }
    }
}
