public class MyDateTester {
    public static void main(String[] args) {
        // Invoking no-arg constructor
        MyDate d1 = new MyDate();

        // Invoking long elapsedTime constructor
        MyDate d2 = new MyDate(34355555133101L);

        // Example to ensure proper output
        MyDate d3 = new MyDate(561555550000L);

        // Display the year, month, and day for both dates.
        // Note: Month is 0-based. Add 1 for display as a human-readable month if desired.
        System.out.println("Date 1: No-arg");
        System.out.println("Year:  " + d1.getYear());
        System.out.println("Month: " + d1.getMonth());
        System.out.println("Day:   " + d1.getDay() + '\n');
        
        System.out.println("Date 2: Given (long elapsedTime)");
        System.out.println("Year:  " + d2.getYear());
        System.out.println("Month: " + d2.getMonth());
        System.out.println("Day:   " + d2.getDay() + '\n');

        System.out.println("Date 3: Example");
        System.out.println("Year:  " + d3.getYear());
        System.out.println("Month: " + d3.getMonth());
        System.out.println("Day:   " + d3.getDay());
    }
}
