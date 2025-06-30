package BANK;

public final class FacilitiesLoan extends BaseLoan{
    public FacilitiesLoan(double amount, int durationInMonths, Customer borrower , Account accountGetLoan){
        super(amount , durationInMonths, 1 , 4 , 8 , borrower , accountGetLoan , '1');

    }
    @Override
    public String getLoanType(){
        return "Facilities";
    }
    @Override
    public void calculatePenalty(int delayMonths){
        double penalty = (double)amount * penaltyPercent;
    }
}
