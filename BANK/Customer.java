package BANK;

import java.util.ArrayList;
import java.util.List;
import java.util.ArrayList;

public final class Customer extends Person {
    private static int customerCounter = 0;
    private String customerId;
    private ArrayList<Account> accounts;
    private ArrayList<Letter> inboxMessages;
    private String nationalCode;
    private Branch referBranch;
    private ArrayList<BaseLoan> loans;
    //      contractors
    public Customer(Branch referBranch) {
        super("withdout Neme","without lastname");
        this.customerId = "C" + (customerCounter++);
        this.inboxMessages = new ArrayList<>();
        this.referBranch = referBranch;
        this.referBranch.bank.addCustomer(this);
        this.accounts = new ArrayList<>();
        this.loans = new ArrayList<>();
    }
    public Customer(String name,String lastName, Branch referBranch) {
        super(name,lastName);
        this.customerId = "C" + (customerCounter++);
        this.inboxMessages = new ArrayList<>();
        this.referBranch = referBranch;
        this.referBranch.bank.addCustomer(this);
        this.accounts = new ArrayList<>();
        this.loans = new ArrayList<>();
    }
    public Customer(String name, String familyName, MyDate birthDate, String nationalCode, String phoneNumber, String address , Branch refferalBranch) {
        super(name, familyName, birthDate,nationalCode, phoneNumber, address);
        this.customerId = "C" + (customerCounter++);
        this.accounts = new ArrayList<>();
        this.inboxMessages = new ArrayList<>();
        this.referBranch = refferalBranch;
        this.referBranch.bank.addCustomer(this);
        this.accounts = new ArrayList<>();
        this.loans = new ArrayList<>();
    }
    //              ****get methods****
    public String getCustomerId() {
        return customerId;
    }

