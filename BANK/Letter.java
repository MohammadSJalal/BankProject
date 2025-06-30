package BANK;

public final class Letter {


    //          all fields              //


    //this is public for all letter
    private String senderId ;
    private char receiverType ;
    private String message ;
    private MyDate dateOfMessage ;
    private Branch branch ;
    //this is for open account
    private String typeOfAccount;
    private double initialBalance;
    //this is for close account
    public int index;
    //this is for loan request
    protected String accountNumber;
    public int duration;
    public double amount;
    /** 0 for normal and 1 for specific is value that inserted inside of variable*/
    protected char typeOfLoan;
    /**this is for employee that confirm and request get go up for advanced employee*/
    private String confirmEmployee;
    private boolean letterIsSafe;
    //              all constructors                //

    /**
     * this constractor is for open account
     */
    public Letter(String senderId , String message , MyDate dateOfMessage , Branch branch, String typeOfAccount) {
        this.senderId = senderId;
        this.message = message;
        this.dateOfMessage = dateOfMessage;
        this.typeOfAccount = typeOfAccount;
        this.branch = branch;
        this.letterIsSafe = true;
    }
    public Letter(String senderId , String message , MyDate dateOfMessage , Branch branch , String typeOfAccount, double initialBalance) {
        this.senderId = senderId;
        this.message = message;
        this.dateOfMessage = dateOfMessage;
        this.typeOfAccount = typeOfAccount;
        this.branch = branch;
        this.initialBalance = initialBalance;
        this.letterIsSafe = true;
    }
    /**
     * this constractor is for close account
     */
    public Letter(String senderId, String message , MyDate dateOfMessage , Branch branch , int index) {
        this.senderId = senderId;
        this.message = message;
        this.dateOfMessage = dateOfMessage;
        this.branch = branch;
        this.index = index;
        this.letterIsSafe = true;
    }
    /**
     * this constractor is for loan request
     */
    public Letter(String senderId , String message , MyDate dateOfMessage , Branch branch , String accountNumber, int duration, double amount, char typeOfLoan) {
        this.senderId = senderId;
        this.message = message;
        this.dateOfMessage = dateOfMessage;
        this.branch = branch;
        this.typeOfLoan = typeOfLoan;
        setAccountNumber(accountNumber);
        setDuration(duration);
        setAmount(amount);
        this.letterIsSafe = true;
    }
    /**this constractor is for test*/
    public Letter(){ this.letterIsSafe = false; };


    //              set functions           //


    public void setDateOfMessage(MyDate dateOfMessage) {
        this.dateOfMessage = dateOfMessage;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    /** this function have a patter for write just give confirm
     * @param ID id of employee we not allow anyone else to confirm
     * @param confirm this is a message that employees write for other employee
     * @param RT it is abbrivation for receiverType
     */
    public void setConfirmEmployee(String ID,String confirm,char RT) throws IllegalArgumentException {
        setReceiverType(RT);
        switch (ID.charAt(0)) {
            case 'T':
                confirmEmployee += "Teller : " + confirm + "\n"+"id of Teller : " + ID +"\n";
                break;
            case 'A':
                confirmEmployee += "Deputy : " + confirm + "\n"+"id of deputy : " + ID +"\n";
                break;
            case 'M':
                confirmEmployee += "Manager : " + confirm + "\n"+"id of deputy : " + ID +"\n";
                break;
            default:
                throw new IllegalArgumentException("this id isn't for a employee ! ID: " + ID);

        }
    }
    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public void setReceiverType(char receiverId) throws IllegalArgumentException {
        if (receiverId == 'A' || receiverId == 'M' || receiverId == 'T' || receiverId == 'C' || receiverId == '-') {
            this.receiverType = receiverId;
        }
        else throw new IllegalArgumentException("this id isn't for a receiver ! ID: " + receiverId +" we expect to be A or M or T or C");
    }
    public void setDuration(int duration) {
        if (duration <= 0) throw new IllegalArgumentException("Duration cannot be negative or zero (error is inside of setDuration)");
        this.duration = duration;
    }
    public void setAmount(double amount) {
        if(amount <= 0) throw new IllegalArgumentException("Amount cannot be negative or zero (error is inside of setAmount)");
        this.amount = amount;
    }
    public void setAccountNumber(String accountNumber) {
        if (accountNumber.length() != 13) throw new IllegalArgumentException("Account number must be 13 characters in length");
        this.accountNumber = accountNumber;
    }
    public final void setInitialBalance(double initialBalance) {
        if(initialBalance > 0)this.initialBalance = initialBalance;
        throw new IllegalArgumentException("inital balance cannot be less than zero (error is inside of setInitialBalance)");
    }

    //          all get method          //


    public Branch getBranch() {
        return branch;
    }
    public boolean getLetterIsSafe() {return letterIsSafe;}
    public String getAccountNumber() {
        return accountNumber;
    }
    public Person getSender() {
        char typeOfPerson = senderId.charAt(0);
        switch(typeOfPerson){
            case 'M':
                return branch.getManager();
            case 'A':
                return branch.foundLessBusyDeputy();
            case 'T':
                return branch.foundLessBusyTaller();
            case 'C':
                return branch.bank.findCustomer(senderId);
        }
        throw new IllegalArgumentException("sadly you id for sender is not valid (getSender function inside of Letter class)");
    }
    public String getSenderId() {
        return senderId;
    }
    public String getMessage() {
        return message;
    }
    public MyDate getDateOfMessage() {
        return dateOfMessage;
    }
    public char getReceiverType(){return receiverType;}
    public String getConfirmEmployee() {
        return confirmEmployee;
    }
    public double getAmount(){
        return amount;
    }
    public String getTypeOfAccount() {
        return typeOfAccount;
    }
    public int getDuration() {return duration;}
    public double getInitialBalance() {return initialBalance;}

    public String toString (){
        return "\t\t\t form\t\t\t\nsender : "+"\ndate of message : "+dateOfMessage+"sender id : " + senderId +"to (sir / miss) employee : "+receiverType+ "\nmessage : " + message + "employee confirm and notation : "+
                getConfirmEmployee();
    }
}
