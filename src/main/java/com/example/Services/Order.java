package com.example.Services;

public class Order {
    private int orderId;
    private String customerName;
    private String restaurant;
    private String status;
    private String orderDate;

    public Order(int orderId, String customerName, String restaurant, String status) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.restaurant = restaurant;
        this.status = status;
        this.orderDate = orderDate;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public String getStatus() {
        return status;
    }

    public String getOrderDate() { // This matches the 'orderDate' property in PropertyValueFactory
        return orderDate;
    }
}
