package com.example.Services;

import javafx.beans.property.*;

public class FoodItem {
    private final IntegerProperty foodId;
    private final StringProperty foodName;
    private final DoubleProperty price;

    public FoodItem(int foodId, String foodName, double price) {
        this.foodId = new SimpleIntegerProperty(foodId);
        this.foodName = new SimpleStringProperty(foodName);
        this.price = new SimpleDoubleProperty(price);
    }

    public int getFoodId() {
        return foodId.get();
    }

    public IntegerProperty foodIdProperty() {
        return foodId;
    }

    public String getFoodName() {
        return foodName.get();
    }

    public StringProperty foodNameProperty() {
        return foodName;
    }

    public double getPrice() {
        return price.get();
    }

    public DoubleProperty priceProperty() {
        return price;
    }
}
