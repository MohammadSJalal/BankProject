package BANK;

import java.util.ArrayList;
import java.util.List;

public class Customer extends Person {
    private static int customerCounter = 0;
    private String customerId;
    private List<Account> accounts;
    private List<String> inboxMessages;
    private String nationalCode;
    private Branch refferalBranch;
    public Bank bank;
    public Customer(String name,String lastName,Bank bank) {
        super(name,lastName);
        this.customerId = "C" + (++customerCounter);
        this.inboxMessages = new ArrayList<>();
        this.bank = bank;
    }
    public Customer(String name, String familyName, MyDate birthDate, String nationalCode, String phoneNumber, String address , Branch refferalBranch) {
        super(name, familyName, birthDate,nationalCode, phoneNumber, address);
        this.customerId = "C" + (++customerCounter);
        this.accounts = new ArrayList<>();
        this.inboxMessages = new ArrayList<>();
        this.refferalBranch = refferalBranch;
    }
    public String getCustomerId() {
        return customerId;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public List<String> getInboxMessages() {
        return inboxMessages;
    }

    public void addMessage(String message) {
        inboxMessages.add(message);
    }

    public void createAccountRequest(String accountType, double initialBalance,MyDate d,boolean isBoss) {
        if (true) { //this have a problem i want to a employee verify to create a card
            generateUniqueAccountNumber(accountType,d,isBoss);
            accounts.get(accounts.size() - 1).deposit(initialBalance);
        }
    }

    public void requestCloseAccount(Account account, Branch branch) {
        inboxMessages.add("Account closure request sent for account number: " + account.getAccountNumber());
        System.out.println("Account closure request sent to branch.");
    }

    public void requestLoan(BaseLoan loan, Branch branch) {
        inboxMessages.add("Loan request submitted: Amount " + loan.getAmount());
        System.out.println("Loan request submitted.");
    }

    public void transferMoney(Account from, Account to, double amount) {
        try {
            if (from.getBalance() >= amount) {
                from.setBalance(from.getBalance() - amount);
                to.setBalance(to.getBalance() + amount);
                System.out.println("Amount " + amount + " transferred from account " + from.getAccountNumber() + " to account " + to.getAccountNumber() + " successfully.");
            } else {
                throw new IllegalArgumentException("Insufficient balance.");
            }
        } catch (Exception e) {
            System.out.println("Transfer failed: " + e.getMessage());
        }
    }

    public void deposit(double amount,Account account) throws IllegalArgumentException{
        account.deposit(amount);
    }

    public void withdraw(Account account, double amount) throws IllegalArgumentException {
        account.withdraw(amount);
        System.out.println("Withdrawn : " + amount);
        System.out.println("Withdrawn " + amount + " from account " + account.getAccountNumber());
    }

    public void viewAccounts() {
        for (Account acc : accounts) {
            System.out.println("Account: " + acc.getAccountNumber() + " | Balance: " + acc.getBalance());
        }
    }

    private void generateUniqueAccountNumber(String accountType,MyDate d,boolean isBoss) {
        switch (accountType.toLowerCase()) {
            case "jari", "جاری" -> accounts.add(new CurrentAccount(this,bank,d,isBoss));
            case "kootah", "کوتاه" -> accounts.add(new ShortTermSavingAccount(this,bank,d,isBoss));
            case "gharz", "قرض" -> accounts.add(new CharitableLoan(this,bank,d,isBoss));
            default -> throw new IllegalArgumentException("not recognized\nthis value for create account is not recognize"); // ناشناخته
        };
    }

    @Override
    public String toString() {
        if (refferalBranch != null)
            return "Customer ID: " + customerId + "\nName: " + name + "\nlast Name: " + lastName + "\nNational Code: " + nationalCode+
                "\nfirst branch visited : Branch name ->" + refferalBranch.getName() +" and branch id is : "+ refferalBranch.getId();
        else
            return "Customer ID: " + customerId + "\nName: " + name + " " + lastName + "\nNational Code: " + nationalCode;

    }
}