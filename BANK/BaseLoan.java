package BANK;

/**
 * کلاس پایه برای مدیریت ساختار و رفتار انواع وام‌ها
 * این کلاس abstract هست یعنی خودش مستقیماً قابل ساخت نیست،
 * بلکه باید توسط کلاس‌های فرزند (مثلاً NormalLoan یا FacilityLoan) گسترش پیدا کنه.
 */
public abstract class BaseLoan {
    /**account that customer get loan with it*/
    Account accountGetLoan;
    // مبلغ وام (عدد صحیح مثبت)
    protected Double amount;
    // this is id for loan and track it easily
    String LoanId;
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

    /**this is a variable that count months for pay */
    private int paidMonths;

    // it a const instalment for pay
    private double instalment;

    /**
     * سازنده‌ی کلاس وام
     * در این سازنده اطلاعات اصلی وام مقداردهی می‌شوند.
     * برخی چک‌ها برای اطمینان از درستی داده‌ها انجام می‌شود.
     */
    public BaseLoan( double amount, int durationInMonths, int governmentShare,
                     int customerShare, int penaltyPercent, Customer borrower ,
                     Account accountGetLoan , char typeLoan) throws IllegalArgumentException{
        checkValidity(typeLoan);
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
        this.accountGetLoan = accountGetLoan;
    }

    // متد getter برای مبلغ وام
    public double getAmount() {
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

    public Account getAccountGetLoan() {
        //this is account that costumer get with it loan
        return accountGetLoan;
    }
    public boolean checkAccount(Account account) {
        //this method check sended account is get loan account ?
        return accountGetLoan.equals(account);
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

    /**
     * this function is for check that account is correspond with loan
     * mean normal account can not get charity loan this function check it.
     * parameter it is a char with '1' '2' or '3' that we indicate type of
     * account
     */
    private void checkValidity(char typeloan) throws IllegalArgumentException {
        // if it is zero mean dont care about it. it mean customer want a normal that is legal for all customer
        if (typeloan == '0' && (accountGetLoan.getAccountNumber().charAt(0) == '1' || accountGetLoan.getAccountNumber().charAt(0) == '2'));
        else if (accountGetLoan.getAccountNumber().charAt(0) == '1') ;
        else {
            throw new IllegalArgumentException("Account type is not valid. : "+typeloan);
        }
    }
}