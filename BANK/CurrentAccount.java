package BANK;

public class CurrentAccount extends Account {
    public static String accountType = "CA";
    public static String count = "0000";
    public CurrentAccount(Customer ownerAccountName) {
        super(ownerAccountName);
        createAccountNumber();
    }
    public void createAccountNumber() {
        // CA is type 1 as first digit of cart
        setAccountNumber("1"+getDateOfOpening().getYear()+getDateOfOpening().getMonth()+getDateOfOpening().getDay()+count);
        if (count.equals("9999")) count = "0000";
        else count = countString(count);
    }
}
