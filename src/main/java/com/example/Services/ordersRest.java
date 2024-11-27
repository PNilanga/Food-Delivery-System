package com.example.Services;

public class ordersRest {
    private int orderId;
    private String customerName;
    private double amount;
    private String status;
    private String orderDate;

    public ordersRest(int orderId, String customerName, double amount, String status) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.amount = amount;
        this.status = status;
    }

    public ordersRest(int orderId, String customerName, double amount, String status, String orderDate) {
        this(orderId, customerName, amount, status);
        this.orderDate = orderDate;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public double getAmount() {
        return amount;
    }

    public String getStatus() {
        return status;
    }

    public String getOrderDate() {
        return orderDate;
    }
}
