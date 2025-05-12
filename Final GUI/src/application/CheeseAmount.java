package application;

public enum CheeseAmount {
	 NONE("None"),
	 LITE("Lite"),
	 REGULAR("Regular"),
	 EXTRA("Extra"),
	 DOUBLE("Double");

	 private String label;
	 private CheeseAmount(String label) { this.label = label; }
	 @Override
	 public String toString() { return label; }
	}