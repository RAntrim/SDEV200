package application;

public class Pizza {
    private PizzaSize size;
    private CrustType crust;
    private SauceType sauce;
    private CheeseAmount cheese;
    private int toppingCount;
    private Topping[] toppings;
    private double price;

    // Default constructor
    public Pizza()
    {
        toppingCount = 0;
        toppings = new Topping[0];
        price = 0.0;
    }

    // Setters convert from a String (from the GUI) to an enum
    public void setSize(String sizeStr) {
        if(sizeStr.equals("Personal Pan"))
            this.size = PizzaSize.PERSONAL_PAN;
        else if(sizeStr.equals("Small"))
            this.size = PizzaSize.SMALL;
        else if(sizeStr.equals("Medium"))
            this.size = PizzaSize.MEDIUM;
        else if(sizeStr.equals("Large"))
            this.size = PizzaSize.LARGE;
        else if(sizeStr.equals("Extra Large"))
            this.size = PizzaSize.EXTRA_LARGE;
    }
    public PizzaSize getSize() { return size; }

    public void setCrust(String crustStr) {
        if(crustStr.equals("Pan"))
            this.crust = CrustType.PAN;
        else if(crustStr.equals("Thin"))
            this.crust = CrustType.THIN;
        else if(crustStr.equals("Chicago Style"))
            this.crust = CrustType.CHICAGO_STYLE;
        else if(crustStr.equals("Stuffed Crust"))
            this.crust = CrustType.STUFFED_CRUST;
    }
    public CrustType getCrust() { return crust; }

    public void setSauce(String sauceStr) {
        if(sauceStr.equals("Marinara"))
            this.sauce = SauceType.MARINARA;
        else if(sauceStr.equals("Sweet Marinara"))
            this.sauce = SauceType.SWEET_MARINARA;
        else if(sauceStr.equals("BBQ"))
            this.sauce = SauceType.BBQ;
        else if(sauceStr.equals("Alfredo Sauce"))
            this.sauce = SauceType.ALFREDO_SAUCE;
    }
    public SauceType getSauce() { return sauce; }

    public void setCheese(String cheeseStr) {
        if(cheeseStr.equals("None"))
            this.cheese = CheeseAmount.NONE;
        else if(cheeseStr.equals("Lite"))
            this.cheese = CheeseAmount.LITE;
        else if(cheeseStr.equals("Regular"))
            this.cheese = CheeseAmount.REGULAR;
        else if(cheeseStr.equals("Extra"))
            this.cheese = CheeseAmount.EXTRA;
        else if(cheeseStr.equals("Double"))
            this.cheese = CheeseAmount.DOUBLE;
    }
    public CheeseAmount getCheese() { return cheese; }

    // For toppings a setter accepts an array of Topping objects.
    public void setToppings(Topping[] toppings)
    {
        this.toppings = toppings;
        this.toppingCount = (toppings != null) ? toppings.length : 0;
    }
    public Topping[] getToppings() { return toppings; }
    public int getToppingCount() { return toppingCount; }

    public void setPrice(double price) { this.price = price; }
    public double getPrice() { return price; }

    // A toString that uses the enum's toString values to generate a printable description.
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Size: ").append(size.toString()).append("\n")
          .append("Crust: ").append(crust.toString()).append("\n")
          .append("Sauce: ").append(sauce.toString()).append("\n")
          .append("Cheese: ").append(cheese.toString()).append("\n")
          .append("Topping Count: ").append(toppingCount).append("\n")
          .append("Toppings: ");
        if(toppings != null && toppings.length > 0)
        {
            for(Topping t : toppings)
                sb.append(t.toString()).append(", ");
            // Remove the trailing comma and space.
            sb.delete(sb.length() - 2, sb.length());
        } else
            sb.append("None");
        sb.append("\nPrice: $").append(String.format("%.2f", price));

        return sb.toString();
    }
}
