package BANK;
import java.util.Date;
import java.util.Calendar;
public final class MyDate {
    private int year;
    private int month;
    private int day;
    public static final int [] daysPerMonth = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    public MyDate(int year, int month, int day) throws IllegalArgumentException {
        if (month < 1 || month > 12)
            throw new IllegalArgumentException("Invalid month");
        if (day < 1 || (day > daysPerMonth[month] && !(month == 2 && day == 29)))
            throw new IllegalArgumentException("Invalid day , day is out of range!!");
        if (month == 2 && day == 29 && !(year % 400 == 0 ||(year % 4 == 0 && year % 100 != 0)))
            throw new IllegalArgumentException("day is out of range for specific month and year!");
        this.year = year;
        this.month = month;
        this.day = day;
    }
    public void setDate(int year, int month, int day) {
        if (month < 1 || month > 12)
            throw new IllegalArgumentException("Invalid month");
        if (day < 1 || (day > daysPerMonth[month] && !(month == 2 && day == 29)))
            throw new IllegalArgumentException("Invalid day , day is out of range!!");
        if (month == 2 && day == 29 && !(year % 400 == 0 ||(year % 4 == 0 && year % 100 != 0)))
            throw new IllegalArgumentException("day is out of range for specific month and year!");
        this.year = year;
        this.month = month;
        this.day = day;
    }
    public int getYear() {
        return year;
    }
    public int getMonth() {
        return month;
    }
    public int getDay() {
        return day;
    }
    public static int calculateSubOfDate(MyDate date1 ,MyDate date2) {
        /*
        this function return number of day between two year
        we calculate date2 - date1
         */
        int totalDays = 0;
        totalDays = date2.getDay() - date1.getDay();
        if (date1.getMonth() > date2.getMonth()) totalDays -= calculateMonth(date2.getMonth() , date1.getMonth());
        else {
            totalDays += calculateMonth(date2.getMonth() , date1.getMonth());
        }
        totalDays += (date2.getYear() - date1.getYear()) * 365 + (date2.getMonth() - date1.getMonth()) / 4;
        return totalDays;
    }
    public static int calculateMonth(int month1 ,int month2) {
        int totalDays = 0;
        for (int i = month1; i <= month2; i++) {
            totalDays += daysPerMonth[i];
        }
        return totalDays;
    }
    public static MyDate today(){
        // Get current date
        Date date = new Date();
        // Create calendar instance and set the time
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // Get day, month, year
        int day = calendar.get(Calendar.DAY_OF_MONTH);      // 6 for June 6
        int month = calendar.get(Calendar.MONTH) + 1;       // 6 for June (0-based index)
        int year = calendar.get(Calendar.YEAR);             // e.g., 2025
        return new MyDate(year, month, day);
    }
    public String toString() {
        return year + "-" + month + "-" + day;
    }
}
