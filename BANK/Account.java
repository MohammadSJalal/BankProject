public class Account {
    private static int counter = 1;
    private final String accountNumber;
    private int balance;
    private Customer owner;

    public Account(Customer owner, int initialBalance, String typePrefix) {
        if (initialBalance < 0) {
            throw new IllegalArgumentException("موجودی اولیه نمی‌تونه منفی باشه.");
        }
        this.accountNumber = generateAccountNumber(typePrefix);
        this.balance = initialBalance;
        this.owner = owner;
    }

    private String generateAccountNumber(String prefix) {
        String accNum;
        do {
            accNum = prefix + String.format("%011d", counter++);
        } while (Bank.isAccountNumberUsed(accNum));
        Bank.markAccountNumberUsed(accNum);
        return accNum;
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

    public void deposit(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("مبلغ واریز باید بیشتر از صفر باشه.");
        }
        balance += amount;
    }

    public void withdraw(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("مبلغ برداشت باید بیشتر از صفر باشه.");
        }
        if (amount > balance) {
            throw new IllegalArgumentException("موجودی کافی نیست.");
        }
        balance -= amount;
    }

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
