package app.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    private static final String URL = "jdbc:sqlite:LOGGING_CLAIM_DATABASE.db";

    public static Connection connect() {
        Connection conn = null;
        try {
            // Explicitly load the SQLite JDBC driver
            Class.forName("org.sqlite.JDBC");

            conn = DriverManager.getConnection(URL);
            if (conn != null) {
                try (Statement stmt = conn.createStatement()) {
                    stmt.execute("PRAGMA foreign_keys = ON;");
                    String createTableSQL = "CREATE TABLE IF NOT EXISTS baggage (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "passenger_name TEXT NOT NULL, " +
                            "flight_no TEXT NOT NULL, " +
                            "weight_kg REAL NOT NULL, " +
                            "status TEXT NOT NULL);";
                    stmt.execute(createTableSQL);
                }
            }
        } catch (ClassNotFoundException e) {
            System.out.println("SQLite Driver class not found: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Database connection error: " + e.getMessage());
        }
        return conn;
    }
}