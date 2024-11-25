package com.example.food_delivery_system;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;

import java.util.ArrayList;
import java.util.List;

public class CustomerController {

    @FXML
    private ListView<String> restaurantListView;
    @FXML
    private ListView<String> foodListView;
    @FXML
    private ListView<String> orderStatusListView;
    @FXML
    private ProgressBar orderProgress;

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


    private List<String> restaurantList;
    private List<String> foodList;
    private List<String> orderStatuses;


    @FXML
    public void initialize() {

        restaurantList = new ArrayList<>();
        restaurantList.add("Restaurant 1");
        restaurantList.add("Restaurant 2");
        restaurantList.add("Restaurant 3");
        restaurantListView.getItems().setAll(restaurantList);


        foodList = new ArrayList<>();
        foodList.add("Pizza");
        foodList.add("Burger");
        foodList.add("Pasta");
        foodListView.getItems().setAll(foodList);

        orderStatuses = new ArrayList<>();
        orderStatuses.add("Preparing");
        orderStatuses.add("Out for Delivery");
        orderStatuses.add("Delivered");
        orderStatusListView.getItems().setAll(orderStatuses);


        orderProgress.setProgress(0.0);
    }


    @FXML
    private void handleAddToCart() {
        String quantity = quantityField.getText();
        String selectedFood = foodListView.getSelectionModel().getSelectedItem();

        if (selectedFood != null && !quantity.isEmpty()) {

            System.out.println("Added " + quantity + " of " + selectedFood + " to the cart.");
        }
    }


    @FXML
    private void handlePayment() {
        String paymentAmount = paymentField.getText();

        if (!paymentAmount.isEmpty()) {

            System.out.println("Payment of " + paymentAmount + " processed.");
        }
    }

    @FXML
    private void handleViewOrderHistory() {
        // View the order history
        System.out.println("Viewing order history...");
    }

    @FXML
    private void handleUpdateAccount() {
        String name = nameField.getText();
        String email = emailField.getText();

        if (!name.isEmpty() && !email.isEmpty()) {
            // Update the account details
            System.out.println("Updated account with Name: " + name + ", Email: " + email);
        }
    }


    @FXML
    private void handleTrackOrder() {
        String selectedStatus = orderStatusListView.getSelectionModel().getSelectedItem();

        if (selectedStatus != null) {
            switch (selectedStatus) {
                case "Preparing":
                    orderProgress.setProgress(0.0);
                    break;
                case "Out for Delivery":
                    orderProgress.setProgress(0.5);
                    break;
                case "Delivered":
                    orderProgress.setProgress(1.0);
                    break;
            }
        }
    }
}
