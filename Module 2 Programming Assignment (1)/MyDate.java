import java.util.GregorianCalendar;

public class MyDate {
    // Data Fields
    private int year;
    private int month;
    private int day;

    // Constructors
    public MyDate() { // No-arg
        this(System.currentTimeMillis());
    }
    public MyDate(long elapsedTime) { // Specified time since 12:00AM, 1/1/1970
        setDate(elapsedTime);
    }
    public MyDate(int year, int month, int day) { // Specified date
        this.year = year;
        this.month = month;
        this.day = day;
    }

    // Getters
    public int getYear() {
        return year;
    }
    public int getMonth() {
        return month;
    }
    public int getDay() {
        return day;
    }

    // Setter
    public void setDate(long elapsedTime) {
        GregorianCalendar calendar = new GregorianCalendar();

        calendar.setTimeInMillis(elapsedTime);
        this.year = calendar.get(GregorianCalendar.YEAR);
        this.month = calendar.get(GregorianCalendar.MONTH);
        this.day = calendar.get(GregorianCalendar.DAY_OF_MONTH);
    }
}
