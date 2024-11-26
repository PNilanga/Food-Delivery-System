package com.example.food_delivery_system;

import com.example.Services.Order;
import com.example.Services.Util;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TrackOrdersController {

    @FXML
    private TableView<Order> ordersTable;

    private Util util = new Util();

    @FXML
    public void initialize() {
        ordersTable.getColumns().clear();
        // Set up columns in the table
        TableColumn<Order, Integer> orderIdColumn = new TableColumn<>("Order ID");
        orderIdColumn.setCellValueFactory(new PropertyValueFactory<>("orderId"));

        TableColumn<Order, String> customerNameColumn = new TableColumn<>("Customer Name");
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));

        TableColumn<Order, String> restaurantColumn = new TableColumn<>("Restaurant");
        restaurantColumn.setCellValueFactory(new PropertyValueFactory<>("restaurant"));

        TableColumn<Order, String> statusColumn = new TableColumn<>("Status");
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        TableColumn<Order, String> orderDateColumn = new TableColumn<>("Order Date");
        orderDateColumn.setCellValueFactory(new PropertyValueFactory<>("orderDate"));

        ordersTable.getColumns().addAll(orderIdColumn, customerNameColumn, restaurantColumn, statusColumn, orderDateColumn);

        // Load initial data
        loadOrders();
    }

    private void loadOrders() {
        List<Order> orders = new ArrayList<>();
        try {
            ResultSet rs = util.getData("orders");
            while (rs.next()) {
                orders.add(new Order(
                        rs.getInt("orderId"),
                        rs.getString("customerName"),
                        rs.getString("restaurant"),
                        rs.getString("status"),
                        rs.getString("orderDate")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ordersTable.getItems().setAll(orders);
    }

    @FXML
    private void refreshOrders() {
        loadOrders();
    }

    @FXML
    private void backToDashboard() {
        try {
            Stage stage = (Stage) ordersTable.getScene().getWindow();
            stage.close(); // Close the current stage

            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminDashboard.fxml"));
            Stage dashboardStage = new Stage();
            dashboardStage.setScene(new Scene(loader.load()));
            dashboardStage.setTitle("Admin Dashboard");
            dashboardStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
