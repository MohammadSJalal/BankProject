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
    private Bank bank;
    public Account(Customer OwnerAccountName, Bank bank ) {
        this.OwnerAccountName = OwnerAccountName;
        this.bank = bank;
        bank.addCustomer(OwnerAccountName); //   here we push customer inside of bank customers
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
    public String createNDigitString(int n, int numberOfDigits) throws IllegalArgumentException {
        String s = Integer.toString(n);
        StringBuilder zeros = new StringBuilder();
        if (s.length() == numberOfDigits){
            return s;
        }
        else if (numberOfDigits > s.length()){
            for (int i = 0; i < numberOfDigits - s.length(); i++){
                zeros.append("0");
            }
            return zeros.toString() + n;
        }
        else{
            throw new IllegalArgumentException("it can not be length of number less than "+numberOfDigits+" digits");
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
    public void withdraw(double amount) throws IllegalArgumentException {
        // withdraw is type 1
        checkValue(amount,1);
        this.balance -= amount;
    }
    public double deposit(double amount) throws IllegalArgumentException {
        //deposit is type 2
        checkValue(amount, 2);
        this.balance += amount;
        return balance;
    }
    public void transferMoney(double amount, String AccountNumber) throws IllegalArgumentException {
        try{
            checkValidityOfAccountNumber(AccountNumber);
            this.withdraw(amount);
            bank.searchAccount(AccountNumber).deposit(amount);
        }
        catch (IllegalArgumentException e){
            System.out.println("please enter a valid account number");
        }
    }
    @Override
    public String toString() {
        return "account owner name: " + OwnerAccountName.getName() + ", account number: " + accountNumber + ", balance: " + balance + ", date of opening: " + dateOfOpening;
    }
    public void checkValue(double value , int type) {
        /* if type = 1 then we can get result it is withdraw
           type = 2 -> deposit
           type = 3 -> transfer
         */
        if (value < 0) throw new IllegalArgumentException("value must be greater than 0");
        else if (value > balance && (type == 3 || type == 1)) throw new IllegalArgumentException("value must be less than balance");
    }
    public void checkValidityOfAccountNumber(String AccountNumber) throws IllegalArgumentException {
        int typeAccount = Integer.valueOf(AccountNumber.charAt(0)) - 48;
        int year = Integer.valueOf(AccountNumber.substring(1,5));
        int month = Integer.valueOf(AccountNumber.substring(5,7));
        int day = Integer.valueOf(AccountNumber.substring(7,9));
        if (!(typeAccount <=3 && typeAccount >=1 && year>= MyDate.today().getYear()-100 && year <=MyDate.today().getYear()
        &&  month >= 1 && month <= 12 && day >= 1 && day <= 31)) {
            throw new IllegalArgumentException("Invalid account number");
        }
    }
}
