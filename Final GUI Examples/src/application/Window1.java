package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Window1 extends Application
{
    @Override
    public void start(Stage primaryStage)
    {
        primaryStage.setTitle("Pizza Order - Customer Information");

        // Create the layout
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(10);

        // Customer phone number (exactly 10 digits)
        Label phoneLabel = new Label("Phone Number:");
        TextField phoneField = new TextField();
        phoneField.setPromptText("10-digit phone number");

        // Customer name
        Label nameLabel = new Label("Name:");
        TextField nameField = new TextField();
        nameField.setPromptText("Enter your name");

        // Customer address
        Label addressLabel = new Label("Address:");
        TextField addressField = new TextField();
        addressField.setPromptText("Enter your address");

        // Optional delivery preferences
        Label deliveryPrefLabel = new Label("Delivery Preferences:");
        TextField deliveryField = new TextField();
        deliveryField.setPromptText("Optional: Enter delivery instructions");

        // Add components to the grid (each on its own row)
        grid.add(phoneLabel, 0, 0);
        grid.add(phoneField, 1, 0);
        
        grid.add(nameLabel, 0, 1);
        grid.add(nameField, 1, 1);
        
        grid.add(addressLabel, 0, 2);
        grid.add(addressField, 1, 2);
        
        grid.add(deliveryPrefLabel, 0, 3);
        grid.add(deliveryField, 1, 3);

        // Confirm button to validate and process input
        Button confirmButton = new Button("Confirm");
        grid.add(confirmButton, 1, 4);

        // Event handler for the confirm button
        confirmButton.setOnAction
        (e -> {
            // Retrieve and trim input values
            String phone = phoneField.getText().trim();
            String name = nameField.getText().trim();
            String address = addressField.getText().trim();
            String deliveryPref = deliveryField.getText().trim();

            // Validate the phone number to be exactly 10 digits using regex
            if (phone.length() != 10 || !phone.matches("\\d{10}"))
            {
                showAlert(Alert.AlertType.ERROR, "Invalid Phone Number", 
                          "Please enter a valid 10-digit phone number.");
                return;
            }

            // Validate required fields (name & address)
            if (name.isEmpty() || address.isEmpty())
            {
                showAlert(Alert.AlertType.ERROR, "Missing Information",
                          "Please enter both your name and address.");
                return;
            }

            // If validation passes, print customer info to the console.
            // Database implementation will come later.
            System.out.println("Customer Info:");
            System.out.println("Phone: " + phone);
            System.out.println("Name: " + name);
            System.out.println("Address: " + address);
            System.out.println("Delivery Preferences: " + deliveryPref);

            // Close this window on successful confirmation and prepare for Window 2.
            // Maybe call a method like openPizzaOptionsWindow() here.
            primaryStage.close();
        });

        // Create and set the scene
        Scene scene = new Scene(grid, 400, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Helper method for creating an alert dialog
    private void showAlert(Alert.AlertType alertType, String title, String message)
    {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);  // No header
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
