public class Circle extends GeometricObject implements Comparable<Circle> {
    // Internal State
    private double radius;

    // External State
    // Constructors
    public Circle() {}
    public Circle(double radius, String color, boolean filled)
    {
        this.radius = radius;
        setColor(color);
        setFilled(filled);
    }

    // Setter(s)
    public void setRadius(double radius) {this.radius = radius;}

    // Getter(s)
    public double getRadius() {return radius;}
    public double getArea() {return radius * radius * Math.PI;}
    public double getDiameter() {return 2 * radius;}

    // Other
    public void printCircle()
    {
        System.out.println
        ("The circle is created " + getDateCrated() + " and the radius is " + radius);
    }

    @Override
    public boolean equals(Circle o) {return o.getRadius() == this.getRadius();}
}
