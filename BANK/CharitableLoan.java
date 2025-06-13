package BANK;

public class CharitableLoan extends Account {
    public static String count = "0000";
    public CharitableLoan(Customer ownerAccountName, Bank bank) {
        super(ownerAccountName, bank);
        createAccountNumber();
    }
    public void createAccountNumber(double balance, String ownerAccountName, MyDate dateOfOpening) {
        setBalance(balance);
    }
    @Override
    public void createAccountNumber() {
        // CL is type 3 as first digit of cart
        setAccountNumber("3"+getDateOfOpening().getYear()+createNDigitString(getDateOfOpening().getMonth(),2)+createNDigitString(getDateOfOpening().getDay(),2)+count);
        if (count.equals("9999")) count = "0000";
        else count = countString(count);
    }
}
