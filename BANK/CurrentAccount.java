package BANK;

public final class CurrentAccount extends Account {
    /*
    we allow the customer to have 3 account that are same if they want more then we send a notice
    for this type of account that main type is 01 we assign the 1 , 4 , 7
     */
    public final String typeOfAccount = "01";
    public CurrentAccount(Customer ownerAccountName, Bank bank, MyDate dateOfOpening) {
        super(ownerAccountName, bank);
        this.setDateOfOpening(dateOfOpening);
        createAccountNumber();
    }
    public CurrentAccount(Customer ownerAccountName, Bank bank, MyDate dateOfOpening , double initialBalance) {
        super(ownerAccountName, bank , initialBalance);
        this.setDateOfOpening(dateOfOpening);
        createAccountNumber();
    }
    public String getAccountNumberForType() {
        return typeOfAccount+getAccountNumber().substring(3,getAccountNumber().length()-2);
    }
    private void createAccountNumber() {
        // CA is type 1 as first digit of cart
        char firstDigit = Account.check(getOwner(),'1');
        String eightDigits = getOwner().getReferBranch().eightDigitT1;
        if (!getOwner().getAccounts().isEmpty()){
            eightDigits = getOwner().getAccounts().get(0).getAccountNumber().substring(8,getOwner().getAccounts().size()-1);
        }
        else {
            if (getOwner().getReferBranch().eightDigitT1 == "99999999") getOwner().getReferBranch().eightDigitT1 = "00000000";
            getOwner().getReferBranch().eightDigitT1 = countString(eightDigits);
        }
        setAccountNumber(Account.check(getOwner(),'1')+bank.getCardIdentity()+
                getOwner().getReferBranch().cardIdentity+eightDigits);
        Account.accounts.put(getAccountNumber(),this);
    }
}
