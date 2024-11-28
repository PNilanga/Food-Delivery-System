package com.example.food_delivery_system;

import com.example.Services.FoodItem;
import com.example.Services.Util;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDashboardController {

    @FXML
    private TableView<FoodItem> menuItemsTable;
    @FXML
    private TableColumn<FoodItem, Integer> menuItemIDColumn;
    @FXML
    private TableColumn<FoodItem, String> nameColumn;
    @FXML
    private TableColumn<FoodItem, Double> priceColumn;

    @FXML
    private ComboBox<String> restaurantDropdown;
    @FXML
    private ListView<String> selectedItemsList;
    @FXML
    private Label totalAmountLabel;
    @FXML
    private Label statusLabel;

    private ObservableList<FoodItem> foodItems = FXCollections.observableArrayList();
    private ObservableList<String> selectedItems = FXCollections.observableArrayList();
    private ObservableList<String> restaurantList = FXCollections.observableArrayList();
    private double totalAmount = 0.0;

    private Util util = new Util();

    @FXML
    public void initialize() {
        // Set up table columns
        menuItemIDColumn.setCellValueFactory(data -> data.getValue().foodIdProperty().asObject());
        nameColumn.setCellValueFactory(data -> data.getValue().foodNameProperty());
        priceColumn.setCellValueFactory(data -> data.getValue().priceProperty().asObject());

        // Load restaurant dropdown
        loadRestaurants();

        // Add listener for restaurant selection
        restaurantDropdown.setOnAction(event -> loadFoodItems());

        // Set table data
        menuItemsTable.setItems(foodItems);

        // Bind selected items list
        selectedItemsList.setItems(selectedItems);

        // Add listener for table row selection
        menuItemsTable.setOnMouseClicked(this::handleFoodItemSelection);
    }

    private void loadRestaurants() {
        try {
            // Query the restaurants table
            ResultSet rs = util.getData("restaurants");
            while (rs.next()) {
                restaurantList.add(rs.getString("restaurantId")+" "+rs.getString("name"));
            }
            restaurantDropdown.setItems(restaurantList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadFoodItems() {
        foodItems.clear(); // Clear existing food items
        String selectedRestaurant = restaurantDropdown.getValue();
        if (selectedRestaurant == null) {
            statusLabel.setText("Please select a restaurant to view menu items.");
            return;
        }
        try {
            // Query menuItems table based on the selected restaurant
            ResultSet rs = util.getData("menuItems");
            while (rs.next()) {
                if (rs.getString("restaurantId").equals(selectedRestaurant.split(" ")[0]) && rs.getBoolean("availability")) {
                    foodItems.add(new FoodItem(
                            rs.getInt("menuItemId"),
                            rs.getString("name"),
                            rs.getDouble("price")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void handleFoodItemSelection(MouseEvent event) {
        FoodItem selectedFood = menuItemsTable.getSelectionModel().getSelectedItem();
        if (selectedFood != null && !selectedItems.contains(selectedFood.getFoodName())) {
            selectedItems.add(selectedFood.getFoodName());
            totalAmount += selectedFood.getPrice();
            totalAmountLabel.setText("$" + String.format("%.2f", totalAmount));
        }
    }

    @FXML
    private void handlePlaceOrder() {
        if (selectedItems.isEmpty()) {
            statusLabel.setText("Please select items to place an order.");
            return;
        }

        String selectedRestaurant = restaurantDropdown.getValue();
        if (selectedRestaurant == null) {
            statusLabel.setText("Please select a restaurant before placing the order.");
            return;
        }

        // Place order logic
        Object[] values = {null, 1, selectedRestaurant, totalAmount, "Pending", new java.sql.Date(System.currentTimeMillis())};
        int result = util.insert("orders", "orderId, customerName, restaurant, totalAmount, status, orderDate", values);

        if (result > 0) {
            statusLabel.setText("Order placed successfully!");
            selectedItems.clear();
            totalAmount = 0.0;
            totalAmountLabel.setText("$0.00");
        } else {
            statusLabel.setText("Failed to place the order.");
        }
    }

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
}
