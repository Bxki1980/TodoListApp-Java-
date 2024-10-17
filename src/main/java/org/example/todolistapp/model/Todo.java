package org.example.todolistapp.model;

import javafx.beans.property.*;

public class Todo {
    // Properties for ID, task, and status
    private IntegerProperty id;
    private StringProperty task;
    private StringProperty status;

    // Constructor to initialize the Todo object
    public Todo(int id, String task, String status) {
        this.id = new SimpleIntegerProperty(id);
        this.task = new SimpleStringProperty(task);
        this.status = new SimpleStringProperty(status);
    }

    // Getters and setters for the ID property
    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    // Getters and setters for the task property
    public String getTask() {
        return task.get();
    }

    public void setTask(String task) {
        this.task.set(task);
    }

    public StringProperty taskProperty() {
        return task;
    }

    // Getters and setters for the status property
    public String getStatus() {
        return status.get();
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public StringProperty statusProperty() {
        return status;
    }
}
