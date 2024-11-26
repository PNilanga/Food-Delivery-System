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
import java.util.Date;
import java.util.Map;

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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TrackOrder.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) Stage.getWindows().filtered(Window::isShowing).get(0);
            stage.setScene(new Scene(root));
            stage.setTitle("Track Orders");
        } catch (IOException e) {
            e.printStackTrace();
            statusLabel.setText("Error: Unable to navigate to Track Orders page.");
        }
    }

    @FXML
    private void handleAssignRoles() {
        System.out.println("Assign Roles clicked.");
        // Implement logic to assign roles and permissions
    }

    @FXML
    private void handleGenerateReports() {
        try {
            // Load the Generate Reports Screen FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("GenerateReport.fxml"));
            Parent reportRoot = loader.load();

            // Get the current stage and set the new scene
            Stage stage = (Stage) Stage.getWindows().filtered(Window::isShowing).get(0);
            stage.setScene(new Scene(reportRoot));
            stage.setTitle("Generate Reports");
        } catch (IOException e) {
            e.printStackTrace();
            statusLabel.setText("Error: Unable to navigate to Generate Reports page.");
        }
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
