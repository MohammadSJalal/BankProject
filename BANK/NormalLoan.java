package BANK;

public final class NormalLoan extends BaseLoan {
    public NormalLoan(double amount ,int durationInMonths,MyDate date, Customer borrower , Account accountGetLoan) {
        super( amount,durationInMonths,date,80,20, 80, 6, borrower , accountGetLoan,24,'0');
    }
    public NormalLoan(double amount ,double installment ,Customer borrower,MyDate d , Account accountGetLoan){
        super(amount ,installment,d,80,20,80, 6, borrower , accountGetLoan,20,'0');
    }
    public NormalLoan(double amount , double durationInMonths,MyDate date , Customer borrower , Account accountGetLoan , boolean accept){
        super(amount , durationInMonths, date , 100,0, 100, 10, borrower , accountGetLoan,30,'0');
    }
    @Override
    public String getLoanType(){
        return "Normal";
    }
}
