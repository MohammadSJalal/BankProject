import java.util.UUID;

public abstract class BaseLoan {
    private final String loanId;
    private final Customer customer;
    private final int principal; 
    private int remaining;
    private int monthsLeft;
    private boolean active;

    public BaseLoan(Customer customer, int principal, int months) {
        this.loanId = "LOAN-" + UUID.randomUUID().toString().substring(0, 6);
        this.customer = customer;
        this.principal = principal;
        this.remaining = principal;
        this.monthsLeft = months;
        this.active = true;
    }

    public String getLoanId() { return loanId; }
    public Customer getCustomer() { return customer; }
    public int getRemaining() { return remaining; }
    public boolean isActive() { return active; }

    
    public void nextMonth() {
        if (!active) return;
        if (monthsLeft > 0) {
            monthsLeft--;
            if (remaining > 0) {
                int penalty = calculatePenalty();
                remaining += penalty;
                Report r = new Report(
                        "جریمه وام",
                        "جریمه ماهانه برای وام " + loanId + ": " + penalty,
                        false
                );
                BankSystemHolder.getBank().addReport(r);
            }
        } else {
            if (remaining > 0) {
                int penalty = calculatePenalty() * 2; 
                remaining += penalty;
                Report r = new Report(
                        "جریمه سنگین وام",
                        "تاخیر زیاد در وام " + loanId + ": " + penalty,
                        false
                );
                BankSystemHolder.getBank().addReport(r);
            }
        }
    }

    public void payInstallment(int amount) {
        if (!active) {
            System.out.println("❌ این وام بسته شده.");
            return;
        }
        if (amount <= 0) {
            System.out.println("❌ مبلغ نامعتبر.");
            return;
        }
        if (amount > remaining) amount = remaining;
        remaining -= amount;

        Report r = new Report(
                "پرداخت قسط",
                "پرداخت " + amount + " برای وام " + loanId,
                true
        );
        BankSystemHolder.getBank().addReport(r);

        if (remaining == 0) {
            active = false;
            Report r2 = new Report(
                    "تسویه وام",
                    "وام " + loanId + " توسط " + customer.getName() + " تسویه شد.",
                    true
            );
            BankSystemHolder.getBank().addReport(r2);
        }
    }

    public abstract int calculatePenalty();

    public String getLoanInfo() {
        return "🔹 وام " + loanId + " | مبلغ باقی‌مانده: " + remaining +
                " | ماه‌های باقیمانده: " + monthsLeft +
                " | فعال: " + active;
    }
}
