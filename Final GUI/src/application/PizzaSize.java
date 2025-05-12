package application;

public enum PizzaSize {
	 PERSONAL_PAN("Personal Pan"),
	 SMALL("Small"),
	 MEDIUM("Medium"),
	 LARGE("Large"),
	 EXTRA_LARGE("Extra Large");

	 private String label;
	 private PizzaSize(String label) { this.label = label; }
	 @Override
	 public String toString() { return label; }
	}