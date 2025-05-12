package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class InputOrderConfirmation extends Application {
    // This list holds all Pizza objects currently in the order.
    // In production, Main will populate this list from InputPizzaDetails.
    private List<Pizza> pizzas = new ArrayList<>();
    
    // VBox that will display the current pizzas in a scrollable view.
    private VBox pizzaDisplay;
    
    /**
     * Optionally, provide a setter so that Main can pass the order list.
     */
    public void setPizzas(List<Pizza> pizzas) {
        this.pizzas = pizzas;
    }
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Order Summary");
        
        // If no pizzas have been added, warn and close the window.
        if (pizzas.isEmpty()){
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("No Pizzas");
            alert.setHeaderText(null);
            alert.setContentText("No pizzas in the order.");
            alert.showAndWait();
            primaryStage.close();
            return;
        }
        
        // Create a VBox to display the list of pizzas.
        pizzaDisplay = new VBox(10);
        pizzaDisplay.setPadding(new Insets(10));
        updatePizzaDisplay();
        
        // Place the pizza display inside a ScrollPane.
        ScrollPane scrollPane = new ScrollPane(pizzaDisplay);
        scrollPane.setFitToWidth(true);
        
        // Create the three option buttons.
        Button btnMakeAnother = new Button("Make Another Pizza");
        Button btnChangeOrder = new Button("Change Order");
        Button btnFinalize = new Button("Finalize");
        
        // ----- Button Actions -----
        
        // Make Another Pizza: call InputPizzaDetails.showWindow2() to create a new Pizza.
        btnMakeAnother.setOnAction(e -> {
            // InputPizzaDetails.showWindow2() should launch the pizza customizer and return a Pizza.
            Pizza newPizza = InputPizzaDetails.showWindow2();
            if(newPizza != null){
                pizzas.add(newPizza);
                updatePizzaDisplay();
            }
        });
        
        // Change Order: allow the user to choose which pizza to update.
        btnChangeOrder.setOnAction(e -> {
            if (pizzas.isEmpty()) {
                showAlert(AlertType.ERROR, "Error", "There are no pizzas in the order to change.");
                return;
            }
            // Build a list of choices (e.g., "Pizza 1", "Pizza 2", etc.)
            List<String> choices = new ArrayList<>();
            for (int i = 0; i < pizzas.size(); i++) {
                choices.add("Pizza " + (i + 1));
            }
            
            ChoiceDialog<String> dialog = new ChoiceDialog<>(choices.get(0), choices);
            dialog.setTitle("Change Order");
            dialog.setHeaderText("Select the pizza to change:");
            dialog.setContentText("Pizza:");
            
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(choice -> {
                int idx = choices.indexOf(choice);
                // Launch InputPizzaDetails to update the selected pizza.
                Pizza changedPizza = InputPizzaDetails.showWindow2();
                if(changedPizza != null){
                    pizzas.set(idx, changedPizza);
                    updatePizzaDisplay();
                }
            });
        });
        
        // Finalize: compute and show the full order summary, then thank the user and close the window.
        btnFinalize.setOnAction(e -> {
            double subtotal = 0;
            StringBuilder orderSummary = new StringBuilder();
            orderSummary.append("Order Summary:\n\n");
            for (int i = 0; i < pizzas.size(); i++) {
                Pizza p = pizzas.get(i);
                orderSummary.append("Pizza ").append(i + 1).append(":\n");
                orderSummary.append(p.toString()).append("\n\n");
                subtotal += p.getPrice();
            }
            
            // Assume a tax rate of 8%.
            double tax = subtotal * 0.08;
            double fees = 0.0;
            double total = subtotal + tax + fees;
            
            orderSummary.append("Subtotal: $").append(String.format("%.2f", subtotal)).append("\n")
                        .append("Tax (8%): $").append(String.format("%.2f", tax)).append("\n")
                        .append("Fees: $").append(String.format("%.2f", fees)).append("\n")
                        .append("Total: $").append(String.format("%.2f", total));
            
            Alert summaryAlert = new Alert(AlertType.INFORMATION);
            summaryAlert.setTitle("Finalize Order");
            summaryAlert.setHeaderText("Your Order:");
            summaryAlert.setContentText(orderSummary.toString());
            summaryAlert.showAndWait();
            
            Alert thankYouAlert = new Alert(AlertType.INFORMATION);
            thankYouAlert.setTitle("Thank You");
            thankYouAlert.setHeaderText(null);
            thankYouAlert.setContentText("Thank you for your order!");
            thankYouAlert.showAndWait();
            
            primaryStage.close();
        });
        
        // Layout the buttons at the bottom.
        HBox buttonBox = new HBox(10, btnMakeAnother, btnChangeOrder, btnFinalize);
        buttonBox.setPadding(new Insets(10));
        
        // Create the main layout.
        BorderPane rootPane = new BorderPane();
        rootPane.setCenter(scrollPane);
        rootPane.setBottom(buttonBox);
        
        Scene scene = new Scene(rootPane, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    // Refreshes the pizza display area with the current pizzas.
    private void updatePizzaDisplay() {
        pizzaDisplay.getChildren().clear();
        for (int i = 0; i < pizzas.size(); i++) {
            Pizza p = pizzas.get(i);
            Label lbl = new Label("Pizza " + (i + 1) + ":\n" + p.toString());
            lbl.setWrapText(true);
            pizzaDisplay.getChildren().add(lbl);
        }
    }
    
    // Helper method for showing alerts.
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
