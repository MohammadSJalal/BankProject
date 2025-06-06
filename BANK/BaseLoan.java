package BANK;

public abstract class BaseLoan {
    protected int amount;
    protected int durationInMonths;
    protected int governmentShare;
    protected int customerShare;
    protected int penaltyPercent;
    protected Customer borrower;

    public BaseLoan(int amount, int durationInMonths, int governmentShare, int customerShare, int penaltyPercent, Customer borrower) {
        if (amount <= 0 || durationInMonths <= 0)
            throw new IllegalArgumentException("Amount and duration must be positive.");

        if (governmentShare + customerShare != 100)
            throw new IllegalArgumentException("Government and customer shares must sum to 100.");

        this.amount = amount;
        this.durationInMonths = durationInMonths;
        this.governmentShare = governmentShare;
        this.customerShare = customerShare;
        this.penaltyPercent = penaltyPercent;
        this.borrower = borrower;
    }

    public int getAmount() {
        return amount;
    }

    public int getDurationInMonths() {
        return durationInMonths;
    }

    public int getPenaltyPercent() {
        return penaltyPercent;
    }

    public Customer getBorrower() {
        return borrower;
    }

    public abstract void calculatePenalty(int delayedMonths);

    public abstract String getLoanType();

    @Override
    public String toString() {
        return "Loan Type: " + getLoanType() + "\n" +
               "Amount: " + amount + "\n" +
               "Duration: " + durationInMonths + " months\n" +
               "Gov Share: " + governmentShare + "%\n" +
               "Customer Share: " + customerShare + "%\n" +
               "Penalty: " + penaltyPercent + "% per month\n" +
               "Borrower ID: " + borrower.getCustomerId();
    }
}