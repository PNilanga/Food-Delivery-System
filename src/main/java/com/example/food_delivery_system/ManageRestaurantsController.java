package com.example.food_delivery_system;

import com.example.Services.Util;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ManageRestaurantsController {

    @FXML
    private TextField restaurantNameField;

    @FXML
    private TextField locationField;

    @FXML
    private TextField deleteRestaurantField;

    @FXML
    private TextField updateRestaurantField;

    @FXML
    private TextField newLocationField;

    @FXML
    private Label statusLabel;

    @FXML
    private TableView<Restaurant> restaurantsTable;

    @FXML
    private TableColumn<Restaurant, String> nameColumn;

    @FXML
    private TableColumn<Restaurant, String> locationColumn;

    private Util util = new Util();

    private ObservableList<Restaurant> restaurantList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Bind TableView columns to Restaurant properties
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        locationColumn.setCellValueFactory(cellData -> cellData.getValue().locationProperty());

        // Load the initial restaurant data
        loadRestaurants();
    }

    @FXML
    private void handleAddRestaurant() {
        String name = restaurantNameField.getText().trim();
        String location = locationField.getText().trim();
        String tableName = "restaurants";
        String columns = "name, location";
        Object[] values = {name, location};

        if (name.isEmpty() || location.isEmpty()) {
            statusLabel.setText("All fields are required to add a restaurant.");
            return;
        }

        int result = util.insert(tableName, columns, values);

        if (result > 0) {
            statusLabel.setText("Restaurant added successfully.");
            loadRestaurants(); // Refresh the table to show the newly added restaurant
        } else {
            statusLabel.setText("Failed to add restaurant.");
        }
    }

    @FXML
    private void handleDeleteRestaurant() {
        String name = deleteRestaurantField.getText().trim();

        if (name.isEmpty()) {
            statusLabel.setText("Restaurant name is required to delete.");
            return;
        }

        int result = util.delete("restaurants", "name='" + name + "'");

        if (result > 0) {
            statusLabel.setText("Restaurant deleted successfully.");
            loadRestaurants(); // Refresh the table after deletion
        } else {
            statusLabel.setText("Failed to delete restaurant. Restaurant may not exist.");
        }
    }

    @FXML
    private void handleUpdateRestaurant() {
        String name = updateRestaurantField.getText().trim();
        String newLocation = newLocationField.getText().trim();

        if (name.isEmpty() || newLocation.isEmpty()) {
            statusLabel.setText("Both restaurant name and new location are required.");
            return;
        }

        String setClause = "location='" + newLocation + "'";
        int result = util.update("restaurants", setClause, "name='" + name + "'");

        if (result > 0) {
            statusLabel.setText("Restaurant updated successfully.");
            loadRestaurants(); // Refresh the table after update
        } else {
            statusLabel.setText("Failed to update restaurant. Restaurant may not exist.");
        }
    }

    private void loadRestaurants() {
        restaurantList.clear(); // Clear the existing list

        try {
            ResultSet resultSet = util.getData("restaurants");
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String location = resultSet.getString("location");
                restaurantList.add(new Restaurant(name, location)); // Add each restaurant to the list
            }

            restaurantsTable.setItems(restaurantList); // Bind the list to the TableView
        } catch (SQLException e) {
            statusLabel.setText("Failed to load restaurants.");
            e.printStackTrace();
        }
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
