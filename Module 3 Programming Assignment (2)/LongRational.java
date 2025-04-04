public class LongRational extends Number implements Comparable<LongRational> {
    // Internal State
    // Data Fields
    private long numerator = 0;
    private long denominator = 1;

    // Find Greatest Common Divisor
    private static long gcd(long n, long d)
    {
        // Declare variables
        long n1 = Math.abs(n);
        long n2 = Math.abs(d);
        int gcd = 1;

        // For every number less than the numerator and denominator:
        for (int k = 1; k <= n1 == k <= n2; k++)
        {
            // If the numerator and denominator are divisible by said number:
            if (n1 % k == 0 && n2 % k == 0)
                // That number is the new Greatest Common Divisor (so far).
                gcd = k;
        }
        return gcd;
    }

    // External State
    // Constructors
    public LongRational() {this(0, 1);} // No-arg
    public LongRational(long numerator, long denominator) // With arguments
    {
        long gcd = gcd(numerator, denominator);
        this.numerator = (denominator > 0 ? 1 : -1) * numerator / gcd;
        this.denominator = Math.abs(denominator) / gcd;
    }

    // Getters
    public long getNumerator() {return numerator;}
    public long getDenominator() {return denominator;}

    // Arithmetic Operations
    public LongRational add(LongRational secondLongRational) { // Addition
        long n = numerator * secondLongRational.getDenominator()
               + denominator * secondLongRational.getNumerator();

        long d = denominator * secondLongRational.getDenominator();

        return new LongRational(n, d);
    }
    public LongRational subtract(LongRational secondLongRational) { // Subtraction
        long n = numerator * secondLongRational.getDenominator()
               - denominator * secondLongRational.getNumerator();

        long d = denominator * secondLongRational.getDenominator();

        return new LongRational(n, d);
    }
    public LongRational multiply(LongRational secondLongRational) { // Multiplication
        long n = numerator * secondLongRational.getNumerator();
        long d = denominator * secondLongRational.getDenominator();

        return new LongRational(n, d);
    }
    public LongRational divide(LongRational secondLongRational) { // Division
        long n = numerator * secondLongRational.getDenominator();
        long d = denominator * secondLongRational.numerator;

        return new LongRational(n, d);
    }

    // Overrides
    @Override
    public String toString() {
        if (denominator == 1)
            return numerator + "";
        else
            return numerator + "/" + denominator;
    }

    @Override
    public boolean equals(Object other) {
        if ((this.subtract((LongRational)(other))).getNumerator() == 0)
            return true;
        else
            return false;
    }

    @Override
    public double doubleValue() {
        return numerator *  1.0 / denominator;
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
    public int compareTo(LongRational o) {
        if (this.subtract(o).getNumerator() > 0)
            return 1;
        else if (this.subtract(o).getNumerator() < 0)
            return -1;
        else
            return 0;
    }
}
