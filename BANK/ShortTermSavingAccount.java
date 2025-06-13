package BANK;

public class ShortTermSavingAccount extends Account {
    public static String count = "0000";
    public ShortTermSavingAccount(Customer ownerAccountName, Bank bank) {
        super(ownerAccountName, bank);
        createAccountNumber();
    }
    public void createAccountNumber() {
        // STSA is type 2 as first digit of cart
        setAccountNumber("2"+getDateOfOpening().getYear()+createNDigitString(getDateOfOpening().getMonth(),2)+createNDigitString(getDateOfOpening().getDay(),2)+count);
        if (count.equals("9999")) count = "0000";
        else count = countString(count);
    }
}
