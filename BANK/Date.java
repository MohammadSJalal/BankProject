public class Date {
    private int year;
    private int month;
    private int day;
    private static final int[] daysPerMonth = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    public Date(int year, int month, int day) {
        if (month < 1 || month > 12)
            throw new IllegalArgumentException("Invalid month");
        if (day < 1 || (day > daysPerMonth[month] && !(month == 2 && day == 29)))
            throw new IllegalArgumentException("Invalid day , day is out of range!!");
        if (month == 2 && day == 29 && !isLeapYear(year))
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
        if (month == 2 && day == 29 && !isLeapYear(year))
            throw new IllegalArgumentException("day is out of range for specific month and year!");
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public int getYear() { return year; }
    public int getMonth() { return month; }
    public int getDay() { return day; }

    public void incrementDay() {
        day++;
        if ((month == 2 && isLeapYear(year) && day > 29) || day > daysPerMonth[month]) {
            day = 1;
            month++;
            if (month > 12) {
                month = 1;
                year++;
            }
        }
    }

    public void incrementMonth() {
        month++;
        if (month > 12) {
            month = 1;
            year++;
        }
    }

    private boolean isLeapYear(int year) {
        return (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0));
    }

    @Override
    public String toString() {
        return year + "-" + month + "-" + day;
    }
}
