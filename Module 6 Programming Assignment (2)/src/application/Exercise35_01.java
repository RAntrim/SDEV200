package application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.sql.*;

public class Exercise35_01 extends Application {

    // TextArea to display execution details.
    private TextArea textArea = new TextArea();
    // Label to show update status messages.
    private Label statusLabel = new Label("Idle");

    @Override
    public void start(Stage primaryStage) {
        // Create buttons for the two update modes.
        Button btnBatch = new Button("Batch Update");
        Button btnNonBatch = new Button("Non-Batch Update");
        
        // Set action events for each button to execute the corresponding routines.
        btnNonBatch.setOnAction(e -> runNonBatchUpdate());
        btnBatch.setOnAction(e -> runBatchUpdate());

        // Layout for buttons.
        HBox buttonBox = new HBox(10, btnBatch, btnNonBatch);
        buttonBox.setPadding(new Insets(10));
        
        // Configure the text area.
        textArea.setEditable(false);
        textArea.setPrefHeight(300);

        // Use BorderPane to place controls in specific regions.
        BorderPane root = new BorderPane();
        root.setTop(statusLabel);       // Status label at the top.
        BorderPane.setMargin(statusLabel, new Insets(10));
        root.setCenter(textArea);       // Text area in the center.
        BorderPane.setMargin(textArea, new Insets(10));
        root.setBottom(buttonBox);      // Button box at the bottom.

        // Set the scene.
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("Database Batch Update Performance");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Starts the Non-Batch update in a background thread.
    private void runNonBatchUpdate() {
        updateStatusLabel("Running Non-Batch Update...");
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                executeNonBatchUpdate();
                return null;
            }
        };
        new Thread(task).start();
    }

    // Starts the Batch update in a background thread.
    private void runBatchUpdate() {
        updateStatusLabel("\n\nRunning Batch Update...");
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                executeBatchUpdate();
                return null;
            }
        };
        new Thread(task).start();
    }

    // Method to perform Non-Batch inserts.
    private void executeNonBatchUpdate() {
        // Update these values for your specific database configuration
        String url = "jdbc:mysql://localhost:3306/temp";
        String username = "root";
        String password = "";

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            conn.setAutoCommit(false);
            long startNonBatch = System.nanoTime();

            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO Temp(num1, num2, num3) VALUES (?, ?, ?)")) {
                for (int i = 0; i < 1000; i++) {
                    ps.setDouble(1, Math.random());
                    ps.setDouble(2, Math.random());
                    ps.setDouble(3, Math.random());
                    ps.executeUpdate();
                }
            }
            // Commit the inserts.
            conn.commit();
            long endNonBatch = System.nanoTime();
            long timeNonBatch = endNonBatch - startNonBatch;
            appendText("\n\nNon-batch update completed."
              	  + "\nThe elapsed time is " + timeNonBatch);

            // Update the status message.
            updateStatusLabel("Non-Batch Update Completed.");
        } catch (SQLException e) {
            appendText("Error: " + e.getMessage() + "\n");
        }
    }

    // Method to perform Batch inserts.
    private void executeBatchUpdate() {
        // Update these values for your specific database configuration
        String url = "jdbc:mysql://localhost:3306/temp";
        String username = "root";
        String password = "";

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            conn.setAutoCommit(false);

            // Clear the table to ensure a fair test.
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate("DELETE FROM Temp");
            }
            conn.commit();
            long startBatch = System.nanoTime();

            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO Temp(num1, num2, num3) VALUES (?, ?, ?)")) {
                for (int i = 0; i < 1000; i++)
                {
                    ps.setDouble(1, Math.random());
                    ps.setDouble(2, Math.random());
                    ps.setDouble(3, Math.random());
                    ps.addBatch();
                }
                ps.executeBatch();
            }
            conn.commit();
            long endBatch = System.nanoTime();
            long timeBatch = endBatch - startBatch;
            appendText("\n\nBatch update completed."
            	  + "\nThe elapsed time is " + timeBatch);

            // Update the status message.
            updateStatusLabel("Batch Update Completed.");
        } catch (SQLException e) {
            appendText("Error: " + e.getMessage() + "\n");
        }
    }

    // Helper method to update the TextArea safely from any thread.
    private void appendText(String text) {
        Platform.runLater(() -> textArea.appendText(text));
    }

    // Helper method to update the status label.
    private void updateStatusLabel(String message) {
        Platform.runLater(() -> statusLabel.setText(message));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
