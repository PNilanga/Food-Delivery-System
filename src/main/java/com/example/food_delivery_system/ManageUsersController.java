package com.example.food_delivery_system;

import com.example.Services.User;
import com.example.Services.Util;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ManageUsersController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private ComboBox<String> roleComboBox;

    @FXML
    private TextField deleteUsernameField;

    @FXML
    private TextField updateUsernameField;

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private ComboBox<String> newRoleComboBox;

    @FXML
    private Label statusLabel;

    @FXML
    private TableView<User> usersTable;

    @FXML
    private TableColumn<User, String> usernameColumn;

    @FXML
    private TableColumn<User, String> roleColumn;

    private Util util = new Util();

    private ObservableList<User> userList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Initialize ComboBoxes with roles
        roleComboBox.getItems().addAll("Customer", "Restaurant Owner", "Admin");
        newRoleComboBox.getItems().addAll("Customer", "Restaurant Owner", "Admin");

        // Bind TableView columns to User properties
        usernameColumn.setCellValueFactory(cellData -> cellData.getValue().usernameProperty());
        roleColumn.setCellValueFactory(cellData -> cellData.getValue().roleProperty());
    }

    @FXML
    private void handleAddUser() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        String role = roleComboBox.getValue();
        String tableName = "users";
        String columns = "username, password, role";
        Object[] values = {username, password, role};

        if (username.isEmpty() || password.isEmpty() || role == null) {
            statusLabel.setText("All fields are required to add a user.");
            return;
        }

        int result = util.insert(tableName, columns, values);

        if (result > 0) {
            statusLabel.setText("User added successfully.");
            refreshUsersTable(); // Refresh the table to show the newly added user
        } else {
            statusLabel.setText("Failed to add user.");
        }
    }

    @FXML
    private void handleDeleteUser() {
        String username = deleteUsernameField.getText().trim();

        if (username.isEmpty()) {
            statusLabel.setText("Username is required to delete a user.");
            return;
        }

        int result = util.delete("users", "username='" + username + "'");

        if (result > 0) {
            statusLabel.setText("User deleted successfully.");
            refreshUsersTable(); // Refresh the table after deletion
        } else {
            statusLabel.setText("Failed to delete user. User may not exist.");
        }
    }

    @FXML
    private void handleUpdateUser() {
        String username = updateUsernameField.getText().trim();
        String newPassword = newPasswordField.getText().trim();
        String newRole = newRoleComboBox.getValue();

        if (username.isEmpty() || (newPassword.isEmpty() && newRole == null)) {
            statusLabel.setText("Provide a username and at least one field to update.");
            return;
        }

        StringBuilder setClause = new StringBuilder();
        if (!newPassword.isEmpty()) {
            setClause.append("password='").append(newPassword).append("'");
        }
        if (newRole != null) {
            if (setClause.length() > 0) setClause.append(", ");
            setClause.append("role='").append(newRole).append("'");
        }

        int result = util.update("users", setClause.toString(), "username='" + username + "'");

        if (result > 0) {
            statusLabel.setText("User updated successfully.");
            refreshUsersTable(); // Refresh the table after update
        } else {
            statusLabel.setText("Failed to update user. User may not exist.");
        }
    }

    @FXML
    private void showUsersTable() {
        userList.clear(); // Clear the existing list

        try {
            ResultSet resultSet = util.getData("users");
            while (resultSet.next()) {
                String username = resultSet.getString("username");
                String role = resultSet.getString("role");
                userList.add(new User(username, role)); // Add each user to the list
            }

            usersTable.setItems(userList); // Bind the list to the TableView
        } catch (SQLException e) {
            statusLabel.setText("Failed to load users.");
            e.printStackTrace();
        }
    }

    private void refreshUsersTable() {
        showUsersTable(); // Reload the table data
    }
    @FXML
    private void goBackToDashboard() {
        try {
            // Load the Login Screen FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminDashboard.fxml"));
            Parent loginRoot = loader.load();

            // Get the current stage and set the new scene
            Stage stage = (Stage) Stage.getWindows().filtered(Window::isShowing).get(0);
            stage.setScene(new Scene(loginRoot));
            stage.setTitle("AdminDashboard");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
