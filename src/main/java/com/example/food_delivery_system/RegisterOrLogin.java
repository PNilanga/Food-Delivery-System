package com.example.food_delivery_system;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterOrLogin extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(RegisterOrLogin.class.getResource("RegisterOrLogin.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        primaryStage.setTitle("Login Page");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {launch();}
}
