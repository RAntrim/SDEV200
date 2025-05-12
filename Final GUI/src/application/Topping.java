package application;

public class Topping {
    private ToppingType type;
    private ToppingAmount amount;

    public Topping(ToppingType type, ToppingAmount amount) {
        this.type = type;
        this.amount = amount;
    }

    public ToppingType getType() {
        return type;
    }

    public void setType(ToppingType type) {
        this.type = type;
    }

    public ToppingAmount getAmount() {
        return amount;
    }

    public void setAmount(ToppingAmount amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return type.toString() + " (" + amount.toString() + ")";
    }
}
