package com.example.food_delivery_system;

import com.example.Services.MenuItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import com.example.Services.Util;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ManageMenuController {

    @FXML
    private TableView<MenuItem> menuTable;
    @FXML
    private TextField nameField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField availabilityField;

    private final Util util = new Util();
    private final ObservableList<MenuItem> menuItems = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Configure table columns
        TableColumn<MenuItem, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<MenuItem, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<MenuItem, String> availabilityColumn = new TableColumn<>("Availability");
        availabilityColumn.setCellValueFactory(new PropertyValueFactory<>("availability"));

        menuTable.getColumns().addAll(nameColumn, priceColumn, availabilityColumn);

        // Load menu items from the database
        loadMenuItems();
    }

    private void loadMenuItems() {
        menuItems.clear();
        try (ResultSet rs = util.getData("menuItems")) {
            while (rs.next()) {
                menuItems.add(new MenuItem(
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getString("availability")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        menuTable.setItems(menuItems);
    }

    @FXML
    private void addMenuItem() {
        String name = nameField.getText();
        String priceText = priceField.getText();
        String availability = availabilityField.getText();

        // Input validation
        if (name.isEmpty() || priceText.isEmpty() || availability.isEmpty()) {
            showAlert("Error", "Please fill in all fields.");
            return;
        }

        try {
            double price = Double.parseDouble(priceText);
            int result = util.insert("menuItems", "name, price, availability", new Object[]{name, price, availability});
            if (result > 0) {
                showAlert("Success", "Menu item added successfully.");
                loadMenuItems();
                clearFields();
            } else {
                showAlert("Error", "Failed to add menu item.");
            }
        } catch (NumberFormatException e) {
            showAlert("Error", "Price must be a valid number.");
        }
    }

    @FXML
    private void updateMenuItem() {
        MenuItem selectedItem = menuTable.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            showAlert("Error", "Please select a menu item to update.");
            return;
        }

        String name = nameField.getText();
        String priceText = priceField.getText();
        String availability = availabilityField.getText();

        // Input validation
        if (name.isEmpty() || priceText.isEmpty() || availability.isEmpty()) {
            showAlert("Error", "Please fill in all fields.");
            return;
        }

        try {
            double price = Double.parseDouble(priceText);
            String setClause = "name='" + name + "', price=" + price + ", availability='" + availability + "'";
            String whereClause = "name='" + selectedItem.getName() + "'";

            int result = util.update("menuItems", setClause, whereClause);
            if (result > 0) {
                showAlert("Success", "Menu item updated successfully.");
                loadMenuItems();
                clearFields();
            } else {
                showAlert("Error", "Failed to update menu item.");
            }
        } catch (NumberFormatException e) {
            showAlert("Error", "Price must be a valid number.");
        }
    }

    @FXML
    private void deleteMenuItem() {
        MenuItem selectedItem = menuTable.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            showAlert("Error", "Please select a menu item to delete.");
            return;
        }

        String condition = "name='" + selectedItem.getName() + "'";
        int result = util.delete("menuItems", condition);
        if (result > 0) {
            showAlert("Success", "Menu item deleted successfully.");
            loadMenuItems();
            clearFields();
        } else {
            showAlert("Error", "Failed to delete menu item.");
        }
    }

    private void clearFields() {
        nameField.clear();
        priceField.clear();
        availabilityField.clear();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    private void backToDashboard() {
        try {
            // Load the Login Screen FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RestaurantOwnerDashboard.fxml"));
            Parent loginRoot = loader.load();

            // Get the current stage and set the new scene
            Stage stage = (Stage) Stage.getWindows().filtered(Window::isShowing).get(0);
            stage.setScene(new Scene(loginRoot));
            stage.setTitle("Restaurant Owner Dashboard");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
