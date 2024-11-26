module com.example.food_delivery_system {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires com.almasb.fxgl.all;
    requires java.sql;

    opens com.example.food_delivery_system to javafx.fxml;
    opens com.example.Services to javafx.base;

    exports com.example.food_delivery_system;
}