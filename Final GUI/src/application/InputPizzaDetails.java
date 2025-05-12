package application;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class InputPizzaDetails {
    // These arrays hold the current selection for size, crust, sauce, and cheese.
    private static String[] selectedSize = {""};
    private static String[] selectedCrust = {""};
    private static String[] selectedSauce = {""};
    private static String[] selectedCheese = {""};

    // Inner class for topping controls.
    public class ToppingControl {
        CheckBox checkBox;
        ComboBox<String> amountComboBox;

        public ToppingControl(String topping) {
            checkBox = new CheckBox(topping);
            amountComboBox = new ComboBox<>();
            amountComboBox.getItems().addAll("Lite", "Regular", "Extra", "Double");
            amountComboBox.setValue("Regular");
            amountComboBox.setDisable(true);
            checkBox.selectedProperty().addListener((obs, oldVal, newVal) -> {
                amountComboBox.setDisable(!newVal);
            });
        }
    }

    /**
     * Displays the pizza customizer window modally,
     * then returns a Pizza object based on the selections (with pricing logic).
     */
    public static Pizza showWindow2()
    {
    	selectedSize[0] = "";
    	selectedCrust[0] = "";
    	selectedSauce[0] = "";
    	selectedCheese[0] = "";

        final Pizza[] result = new Pizza[1];

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Customize Your Pizza!");

        // List to hold our columns.
        List<Pane> columns = new ArrayList<>();
        StackPane columnStackPane = new StackPane();

        Button prevButton = new Button("Previous Column");
        Button clearButton = new Button("Clear Current Selection");
        Button nextButton = new Button("Next Column");
        Button confirmPizzaButton = new Button("Confirm Pizza");

        // --- Column 1: Pizza Size
        VBox column1 = new VBox(10);
        column1.setPadding(new Insets(20));
        Label sizeLabel = new Label("Select Pizza Size:");
        column1.getChildren().add(sizeLabel);
        String[] sizes = {"Personal Pan", "Small", "Medium", "Large", "Extra Large"};
        ToggleGroup sizeGroup = new ToggleGroup();
        for (String s : sizes) {
            RadioButton rb = new RadioButton(s);
            rb.setToggleGroup(sizeGroup);
            rb.setOnAction(e -> {
                selectedSize[0] = s;
                updateConfirm(confirmPizzaButton);
            });
            column1.getChildren().add(rb);
        }
        columns.add(column1);

        // --- Column 2: Crust & Sauce
        VBox column2 = new VBox(15);
        column2.setPadding(new Insets(20));
        Label crustLabel = new Label("Select Crust:");
        column2.getChildren().add(crustLabel);
        HBox crustBox = new HBox(10);
        String[] crusts = {"Pan", "Thin", "Chicago Style", "Stuffed Crust"};
        ToggleGroup crustGroup = new ToggleGroup();
        for (String c : crusts) {
            RadioButton rb = new RadioButton(c);
            rb.setToggleGroup(crustGroup);
            rb.setOnAction(e -> {
                selectedCrust[0] = c;
                updateConfirm(confirmPizzaButton);
            });
            crustBox.getChildren().add(rb);
        }
        column2.getChildren().add(crustBox);
        Label sauceLabel = new Label("Select Sauce:");
        column2.getChildren().add(sauceLabel);
        HBox sauceBox = new HBox(10);
        String[] sauces = {"Marinara", "Sweet Marinara", "BBQ", "Alfredo Sauce"};
        ToggleGroup sauceGroup = new ToggleGroup();
        for (String s : sauces) {
            RadioButton rb = new RadioButton(s);
            rb.setToggleGroup(sauceGroup);
            rb.setOnAction(e -> {
                selectedSauce[0] = s;
                updateConfirm(confirmPizzaButton);
            });
            sauceBox.getChildren().add(rb);
        }
        column2.getChildren().add(sauceBox);
        columns.add(column2);

        // --- Column 3: Cheese Amount ---
        VBox column3 = new VBox(10);
        column3.setPadding(new Insets(20));
        Label cheeseLabel = new Label("Select Cheese Amount:");
        column3.getChildren().add(cheeseLabel);
        String[] cheeses = {"None", "Lite", "Regular", "Extra", "Double"};
        ToggleGroup cheeseGroup = new ToggleGroup();
        for (String ch : cheeses) {
            RadioButton rb = new RadioButton(ch);
            rb.setToggleGroup(cheeseGroup);
            rb.setOnAction(e -> {
                selectedCheese[0] = ch;
                updateConfirm(confirmPizzaButton);
            });
            column3.getChildren().add(rb);
        }
        columns.add(column3);

        // --- Column 4: Toppings (split into two mini-columns) ---
        VBox column4 = new VBox(10);
        column4.setPadding(new Insets(20));
        Label toppingsLabel = new Label("Select Toppings:");
        column4.getChildren().add(toppingsLabel);
        String[] toppingNames = {"Pepperoni", "Sausage", "Beef", "Ham",
                                  "Bacon", "Black Olives", "Pineapple", "Mushrooms",
                                  "Onion", "Banana Peppers", "Jalapenos"};
        VBox leftToppings = new VBox(10);
        VBox rightToppings = new VBox(10);
        List<ToppingControl> toppingControls = new ArrayList<>();
        int half = (int) Math.ceil(toppingNames.length / 2.0);
        for (int i = 0; i < toppingNames.length; i++) {
            ToppingControl tc = new InputPizzaDetails().new ToppingControl(toppingNames[i]);
            toppingControls.add(tc);
            HBox row = new HBox(10, tc.checkBox, tc.amountComboBox);
            if (i < half)
                leftToppings.getChildren().add(row);
            else
                rightToppings.getChildren().add(row);
        }
        HBox splitColumns = new HBox(20, leftToppings, rightToppings);
        column4.getChildren().add(splitColumns);
        columns.add(column4);

        // Add columns to the StackPane; only the first column visible.
        for (int i = 0; i < columns.size(); i++) {
            Pane p = columns.get(i);
            p.setVisible(i == 0);
            columnStackPane.getChildren().add(p);
        }
        final int[] currentColumnIndex = {0};
        
     // --- Navigation Buttons ---
        // Disable confirm button initially.
        confirmPizzaButton.setDisable(true);

        // Update previous and next buttons as needed.
        updateNavButtons(prevButton, nextButton, currentColumnIndex[0], columns.size());

        prevButton.setOnAction(e -> {
            if (currentColumnIndex[0] > 0) {
                columns.get(currentColumnIndex[0]).setVisible(false);
                currentColumnIndex[0]--;
                columns.get(currentColumnIndex[0]).setVisible(true);
                updateNavButtons(prevButton, nextButton, currentColumnIndex[0], columns.size());
            }
        });
        nextButton.setOnAction(e -> {
            if (currentColumnIndex[0] < columns.size() - 1) {
                columns.get(currentColumnIndex[0]).setVisible(false);
                currentColumnIndex[0]++;
                columns.get(currentColumnIndex[0]).setVisible(true);
                updateNavButtons(prevButton, nextButton, currentColumnIndex[0], columns.size());
            }
        });
        clearButton.setOnAction(e -> {
            switch (currentColumnIndex[0]) {
                case 0:
                    sizeGroup.selectToggle(null);
                    selectedSize[0] = "";
                    break;
                case 1:
                    crustGroup.selectToggle(null);
                    sauceGroup.selectToggle(null);
                    selectedCrust[0] = "";
                    selectedSauce[0] = "";
                    break;
                case 2:
                    cheeseGroup.selectToggle(null);
                    selectedCheese[0] = "";
                    break;
                case 3:
                    for (ToppingControl tc : toppingControls) {
                        tc.checkBox.setSelected(false);
                        tc.amountComboBox.setValue("Regular");
                    }
                    break;
                default:
                    break;
            }
            updateConfirm(confirmPizzaButton);
        });
        // Place all nav buttons together:
        HBox navBox = new HBox(10, prevButton, clearButton, nextButton, confirmPizzaButton);
        navBox.setAlignment(Pos.CENTER_LEFT);
        navBox.setPadding(new Insets(10));

        // --- (Optional) Pizza graphic pane; not used for pricing --
        Pane graphicPane = new Pane();
        graphicPane.setPrefSize(200, 200);
        graphicPane.setMaxSize(200, 200);
        graphicPane.setStyle("-fx-border-color: black;");
        // (You can call updatePizzaGraphic() here if needed.)

        // --- Confirm Pizza Button Action ---
        confirmPizzaButton.setOnAction(e -> {
            // Validate that required selections are present.
            if (selectedSize[0].isEmpty() || selectedCrust[0].isEmpty() ||
                selectedSauce[0].isEmpty() || selectedCheese[0].isEmpty()) {
                Alert alert = new Alert(AlertType.ERROR,
                        "Please complete Size, Crust & Sauce, and Cheese selections.");
                alert.showAndWait();
                return;
            }
            
            // Build the toppings list.
            List<Topping> toppingList = new ArrayList<>();
            for (ToppingControl tc : toppingControls) {
                if (tc.checkBox.isSelected()) {
                    try {
                        ToppingType tt = ToppingType.valueOf(tc.checkBox.getText().toUpperCase().replace(" ", "_"));
                        ToppingAmount ta = ToppingAmount.valueOf(tc.amountComboBox.getValue().toUpperCase());
                        toppingList.add(new Topping(tt, ta));
                    } catch (Exception ex) {
                        System.out.println("Error converting topping: " + tc.checkBox.getText());
                    }
                }
            }
            
            // Create the Pizza object and set its attributes.
            Pizza pizza = new Pizza();
            pizza.setSize(selectedSize[0]);
            pizza.setCrust(selectedCrust[0]);
            pizza.setSauce(selectedSauce[0]);
            pizza.setCheese(selectedCheese[0]);
            pizza.setToppings(toppingList.toArray(new Topping[0]));
            
            // --- Pricing Logic ---
            double basePrice = 0;
            double unitPrice = 0;
            switch (selectedSize[0]) {
                case "Personal Pan": 
                    basePrice = 4.99; unitPrice = 0.50; break;
                case "Small": 
                    basePrice = 6.99; unitPrice = 0.70; break;
                case "Medium": 
                    basePrice = 9.99; unitPrice = 0.90; break;
                case "Large": 
                    basePrice = 11.99; unitPrice = 1.10; break;
                case "Extra Large": 
                    basePrice = 14.99; unitPrice = 1.30; break;
            }
            double toppingCost = 0;
            for (Topping t : toppingList) {
                double multiplier = 1.0;
                switch (t.getAmount()) {
                    case LITE:    multiplier = 0.5; break;
                    case REGULAR: multiplier = 1.0; break;
                    case EXTRA:   multiplier = 1.5; break;
                    case DOUBLE:  multiplier = 2.0; break;
                }
                toppingCost += multiplier * unitPrice;
            }
            double cheeseMultiplier = 1.0;
            if (selectedCheese[0].equals("Extra"))
                cheeseMultiplier = 1.5;
            else if (selectedCheese[0].equals("Double"))
                cheeseMultiplier = 2.0;
            double cheeseCost = cheeseMultiplier * unitPrice;
            
            double totalPrice = basePrice + toppingCost + cheeseCost;
            pizza.setPrice(totalPrice);
            
            result[0] = pizza;
            stage.close();
        });

        // --- Assemble the Layout ---
        VBox centerBox = new VBox(10, columnStackPane, navBox);
        centerBox.setPadding(new Insets(10));
        
        BorderPane mainPane = new BorderPane();
        mainPane.setCenter(centerBox);
        mainPane.setRight(graphicPane);
        mainPane.setPadding(new Insets(10));
        
        Scene scene = new Scene(mainPane, 800, 400);
        stage.setScene(scene);
        stage.showAndWait();
        
        return result[0];
    }

    // Updates the Previous and Next buttons based on current column index.
    private static void updateNavButtons(Button prev, Button next, int currentIndex, int totalColumns) {
        prev.setDisable(currentIndex == 0);
        next.setDisable(currentIndex == totalColumns - 1);
    }
    
	// Updates the Confirm Pizza button's state based on required selections.
    static private void updateConfirm(Button conf)
    {
        boolean ready = !selectedSize[0].isEmpty() 
                     && !selectedCrust[0].isEmpty() 
                     && !selectedSauce[0].isEmpty() 
                     && !selectedCheese[0].isEmpty();
        conf.setDisable(!ready);
    }
}