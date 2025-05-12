package application;

public enum CrustType {
	 PAN("Pan"),
	 THIN("Thin"),
	 CHICAGO_STYLE("Chicago Style"),
	 STUFFED_CRUST("Stuffed Crust");

	 private String label;
	 private CrustType(String label) { this.label = label; }
	 @Override
	 public String toString() { return label; }
}