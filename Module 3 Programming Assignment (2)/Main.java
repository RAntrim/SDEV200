import java.math.BigInteger;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Enter the first rational number: ");
        String i1 = input.nextLine();

        String[] halves1 = i1.split(" ");
        BigInteger n1 = new BigInteger(halves1[0]);
        BigInteger d1 = new BigInteger(halves1[1]);
        Rational r1 = new Rational(n1, d1);

        System.out.println("Enter the second rational number: ");
        String i2 = input.nextLine();

        String[] halves2 = i2.split(" ");
        BigInteger n2 = new BigInteger(halves2[0]);
        BigInteger d2 = new BigInteger(halves2[1]);
        Rational r2 = new Rational(n2, d2);

        input.close();

        System.out.println(r1.toString() + " + " + r2.toString() + " = " + r1.add(r2));
        System.out.println(r1.toString() + " - " + r2.toString() + " = " + r1.subtract(r2));
        System.out.println(r1.toString() + " * " + r2.toString() + " = " + r1.multiply(r2));
        System.out.println(r1.toString() + " / " + r2.toString() + " = " + r1.divide(r2));
        System.out.println(r2.toString() + " is " + r2.doubleValue());
    }
}