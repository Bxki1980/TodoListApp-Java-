package org.example.todolistapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/org/example/todolistapp/View/Main.fxml"));
            AnchorPane root = loader.load();

            // Create a scene with the loaded FXML layout and set it to the primary stage
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("To-Do List Application");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // The main method that launches the JavaFX application
    public static void main(String[] args) {
        launch(args);
    }
}
