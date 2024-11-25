package com.example.food_delivery_system;

import com.example.Services.DBConnector;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() throws SQLException {
    }
    @FXML
    private void goToLoginRegister() {
        try {
            // Load the Login/Register FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RegisterOrLogin.fxml"));
            Parent loginRegisterRoot = loader.load();

            // Get the current stage and set the new scene
            Stage stage = (Stage) Stage.getWindows().filtered(Window::isShowing).get(0);
            stage.setScene(new Scene(loginRegisterRoot));
            stage.setTitle("Login/Register Page");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
