package org.example.todolistapp.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // MySQL database connection details
    private static final String URL = "jdbc:mysql://localhost:3306/todo_db";
    private static final String USERNAME = "root"; // Update with your MySQL username
    private static final String PASSWORD = "Mohammadsql0"; // Update with your MySQL password

    // Method to establish a connection to the MySQL database
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}
