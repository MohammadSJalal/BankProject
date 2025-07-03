package BANK;

import java.util.ArrayList;
import java.util.List;
import java.util.ArrayList;

public final class Customer extends Person {
    private String customerId;
    private ArrayList<Account> accounts;
    private ArrayList<Letter> inboxMessages;
    private String nationalCode;
    private Branch referBranch;
    private ArrayList<BaseLoan> loans;
    //      contractors
    public Customer(Branch referBranch) {
        super("withdout Neme","without lastname");
        this.referBranch = referBranch;
        this.customerId = referBranch.getId() + 'C' +(referBranch.bank.customerCount++);
        this.referBranch.clients.put(this.customerId,this);
        this.inboxMessages = new ArrayList<>();
        this.accounts = new ArrayList<>();
        this.loans = new ArrayList<>();
    }
    public Customer(String name,String lastName, Branch referBranch) {
        super(name,lastName);
        this.referBranch = referBranch;
        this.customerId =  referBranch.getId()+ "C"+(referBranch.bank.customerCount++);
        this.referBranch.clients.put(this.customerId,this);
        this.inboxMessages = new ArrayList<>();
        this.accounts = new ArrayList<>();
        this.loans = new ArrayList<>();
    }
    public Customer(String name, String familyName, MyDate birthDate, String nationalCode, String phoneNumber, String address , Branch referBranch) {
        super(name, familyName, birthDate,nationalCode, phoneNumber, address);
        this.referBranch = referBranch;
        this.customerId =  referBranch.getId()+"C" +(referBranch.bank.customerCount++);
        this.referBranch.clients.put(this.customerId,this);
        this.inboxMessages = new ArrayList<>();
        this.accounts = new ArrayList<>();
        this.loans = new ArrayList<>();
    }
    //              ****get methods****
    public String getCustomerId() {
        return customerId;
    }

