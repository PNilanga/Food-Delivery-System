package com.example.Services;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class Util {
    DBConnector con = new DBConnector();

    private String query;
    public int insert(String tableName, String columns, Object[] values) {
        String query = generateInsertQuery(tableName, columns, values.length);
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            // Set the values in the prepared statement
            for (int i = 0; i < values.length; i++) {
                stmt.setObject(i + 1, values[i]);
            }

            // Execute the insert operation
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1; // Return -1 if an error occurs
        }
    }


    private String generateInsertQuery(String tableName, String columns, int valueCount) {
        String placeholders = String.join(", ", "?".repeat(valueCount).split(""));
        return "INSERT INTO " + tableName + " (" + columns + ") VALUES (" + placeholders + ")";
    }

    public ResultSet getData(String tableName){
        try {
            Statement stmt = DBConnector.getConnection().createStatement();
            query = "select * from " + tableName;
            return stmt.executeQuery(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int update(String tableName,String set, String where){

        try{
            Statement stmt = DBConnector.getConnection().createStatement();
            query= "UPDATE " + tableName+
                    " SET "+set+ " WHERE "+ where;
            return stmt.executeUpdate(query);
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int delete(String tableName,String condition){

        try{
            Statement stmt = DBConnector.getConnection().createStatement();
            query= "DELETE FROM "+tableName+" WHERE "+condition;
            return stmt.executeUpdate(query);
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    // Generate total revenue and total orders for the specified date range
    public Map<String, Double> generateRevenueReport(Date startDate, Date endDate) {
        Map<String, Double> report = new HashMap<>();
        String query = "SELECT SUM(totalAmount) AS total_revenue FROM orders WHERE orderDate BETWEEN ? AND ?";

        try (Connection connection = DBConnector.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setDate(1, startDate);
            stmt.setDate(2, endDate);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                double totalRevenue = rs.getDouble("total_revenue");
                report.put("Total Revenue", totalRevenue);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return report;
    }

    // Generate order statistics (total orders) for the specified date range
    public Map<String, Integer> generateOrderStatistics(Date startDate, Date endDate) {
        Map<String, Integer> stats = new HashMap<>();
        String query = "SELECT COUNT(*) AS total_orders FROM orders WHERE orderDate BETWEEN ? AND ?";

        try (Connection connection = DBConnector.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setDate(1, startDate);
            stmt.setDate(2, endDate);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int totalOrders = rs.getInt("total_orders");
                stats.put("Total Orders", totalOrders);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stats;
    }
}
