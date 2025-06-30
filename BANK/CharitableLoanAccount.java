package BANK;

public final class CharitableLoanAccount extends Account {
    public static String count = "0000";
    public CharitableLoanAccount(Customer ownerAccountName, Bank bank, MyDate dateOfOpening) {
        super(ownerAccountName, bank);
        this.setDateOfOpening(dateOfOpening);
        createAccountNumber();
    }
    public CharitableLoanAccount(Customer ownerAccountName, Bank bank, MyDate dateOfOpening, boolean isBoss, double initialBalance) {
        super(ownerAccountName, bank , initialBalance);
        this.setDateOfOpening(dateOfOpening);
        createAccountNumber();
    }
    public void createAccountNumber() {
        // CL is type 3 as first digit of cart
        setAccountNumber("3"+getDateOfOpening().getYear()+createNDigitString(getDateOfOpening().getMonth(),2)+createNDigitString(getDateOfOpening().getDay(),2)+count);
        if (count.equals("9999")) count = "0000";
        else count = countString(count);
    }
}
