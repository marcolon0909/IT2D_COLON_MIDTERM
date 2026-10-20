package app.dao;

import app.model.ScenarioEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ScenarioDao {

    public boolean insertRecord(String passengerName, String flightNo, double weightKg, String status) {
        String sql = "INSERT INTO baggage(passenger_name, flight_no, weight_kg, status) VALUES(?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, passengerName);
            pstmt.setString(2, flightNo);
            pstmt.setDouble(3, weightKg);
            pstmt.setString(4, status);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error inserting record: " + e.getMessage());
            return false;
        }
    }

    public List<ScenarioEntity> getAllRecords() {
        List<ScenarioEntity> list = new ArrayList<>();
        String sql = "SELECT id, passenger_name, flight_no, weight_kg, status FROM baggage";
        try (Connection conn = DatabaseConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new ScenarioEntity(
                        rs.getInt("id"),
                        rs.getString("passenger_name"),
                        rs.getString("flight_no"),
                        rs.getDouble("weight_kg"),
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching records: " + e.getMessage());
        }
        return list;
    }
}