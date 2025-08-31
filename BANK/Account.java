import java.util.concurrent.atomic.AtomicInteger;

public class Account {
    public enum AccountType { JARI, KOOTAH, GHARZ }

    private static final AtomicInteger counter = new AtomicInteger(1000);
    private final String accountNumber;
    private int balance;
    private final Customer owner;
    private final AccountType type;

    public Account(Customer owner, int balance, AccountType type) {
        if (balance < 0) throw new IllegalArgumentException("❌ موجودی اولیه نمی‌تواند منفی باشد.");
        this.owner = owner;
        this.balance = balance;
        this.type = type;
        this.accountNumber = "ACC-" + counter.getAndIncrement();
    }

    public String getAccountNumber() { return accountNumber; }
    public int getBalance() { return balance; }
    public Customer getOwner() { return owner; }
    public AccountType getType() { return type; }

    public void deposit(int amount) {
        if (amount <= 0) throw new IllegalArgumentException("❌ مبلغ واریز باید مثبت باشد.");
        balance += amount;
    }

    public void withdraw(int amount) {
        if (amount <= 0) throw new IllegalArgumentException("❌ مبلغ برداشت باید مثبت باشد.");
        if (amount > balance) throw new IllegalArgumentException("❌ موجودی کافی نیست.");
        balance -= amount;
    }

    public void applyMonthlyInterest() {
        if (type == AccountType.KOOTAH) {
            balance += (balance * 5) / (12 * 100);
        }
    }

    @Override
    public String toString() {
        return "💳 حساب{" +
                " شماره='" + accountNumber + '\'' +
                ", موجودی=" + balance +
                ", نوع=" + type +
                ", صاحب=" + owner.getName() + " " + owner.getFamily() +
                '}';
    }
}
