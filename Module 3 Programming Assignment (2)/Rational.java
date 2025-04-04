import java.math.BigInteger;

public class Rational extends Number implements Comparable<Rational> {
    // Internal State
    // Data Fields
    private BigInteger numerator = BigInteger.ZERO;
    private BigInteger denominator = BigInteger.ONE;

    // Find Greatest Common Divisor
    private static BigInteger gcd(BigInteger n, BigInteger d) {return n.gcd(d);}

    // External State
    // Constructors
    public Rational() {this(BigInteger.ZERO, BigInteger.ONE);} // No-arg
    public Rational(BigInteger numerator, BigInteger denominator) // With arguments
    {
        BigInteger gcd = gcd(numerator, denominator);
        this.numerator = (denominator.compareTo(BigInteger.ZERO) > 0 ?
                          BigInteger.ONE : BigInteger.ONE.negate())
                          .multiply(numerator.divide(gcd));
        this.denominator = denominator.abs().divide(gcd);
    }

    // Getters
    public BigInteger getNumerator() {return numerator;}
    public BigInteger getDenominator() {return denominator;}

    // Arithmetic Operations
    public Rational add(Rational secondRational) { // Addition
        BigInteger n = numerator.multiply(secondRational.getDenominator())
                       .add(denominator.multiply(secondRational.getNumerator()));

        BigInteger d = denominator.multiply(secondRational.getDenominator());

        return new Rational(n, d);
    }
    public Rational subtract(Rational secondRational) { // Subtraction
        BigInteger n = numerator.multiply(secondRational.getDenominator())
                       .subtract(denominator.multiply(secondRational.getNumerator()));

        BigInteger d = denominator.multiply(secondRational.getDenominator());

        return new Rational(n, d);
    }
    public Rational multiply(Rational secondRational) { // Multiplication
        BigInteger n = numerator.multiply(secondRational.getNumerator());
        BigInteger d = denominator.multiply(secondRational.getDenominator());

        return new Rational(n, d);
    }
    public Rational divide(Rational secondRational) { // Division
        BigInteger n = numerator.multiply(secondRational.getDenominator());
        BigInteger d = denominator.multiply(secondRational.getNumerator());

        return new Rational(n, d);
    }

    // Overrides
    @Override
    public String toString() {
        return denominator.equals(BigInteger.ONE) ?
        numerator.toString() : numerator + "/" + denominator;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Rational) {
            return this.subtract((Rational) other).getNumerator().equals(BigInteger.ZERO);
        }
        return false;
    }

    @Override
    public double doubleValue() {
        return numerator.doubleValue() / denominator.doubleValue();
    }

    @Override
    public int intValue() {
        return (int)doubleValue();
    }

    @Override
    public float floatValue() {
        return (float)doubleValue();
    }

    @Override
    public long longValue() {
        return (long)doubleValue();
    }

    @Override
    public int compareTo(Rational o) {
        return this.subtract(o).getNumerator().compareTo(BigInteger.ZERO);
    }
}
