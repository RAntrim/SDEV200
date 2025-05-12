package application;

public class Customer {
    // Fields
    private String phoneNumber; // exactly 10 characters
    private String name;
    private String address;
    private String preference;
    
    public Customer(String phone, String name, String address, String pref) {
        setPhone(phone);
        setName(name);
        setAddress(address);
        setPreference(pref);
    }
    
    // Modifier: setPhone() expects a String and stores the first 10 characters.
    public void setPhone(String phone)
    { phoneNumber = phone; }
    
    // Accessor: returns the phone number as a String.
    public String getPhone() { return new String(phoneNumber); }
    
    // Modifier for name
    public void setName(String name) { this.name = name; }
    
    // Accessor for name
    public String getName() { return name; }
    
    // Modifier for address
    public void setAddress(String address) { this.address = address; }
    
    // Accessor for address
    public String getAddress() { return address; }
    
    // Modifier for delivery preference
    public void setPreference(String preference) { this.preference = preference; }
    
    // Accessor for delivery preference
    public String getPreference() { return preference; }
    
    // Optional: A toString method to display customer details.
    @Override
    public String toString()
    {
        return "Customer Name: " + name + "\n" +
               "Phone: " + getPhone() + "\n" +
               "Address: " + address + "\n" +
               "Preferences: " + preference;
    }
}
