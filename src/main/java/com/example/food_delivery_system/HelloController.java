package com.example.food_delivery_system;

import com.example.Services.DBConnector;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() throws SQLException {
        DBConnector db = new DBConnector();
        db.getConnection();




    }
}
