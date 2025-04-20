// DBConnection.java

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // Database connection details
    private static final String URL = "jdbc:mysql://localhost:3306/student_db"; // URL to the MySQL database
    private static final String USER = "root";  // Database username
    private static final String PASSWORD = "tommietom"; // Database password

    // Static method to establish and return a connection to the database
    public static Connection getConnection() throws SQLException {
        // Establish a connection to the database using the provided URL, USER, and PASSWORD
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
