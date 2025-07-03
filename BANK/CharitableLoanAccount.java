package BANK;

public final class CharitableLoanAccount extends Account {
    /*
    we allow the customer to have 3 account that are same if they want more then we send a notice
    for this type of account that main type is 03 we assign the 3 , 6 , 9
     */
    public final String typeOfAccount = "03";
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
    public String getAccountNumberForType() {
        return typeOfAccount+getAccountNumber().substring(3,getAccountNumber().length()-2);
    }
    public void createAccountNumber() {
        // CL is type 3 as first digit of cart
        char firstDigit = Account.check(getOwner(),'3');
        String eightDigits = getOwner().getReferBranch().eightDigitT3;
        if (!getOwner().getAccounts().isEmpty()){
            eightDigits = getOwner().getAccounts().get(0).getAccountNumber().substring(8,getOwner().getAccounts().size()-1);
        }
        else {
            if (getOwner().getReferBranch().eightDigitT3 == "99999999") getOwner().getReferBranch().eightDigitT3 = "00000000";
            getOwner().getReferBranch().eightDigitT3 = countString(eightDigits);
        }
        setAccountNumber(Account.check(getOwner(),'3')+bank.getCardIdentity()+
                getOwner().getReferBranch().cardIdentity+eightDigits);
        Account.accounts.put(getAccountNumber(),this);
    }
}
