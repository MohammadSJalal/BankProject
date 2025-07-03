package BANK;

public final class ShortTermSavingAccount extends Account {
    /*
    we allow the customer to have 3 account that are same if they want more then we send a notice
    for this type of account that main type is 02 we assign the 2 , 5 , 8
     */
    public final String typeOfAccount = "02";
    public ShortTermSavingAccount(Customer ownerAccountName, Bank bank,MyDate dateOfOpening) {
        super(ownerAccountName, bank);
        this.setDateOfOpening(dateOfOpening);
        createAccountNumber();
    }
    public ShortTermSavingAccount(Customer ownerAccountName, Bank bank,MyDate dateOfOpening, double initialBalance) {
        super(ownerAccountName, bank , initialBalance);
        this.setDateOfOpening(dateOfOpening);
        createAccountNumber();
    }
    public String getAccountNumberForType() {
        return typeOfAccount+getAccountNumber().substring(3,getAccountNumber().length()-2);
    }
    private void createAccountNumber() {
        // STSA is type 2 as first digit of cart
        char firstDigit = Account.check(getOwner(),'2');
        String eightDigits = getOwner().getReferBranch().eightDigitT2;
        if (!getOwner().getAccounts().isEmpty()){
            eightDigits = getOwner().getAccounts().get(0).getAccountNumber().substring(8,getOwner().getAccounts().size()-1);
        }
        else {
            if (getOwner().getReferBranch().eightDigitT2 == "99999999") getOwner().getReferBranch().eightDigitT2 = "00000000";
            getOwner().getReferBranch().eightDigitT2 = countString(eightDigits);
        }
        setAccountNumber(Account.check(getOwner(),'2')+bank.getCardIdentity()+
                getOwner().getReferBranch().cardIdentity+eightDigits);
        Account.accounts.put(getAccountNumber(),this);
    }
}
