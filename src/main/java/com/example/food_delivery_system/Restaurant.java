package com.example.food_delivery_system;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Restaurant {
    private final StringProperty name;
    private final StringProperty location;

    public Restaurant(String name, String location) {
        this.name = new SimpleStringProperty(name);
        this.location = new SimpleStringProperty(location);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getLocation() {
        return location.get();
    }

    public StringProperty locationProperty() {
        return location;
    }
}
