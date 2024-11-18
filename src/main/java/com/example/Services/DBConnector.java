package com.example.Services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
    static Connection con = null;
    static final String username = "sql5745261";
    static final String password = "HBZZTBcHmm";
    static final String dbURL = "jdbc:mysql://sql5.freesqldatabase.com:3306/sql5745261";

    public static Connection getConnection() throws SQLException {

        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(dbURL, username, password);
            System.out.println("done");
        } catch (Exception e) {
            System.out.println(e);
        }
        return con;
    }
    public DBConnector() {

    }
}
