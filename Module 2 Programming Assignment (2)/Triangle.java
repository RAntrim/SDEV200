public class Triangle extends GeometricObject {
    // Data Fields
    private double side1 = 1.0;
    private double side2 = 1.0;
    private double side3 = 1.0;

    // Constructors
    public Triangle() { // No-arg
        super();
        this.side1 = 1.0;
        this.side2 = 1.0;
        this.side3 = 1.0;
    }
    public Triangle(double s1, double s2, double s3) { // User-specified side lengths
        super();
        this.side1 = s1;
        this.side2 = s2;
        this.side3 = s3;
    }

    // Data Fields' Getters
    public double getSideOne() {
        return side1;
    }
    public double getSideTwo() {
        return side2;
    }
    public double getSideThree() {
        return side3;
    }

    // Other Getters
    public double getArea() {
        double s = (side1 + side2 + side3) / 2;
        return Math.sqrt(s * (s - side1) * (s - side2) * (s - side3));
    }
    public double getPerimeter() {
        return side1 + side2 + side3;
    }

    // String Description
    public String toString() {
        return "Triangle: side1 = " + side1
        + " side2 = " + side2
        + " side3 = " + side3;
    }
}
