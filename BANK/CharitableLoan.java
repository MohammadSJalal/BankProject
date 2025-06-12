package BANK;

public class CharitableLoan extends Account {
    public static String count = "0000";
    public CharitableLoan(Customer ownerAccountName) {
        super(ownerAccountName);
        createAccountNumber();
    }
    public void createAccountNumber(double balance, String ownerAccountName, MyDate dateOfOpening) {
        setBalance(balance);
    }
    @Override
    public void createAccountNumber() {
        // CL is type 3 as first digit of cart
        setAccountNumber("3"+getDateOfOpening().getYear()+count);
        if (count.equals("9999")) count = "0000";
        else count = countString(count);
    }
}
