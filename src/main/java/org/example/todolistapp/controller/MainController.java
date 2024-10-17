package org.example.todolistapp.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.todolistapp.model.Todo;
import org.example.todolistapp.util.DatabaseConnection;

import java.sql.*;

public class MainController {

    // UI components
    @FXML
    private TableView<Todo> todoTable;

    @FXML
    private TableColumn<Todo, Number> idColumn;

    @FXML
    private TableColumn<Todo, String> taskColumn;

    @FXML
    private TableColumn<Todo, String> statusColumn;

    @FXML
    private TextField taskField;

    @FXML
    private TextField statusField;

    // ObservableList to store the to-do items
    private ObservableList<Todo> todoList = FXCollections.observableArrayList();

    // Method called automatically after the FXML is loaded
    @FXML
    private void initialize() {
        // Link the table columns with the Todo properties
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        taskColumn.setCellValueFactory(cellData -> cellData.getValue().taskProperty());
        statusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());

        // Load the to-do data from the database
        loadTodoData();
    }

    // Load to-do data from the database and populate the table
    private void loadTodoData() {
        todoList.clear();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM todos";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                todoList.add(new Todo(
                        rs.getInt("id"),
                        rs.getString("task"),
                        rs.getString("status")
                ));
            }
            todoTable.setItems(todoList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Add a new task to the database
    @FXML
    private void addTask() {
        String task = taskField.getText();
        String status = statusField.getText();

        if (task.isEmpty() || status.isEmpty()) {
            showAlert("Please fill in all fields.");
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO todos (task, status) VALUES (?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, task);
            pstmt.setString(2, status);
            pstmt.executeUpdate();

            taskField.clear();
            statusField.clear();
            loadTodoData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update the selected task in the database
    @FXML
    private void updateTask() {
        Todo selectedTodo = todoTable.getSelectionModel().getSelectedItem();
        if (selectedTodo == null) {
            showAlert("Please select a task to update.");
            return;
        }

        String task = taskField.getText();
        String status = statusField.getText();

        if (task.isEmpty() || status.isEmpty()) {
            showAlert("Please fill in all fields.");
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "UPDATE todos SET task = ?, status = ? WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, task);
            pstmt.setString(2, status);
            pstmt.setInt(3, selectedTodo.getId());
            pstmt.executeUpdate();

            taskField.clear();
            statusField.clear();
            loadTodoData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete the selected task from the database
    @FXML
    private void deleteTask() {
        Todo selectedTodo = todoTable.getSelectionModel().getSelectedItem();
        if (selectedTodo == null) {
            showAlert("Please select a task to delete.");
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "DELETE FROM todos WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, selectedTodo.getId());
            pstmt.executeUpdate();

            loadTodoData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Refresh the table by reloading the data
    @FXML
    private void refreshTable() {
        loadTodoData();
    }

    // Show an alert dialog with a given message
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("To-Do List");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
