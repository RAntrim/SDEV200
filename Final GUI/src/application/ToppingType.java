package application;

public enum ToppingType {
    PEPPERONI("Pepperoni"),
    SAUSAGE("Sausage"),
    BEEF("Beef"),
    HAM("Ham"),
    BACON("Bacon"),
    BLACK_OLIVES("Black Olives"),
    PINEAPPLE("Pineapple"),
    MUSHROOMS("Mushrooms"),
    ONION("Onion"),
    BANANA_PEPPERS("Banana Peppers"),
    JALAPENOS("Jalapenos");

    private String label;
    private ToppingType(String label) { this.label = label; }
    @Override 
    public String toString() { return label; }
}