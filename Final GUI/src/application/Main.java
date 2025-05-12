package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Main extends Application {
    
    private static Customer globalCustomer;
    private static List<Pizza> orderList = new ArrayList<>();
    
    @Override
    public void start(Stage primaryStage) {
        // --- Step 1: Welcome Window ---
        Stage welcomeStage = new Stage();
        welcomeStage.initModality(Modality.APPLICATION_MODAL);
        welcomeStage.setTitle("Welcome to Peppino's Pizza");
        
        Label welcomeLabel = new Label("Thank you for choosing Peppino's Pizza!");
        Button btnExit = new Button("Exit");
        Button btnProceed = new Button("Proceed");
        HBox buttonBox = new HBox(10, btnExit, btnProceed);
        buttonBox.setAlignment(Pos.CENTER);
        VBox welcomeLayout = new VBox(20, welcomeLabel, buttonBox);
        welcomeLayout.setAlignment(Pos.CENTER);
        Scene welcomeScene = new Scene(welcomeLayout, 350, 150);
        welcomeStage.setScene(welcomeScene);
        
        // Set actions for the buttons.
        btnExit.setOnAction(e -> {
            Platform.exit();
        });
        btnProceed.setOnAction(e -> {
            welcomeStage.close();
        });
        
        welcomeStage.showAndWait();
        
        // --- Step 2: Get Customer Information from Window1 ---
        globalCustomer = InputCustomerInfo.showWindow1();
        if (globalCustomer == null) {
            System.out.println("Customer information not provided. Exiting.");
            Platform.exit();
            return;
        }
        
        // --- Step 3: Enter a loop for ordering pizzas ---
        boolean orderFinalized = false;
        // We repeatedly call Window2 to create a Pizza, then call Window3 to review the order.
        while (!orderFinalized)
        {
            // Call Window2 to get a new Pizza from the customer.
            Pizza newPizza = InputPizzaDetails.showWindow2();
            if (newPizza != null)
                orderList.add(newPizza);
            
            // Call Window3 to review the current order.
            // Window3 will let the user choose to make another pizza,
            // change an existing one, or finalize the order.
            orderFinalized = showWindow3(orderList);
        }
        
        // --- Step 4: Display Final Order Summary ---
        double subtotal = 0;
        StringBuilder orderDetails = new StringBuilder();
        orderDetails.append("Order Summary:\n\n");
        for (int i = 0; i < orderList.size(); i++) {
            Pizza p = orderList.get(i);
            orderDetails.append("Pizza ").append(i + 1).append(":\n");
            orderDetails.append(p.toString()).append("\n\n");
            subtotal += p.getPrice();
        }
        double tax = subtotal * 0.08;  // e.g., 8% tax
        double total = subtotal + tax;
        orderDetails.append("Subtotal: $").append(String.format("%.2f", subtotal)).append("\n");
        orderDetails.append("Tax (8%): $").append(String.format("%.2f", tax)).append("\n");
        orderDetails.append("Final Total: $").append(String.format("%.2f", total));
        
        Alert finalAlert = new Alert(AlertType.INFORMATION);
        finalAlert.setTitle("Final Order");
        finalAlert.setHeaderText("Thank you for your order, " + globalCustomer.getName() + "!");
        finalAlert.setContentText(orderDetails.toString());
        finalAlert.showAndWait();
        
        Platform.exit();
    }
    
    /**
     * Simulates the order review window (Window3).
     * This method displays the current order (list of pizzas) in a scrollable view and
     * presents three options to the user:
     *   - Make Another Pizza: Return false.
     *   - Change Order: (Simulated by updating one pizza) Return false.
     *   - Finalize: Return true.
     */
    private boolean showWindow3(List<Pizza> currentOrder) {
        ButtonType btnMakeAnother = new ButtonType("Make Another Pizza");
        ButtonType btnChangeOrder = new ButtonType("Change Order");
        ButtonType btnFinalize = new ButtonType("Finalize Order");
        
        Alert orderAlert = new Alert(AlertType.CONFIRMATION);
        orderAlert.setTitle("Review Your Order");
        orderAlert.setHeaderText("Current Order:");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < currentOrder.size(); i++) {
            sb.append("Pizza ").append(i + 1).append(":\n")
              .append(currentOrder.get(i).toString()).append("\n\n");
        }
        orderAlert.setContentText(sb.toString());
        orderAlert.getButtonTypes().setAll(btnMakeAnother, btnChangeOrder, btnFinalize);
        
        Optional<ButtonType> result = orderAlert.showAndWait();
        if (result.isPresent()) {
            if (result.get() == btnFinalize)
                return true;
            else if (result.get() == btnChangeOrder)
            {
                // For simulation, update the first pizza.
                // In a full implementation, you’d let the user choose which pizza to change,
                // launch Window2 pre-populated with that pizza's data, then update the order.
                Pizza changedPizza = InputPizzaDetails.showWindow2();  
                if (!currentOrder.isEmpty())
                    currentOrder.set(0, changedPizza);
                return false;
            } else {
                // Make Another Pizza (option selected).
                return false;
            }
        }
        return false;
    }
}