    public ArrayList<BaseLoan> getLoans() {
        return loans;
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public ArrayList<Letter> getInboxMessages() {
        return inboxMessages;
    }

    public Branch getReferBranch() {return referBranch;}

    //              ****section for message****
    @Override
    public Letter searchLetter(String subject) throws IllegalArgumentException{
        for(Letter l : inboxMessages){
            if (l.getSubject().equals(subject)) return l;
        }
        throw new IllegalArgumentException("we haven't such letter with this subject ");
    }
    @Override
    public void receiveMessage(Letter form) {
        inboxMessages.add(form);
    }
    @Override
    public void checkMessage() {
        System.out.println(inboxMessages.get(inboxMessages.size()-1));
    }
    @Override
    public void deleteMessage(Letter form) {
        inboxMessages.remove(form);
    }
    @Override
    public void sendMessage(Letter form) throws IncorrectEmployeeType {
        switch (form.getReceiverType()){
            case 'T':
                referBranch.foundLessBusyTaller().receiveMessage(form);
                break;
            case 'A':
                referBranch.foundLessBusyDeputy().receiveMessage(form);
                break;
            case 'M':
                referBranch.getManager().receiveMessage(form);
                break;
            default:
                throw new IncorrectEmployeeType("type of receiver not valid !!! ");
        }
    }
    public void showAllMessages(){
        System.out.println("\t\t\tmessage information");
        for (Letter letter : inboxMessages) {
            System.out.println(letter);
        }
    }

    //          *** section for all show functions ***
    public void showAllAccount(){
        for (Account a : accounts) {
            System.out.println(a);
        }
    }



    //          *** section for all request functions ***
    public void createAccountRequest(String accountType, double initialBalance,MyDate d) throws IllegalArgumentException {
        if (Account.permission(this,accountType.charAt(1))) {
            Letter form = new Letter(customerId,"please create a account",d,referBranch,accountType , initialBalance);
            this.referBranch.foundLessBusyTaller().createAccount(form);
        }
        else throw new IllegalArgumentException("you already have three account of type this !!! ");
    }
    public void requestCloseAccount( Branch branch , MyDate d , int index) throws IllegalArgumentException {
        Letter form = new Letter(this.customerId,"please close this account",d,this.referBranch,index);
        form.setReceiverType('T');
        sendMessage(form);
    }
    /** @param typeOfLoan
     * this specifying which loan if type of loan is 0 mean normal
     * and if 1 mean charity
     */
    public void requestLoan(double amount , int durationInMonths ,MyDate d ,Account acc, char typeOfLoan) {
        Letter form = new Letter(customerId,"loan request",d,referBranch,acc.getAccountNumber(),durationInMonths,amount,typeOfLoan);
        referBranch.foundLessBusyTaller().loanRequest(form);
    }
    /**
     * this function is for getting loan based worked account
     */
    public void requestLoan(int durationInMonths ,MyDate d ,Account acc , char typeOfLoan) {
        Letter form = new Letter(this.customerId , "loan request and i agree with 34 percent of interest",
                d , referBranch , acc.getAccountNumber(),durationInMonths , acc.getAverageBalance()*2 , typeOfLoan);
        referBranch.foundLessBusyTaller().loanRequest(form);
    }

    //          *** section for all operate of money
    /**
     * this function transfer money
     * @param from a account that is for person that want to transfer another person
     * @param to a account number for getting money
     * @param amount
     */
    public void transferMoney(Account from, String to, double amount) {
        from.transferMoney(amount, to);
    }
    /**
     * this function transfer money
     * @param firstDigit a first digit of a account of right it must between 1 till 9
     * @param to a account number for getting money
     * @param nth it mean nth account with the same type
     * @param amount
     */
    public void transferMoney(int firstDigit, String to, double amount , int nth) throws IllegalArgumentException {
        Account acc = Account.searchAccount(accounts,firstDigit,nth);
        acc.transferMoney(amount,to);
    }
    public void deposit(Account account ,double amount, boolean receipt) throws IllegalArgumentException{
        if (receipt) System.out.println("balance : "+account.getBalance() + " + "+amount);
        account.deposit(amount);
        if (receipt) System.out.println("now balance : "+account.getBalance());
    }
    public void deposit( int firstDigit,double amount , boolean receipt , int nth) throws IllegalArgumentException {
        Account account = Account.searchAccount(accounts,firstDigit,nth);
        if (account == null) throw new  IllegalArgumentException("there are no such account");
        if (receipt) System.out.println("balance : "+account.getBalance() + " + "+amount);
        account.deposit(amount);
        if (receipt) System.out.println("now balance : "+account.getBalance());
    }

    public void withdraw(Account account, double amount , boolean receipt) throws IllegalArgumentException {
        if (receipt) System.out.println("balance : "+ account.getBalance() + " - " + amount);
        account.withdraw(amount,0);
        if (receipt) System.out.println("now balance : " + account.getBalance()+"\n");
    }
    /**withdraw with type of account type must be 1 -> current account and so on
     * @param firstDigit legal value 1 2 3 ... 9 if customer have such account if not it throw a exception
     */
    public double withdraw(int firstDigit,double amount , boolean receipt , int nth) throws IllegalArgumentException {
        if (firstDigit < 1 || firstDigit > 9) throw new IllegalArgumentException("invalid type of account");
        Account acc = Account.searchAccount(accounts,firstDigit,nth);
        if (receipt) System.out.println("balance : "+ acc.getBalance() + " - " + amount);
        acc.withdraw(amount,0);
        if (receipt) System.out.println("now balance : " + acc.getBalance()+"\n");
        return amount;
    }
    public double withdraw(String accountNumber, double amount , boolean receipt) throws IllegalArgumentException {
        Account account = Account.searchAccount(accounts,accountNumber);
        if (receipt) System.out.println("balance : "+ account.getBalance() + " - " + amount);
        account.withdraw(amount,0);
        if (receipt) System.out.println("now balance : " + account.getBalance()+"\n");
        return amount;
    }
    public void viewAccounts() {
        for (Account acc : accounts) {
            System.out.println("Account: " + acc.getAccountNumber() + " | Balance: " + acc.getBalance());
        }
    }
    public void showMessage() {
        for(Letter l : inboxMessages){
            System.out.println(l);
        }
    }
    public void addLoan(BaseLoan loan) {
        loans.add(loan);
    }
    public void deleteLoan(BaseLoan loan) {
        loans.remove(loan);
    }
    public void showLoans(){
        for (BaseLoan loan : loans) {
            System.out.println(loan);
        }
    }
    @Override
    public String toString() {
        StringBuilder accInfo = new StringBuilder();
        for (Account acc : accounts) {
            accInfo.append(acc.toString());
        }
        if (referBranch != null)
            return "\n\n\t\tCUSTOMER"+"\nName: "+name+"\nlast Name: " + lastName +"\nCustomer ID: " + customerId + "\nNational Code: " + nationalCode+
                "\nfirst branch visited : Branch name ->" + referBranch.getName() +"\n and branch id is : "+ referBranch.getId()+
                    "\n\t\tall account information"+accInfo.toString();
        else
            return "Customer ID: " + customerId + "\nName: " + name + " " + lastName + "\nNational Code: " + nationalCode;

    }
}