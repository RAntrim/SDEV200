public abstract class GeometricObject
{
  // Data Fields
  private String color = "white";
  private boolean filled = true;

  // Constructors
  protected GeometricObject() {} // Default
  protected GeometricObject(String color, boolean filled) // With Arguments
  {
    this.color = color;
    this.filled = filled;
  }

  // Getters
  public String getColor() {
    return color;
  }
  public boolean isFilled() {
    return filled;
  }

  // Setters
  public void setColor(String color) {
    this.color = color;
  }
  public void setFilled(boolean filled) {
    this.filled = filled;
  }

  // Abstract Methods
  public abstract double getArea();
  public abstract double getPerimeter();
}