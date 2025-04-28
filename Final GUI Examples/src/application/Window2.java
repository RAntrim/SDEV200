package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.*;

public class Window2 extends Application {

    // Variables to store selections
    private String selectedSize = "";
    private String selectedCrust = "";
    private String selectedSauce = "";
    private String selectedCheese = "";
    private Map<String, String> toppingSelections = new LinkedHashMap<>();

    // Helper class to encapsulate a topping control (checkbox + amount combo)
    class ToppingControl {
        CheckBox checkBox;
        ComboBox<String> amountComboBox;
        
        ToppingControl(String topping)
        {
            checkBox = new CheckBox(topping);
            amountComboBox = new ComboBox<>();
            amountComboBox.getItems().addAll("Lite", "Regular", "Extra", "Double");
            amountComboBox.setValue("Regular"); // default value
            amountComboBox.setDisable(true);    // remain disabled until checkbox is checked

            // When the checkbox is selected, enable the combo
            checkBox.selectedProperty().addListener(
            		(obs, wasSelected, isNowSelected) -> {
            		amountComboBox.setDisable(!isNowSelected);
            	}
            );
        }
        
        public String getSelection()
        {
            return checkBox.isSelected() ? amountComboBox.getValue() : "";
        }
    }

    // List to store topping controls and names for our 11 toppings
    private List<ToppingControl> toppingControls = new ArrayList<>();
    private final String[] toppingNames =
    	{
            "Pepperoni", "Sausage", "Beef", "Ham",
            "Bacon", "Black Olives", "Pineapple", "Mushrooms",
            "Onion", "Banana Peppers", "Jalapenos"
    };

    // Navigation: list of columns and the current column index.
    private int currentColumnIndex = 0;
    private List<Pane> columns = new ArrayList<>();
    private StackPane columnStackPane = new StackPane();

