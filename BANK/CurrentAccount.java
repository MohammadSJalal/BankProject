package BANK;

public class CurrentAccount extends Account {
    public static String accountType = "CA";
    public static String count = "0000";
    public CurrentAccount(Customer ownerAccountName, Bank bank, MyDate dateOfOpening ,boolean isBoss) {
        super(ownerAccountName, bank);
        this.setDateOfOpening(dateOfOpening,isBoss);
        createAccountNumber();
    }
    public void createAccountNumber() {
        // CA is type 1 as first digit of cart
        setAccountNumber("1"+getDateOfOpening().getYear()+createNDigitString(getDateOfOpening().getMonth(),2)+createNDigitString(getDateOfOpening().getDay(),2)+count);
        if (count.equals("9999")) count = "0000";
        else count = countString(count);
    }
}
