package com.example.food_delivery_system;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import com.example.Services.Util;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.sql.Date;
import java.util.Map;

public class GenerateReportsController {

    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private Label totalRevenueLabel;
    @FXML
    private Label totalOrdersLabel;
    @FXML
    private Label reportStatusLabel;

    private Util util = new Util();

    @FXML
    public void generateReport() {
        // Retrieve the start and end date values from the DatePickers
        Date startDate = Date.valueOf(startDatePicker.getValue());
        Date endDate = Date.valueOf(endDatePicker.getValue());

        // Generate the reports by querying the orders table
        Map<String, Double> revenueReport = util.generateRevenueReport(startDate, endDate);
        Map<String, Integer> orderStats = util.generateOrderStatistics(startDate, endDate);

        // Update the labels with the results
        totalRevenueLabel.setText("Total Revenue: $" + revenueReport.getOrDefault("Total Revenue", 0.0));
        totalOrdersLabel.setText("Total Orders: " + orderStats.getOrDefault("Total Orders", 0));
        reportStatusLabel.setText("Report generated successfully.");
    }
    @FXML
    private void backToAdminDashboard() {
        try {
            // Load the Login Screen FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminDashboard.fxml"));
            Parent loginRoot = loader.load();

            // Get the current stage and set the new scene
            Stage stage = (Stage) Stage.getWindows().filtered(Window::isShowing).get(0);
            stage.setScene(new Scene(loginRoot));
            stage.setTitle("Admin Dashboard");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
