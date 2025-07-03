package BANK;

public final class FacilitiesLoan extends BaseLoan{
    public FacilitiesLoan(double amount ,int durationInMonths,MyDate dateOfGettingLoan, Customer borrower , Account accountGetLoan){
        super(amount ,durationInMonths,dateOfGettingLoan,80, 100 , 0 , 8 , borrower , accountGetLoan,4 , '1');
    }
    @Override
    public String getLoanType(){
        return "Facilities";
    }
}