    @Override
    public void start(Stage primaryStage)
    {
        primaryStage.setTitle("Customize Your Pizza!");

        // Create the four columns.
        createColumns();

        // Add all columns to a StackPane; only one is visible at a time.
        columnStackPane.getChildren().addAll(columns);
        for (int i = 0; i < columns.size(); i++)
        {
            columns.get(i).setVisible(i == currentColumnIndex);
        }

        // Create navigation buttons
        Button backButton = new Button("Back");
        Button nextButton = new Button("Next");
        Button confirmButton = new Button("Confirm Pizza");

        // Back button action
        backButton.setOnAction
        (e -> {
            if (currentColumnIndex > 0)
            {
                columns.get(currentColumnIndex).setVisible(false);
                currentColumnIndex--;
                columns.get(currentColumnIndex).setVisible(true);
            }
        });

        // Next button action
        nextButton.setOnAction
        (e -> {
            if (currentColumnIndex < columns.size() - 1)
            {
                columns.get(currentColumnIndex).setVisible(false);
                currentColumnIndex++;
                columns.get(currentColumnIndex).setVisible(true);
            }
        });

        // Confirm button opens a summary pop-up for review.
        confirmButton.setOnAction
        (e -> {
            // Check required selections from columns 1-3.
            if (selectedSize.isEmpty())
            {
                showAlert("Incomplete", "Please select a pizza size.");
                return;
            }
            if (selectedCrust.isEmpty() || selectedSauce.isEmpty())
            {
                showAlert("Incomplete", "Please select both a crust and a sauce.");
                return;
            }
            if (selectedCheese.isEmpty())
            {
                showAlert("Incomplete", "Please select a cheese amount.");
                return;
            }
            // Gather topping selections.
            toppingSelections.clear();
            for (ToppingControl tc : toppingControls)
                if (tc.checkBox.isSelected())
                    toppingSelections.put(tc.checkBox.getText(), tc.amountComboBox.getValue());

            // Show the summary pop-up.
            showSummarypopup(primaryStage);
        });

        // Arrange the navigation buttons in an HBox
        HBox navBox = new HBox(10);
        navBox.setAlignment(Pos.CENTER);
        navBox.getChildren().addAll(backButton, nextButton, confirmButton);
        navBox.setPadding(new Insets(10));

        // Use a BorderPane layout: center for the current column and bottom for navigation.
        BorderPane root = new BorderPane();
        root.setCenter(columnStackPane);
        root.setBottom(navBox);

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Method that creates the four columns of options.
    private void createColumns()
    {
        // Column 1: Pizza Size
        VBox column1 = new VBox(10);
        column1.setPadding(new Insets(20));
        column1.getChildren().add(new Label("Select Pizza Size:"));
        String[] sizes = {"Personal Pan", "Small", "Medium", "Large", "Extra Large"};
        ToggleGroup sizeGroup = new ToggleGroup();
        for (String size : sizes)
        {
            RadioButton rb = new RadioButton(size);
            rb.setToggleGroup(sizeGroup);
            rb.setOnAction(e -> selectedSize = size);
            column1.getChildren().add(rb);
        }
        columns.add(column1);

        // Column 2: Crust & Sauce
        VBox column2 = new VBox(15);
        column2.setPadding(new Insets(20));
        Label crustLabel = new Label("Select Crust:");
        String[] crusts = {"Pan", "Thin", "Chicago Style", "Stuffed Crust"};
        ToggleGroup crustGroup = new ToggleGroup();
        HBox crustBox = new HBox(10);
        for (String crust : crusts)
        {
            RadioButton rb = new RadioButton(crust);
            rb.setToggleGroup(crustGroup);
            rb.setOnAction(e -> selectedCrust = crust);
            crustBox.getChildren().add(rb);
        }
        
        Label sauceLabel = new Label("Select Sauce:");
        String[] sauces = {"Marinara", "Sweet Marinara", "BBQ", "Alfredo Sauce"};
        ToggleGroup sauceGroup = new ToggleGroup();
        HBox sauceBox = new HBox(10);
        for (String sauce : sauces)
        {
            RadioButton rb = new RadioButton(sauce);
            rb.setToggleGroup(sauceGroup);
            rb.setOnAction(e -> selectedSauce = sauce);
            sauceBox.getChildren().add(rb);
        }
        column2.getChildren().addAll(crustLabel, crustBox, sauceLabel, sauceBox);
        columns.add(column2);

        // Column 3: Cheese Amount
        VBox column3 = new VBox(10);
        column3.setPadding(new Insets(20));
        column3.getChildren().add(new Label("Select Cheese Amount:"));
        String[] cheeseOptions = {"None", "Lite", "Regular", "Extra", "Double"};
        ToggleGroup cheeseGroup = new ToggleGroup();
        for (String cheese : cheeseOptions)
        {
            RadioButton rb = new RadioButton(cheese);
            rb.setToggleGroup(cheeseGroup);
            rb.setOnAction(e -> selectedCheese = cheese);
            column3.getChildren().add(rb);
        }
        columns.add(column3);

        // Column 4: Toppings (with scroll support)
        VBox column4 = new VBox(10);
        column4.setPadding(new Insets(20));
        column4.getChildren().add(new Label("Select Toppings and Amounts:"));

        // For each topping, create a control (checkbox + combobox)
        for (String topping : toppingNames)
        {
            HBox toppingBox = new HBox(10);
            ToppingControl tc = new ToppingControl(topping);
            toppingControls.add(tc);
            toppingBox.getChildren().addAll(tc.checkBox, tc.amountComboBox);
            column4.getChildren().add(toppingBox);
        }
        // Put into a ScrollPane to allow scrolling if needed.
        ScrollPane scrollPane = new ScrollPane(column4);
        scrollPane.setFitToWidth(true);
        columns.add(scrollPane); // I know this gives an error but I'll fix it.
    }

    // Display a summary pop-up dialog with three custom options.
    private void showSummarypopup(Stage ownerStage)
    {
        StringBuilder summary = new StringBuilder();
        summary.append("Pizza Size: ").append(selectedSize).append("\n")
               .append("Crust: ").append(selectedCrust).append("\n")
               .append("Sauce: ").append(selectedSauce).append("\n")
               .append("Cheese: ").append(selectedCheese).append("\n")
               .append("Toppings:\n");
        if (toppingSelections.isEmpty())
            summary.append(" None");
        else
            for (Map.Entry<String, String> entry : toppingSelections.entrySet())
                summary.append(" ").append(entry.getKey())
                       .append(" (").append(entry.getValue()).append(")\n");

        // Create three custom buttons.
        ButtonType neverMindButton = new ButtonType("Never Mind", ButtonBar.ButtonData.CANCEL_CLOSE);
        ButtonType makeChangesButton = new ButtonType("Make Changes", ButtonBar.ButtonData.OTHER);
        ButtonType iWantThisButton = new ButtonType("I Want This", ButtonBar.ButtonData.OK_DONE);

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirm Your Pizza");
        alert.setHeaderText("Review Your Pizza Options:");
        alert.setContentText(summary.toString());

        // Replace default buttons with our custom buttons.
        alert.getButtonTypes().setAll(neverMindButton, makeChangesButton, iWantThisButton);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent())
        {
            if (result.get() == neverMindButton)
            {
                // Clear selections and return to the first column.
                resetSelections();
                switchToColumn(0);
            }
            else if (result.get() == makeChangesButton)
                // Preserve selections but return to column 1 for adjustments.
                switchToColumn(0);
            else if (result.get() == iWantThisButton)
            {
                // Confirm the pizza. For now, we simply print a message and close the window.
                System.out.println("Pizza confirmed! Proceeding to Order Summary (Window 3)...");
                ownerStage.close();
                // Here you would launch Window 3.
            }
        }
    }

    // Helper to change which column is visible.
    private void switchToColumn(int index)
    {
        if (index < 0 || index >= columns.size()) return;
        columns.get(currentColumnIndex).setVisible(false);
        currentColumnIndex = index;
        columns.get(currentColumnIndex).setVisible(true);
    }

    // A basic reset method (for a complete solution, you would reset each control).
    private void resetSelections()
    {
        selectedSize = "";
        selectedCrust = "";
        selectedSauce = "";
        selectedCheese = "";
        toppingSelections.clear();
        // Note: For a complete reset, you might also clear the selections on each RadioButton and ToppingControl.
    }

    // Utility method to show alerts.
    private void showAlert(String title, String message)
    {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
