package com.example.food_delivery_system;

import com.example.Services.DBConnector;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Label;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CustomerController {

    @FXML
    private ListView<String> foodListView;
    @FXML
    private ListView<String> orderStatusListView;
    @FXML
    private TextField quantityField;
    @FXML
    private TextField paymentField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private Button addToCartButton;
    @FXML
    private Button payNowButton;
    @FXML
    private Button viewOrderHistoryButton;
    @FXML
    private Button updateAccountButton;
    @FXML
    private ProgressBar orderProgress;

    @FXML
    public void initialize() {

        foodListView.setItems(FXCollections.observableArrayList("Pizza", "Burger", "Pasta", "Salad"));
        orderStatusListView.setItems(FXCollections.observableArrayList("Order Placed", "Preparing", "Out for Delivery", "Delivered"));
    }

    @FXML
    private void handleAddToCart() {
        String quantity = quantityField.getText();
        String selectedFood = foodListView.getSelectionModel().getSelectedItem();
        if (selectedFood != null && !quantity.isEmpty()) {
            System.out.println("Added " + quantity + " of " + selectedFood + " to the cart.");


            addToCartInDatabase(selectedFood, Integer.parseInt(quantity));
        }
    }


    private void addToCartInDatabase(String foodName, int quantity) {
        String sql = "INSERT INTO orders (food_name, quantity) VALUES (?, ?)";
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, foodName);
            pst.setInt(2, quantity);
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Order added to database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handlePayment() {
        String paymentAmount = paymentField.getText();
        if (!paymentAmount.isEmpty()) {
            System.out.println("Payment of " + paymentAmount + " processed.");


            processPaymentInDatabase(Double.parseDouble(paymentAmount));
        }
    }

    private void processPaymentInDatabase(double paymentAmount) {
        String sql = "INSERT INTO payments (amount) VALUES (?)";
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setDouble(1, paymentAmount);
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Payment processed in database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleUpdateAccount() {
        String name = nameField.getText();
        String email = emailField.getText();
        if (!name.isEmpty() && !email.isEmpty()) {
            System.out.println("Updated account with Name: " + name + ", Email: " + email);


            updateAccountInDatabase(name, email);
        }
    }

    private void updateAccountInDatabase(String name, String email) {
        String sql = "UPDATE users SET name = ?, email = ? WHERE email = ?";
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, name);
            pst.setString(2, email);
            pst.setString(3, email);
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User account updated in the database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleViewOrderHistory() {
        System.out.println("Viewing order history...");
    }
}
