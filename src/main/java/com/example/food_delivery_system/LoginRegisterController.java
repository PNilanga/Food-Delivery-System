package com.example.food_delivery_system;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginRegisterController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label statusLabel;
    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;
    @FXML
    private ComboBox<String> roleComboBox; // Dropdown for selecting the role

    // Database credentials
    private static final String DB_URL = "jdbc:mysql://sql5.freesqldatabase.com:3306/sql5745261";
    private static final String DB_USERNAME = "sql5745261";
    private static final String DB_PASSWORD = "HBZZTBcHmm";


    // Establish a connection to the database
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
    }

    @FXML
    public void initialize() {
        // Initialize the role dropdown with values
        roleComboBox.getItems().addAll("Admin", "Customer", "Restaurant Owner");
    }

    // Handle Login Button Click
    @FXML
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        String selectedRole = roleComboBox.getValue(); // Get the selected role from the dropdown


        if (username.isEmpty() || password.isEmpty()) {
            statusLabel.setText("Fields cannot be empty.");
            return;
        }

        try (Connection connection = getConnection()) {
            // Check credentials and role
            String loginQuery = "SELECT role FROM users WHERE username = ? AND password = ?";
            PreparedStatement loginStmt = connection.prepareStatement(loginQuery);
            loginStmt.setString(1, username);
            loginStmt.setString(2, password);
            ResultSet rs = loginStmt.executeQuery();

            if (rs.next()) {
                String userRole = rs.getString("role");

                if (userRole.equalsIgnoreCase(selectedRole)) {
                    statusLabel.setText("Login successful! Welcome " + username + " (" + userRole + ").");
                    statusLabel.setStyle("-fx-text-fill: green;");
                    navigateToDashboard(userRole);
                } else {
                    statusLabel.setText("Role mismatch. Please select the correct role.");
                }
            } else {
                statusLabel.setText("Invalid credentials. Try again.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            statusLabel.setText("Error: Unable to login.");
        }
    }
    private void navigateToDashboard(String role) {
        try {
            FXMLLoader loader;
            Parent root;

            // Load different dashboards based on the user's role
            switch (role.toLowerCase()) {
                case "admin":
                    loader = new FXMLLoader(getClass().getResource("AdminDashboard.fxml"));
                    root = loader.load();

                    // Access the AdminDashboardController to handle navigation within admin options
                    AdminDashboardController adminController = loader.getController();
                    break;

                case "customer":
                    loader = new FXMLLoader(getClass().getResource("customer.fxml"));
                    root = loader.load();
                    break;

                case "restaurant owner":
                    loader = new FXMLLoader(getClass().getResource("RestaurantOwnerDashboard.fxml"));
                    root = loader.load();
                    break;

                default:
                    throw new IllegalStateException("Unexpected value: " + role);
            }

            // Set the new scene for the appropriate dashboard
            Stage stage = (Stage) Stage.getWindows().filtered(Window::isShowing).get(0);
            stage.setScene(new Scene(root));
            stage.setTitle(role + " Dashboard");
        } catch (IOException e) {
            e.printStackTrace();
            statusLabel.setText("Error: Unable to navigate to the dashboard.");
        }
    }
    @FXML
    private void goToRegister() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("register.fxml"));
            Parent registerRoot = loader.load();

            RegisterController registerController = loader.getController();

            Stage stage = (Stage) Stage.getWindows().filtered(Window::isShowing).get(0);
            stage.setScene(new Scene(registerRoot));
            stage.setTitle("User Registration");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

