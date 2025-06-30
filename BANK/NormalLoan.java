package BANK;

public final class NormalLoan extends BaseLoan {
    public NormalLoan(Double amount, int durationInMonths, Customer borrower , Account accountGetLoan) {
        super(amount , durationInMonths,2, 23, 6, borrower , accountGetLoan,'0');
    }
    @Override
    public void calculatePenalty(int delyedMonths) {
        double penalty = (double)amount * 0.06;
    }
    @Override
    public String getLoanType(){
        return "Normal";
    }
}
