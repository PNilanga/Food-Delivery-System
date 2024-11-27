package com.example.food_delivery_system;


import com.example.Services.ordersRest;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import com.example.Services.Util;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderHistoryController {

    @FXML
    private TableView<ordersRest> historyTable;

    private final Util util = new Util();
    private final ObservableList<ordersRest> orderHistoryList = FXCollections.observableArrayList();

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

        TableColumn<ordersRest, String> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("orderDate"));

        historyTable.getColumns().addAll(orderIdColumn, customerColumn, amountColumn, statusColumn, dateColumn);

        // Load order history from the database
        loadOrderHistory();
    }

    private void loadOrderHistory() {
        orderHistoryList.clear();
        try (ResultSet rs = util.getData("orders")) {
            while (rs.next()) {
                orderHistoryList.add(new ordersRest(
                        rs.getInt("orderId"),
                        rs.getString("customerName"),
                        rs.getDouble("totalAmount"),
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        historyTable.setItems(orderHistoryList);
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