    public ArrayList<BaseLoan> getLoans() {
        return loans;
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public ArrayList<Letter> getInboxMessages() {
        return inboxMessages;
    }

    //              ****section for message****

    @Override
    public void receiveMessage(Letter form) {
        inboxMessages.add(form);
    }
    @Override
    public boolean checkMessage() {
        System.out.println(inboxMessages.get(inboxMessages.size()-1));
        return false;
    }
    @Override
    public void deleteMessage(Letter form) {
        inboxMessages.remove(form);
    }
    @Override
    public void sendMessage(Letter form){
        switch (form.getReceiverType()){
            case 'T':
                referBranch.foundLessBusyTaller().receiveMessage(form);
                break;
            case 'A':
                referBranch.foundLessBusyDeputy().receiveMessage(form);
                break;
            case 'M':
                referBranch.getManager().receiveMessage(form);
                break;
            default:
                throw new IllegalArgumentException("type of receiver not valid !!! ");
        }
    }
    //
    public void createAccountRequest(String accountType, double initialBalance,MyDate d) throws IllegalArgumentException {
        if (Account.permission(this,accountType.charAt(1))) {
            Letter form = new Letter(customerId,"please create a account",d,referBranch,accountType , initialBalance);
            this.referBranch.foundLessBusyTaller().createAccount(form);
        }
        else throw new IllegalArgumentException("you already have an account of type this !!! ");
    }
    public static int getCustomerCounter(){return customerCounter;}
    public void requestCloseAccount(Account account, Branch branch) {
        this.referBranch.foundLessBusyTaller().closeAccount(this,accounts.indexOf(account));
    }
    /**
     * this function send the request for teller
     */
    /** @param typeOfLoan
     * this specifying which loan if type of loan is 0 mean normal
     * and if 1 mean charity
     */
    public void requestLoan(double amount , int durationInMonths ,MyDate d ,Account acc, char typeOfLoan) {
        Letter form = new Letter(customerId,"loan request",d,referBranch,acc.getAccountNumber(),durationInMonths,amount,typeOfLoan);
        referBranch.foundLessBusyTaller().loanRequest(form);
    }
    public Account findAccountWithAccountNumber(String accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        throw new IllegalArgumentException("No such account number " + accountNumber + " found in findAccountWithAccountNumber function");
    }
    public void transferMoney(Account from, Account to, double amount) {
        try {
            if (from.getBalance() >= amount) {
                from.withdraw(amount);
                to.deposit(amount);
                System.out.println("Amount " + amount + " transferred from account " + from.getAccountNumber() + " to account " + to.getAccountNumber() + " successfully.");
            } else {
                throw new IllegalArgumentException("Insufficient balance.");
            }
        } catch (Exception e) {
            System.out.println("Transfer failed: " + e.getMessage());
        }
    }
    public void transferMoney(Account from, String to, double amount) {
        try {
            if (from.getBalance() >= amount) {
                from.withdraw(amount);
                referBranch.bank.seekForAccount(from.getAccountNumber()).deposit(amount);
                System.out.println("Amount " + amount + " transferred from account " + from.getAccountNumber() + " to account " + to + " successfully.");
            } else {
                throw new IllegalArgumentException("Insufficient balance.");
            }
        } catch (Exception e) {
            System.out.println("Transfer failed: " + e.getMessage());
        }
    }
    public void deposit(double amount,Account account , boolean receipt) throws IllegalArgumentException{
        account.deposit(amount);
    }

    public void withdraw(Account account, double amount , boolean receipt) throws IllegalArgumentException {
        if (receipt) System.out.println("balance : "+ account.getBalance() + " - " + amount);
        account.withdraw(amount);
        if (receipt) System.out.println("now balance : " + account.getBalance()+"\n");
    }
    /**withdraw with type of account type must be 1 -> current account and so on
     * @param typeOfAccount legal value 1 2 3 if customer have such account if not it throw a exception
     */
    public double withdraw(int typeOfAccount,double amount , boolean receipt) throws IllegalArgumentException {
        if (typeOfAccount < 0 || typeOfAccount > 3) throw new IllegalArgumentException("invalid type of account");
        for (Account account : accounts) {
            if (account.getAccountNumber().charAt(0)-48 == typeOfAccount) {
                if (receipt) System.out.println("balance : "+ account.getBalance() + " - " + amount);
                account.withdraw(amount);
                if (receipt) System.out.println("now balance : " + account.getBalance()+"\n");
                return amount;
            }
        }
        throw new IllegalArgumentException("You have not such type of account");
    }
    public double withdraw(String accountNumber, double amount , boolean receipt) throws IllegalArgumentException {
        for(Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                if (receipt) System.out.println("balance : "+ account.getBalance() + " - " + amount);
                account.withdraw(amount);
                if (receipt) System.out.println("now balance : " + account.getBalance()+"\n");
                return amount;
            }
        }
        throw new IllegalArgumentException("You have not such account number");
    }
    public void viewAccounts() {
        for (Account acc : accounts) {
            System.out.println("Account: " + acc.getAccountNumber() + " | Balance: " + acc.getBalance());
        }
    }

    public void addLoan(BaseLoan loan) {
        loans.add(loan);
    }
    public void deleteLoan(BaseLoan loan) {
        loans.remove(loan);
    }
    @Override
    public String toString() {
        StringBuilder accInfo = new StringBuilder();
        for (Account acc : accounts) {
            accInfo.append(acc.toString());
        }
        if (referBranch != null)
            return "_________________________________________\n\t\tCUSTOMER"+"\nName: "+name+"\nlast Name: " + lastName +"\nCustomer ID: " + customerId + "\nNational Code: " + nationalCode+
                "\nfirst branch visited : Branch name ->" + referBranch.getName() +"\n and branch id is : "+ referBranch.getId()+
                    "\n\t\tall account information"+accInfo.toString() + "__________________________________________\n";
        else
            return "Customer ID: " + customerId + "\nName: " + name + " " + lastName + "\nNational Code: " + nationalCode;

    }
}