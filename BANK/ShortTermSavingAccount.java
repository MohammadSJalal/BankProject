package BANK;

public class ShortTermSavingAccount extends Account {
    public static String count = "0000";
    public ShortTermSavingAccount(Customer ownerAccountName) {
        super(ownerAccountName);
        createAccountNumber();
    }
    public void createAccountNumber() {
        // STSA is type 2 as first digit of cart
        setAccountNumber("2"+getDateOfOpening().getYear()+getDateOfOpening().getMonth()+getDateOfOpening().getDay()+count);
        if (count.equals("9999")) count = "0000";
        else count = countString(count);
    }
}
