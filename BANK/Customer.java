import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Customer extends Person {
    private static int counter = 1;
    private final String customerId;
    private final List<Account> accounts = new ArrayList<>();
    private final List<BaseLoan> loans = new ArrayList<>();
    private final List<String> inboxMessages = new ArrayList<>();
    private Branch branch;

    public Customer(String name, String family, Date birthDate,
                    String nationalCode, String phone, String address) {
        super(name, family, birthDate, nationalCode, phone, address);
        this.customerId = "CUST-" + (counter++);
    }

    public String getCustomerId() { return customerId; }
    public List<Account> getAccounts() { return accounts; }
    public List<BaseLoan> getLoans() { return loans; }
    public List<String> getInboxMessages() { return inboxMessages; }

    // ---------- شعبه / بانک ----------
    public Branch getBranch() { return branch; }
    public void setBranch(Branch b) { this.branch = b; }
    public Bank getBank() { return BankSystemHolder.getBank(); }

    public boolean hasActiveLoan() {
        for (BaseLoan l : loans) if (l.isActive()) return true;
        return false;
    }

    // ---------- ساخت حساب ----------
    public void createAccount(String type, int balance) {
        Account.AccountType accType = parseAccountType(type);
        if (accType == null) {
            System.out.println("❌ نوع حساب نامعتبر. یکی از موارد «جاری / کوتاه / قرض» یا 01/02/03 را وارد کنید.");
            return;
        }
        if (balance < 0) {
            System.out.println("❌ موجودی اولیه نمی‌تواند منفی باشد.");
            return;
        }

        Account acc = new Account(this, balance, accType);
        accounts.add(acc);
        Bank bank = getBank();
        if (bank != null) bank.addAccount(acc);

        if (bank != null) {
            bank.addReport(new Report("ایجاد حساب", "ایجاد حساب جدید " + acc.getAccountNumber() + " برای مشتری " + getName(), true));
        }
        System.out.println("✅ حساب ساخته شد: " + acc.getAccountNumber());
    }

    private Account.AccountType parseAccountType(String input) {
        if (input == null) return null;
        String s = input.trim().toLowerCase();
        if (s.equals("جاری") || s.equals("حساب جاری") || s.equals("01") || s.equals("1")) return Account.AccountType.JARI;
        if (s.equals("کوتاه") || s.equals("کوتاه مدت") || s.equals("02") || s.equals("2")) return Account.AccountType.KOOTAH;
        if (s.equals("قرض") || s.equals("قرض الحسنه") || s.equals("03") || s.equals("3")) return Account.AccountType.GHARZ;
        if (s.equals("jari") || s.equals("current")) return Account.AccountType.JARI;
        if (s.equals("kootah") || s.equals("short") || s.equals("shortterm")) return Account.AccountType.KOOTAH;
        if (s.equals("gharz") || s.equals("qarz")) return Account.AccountType.GHARZ;
        return null;
    }

    // ---------- مشاهده حساب‌ها ----------
    public void viewAccounts() {
        if (accounts.isEmpty()) {
            System.out.println("❌ هیچ حسابی موجود نیست.");
            return;
        }
        for (Account a : accounts) System.out.println(" - " + a);
    }

    // ---------- انتقال وجه ----------
    public void transferMoney(String from, String to, int amount) {
        Account fromAcc = findAccount(from);
        Account toAcc = getBank().findAccount(to);
        if (fromAcc == null || toAcc == null) {
            System.out.println("❌ حساب مبدا یا مقصد پیدا نشد.");
            return;
        }
        try {
            fromAcc.withdraw(amount);
            toAcc.deposit(amount);
            Bank bank = getBank();
            if (bank != null) bank.addReport(new Report("انتقال وجه", "انتقال " + amount + " از " + from + " به " + to, true));
            System.out.println("✅ انتقال انجام شد.");
        } catch (Exception e) {
            System.out.println("❌ خطا: " + e.getMessage());
        }
    }

    private Account findAccount(String num) {
        for (Account a : accounts) if (a.getAccountNumber().equals(num)) return a;
        return null;
    }

    // ---------- پیام ----------
    public void addMessage(String msg) { inboxMessages.add(msg); }

    // ---------- وام ----------
    public void addLoan(BaseLoan loan) { loans.add(loan); }

    public void viewLoans() {
        if (loans.isEmpty()) {
            System.out.println("❌ وامی ندارید.");
            return;
        }
        for (BaseLoan l : loans) System.out.println(l.getLoanInfo());
    }

    public void payInstallment(String loanId, int amount) {
        for (BaseLoan l : loans) {
            if (l.getLoanId().equals(loanId)) {
                l.payInstallment(amount);
                return;
            }
        }
        System.out.println("❌ وام با این شناسه یافت نشد.");
    }

    // ---------- بستن حساب ----------
    public void requestCloseAccount(String accNum, Teller teller) {
        teller.handleRequest("close account", this, accNum);
    }

    @Override
    public String toString() {
        return "👤 مشتری: " + getName() + " " + getFamily() + " (کد: " + customerId + ")";
    }
}

