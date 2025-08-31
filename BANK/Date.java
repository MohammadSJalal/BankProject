import java.util.*;

public class Date {
    private int year;
    private int month;
    private int day;
    private static final int[] daysPerMonth = {0,31,28,31,30,31,30,31,31,30,31,30,31};

    public Date(int year, int month, int day) {
        if (month < 1 || month > 12) throw new IllegalArgumentException("Invalid month");
        if (day < 1 || (day > daysPerMonth[month] && !(month == 2 && day == 29)))
            throw new IllegalArgumentException("Invalid day , day is out of range!!");
        if (month == 2 && day == 29 && !isLeapYear(year))
            throw new IllegalArgumentException("day is out of range for specific month and year!");
        this.year = year; this.month = month; this.day = day;
    }

    public void setDate(int y,int m,int d){
        if (m < 1 || m > 12) throw new IllegalArgumentException("Invalid month");
        if (d < 1 || (d > daysPerMonth[m] && !(m==2 && d==29))) throw new IllegalArgumentException("Invalid day");
        if (m==2 && d==29 && !isLeapYear(y)) throw new IllegalArgumentException("Invalid leap day");
        this.year=y; this.month=m; this.day=d;
    }

    public int getYear(){return year;}
    public int getMonth(){return month;}
    public int getDay(){return day;}

    public void incrementDay(){
        day++;
        if ((month==2 && isLeapYear(year) && day>29) || day>daysPerMonth[month]) {
            day = 1; month++;
            if (month>12) { month=1; year++; }
        }
    }

    public void incrementMonth(){
        month++;
        if (month>12) { month=1; year++; }
    }

    private boolean isLeapYear(int y){ return (y%400==0) || (y%4==0 && y%100!=0); }

    @Override
    public String toString(){ return year + "-" + month + "-" + day; }
}
