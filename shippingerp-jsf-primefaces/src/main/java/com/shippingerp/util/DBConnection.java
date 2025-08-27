package com.shippingerp.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // Database set to ShippingERP_test
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=ShippingERP_test;encrypt=false";
    private static final String USER = "sa";
    private static final String PASSWORD = "Sandy@4623";

    public static Connection getConnection() throws SQLException {
        try {
            // Explicitly load the SQL Server JDBC driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("SQL Server JDBC Driver not found in classpath", e);
        }

        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
