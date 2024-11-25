package com.example.food_delivery_system;

import com.dlsc.formsfx.model.structure.PasswordField;
import com.example.Services.Util;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class AdminDashboardController {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private ComboBox<String> roleComboBox;

    @FXML
    private Label statusLabel;

    private Util util = new Util();


    @FXML
    private void goToManageUsers() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ManageUsers.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) Stage.getWindows().filtered(Window::isShowing).get(0);
            stage.setScene(new Scene(root));
            stage.setTitle("Manage Users");
        } catch (IOException e) {
            e.printStackTrace();
            statusLabel.setText("Error: Unable to navigate to Manage Users page.");
        }
    }


    @FXML
    private void goToManageRestaurants() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ManageRestaurants.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) Stage.getWindows().filtered(Window::isShowing).get(0);
            stage.setScene(new Scene(root));
            stage.setTitle("Manage Restaurants");
        } catch (IOException e) {
            e.printStackTrace();
            statusLabel.setText("Error: Unable to navigate to Manage Restaurant page.");
        }
    }


    @FXML
    private void handleTrackOrders() {
        System.out.println("Track Orders clicked.");
        // Implement logic to track orders and delivery status
    }

    @FXML
    private void handleAssignRoles() {
        System.out.println("Assign Roles clicked.");
        // Implement logic to assign roles and permissions
    }

    @FXML
    private void handleGenerateReports() {
        System.out.println("Generate Reports clicked.");
        // Implement logic to generate reports on order statistics and revenue
    }

    @FXML
    private void backToLogin() {
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
