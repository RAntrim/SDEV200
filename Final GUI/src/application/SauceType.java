package application;

public enum SauceType {
	 MARINARA("Marinara"),
	 SWEET_MARINARA("Sweet Marinara"),
	 BBQ("BBQ"),
	 ALFREDO_SAUCE("Alfredo Sauce");

	 private String label;
	 private SauceType(String label) { this.label = label; }
	 @Override
	 public String toString() { return label; }
	}