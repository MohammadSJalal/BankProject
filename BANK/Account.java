package BANK;
import java.util.*;
import java.util.Date;
import java.util.Calendar;
public abstract class Account{
    public static Scanner input = new Scanner(System.in);
    private String accountNumber;
    private double balance;
    private MyDate dateOfOpening;
    private Customer OwnerAccountName;
    public Account(Customer OwnerAccountName) {
        dateOfOpening = MyDate.today();
        this.OwnerAccountName = OwnerAccountName;
    }
    // all set functions //
    public void setBalance(double balance) {
        if (balance >= 0) {
            this.balance = balance;
        }
        else throw new IllegalArgumentException("are you kidding me !!! how balance is less than 0 ??");
    }
    public static String countString(String n) {
        /*
        this function get a string like: 00
        and then add one to it -> 01
        then return new string
         */
        if (n.length() == 0){
            return "1";
        }
        int a = n.charAt(n.length() - 1) ;
        a-=47;
        if (a == 10){
            return countString(n.substring(0, n.length() - 1))+"0";
        }
        else{
            return n.substring(0,n.length()-1)+a;
        }
    }
    public boolean setDateOfOpening(MyDate dateOfOpening , boolean isBoss) {
        if (isBoss) {
            System.out.println("please enter your password");
            this.dateOfOpening = dateOfOpening;
            return true;
        }
        else {
            this.dateOfOpening = MyDate.today();
        }
        return false;
    }
    protected void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    // all get functions //
    public double getBalance() {
        return this.balance;
    }
    public String getAccountNumber() {
        return this.accountNumber;
    }

    public MyDate getDateOfOpening() {
        return dateOfOpening;
    }

    // other functions //

    /*
    i set account number such:
    x       xxxx        xxxx        xxxx
    type    year    month/day       n th customer that opened account
    */
    public abstract void createAccountNumber();
    //im there for completing
    public void withdraw(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("amount must be greater than 0");
        else if (amount > balance) throw new IllegalArgumentException("amount must be less than balance");

    }
    @Override
    public String toString() {
        return "account owner name: " + OwnerAccountName + ", account number: " + accountNumber + ", balance: " + balance + ", date of opening: " + dateOfOpening;
    }
}
