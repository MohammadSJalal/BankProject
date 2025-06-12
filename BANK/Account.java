package BANK;

public class Account {
    private static int counter = 1;
    private final String accountNumber; // شماره حساب یکتا 13 رقمی
    private int balance; // موجودی حساب
    private Customer owner; // صاحب حساب

    // سازنده برای ساخت حساب جدید
    public Account(Customer owner, int initialBalance, String typePrefix) {
        if (initialBalance < 0) {
            throw new IllegalArgumentException("موجودی اولیه نمی‌تونه منفی باشه.");
        }
        this.accountNumber = generateAccountNumber(typePrefix);
        this.balance = initialBalance;
        this.owner = owner;
    }

    // تولید شماره حساب یکتا با پیشوند نوع حساب و عدد ترتیبی
    private String generateAccountNumber(String prefix) {
        return prefix + String.format("%011d", counter++); // مثلاً 01 + 00000000023
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        if (balance < 0) {
            throw new IllegalArgumentException("موجودی نمی‌تونه منفی باشه.");
        }
        this.balance = balance;
    }

    public Customer getOwner() {
        return owner;
    }

    // واریز پول به حساب
    public void deposit(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("مبلغ واریز باید بیشتر از صفر باشه.");
        }
        balance += amount;
    }

    // برداشت پول از حساب
    public void withdraw(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("مبلغ برداشت باید بیشتر از صفر باشه.");
        }
        if (amount > balance) {
            throw new IllegalArgumentException("موجودی کافی نیست.");
        }
        balance -= amount;
    }

    // گرفتن موجودی حساب با کارمزد هزار تومانی
    public int getBalanceWithFee() {
        int fee = 1000;
        if (balance < fee) {
            throw new IllegalArgumentException("موجودی برای کسر کارمزد کافی نیست.");
        }
        balance -= fee;
        return balance;
    }

    @Override
    public String toString() {
        return "Account Number: " + accountNumber + ", Balance: " + balance + ", Owner: " + owner.getCustomerId();
    }
}