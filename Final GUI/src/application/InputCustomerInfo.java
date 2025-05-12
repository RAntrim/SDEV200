package application;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class InputCustomerInfo {

    /**
     * Displays the customer information form in a modal window and
     * returns a Customer constructed from the user’s input.
     *
     * @return A Customer object or null if the user closes the window.
     */
    public static Customer showWindow1() {
        // Create a new stage with modality so that this dialog blocks until closed.
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Pizza Order - Customer Information");

        // Create the layout.
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(10);

        // Customer phone number (exactly 10 digits).
        Label phoneLabel = new Label("Phone Number:");
        TextField phoneField = new TextField();
        phoneField.setPromptText("e.g., 1234567890");

        // Customer name.
        Label nameLabel = new Label("Name:");
        TextField nameField = new TextField();
        nameField.setPromptText("Enter your name");

        // Customer address.
        Label addressLabel = new Label("Address:");
        TextField addressField = new TextField();
        addressField.setPromptText("Enter your address");

        // Optional delivery preferences.
        Label deliveryPrefLabel = new Label("Delivery Preferences:");
        TextField deliveryField = new TextField();
        deliveryField.setPromptText("Enter any instructions");

        // Add components to the grid.
        grid.add(phoneLabel, 0, 0);
        grid.add(phoneField, 1, 0);
        grid.add(nameLabel, 0, 1);
        grid.add(nameField, 1, 1);
        grid.add(addressLabel, 0, 2);
        grid.add(addressField, 1, 2);
        grid.add(deliveryPrefLabel, 0, 3);
        grid.add(deliveryField, 1, 3);

        // Confirm button to validate and process input.
        Button confirmButton = new Button("Confirm");
        grid.add(confirmButton, 1, 4);

        // We use an array of size 1 (as a mutable container) to capture the Customer from the lambda.
        final Customer[] result = new Customer[1];

        // Event handler for the confirm button.
        confirmButton.setOnAction(e -> {
            // Retrieve and trim input values.
            String phone = phoneField.getText().trim();
            String name = nameField.getText().trim();
            String address = addressField.getText().trim();
            String deliveryPref = deliveryField.getText().trim();

            // Validate the phone number to be exactly 10 digits using regex.
            if (phone.length() != 10 || !phone.matches("\\d{10}")) {
                showAlert(Alert.AlertType.ERROR, "Invalid Phone Number",
                        "Please enter a valid 10-digit phone number.");
                return;
            }

            // Validate required fields (name & address).
            if (name.isEmpty() || address.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Missing Information",
                        "Please enter both your name and address.");
                return;
            }

            // Create a new Customer object with the provided values.
            result[0] = new Customer(phone, name, address, deliveryPref);
            // Debug output.
            System.out.println("Customer Info:");
            System.out.println(result[0].toString());
            // Close the window.
            stage.close();
        });

        // Create and set the scene.
        Scene scene = new Scene(grid, 500, 250);
        stage.setScene(scene);
        stage.showAndWait();  // This call blocks until stage.close() is called.

        // Return the Customer gathered from the form.
        return result[0];
    }

    // Helper method for displaying alert dialogs.
    private static void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null); // No header.
        alert.setContentText(message);
        alert.showAndWait();
    }
}
