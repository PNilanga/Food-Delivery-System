package com.example.food_delivery_system;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class RestaurantOwnerDashboardController {

    @FXML
    private void goToManageMenu() {
        navigateTo("ManageMenu.fxml", "Manage Food Menu");
    }

    @FXML
    private void processIncomingOrders() {
        navigateTo("ProcessOrders.fxml", "Process Incoming Orders");
    }

    @FXML
    private void trackOrderHistory() {
        navigateTo("OrderHistory.fxml", "Order History & Revenue");
    }

  /*  @FXML
    private void updateProfile() {
        navigateTo("UpdateProfile.fxml", "Update Profile & Business Details");
    }*/

    @FXML
    private void logout() {
        try {
            // Navigate back to the login page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RegisterOrLogin.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) Stage.getWindows().filtered(Window::isShowing).get(0);
            stage.setScene(new Scene(root));
            stage.setTitle("Login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void navigateTo(String fxmlFile, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();

            Stage stage = (Stage) Stage.getWindows().filtered(Window::isShowing).get(0);
            stage.setScene(new Scene(root));
            stage.setTitle(title);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
