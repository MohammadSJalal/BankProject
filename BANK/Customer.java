import java.util.ArrayList;
import java.util.List;

public class Customer extends Person {
    private static int customerCounter = 0;
    private String customerId;
    private List<Account> accounts;
    private List<String> inboxMessages;
    private List<BaseLoan> loans;
    private Bank bank;

    public Customer(String name, String familyName, Date birthDate, String nationalCode, String phoneNumber, String address) {
        super(name, familyName, birthDate, nationalCode, phoneNumber, address);
        this.customerId = "C" + (++customerCounter);
        this.accounts = new ArrayList<>();
        this.inboxMessages = new ArrayList<>();
        this.loans = new ArrayList<>();
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

    public List<BaseLoan> getLoans() {
        return loans;
    }

    public void addLoan(BaseLoan loan) {
        this.loans.add(loan);
    }

    public boolean hasActiveLoan() {
        return !loans.isEmpty();
    }

    public void addMessage(String message) {
        inboxMessages.add(message);
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public Bank getBank() {
        return bank;
    }

    public void createAccount(String accountType, int initialBalance) {
        String prefix = switch (accountType.toLowerCase()) {
            case "جاری", "jari" -> "01";
            case "کوتاه", "kootah" -> "02";
            case "قرض", "gharz" -> "03";
            default -> throw new IllegalArgumentException("نوع حساب نامعتبر است.");
        };

        Account account = new Account(this, initialBalance, prefix);

        if (Bank.isAccountNumberUsed(account.getAccountNumber())) {
            throw new IllegalArgumentException("شماره حساب تکراری است.");
        }

        Bank.markAccountNumberUsed(account.getAccountNumber());
        accounts.add(account);

        System.out.println("حساب جدید با شماره " + account.getAccountNumber() + " برای مشتری " + customerId + " ایجاد شد.");
    }

    public void transferMoney(Account from, Account to, int amount) {
        try {
            if (accounts.contains(from)) {
                from.withdraw(amount);
                to.deposit(amount);
                System.out.println("Amount " + amount + " from account " + from.getAccountNumber() + " to account " + to.getAccountNumber() + " transferred successfully.");
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

    public void closeAccount(String accountNumber) {
        Account toRemove = null;
        for (Account acc : accounts) {
            if (acc.getAccountNumber().equals(accountNumber)) {
                toRemove = acc;
                break;
            }
        }
        if (toRemove != null) {
            if (this.hasActiveLoan()) {
                addMessage("درخواست حذف حساب رد شد: شما دارای وام فعال هستید.");
            } else {
                accounts.remove(toRemove);
                addMessage("حساب با شماره " + accountNumber + " حذف شد.");
            }
        } else {
            addMessage("حساب موردنظر یافت نشد.");
        }
    }

    @Override
    public String toString() {
        return "Customer ID: " + customerId + ", Name: " + name + " " + familyName + ", National Code: " + nationalCode;
    }
}
