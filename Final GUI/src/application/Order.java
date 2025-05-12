package application;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Order {
    
    // Private fields
    private Pizza[] pizzaList;     // Array of Pizza objects in the order
    private double totalPrice;     // Sum of all pizzas' prices
    private Customer cust;         // Customer object
    private String time;           // Order time as a string ("XX:XX AM/PM")
    
    // Constructor that accepts a list of pizzas and a Customer
    public Order(Pizza[] pizzaList, Customer cust) {
        this.pizzaList = pizzaList;
        this.cust = cust;
        calculateTotal();  // Computes totalPrice from the pizzas
        setTime();         // Sets time to the current time
    }
    
    // Helper method that calculates the total price.
    private void calculateTotal() {
        double sum = 0.0;
        if (pizzaList != null) {
            for (Pizza p : pizzaList) {
                sum += p.getPrice();
            }
        }
        this.totalPrice = sum;
    }
    
    // Returns the array of Pizza objects.
    public Pizza[] getPizza() {
        return pizzaList;
    }
    
    // Returns the total price.
    public double getTotal() {
        return totalPrice;
    }
    
    public Customer getCustomer() {
        return cust;
    }
    
    public String getTime() {
        return time;
    }
    
    // setTime(): Obtains the current time using SimpleDateFormat and stores it.
    public void setTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
        this.time = sdf.format(new Date());
    }
    
    // toString(): Builds a detailed description of the order.
    // It prints out details for each pizza, then the subtotal, taxes/fees, and the final total.
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Order Details:\n\n");
        
        // List each pizza's details.
        if (pizzaList != null && pizzaList.length > 0) {
            for (int i = 0; i < pizzaList.length; i++) {
                sb.append("Pizza ").append(i + 1).append(":\n");
                sb.append(pizzaList[i].toString()).append("\n");
                sb.append("--------------------------\n");
            }
        } else {
            sb.append("No pizzas ordered.\n");
        }
        
        // Append the subtotal.
        sb.append("Subtotal: $").append(String.format("%.2f", totalPrice)).append("\n");
        
        // Compute taxes—for example, 8% tax.
        double tax = totalPrice * 0.08;
        sb.append("Tax (8%): $").append(String.format("%.2f", tax)).append("\n");
        
        // Fees can be added here; we'll assume none for now.
        double fees = 0.0;
        sb.append("Fees: $").append(String.format("%.2f", fees)).append("\n");
        
        double finalTotal = totalPrice + tax + fees;
        sb.append("Final Total: $").append(String.format("%.2f", finalTotal)).append("\n");
        
        sb.append("Order Time: ").append(time).append("\n");
        sb.append("Customer: ").append(cust.toString()).append("\n");
        return sb.toString();
    }
}
