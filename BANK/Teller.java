package BANK;

import java.util.ArrayList;

public final class Teller extends Employee {
    private static int counter = 0;
    private double salary;

    public Teller(Branch branchWork) {
        super();
        this.messages = new ArrayList<>();
        this.branchWork = branchWork;
        this.branchWork.bank.addEmployee(this);
        this.branchWork.setEmployeeToList(this);
        this.employeeIdentity = "T"+ counter;
        counter++;
        setSalary();
    }
    public Teller(String name,String lastName, MyDate birthday, String nationalCode , String address, String phoneNumber,Branch branchWork) {
        super(name , lastName , address , nationalCode ,birthday, phoneNumber);
        this.messages = new ArrayList<>();
        this.branchWork = branchWork;
        this.branchWork.bank.addEmployee(this);
        this.branchWork.setEmployeeToList(this);
        this.employeeIdentity = "T" + counter;
        counter++;
        setSalary();
    }
    //                       **this section is for handle message**
    /**
     * this function operate all work that a taller has done in branch it is a main function of
     * taller that detect and divide word and call corresponding method
     * @return if work correctly return true as done and flase for if there are any problem
     * @throws IllegalArgumentException also it maybe happening when corresponding method are called
     * @throws IndexOutOfBoundsException it can be happened
     */
    @Override
    public boolean checkMessage() throws IllegalArgumentException , IndexOutOfBoundsException {
        Letter lastForm = messages.get(messages.size() - 1);
        switch(lastForm.getMessage()){
            case "01": // 0 as open account and 1 is as current account
                createAccount(lastForm);
                break;
            case "02":
                createAccount(lastForm);
                break;
            case "03":
                createAccount(lastForm);
                break;
            case "1": // it is a request for close account
                closeAccount((Customer) lastForm.getSender(),lastForm.index);
                break;
            case "20"://it is for loan of type 0 mean normal loan
                loanRequest(lastForm);
                break;
            case "21":// it is special loan
                boolean agree = loanRequest(lastForm);
                break;
            default:
                throw new IllegalArgumentException("Invalid message in Teller : " + lastForm.getMessage()+" inside of Teller checkMessage");
        }
        return false;
    }
    @Override
    public void deleteMessage(Letter form) {
        for (int i = 0 ; i < messages.size() ; i++) {
            if (messages.get(i) == form) {
                messages.remove(form);
            }
        }
    }
    //          this end of section of message
    @Override
    public void setSalary() {
        this.salary = BaseSalary;
    }

    public double getSalary() {
        return salary;
    }

    public void createAccount(Letter form) {
        Customer c = branchWork.bank.findCustomer(form.getSenderId());
        switch (form.getTypeOfAccount()) {
            case "01":
                c.getAccounts().add(new CurrentAccount(c,branchWork.bank,form.getDateOfMessage(),form.getInitialBalance()));
                break;
            case "02":
                c.getAccounts().add(new ShortTermSavingAccount(c,branchWork.bank,form.getDateOfMessage(), form.getInitialBalance()));
                break;
            case "03":
                c.getAccounts().add(new CurrentAccount(c,branchWork.bank,form.getDateOfMessage(), form.getInitialBalance()));
                break;
            default:
                throw new IllegalArgumentException("something is wrong with this typeAccount");
        }
    }
    public void closeAccount(Customer c, int indexAccount) {

        for (BaseLoan b : c.getLoans()){
            if (b.checkAccount(c.getAccounts().get(indexAccount))){
                throw new IllegalArgumentException("sadly you have a loan with this account!");
            }
        }
        c.getAccounts().remove(indexAccount);
    }

    /**
     * if request of costumer is legal then it can be send for up level employee
     * @param form this is form that contain all necessary information
     * @return true if taller check all require document and are valid and false other hand
     */
    public boolean loanRequest(Letter form) throws IllegalArgumentException {
        if (branchWork.bank.confirmCustomer(form.getSenderId()) && checkAccWithTypeLoan(form.accountNumber,form.typeOfLoan)){
            form.setConfirmEmployee(employeeIdentity," confirm this request ",'A');
            sendMessage(form);
            deleteMessage(form);
        }
        return false;
    }
    public boolean checkAccWithTypeLoan(String accNum, char typeLoan) {
        if ((accNum.charAt(0) == '1' || accNum.charAt(0) == '2') && typeLoan == '0') return true;
        else if (accNum.charAt(0) == '3' && typeLoan == '1') return true;
        else return false;
    }

    @Override
    public String toString() {
        return "\n\t\tTeller\n" +
                showAllInformation() +
               "ID: " + employeeIdentity + "\n" +
               "Salary: " + salary + "\n" +
               "Branch ID: " + branchWork.getId();
    }
}