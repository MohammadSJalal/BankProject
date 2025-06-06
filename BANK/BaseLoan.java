package BANK;

/**
 * کلاس پایه برای مدیریت ساختار و رفتار انواع وام‌ها
 * این کلاس abstract هست یعنی خودش مستقیماً قابل ساخت نیست،
 * بلکه باید توسط کلاس‌های فرزند (مثلاً NormalLoan یا FacilityLoan) گسترش پیدا کنه.
 */
public abstract class BaseLoan {

    // مبلغ وام (عدد صحیح مثبت)
    protected int amount;

    // مدت زمان بازپرداخت به ماه
    protected int durationInMonths;

    // سهم دولت از بازپرداخت (درصد بین 0 تا 100)
    protected int governmentShare;

    // سهم مشتری از بازپرداخت (درصد بین 0 تا 100)
    protected int customerShare;

    // درصد جریمه برای هر ماه تأخیر
    protected int penaltyPercent;

    // مشتری‌ای که وام رو دریافت کرده
    protected Customer borrower;

    /**
     * سازنده‌ی کلاس وام
     * در این سازنده اطلاعات اصلی وام مقداردهی می‌شوند.
     * برخی چک‌ها برای اطمینان از درستی داده‌ها انجام می‌شود.
     */
    public BaseLoan(
        int amount,
        int durationInMonths,
        int governmentShare,
        int customerShare,
        int penaltyPercent,
        Customer borrower
    ) {
        if (amount <= 0 || durationInMonths <= 0) {
            throw new IllegalArgumentException("Amount and duration must be positive.");
        }

        if (governmentShare + customerShare != 100) {
            throw new IllegalArgumentException("Government and customer shares must sum to 100.");
        }

        if (penaltyPercent < 0 || penaltyPercent > 100) {
            throw new IllegalArgumentException("Penalty percent must be between 0 and 100.");
        }

        this.amount = amount;
        this.durationInMonths = durationInMonths;
        this.governmentShare = governmentShare;
        this.customerShare = customerShare;
        this.penaltyPercent = penaltyPercent;
        this.borrower = borrower;
    }

    // متد getter برای مبلغ وام
    public int getAmount() {
        return amount;
    }

    // متد getter برای مدت بازپرداخت
    public int getDurationInMonths() {
        return durationInMonths;
    }

    // متد getter برای درصد جریمه تأخیر
    public int getPenaltyPercent() {
        return penaltyPercent;
    }

    // متد getter برای مشتری دریافت‌کننده‌ی وام
    public Customer getBorrower() {
        return borrower;
    }

    /**
     * متد abstract برای محاسبه جریمه تأخیر
     * کلاس‌های فرزند باید این متد را پیاده‌سازی کنند
     * چون ممکن است نرخ جریمه یا نحوه محاسبه آن متفاوت باشد.
     */
    public abstract void calculatePenalty(int delayedMonths);

    /**
     * متد abstract برای برگرداندن نوع وام به‌صورت رشته
     * به کلاس‌های فرزند اجازه می‌دهد نوع خاص وام خود را مشخص کنند
     * مثل: "Normal Loan" یا "Facility Loan"
     */
    public abstract String getLoanType();

    /**
     * نمایش اطلاعات کلی وام
     * این متد قابل override است، ولی به‌صورت پیش‌فرض اطلاعات پایه را چاپ می‌کند.
     */
    @Override
    public String toString() {
        return "Loan Type: " + getLoanType() + "\n" +
               "Amount: " + amount + "\n" +
               "Duration: " + durationInMonths + " months\n" +
               "Government Share: " + governmentShare + "%\n" +
               "Customer Share: " + customerShare + "%\n" +
               "Penalty per month: " + penaltyPercent + "%\n" +
               "Borrower: " + (borrower != null ? borrower.getCustomerId() : "Unknown");
    }
}