import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // Gathering triangle-specific data
        System.out.print("Enter three side lengths for your triangle: ");
        double s1 = input.nextDouble();
        double s2 = input.nextDouble();
        double s3 = input.nextDouble();
        input.nextLine(); // Clearing newline character
        Triangle newTri = new Triangle(s1, s2, s3);

        // Gathering geometric object-specific data
        System.out.println("\nEnter the color of your triangle: ");
        // Color
        newTri.setColor(input.nextLine());

        System.out.print("\nIs your triangle filled in? Y/N: ");
        // Filled
        char f = Character.toLowerCase(input.next().trim().charAt(0));
        if (f == 'y') {
            newTri.setFilled(true);
        } else if (f == 'n') {
            newTri.setFilled(false);
        } else {
            System.out.println("Invalid input. Setting the value of \"isFilled\" to false.");
            newTri.setFilled(false);
        }

        input.close();

        // Displaying shape data
        System.out.print(newTri.toString() + " color = " + newTri.getColor() + " isFilled = ");
        if (newTri.isFilled() == true) {
            System.out.println("true");
        } else {
            System.out.println("false");
        }
    }
}
