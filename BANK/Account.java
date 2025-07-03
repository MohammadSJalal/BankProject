package BANK;
import java.util.*;
import java.util.Date;
import java.util.HashMap;
public abstract class Account{
    public static Scanner input = new Scanner(System.in);
    protected static HashMap<String,Account> accounts = new HashMap<String,Account>();
    private String accountNumber;
    private double balance;
    private MyDate dateOfOpening;
    private Customer ownerAccount;
    private boolean haveLoan;
    private double limit;
    private boolean blocked;
    //this is for changes of balance over period of time and get average for calculate loan
    private ArrayList<Double> tornover;
    //this is history of all Transaction
    HashMap <Date , Double []> transactions;
    protected Bank bank;
    public Account(Customer ownerAccount, Bank bank ) {
        this.ownerAccount = ownerAccount;
        this.bank = bank;
        this.tornover = new ArrayList<>(); // this save all withdraw or deposit of customer
        this.transactions = new HashMap<>();
        this.haveLoan = false;
        this.setBalance(0);
        this.limit = 0;
        this.blocked = false;
    }
    public Account (Customer ownerAccount, Bank bank , double balance) {
        this.ownerAccount = ownerAccount;
        this.bank = bank;
        this.tornover = new ArrayList<>(); // this save all withdraw or deposit of customer
        this.transactions = new HashMap<>();
        this.haveLoan = false;
        this.limit = 0;
        this.setBalance(balance);
        this.blocked = false;
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

    /**
     * this function applied limitation over account
     * @param limit
     * @return false operation wasn't succeed and true for succeed
     */
    public final boolean setLimit(double limit) {
        if (limit > 0 && this.limit == 0)return false;
        else if ( limit == 0 && this.limit >0) this.getOwner().getReferBranch().bank.limitedAccounts.remove(this);
        else if (limit < 0) throw new AccountLimitation("limit is negative cannot apply");
        this.limit = limit;
        Letter noticeA = new Letter ("bank apply limitation to your account "+ limit +"$ for your contract",MyDate.today());
        ownerAccount.receiveMessage(noticeA);
        return true;
    }
    public final void setHaveLoan(boolean haveLoan , char EmployeeType) throws IllegalArgumentException {
        if (EmployeeType == 'A' || EmployeeType == 'M'){
            this.haveLoan = haveLoan;
        }
        else throw new IllegalArgumentException("we can not set have Loan with request of other people except manager");
    }
    /**
     * this function applied block over account
     * @param blocked true for apply block and false for turn back to unblock
     * @return false operation wasn't succeed and true for succeed
     */
    public final boolean setBlocked(boolean blocked) {
        if (this.blocked == blocked) return false;
        this.blocked = blocked;
        Letter noticeA ;
        if (blocked){
            noticeA = new Letter ("bank apply block your account",MyDate.today());
            this.getOwner().getReferBranch().bank.limitedAccounts.add(this);
        }
        else{
            noticeA = new Letter ("bank unblock your account",MyDate.today());
            this.getOwner().getReferBranch().bank.limitedAccounts.remove(this);
        }
        ownerAccount.receiveMessage(noticeA);
        return true;
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
    public static String createNDigitString(int n, int numberOfDigits) throws IllegalArgumentException {
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
    private void checkLimitationOfAccount(double amount) throws AccountLimitation {
        if (balance - amount < limit) throw new AccountLimitation("you can not have less than " + limit + " balance " +
                "you already accept that limit your account");
    }
    // all get functions //
    public final double getBalance() {
        return this.balance;
    }
    public final String getAccountNumber() {
        return this.accountNumber;
    }
    public abstract String getAccountNumberForType();
    public final MyDate getDateOfOpening() {
        return dateOfOpening;
    }
    public final Customer getOwner() {return ownerAccount;}
    // other functions //

    /**
     * this is function that apply withdraw to account
     * @param amount this is amount that will be decrease of balance
     * @param typeOfFee we have 0-> normal withdraw / 1-> transfer (same bank) / 2->transfer (another bank) / 3-> ?
     * @throws IllegalArgumentException
     * @throws NotEnoughBalance
     * @throws AccountIsBlocked
     * @throws AccountLimitation
     */
    public final void withdraw(double amount , int typeOfFee) throws IllegalArgumentException , NotEnoughBalance ,AccountIsBlocked , AccountLimitation {
        // withdraw is type 1
        checkValue(amount,1);
        checkLimitationOfAccount(amount);
        if (blocked) throw new AccountIsBlocked("your account due of security reasons is blocked");
        if (balance < amount + bank.fees[typeOfFee]) throw new NotEnoughBalance("your balance is not enough");
        this.balance -= amount - bank.fees[typeOfFee];    // i must append a
        bank.addToTotalAmountOfMoney(-amount);
        tornover.add(balance);
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
        tornover.add(balance);
        Double [] value = {amount , balance};
        transactions.put(d , value);
        return balance;
    }
    public void transferMoney(double amount, String toAcc) throws IllegalArgumentException ,AccountIsBlocked , AccountLimitation {
        if (blocked) throw new AccountIsBlocked("your account due of security reasons is blocked");
        if (balance - amount < limit) throw new AccountLimitation("your account have limitation of less than " + limit + " balance");
        Account accTo = accounts.get(toAcc);
        this.withdraw(amount,1);
        accTo.deposit(amount);
//        String bankAccountIdFrom = accountNumber.substring(1,4);
//        String bankAccountIdTo = toAcc.substring(1,4);
//        checkValidityOfAccountNumber(toAcc);
//        if (bankAccountIdFrom.equals(bankAccountIdTo)){
//            this.withdraw(amount,1);
//            bank.searchAccount(toAcc).deposit(amount);
//        }
//        else {
//            Account toAccount = Bank.findBank(bankAccountIdTo).searchAccount(toAcc);
//            toAccount.deposit(amount);
//            this.withdraw(amount,1);
//        }
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
     * @return average
     */
    public final double getAverageBalance() {
        //this is incomplete
        double sum = 0.0;
        for (double i : tornover) {
            sum += i;
        }
        sum /= tornover.size();
        if (balance * 10000 < sum) return balance;
        else if (balance * 100 < sum) return (balance + sum/1000)/2;
        else if (balance * 10 < sum) return (balance + sum/10)/2;
        else if (balance > sum * 10000) return sum;
        else if (balance > sum * 100) return (balance/1000 + sum)/2;
        else if (balance > sum * 10) return (balance/10 + sum)/2;
        else return (balance + sum)/2;
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
        if (c.getAccounts().size() == 9) return false;
        else if (check(c,typeOfAccount) > '9' || check(c,typeOfAccount) < '1') return false;
        else return true;
    }
    public static String validAccountNumber(String accountNumber) {
        return "";// this must be handel
    }

    /**
     * this function check have customer any same account and how many and every time that it find one
     * of them count it such this 1 4 7 and return the last generated number as cher when it return 7 for
     * example it mean the customer had 2 account and now he or she have 3 account that the type of them
     * are same and the value that are returned can used for create account number
     */
    public static char check(Customer c , char typeOfAccount) {
        for (Account acc : c.getAccounts()){
            if (acc.getAccountNumber().charAt(0) == typeOfAccount) typeOfAccount += 3;
        }
        return typeOfAccount;
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
    public static char findOriginTypeAccount(char firstDigit) throws IllegalArgumentException {
        if (firstDigit < '1' || firstDigit > '9') throw new IllegalArgumentException("firstDigit must be between 1 and 9");
        while (firstDigit > '3'){
            firstDigit -= 3;
        }
        return firstDigit;
    }
    public static Account searchAccount(ArrayList<Account> accounts , int firstDigit , int nth) throws IllegalArgumentException {
        if (nth < 1 || nth > 3) throw new IllegalArgumentException("nth must be greater than 0");
        for (Account a : accounts) {
            if (a.getAccountNumber().charAt(0) - 48 == firstDigit) {
                if (nth == 1) return a;
                else nth--;
            }
        }
        throw new IllegalArgumentException("account not found");
    }
    public static Account searchAccount(ArrayList<Account> acc , char firstDigit)throws IllegalArgumentException {
        if (firstDigit < '1' || firstDigit > '9') throw new IllegalArgumentException("firstDigit must be between 1 and 9");
        for (Account a : acc) {
            if (a.getAccountNumber().charAt(0) == firstDigit) return a;
        }
        throw new IllegalArgumentException("account not found");
    }
    public void checkValidityOfAccountNumber(String toAcc) throws IllegalArgumentException {
        int typeAccount = Integer.valueOf(toAcc.charAt(0)) - 48;
        int year = Integer.valueOf(toAcc.substring(4,8));
        int month = Integer.valueOf(toAcc.substring(8,10));
        int day = Integer.valueOf(toAcc.substring(10,12));
        if (!(typeAccount <=9 && typeAccount >=1 && year>= MyDate.today().getYear()-100 && year <=MyDate.today().getYear()
        &&  month >= 1 && month <= 12 && day >= 1 && day <= 31)) {
            throw new IllegalArgumentException("Invalid account number");
        }
    }
    @Override
    public String toString() {
        String infoLoan = (haveLoan ? "yes" : "no");
        return "\n\n\t\tACCOUNT\naccount owner name: " + ownerAccount.getName()+"\nowner ID : "+ ownerAccount.getCustomerId() + "\naccount number: " + accountNumber +
                "\nbalance: " + balance + "\ndate of opening: " + dateOfOpening+
                "\nhave loan: " + infoLoan+"\n";
    }
}
