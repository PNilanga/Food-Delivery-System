package com.example.food_delivery_system;


import com.example.Services.ordersRest;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import com.example.Services.Util;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProcessOrdersController {

    @FXML
    private TableView<ordersRest> ordersTable;

    private final Util util = new Util();
    private final ObservableList<ordersRest> ordersList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Configure table columns
        TableColumn<ordersRest, Integer> orderIdColumn = new TableColumn<>("Order ID");
        orderIdColumn.setCellValueFactory(new PropertyValueFactory<>("orderId"));

        TableColumn<ordersRest, String> customerColumn = new TableColumn<>("Customer");
        customerColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));

        TableColumn<ordersRest, Double> amountColumn = new TableColumn<>("Amount");
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));

        TableColumn<ordersRest, String> statusColumn = new TableColumn<>("Status");
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        ordersTable.getColumns().addAll(orderIdColumn, customerColumn, amountColumn, statusColumn);

        // Load orders from the database
        loadOrders();
    }

    private void loadOrders() {
        ordersList.clear();
        try (ResultSet rs = util.getData("orders")) {
            while (rs.next()) {
                ordersList.add(new ordersRest(
                        rs.getInt("orderId"),
                        rs.getString("customerName"),
                        rs.getDouble("totalAmount"),
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ordersTable.setItems(ordersList);
    }

    @FXML
    private void acceptOrder() {
        ordersRest selectedOrder = ordersTable.getSelectionModel().getSelectedItem();
        if (selectedOrder == null) {
            showAlert("Error", "Please select an order to accept.");
            return;
        }

        String setClause = "status='Accepted'";
        String whereClause = "orderId=" + selectedOrder.getOrderId();
        int result = util.update("orders", setClause, whereClause);

        if (result > 0) {
            showAlert("Success", "Order accepted successfully.");
            loadOrders();
        } else {
            showAlert("Error", "Failed to accept the order.");
        }
    }

    @FXML
    private void markAsProcessed() {
        ordersRest selectedOrder = ordersTable.getSelectionModel().getSelectedItem();
        if (selectedOrder == null) {
            showAlert("Error", "Please select an order to mark as processed.");
            return;
        }

        String setClause = "status='Processed'";
        String whereClause = "orderId=" + selectedOrder.getOrderId();
        int result = util.update("orders", setClause, whereClause);

        if (result > 0) {
            showAlert("Success", "Order marked as processed.");
            loadOrders();
        } else {
            showAlert("Error", "Failed to process the order.");
        }
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
