package BANK;

import java.util.ArrayList;
import java.util.List;

public class Customer extends Person {
    private static int customerCounter = 0;
    private String customerId;
    private List<Account> accounts;
    private List<String> inboxMessages;
    private Bank bank;

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

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public void createAccount(String accountType, int initialBalance) {
        String prefix = switch (accountType.toLowerCase()) {
            case "جاری", "jari" -> "01";
            case "کوتاه", "kootah" -> "02";
            case "قرض", "gharz" -> "03";
            default -> throw new IllegalArgumentException("نوع حساب نامعتبر است.");
        };
        Account account = new Account(this, initialBalance, prefix);
        accounts.add(account);
        System.out.println("حساب جدید با شماره " + account.getAccountNumber() + " برای مشتری " + customerId + " ایجاد شد.");
    }

    public void transferMoney(Account from, Account to, int amount) {
        try {
            if (accounts.contains(from)) {
                from.withdraw(amount);
                to.deposit(amount);
                System.out.println("مبلغ " + amount + " از حساب " + from.getAccountNumber() + " به حساب " + to.getAccountNumber() + " انتقال یافت.");
            } else {
                System.out.println("حساب مبدا متعلق به این مشتری نیست.");
            }
        } catch (Exception e) {
            System.out.println("خطا در انتقال: " + e.getMessage());
        }
    }

    public void deposit(Account account, int amount) {
        account.deposit(amount);
        System.out.println("مبلغ " + amount + " به حساب " + account.getAccountNumber() + " واریز شد.");
    }

    public void withdraw(Account account, int amount) {
        account.withdraw(amount);
        System.out.println("مبلغ " + amount + " از حساب " + account.getAccountNumber() + " برداشت شد.");
    }

    public void viewAccounts() {
        System.out.println("حساب‌های مشتری " + customerId + ":");
        for (Account acc : accounts) {
            System.out.println(acc);
        }
    }

    @Override
    public String toString() {
        return "Customer ID: " + customerId + ", Name: " + name + " " + familyName + ", National Code: " + nationalCode;
    }
}