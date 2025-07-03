package BANK;

import java.io.IOException;
import java.util.ArrayList;

public final class BranchManager extends Employee {
    private double salary;
    public BranchManager(Branch branchWork) {
        super(branchWork);
        this.employeeIdentity =  branchWork.getId()+"M"+ branchWork.bank.managerCounter++;
        branchWork.setEmployeeToList(this);
        setSalary();
    }
    public BranchManager(String name,String lastName, MyDate birthday, String nationalCode , String address, String phoneNumber,Branch branchWork) {
        super(branchWork , name , lastName , address , nationalCode ,birthday, phoneNumber);
        this.employeeIdentity =  branchWork.getId()+"M"+ branchWork.bank.managerCounter++;
        branchWork.setEmployeeToList(this);
        setSalary();
    }
    @Override
    public void setSalary(){
        this.salary = 2 * BaseSalary;
    }
    public void ShowAllEmployees(){
        for (Teller i : branchWork.tellers.values()){
            System.out.println(i);
        }
        for (AssistantManager a : branchWork.deputy.values()){
            System.out.println(a);
        }
        System.out.println(this);
    }
    @Override
    public void checkMessage() {
        /*
        this function return a boolean value as the result of agreement or
        disagreement in all classes that override it.
         */
        Letter form = messages.get(messages.size() - 1);
        if (form.amount >= branchWork.bank.getTotalAmountOfMoney()) throw new IllegalArgumentException ("this amount is not acceptable");
        switch(form.typeOfLoan){
            case '0':
                if(form.amount > 1000000000) {
                    form.setConfirmEmployee(employeeIdentity, " not confirm this request because it amount is not acceptable ", 'C');
                    sendMessage(form);
                    deleteMessage(form);
                }
                else {
                    form.setConfirmEmployee(employeeIdentity, " bank accept you request and your request is inside of queue", 'C');
                    boolean accept = form.getMessage().equals("loan request and i agree with 34 percent of interest");
                    sendMessage(form);
                    Customer c = branchWork.bank.findCustomer(form.getSenderId());
                    if (accept){
                        NormalLoan l = new NormalLoan(form.amount , form.duration , form.getDateOfMessage() , c , Account .searchAccount(c.getAccounts(),form.accountNumber), accept);
                        l.setSate(1);
                    }
                    else{
                        NormalLoan l = new NormalLoan(form.amount,form.duration,form.getDateOfMessage(),c,Account.searchAccount(c.getAccounts(),form.accountNumber));
                        l.setSate(1);
                    }
                    deleteMessage(form);
                    break;
                }
            case '1':
                if (form.amount > 500000000) {
                    form.setConfirmEmployee(employeeIdentity, " not confirm this request because it amount is not acceptable ", 'C');
                    sendMessage(form);
                }
                else {
                    form.setConfirmEmployee(employeeIdentity, " confirm this request ", 'C');
                    sendMessage(form);
                    Customer c = branchWork.bank.findCustomer(form.getSenderId());
                    FacilitiesLoan f = new FacilitiesLoan(form.amount,form.duration,form.getDateOfMessage(),c,Account.searchAccount(c.getAccounts(),form.accountNumber));
                    f.setSate(1);
                }
                deleteMessage(form);
                break;
            default:
        }
    }
    @Override
    public void deleteMessage(Letter form) {
        for (int i = 0 ; i < messages.size() ; i++) {
            if (messages.get(i) == form) {
                messages.remove(form);
            }
        }
    }
    public String writeAgreement(char typeOfLoan)throws IllegalArgumentException{
        switch(typeOfLoan){
            case '0':
                return "we agreement with normal loan of you";
            case '1':
                return "we agreement with facilities loan";
            default:
                throw new IllegalArgumentException ("this type of loan is not acceptable");
        }
    }
    //this function is not interesting
    public void giveLoan(Letter form , char typeOfLoan){
        Customer c = branchWork.bank.findCustomer(form.getSenderId());
        switch(typeOfLoan){
            case '0':
                c.getLoans().add(new NormalLoan(form.amount
                        ,form.duration,form.getDateOfMessage(),c,Account.searchAccount(c.getAccounts(),form.accountNumber)));
                break;
            case '1':
                c.getLoans().add(new FacilitiesLoan(form.getAmount(),form.duration,form.getDateOfMessage(),c,Account.searchAccount(c.getAccounts(),form.accountNumber)));
                break;
            default:
                throw new IllegalArgumentException ("this type of loan is not acceptable");
        }
        c.getLoans().add(new NormalLoan(form.amount,form.duration,form.getDateOfMessage(),c,Account.searchAccount(c.getAccounts(),form.accountNumber)));
    }
    @Override
    public String toString(){
        return "\n\n\t\t MANAGER"+
                showAllInformation()+
                "\nsalary : "+ salary +
                "\nEmployee Identity : "+ employeeIdentity +
                "\nbranch work id : " + branchWork.getId();
    }
}
