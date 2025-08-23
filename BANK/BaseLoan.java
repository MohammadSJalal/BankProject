public abstract class BaseLoan {
    protected int amount;
    protected int durationInMonths;
    protected int governmentShare;
    protected int customerShare;
    protected int penaltyPercent;
    protected Customer borrower;

    public BaseLoan(int amount, int durationInMonths, int governmentShare, int customerShare, int penaltyPercent, Customer borrower) {
        if (amount <= 0 || durationInMonths <= 0) {
            throw new IllegalArgumentException("مبلغ و مدت باید مثبت باشند.");
        }
        if (governmentShare + customerShare != 100) {
            throw new IllegalArgumentException("مجموع سهم دولت و مشتری باید ۱۰۰ درصد باشد.");
        }
        if (penaltyPercent < 0 || penaltyPercent > 100) {
            throw new IllegalArgumentException("درصد جریمه باید بین ۰ تا ۱۰۰ باشد.");
        }
        this.amount = amount;
        this.durationInMonths = durationInMonths;
        this.governmentShare = governmentShare;
        this.customerShare = customerShare;
        this.penaltyPercent = penaltyPercent;
        this.borrower = borrower;
    }

    public int getAmount() { return amount; }
    public int getDurationInMonths() { return durationInMonths; }
    public int getPenaltyPercent() { return penaltyPercent; }
    public Customer getBorrower() { return borrower; }

    public abstract void calculatePenalty(int delayedMonths);
    public abstract String getLoanType();

    @Override
    public String toString() {
        return "Loan Type: " + getLoanType() + ", Amount: " + amount +
                ", Duration: " + durationInMonths + " months, Borrower: " + borrower.getCustomerId();
    }
}
