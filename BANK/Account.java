package BANK;
import java.util.*;
import java.util.Date;
import java.util.Calendar;
import java.util.HashMap;
public abstract class Account{
    public static Scanner input = new Scanner(System.in);
    private String accountNumber;
    private double balance;
    private MyDate dateOfOpening;
    private Customer OwnerAccountName;
    private boolean haveLoan;
    //this is for changes of balance over period of time and get average for calculate loan
    private ArrayList<Double> tornover;
    //this is history of all Transaction
    HashMap <Date , Double []> transactions;
    private Bank bank;
    public Account(Customer OwnerAccountName, Bank bank ) {
        this.OwnerAccountName = OwnerAccountName;
        this.bank = bank;
        bank.addCustomer(OwnerAccountName); //   here we push customer inside of bank customers
        this.tornover = new ArrayList<>(); // this save all withdraw or deposit of customer
        this.transactions = new HashMap<>();
        this.haveLoan = false;
        this.setBalance(0);
    }
    public Account (Customer OwnerAccountName , Bank bank , double balance) {
        this.OwnerAccountName = OwnerAccountName;
        this.bank = bank;
        bank.addCustomer(OwnerAccountName); //   here we push customer inside of bank customers
        this.tornover = new ArrayList<>(); // this save all withdraw or deposit of customer
        this.transactions = new HashMap<>();
        this.haveLoan = false;
        this.setBalance(balance);
    }
    public final boolean getHaveLoan(char EmployeeType) throws IllegalArgumentException {
        if (EmployeeType == 'A' || EmployeeType == 'M'){
            return haveLoan;
        }
        else throw new IllegalArgumentException("we can not answer to other people except manager or Assistance Manager");
    }
    // all set functions //
    private void setBalance(double balance) {
        if (balance >= 0) {
            this.balance = balance;
            this.tornover.add(balance);
            this.bank.addToTotalAmountOfMoney(balance);
        }
        else throw new IllegalArgumentException("are you kidding me !!! how balance is less than 0 ??");
    }
    public final void setHaveLoan(boolean haveLoan , char EmployeeType) throws IllegalArgumentException {
        if (EmployeeType == 'A' || EmployeeType == 'M'){
            this.haveLoan = haveLoan;
        }
        else throw new IllegalArgumentException("we can not set have Loan with request of other people except manager");
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
    public final boolean setDateOfOpening(MyDate dateOfOpening) {
        this.dateOfOpening = dateOfOpening;
        return true;
    }
    protected final void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    // all get functions //
    public final double getBalance() {
        return this.balance;
    }
    public final String getAccountNumber() {
        return this.accountNumber;
    }
    public final MyDate getDateOfOpening() {
        return dateOfOpening;
    }

    // other functions //

    /*
    i set account number such:
    x       xxxx        xxxx        xxxx
    type    year    month/day       n th customer that opened account
    */
    public final void withdraw(double amount) throws IllegalArgumentException {
        // withdraw is type 1
        checkValue(amount,1);
        this.balance -= amount;
        bank.addToTotalAmountOfMoney(-amount);
        Date d = new Date();
        Double [] value = {-amount , balance};
        transactions.put( d , value);
    }
    public final double deposit(double amount) throws IllegalArgumentException {
        //deposit is type 2
        checkValue(amount, 2);
        this.balance += amount;
        bank.addToTotalAmountOfMoney(amount);
        Date d = new Date();
        Double [] value = {amount , balance};
        transactions.put(d , value);
        return balance;
    }
    public void transferMoney(double amount, String AccountNumber) throws IllegalArgumentException {
        try{
            checkValidityOfAccountNumber(AccountNumber);
            this.withdraw(-amount);
            bank.searchAccount(AccountNumber).deposit(amount);
        }
        catch (IllegalArgumentException e){
            System.out.println("please enter a valid account number");
        }
    }

    /** this function check condition for valid withdraw , deposit and transfer that corresponding condition checked with:
     * if type = 1 then we can get result it is withdraw
     *type = 2 -> deposit
     *type = 3 -> transfer
    */
    public void checkValue(double value , int type) throws IllegalArgumentException {
        if (value < 0) throw new IllegalArgumentException("value must be greater than 0");
        else if (value > balance && (type == 3 || type == 1)) throw new IllegalArgumentException("value must be less than balance");
    }

    /**
     * this function give a average balance for calculate loan point
     * @param intervalOfMonths this is for specifying
     * @return
     */
    public final double getAverageBalance(int intervalOfMonths) {
        //this is incomplete
        double sum = 0.0;
        for (double i : tornover) {
            sum += i;
        }
        return sum / tornover.size();
    }
    /**
     * this is function that show all history of account
     */
    public final void showTransactions() {
        for (Date d : transactions.keySet()) {
            System.out.println(d +": " + transactions.get(d)[0] + " rest of money " + transactions.get(d)[1]);
        }
    }
    /** this function let to create an account for customer if they have not already same account*/
    public static boolean permission(Customer c,char typeOfAccount) {
        if (c.getAccounts().size() > 3) return false;
        switch (typeOfAccount) {
            case '1':
                return check(c,'1');
            case '2':
                return check(c,'2');
            case '3':
                return check(c,'3');
        }
        return false;
    }
    private static boolean check(Customer c , char typeOfAccount) {
        for (Account a : c.getAccounts()) {
            if (a.getAccountNumber().charAt(0) == typeOfAccount) {
                return false;
            }
        }
        return true;
    }
    /**
     * this function is for search a account based on account number of it
     * @param accounts accounts of customer
     * @param accountNumber
     * @return wanted account
     */
    public static Account searchAccount(ArrayList<Account> accounts , String accountNumber) {
        for (Account a : accounts) {
            if (a.getAccountNumber().equals(accountNumber)) {
                return a;
            }
        }
        throw new IllegalArgumentException("account not found");
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
    @Override
    public String toString() {
        String infoLoan = (haveLoan ? "yes" : "no");
        return "\naccount owner name: " + OwnerAccountName.getName() + "\naccount number: 0" + accountNumber +
                "\nbalance: " + balance + "\ndate of opening: " + dateOfOpening+
                "\nhave loan: " + infoLoan+"\n";
    }
}
