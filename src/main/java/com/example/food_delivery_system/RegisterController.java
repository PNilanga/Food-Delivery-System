package com.example.food_delivery_system;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.sql.*;

public class RegisterController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label statusLabel;

    // Database credentials
    private static final String DB_URL = "jdbc:mysql://sql5.freesqldatabase.com:3306/sql5745261";
    private static final String DB_USERNAME = "sql5745261";
    private static final String DB_PASSWORD = "HBZZTBcHmm";

    // Store the role of the currently logged-in user
    private String currentUserRole;

    // Establish a connection to the database
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
    }


    // Handle Register Button Click
    @FXML
    private void handleRegister() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        String role = "Customer"; // Default role for new users

        if (username.isEmpty() || password.isEmpty()) {
            statusLabel.setText("All fields are required.");
            return;
        }

        try (Connection connection = getConnection()) {
            // Check if user already exists
            String checkQuery = "SELECT COUNT(*) FROM users WHERE username = ?";
            PreparedStatement checkStmt = connection.prepareStatement(checkQuery);
            checkStmt.setString(1, username);
            ResultSet rs = checkStmt.executeQuery();
            rs.next();

            if (rs.getInt(1) > 0) {
                statusLabel.setText("User already exists. Try logging in.");
                return;
            }

            // Register new user with the role "Customer"
            String insertQuery = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
            PreparedStatement insertStmt = connection.prepareStatement(insertQuery);
            insertStmt.setString(1, username);
            insertStmt.setString(2, password);
            insertStmt.setString(3, role); // Ensure role is explicitly set to "Customer"
            insertStmt.executeUpdate();

            statusLabel.setText("Registration successful! Please login.");
        } catch (SQLException e) {
            e.printStackTrace();
            statusLabel.setText("Error: Unable to register user.");
        }
    }

    @FXML
    private void goBackToLogin() {
        try {
            // Load the Login Screen FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RegisterOrLogin.fxml"));
            Parent loginRoot = loader.load();

            // Get the current stage and set the new scene
            Stage stage = (Stage) Stage.getWindows().filtered(Window::isShowing).get(0);
            stage.setScene(new Scene(loginRoot));
            stage.setTitle("Login Page");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
