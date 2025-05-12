package application;

public enum ToppingAmount {
	 LITE("Lite"),
	 REGULAR("Regular"),
	 EXTRA("Extra"),
	 DOUBLE("Double");

	 private String label;
	 private ToppingAmount(String label) { this.label = label; }
	 @Override
	 public String toString() { return label; }
	}