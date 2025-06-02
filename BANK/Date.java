package BANK;

public class Date {
    private int year;
    private int month;
    private int day;
    private static final int [] daysPerMonth = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    public Date(int year, int month, int day) {
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
    public static int calculateSubOfDate(Date date1 ,Date date2) {
        /*
        this function return number of day between two year
        we calculate date2 - date1
         */
        int totalDays = 0;
        totalDays = date1.getDay() - date2.getDay();
        if (date1.getMonth() > date2.getMonth()) totalDays -= calculateMonth(date2.getMonth() , date1.getMonth());
        else {
            totalDays += calculateMonth(date2.getMonth() , date1.getMonth());
        }
        totalDays += (date2.getDay() - date1.getDay()) * 365 + (date2.getMonth() - date1.getMonth()) / 4;
        return totalDays;
    }
    public static int calculateMonth(int month1 ,int month2) {
        int totalDays = 0;
        for (int i = month1; i <= month2; i++) {
            totalDays += daysPerMonth[i];
        }
        return totalDays;
    }
    public String toString() {
        return year + "-" + month + "-" + day;
    }
}
