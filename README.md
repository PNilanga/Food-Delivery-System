# 🍔 Food Delivery Management System

> A comprehensive, role-based desktop application built with Java and JavaFX for managing a multi-tenant food delivery platform. 

This project showcases a complete end-to-end desktop application utilizing the **Model-View-Controller (MVC)** architectural pattern, object-oriented programming (OOP) principles, and a remote MySQL database integration via JDBC. It is designed to handle multiple user types, each with their own dedicated dashboards, permissions, and workflows.

## ✨ Key Features

### 🔐 Security & Access
- **Role-Based Access Control (RBAC):** Distinct dashboards and isolated capabilities for Customers, Restaurant Owners, and System Administrators.
- **User Authentication:** Secure login and registration flows tied to specific user roles.

### 🛒 Customer Experience
- Seamlessly browse available restaurants and their specific menus.
- Interactive shopping cart functionality to select items and calculate real-time totals.
- Trackable order placement and history.

### 🏪 Restaurant Management
- **Menu Management:** Full CRUD (Create, Read, Update, Delete) capabilities for restaurant food items.
- **Order Processing:** View, accept, and update statuses for incoming customer orders.
- **Business Analytics:** Track historical order data and generate basic revenue reports.

### 👨‍💻 System Administration
- Global overview of platform operations, user management, and system health.

## 🛠️ Technical Stack & Architecture

- **Language:** Java 23
- **GUI Framework:** JavaFX 17 (utilizing FXML for View separation, ControlsFX, and FormsFX)
- **Build Automation:** Maven
- **Database:** MySQL (Remote hosting via FreeSQLDatabase)
- **Connectivity:** Java Database Connectivity (JDBC)
- **Architecture:** MVC (Model-View-Controller) separating UI logic from business and database operations.

## 📸 Screenshots

*(Note: Replace these placeholders with actual screenshots of your application before sharing with recruiters)*

| Login & Registration | Customer Dashboard | Restaurant Management |
|:---:|:---:|:---:|
| <img src="https://via.placeholder.com/250x150.png?text=Login+Screen" alt="Login"> | <img src="https://via.placeholder.com/250x150.png?text=Customer+View" alt="Customer Dashboard"> | <img src="https://via.placeholder.com/250x150.png?text=Restaurant+View" alt="Restaurant Management"> |

## 🚀 Quick Start

### Prerequisites
- **JDK 23** or higher installed and configured in your `JAVA_HOME`.
- **Maven** (Optional, as the project includes a Maven Wrapper `mvnw`).
- An active internet connection (required to connect to the remote database).

### Installation & Execution

1. **Clone the repository:**
   ```bash
   git clone https://github.com/PNilanga/Food-Delivery-System.git
   ```
2. **Navigate to the project directory:**
   ```bash
   cd Food-Delivery-System
   ```
3. **Build and run the application:**
   Using the Maven JavaFX plugin:
   ```bash
   mvn clean javafx:run
   ```
   *Or, using the included wrapper:*
   ```bash
   ./mvnw clean javafx:run
   ```

## 🗄️ Project Configuration & Note to Reviewers

**Plug-and-Play Database:** For ease of demonstration and testing by recruiters and reviewers, this application is currently configured to connect directly to a remote, cloud-hosted MySQL database. You do not need to set up a local database schema to test the application's functionality.

> **Security Note:** In a production environment, database credentials (`DB_URL`, `DB_USERNAME`, `DB_PASSWORD`) found in `DBConnector.java` and `LoginRegisterController.java` would be externalized to environment variables or a secure configuration file. They are hardcoded here strictly for portfolio demonstration purposes to ensure a frictionless evaluation.

If you prefer to connect to a local MySQL instance, simply update the connection strings within:
- `src/main/java/com/example/Services/DBConnector.java`
- `src/main/java/com/example/food_delivery_system/LoginRegisterController.java`

---

**Author:** [Your Name/Link to LinkedIn]  
**Status:** Active Development / Portfolio Project
