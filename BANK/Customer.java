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
    public Customer(String name,String lastName) {
        super(name,lastName);
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

    public void createAccountRequest(String accountType, double initialBalance, MyDate openDate) {
        
        String accountNumber = generateUniqueAccountNumber(accountType);
        switch(accountType) {
            case "Current Account":


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

    public void deposit(Account account, double amount) {
        if (amount > 0) {
            account.setBalance(account.getBalance() + amount);
            System.out.println("Deposited " + amount + " to account " + account.getAccountNumber());
        }
    }

    public void withdraw(Account account, double amount) {
        if (amount > 0 && account.getBalance() >= amount) {
            account.setBalance(account.getBalance() - amount);
            System.out.println("Withdrawn " + amount + " from account " + account.getAccountNumber());
        } else {
            System.out.println("Withdrawal failed. Check amount or balance.");
        }
    }

    public void viewAccounts() {
        for (Account acc : accounts) {
            System.out.println("Account: " + acc.getAccountNumber() + " | Balance: " + acc.getBalance());
        }
    }

    private String generateUniqueAccountNumber(String accountType) {
        String prefix = switch (accountType.toLowerCase()) {
            case "jari", "جاری" -> "01";
            case "kootah", "کوتاه" -> "02";
            case "gharz", "قرض" -> "03";
            default -> "00"; // ناشناخته
        };
        return prefix + String.format("%011d", System.currentTimeMillis() % 1_000_000_000);
    }

    @Override
    public String toString() {
        if (refferalBranch != null)
            return "Customer ID: " + customerId + "\nName: " + name + " " + lastName + "\nNational Code: " + nationalCode+
                "\nfirst branch visited : Branch name ->" + refferalBranch.getName() +" and branch id is : "+ refferalBranch.getId();
        else
            return "Customer ID: " + customerId + "\nName: " + name + " " + lastName + "\nNational Code: " + nationalCode;

    }
}