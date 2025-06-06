package BANK;

import java.util.ArrayList;
import java.util.List;

public class Customer extends Person {
    private static int customerCounter = 0;
    private String customerId;
    private List<Account> accounts;
    private List<String> inboxMessages;

    public Customer(String name, String familyName, Date birthDate, String nationalCode, String phoneNumber, String address) {
        super(name, familyName, birthDate, nationalCode, phoneNumber, address);
        this.customerId = "C" + (++customerCounter);
        this.accounts = new ArrayList<>();
        this.inboxMessages = new ArrayList<>();
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

    public void createAccount(String accountType, double initialBalance, Date openDate) {
        // شماره حساب باید 13 رقمی و یکتا باشد (در اینجا فرض می‌کنیم تولید یکتا انجام می‌شود)
        String accountNumber = generateUniqueAccountNumber(accountType);
        Account account = new Account(initialBalance, openDate.getYear(), openDate.getMonth(), openDate.getDay());
        // فرض بر این است که accountNumber در Account ست خواهد شد
        accounts.add(account);
        System.out.println("Account created successfully for customer: " + this.customerId);
    }

    public void requestCloseAccount(Account account, Branch branch) {
        // فرض: ارسال درخواست حذف به شعبه
        inboxMessages.add("Account closure request sent for account number: " + account.getAccountNumber());
        System.out.println("Account closure request sent to branch.");
    }

    public void requestLoan(BaseLoan loan, Branch branch) {
        // فرض: ارسال درخواست وام به تحویل‌دار شعبه
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
        return "Customer ID: " + customerId + "\nName: " + name + " " + familyName + "\nNational Code: " + nationalCode;
    }
}