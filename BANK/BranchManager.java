package BANK;

import java.io.IOException;
import java.util.ArrayList;

public final class BranchManager extends Employee {
    public static int counter = 0;
    private double salary;
    public BranchManager(Branch branchWork) {
        super();
        this.messages = new ArrayList<>();
        this.branchWork = branchWork;
        this.branchWork.bank.addEmployee(this);
        this.branchWork.setEmployeeToList(this);
        this.employeeIdentity = "M"+ counter;
        counter++;
        setSalary();
    }
    public BranchManager(String name,String lastName, MyDate birthday, String nationalCode , String address, String phoneNumber,Branch branchWork) {
        super(name , lastName , address , nationalCode ,birthday, phoneNumber);
        this.messages = new ArrayList<>();
        this.branchWork = branchWork;
        this.branchWork.bank.addEmployee(this);
        this.branchWork.setEmployeeToList(this);
        this.employeeIdentity = "M"+ counter;
        counter++;
        setSalary();
    }
    @Override
    public void setSalary(){
        this.salary = 2 * BaseSalary;
    }
    public void closeAccountCostumer(){

    }
    public void ShowAllEmployees(){
        for (Employee i : branchWork.getEmployees()){
            System.out.println(i);
        }
    }
    public void agreeWithRequest(){

    }
    @Override
    public boolean checkMessage() {
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
                    return false;
                }
                else {
                    form.setConfirmEmployee(employeeIdentity, " confirm this request ", 'C');
                    sendMessage(form);
                    deleteMessage(form);
                    return true;
                }
            case '1':
                if (form.amount > 500000000) {
                    form.setConfirmEmployee(employeeIdentity, " not confirm this request because it amount is not acceptable ", 'C');
                    sendMessage(form);
                    deleteMessage(form);
                    return false;
                }
                else {
                    form.setConfirmEmployee(employeeIdentity, " confirm this request ", 'C');
                    sendMessage(form);
                    deleteMessage(form);
                    return true;
                }
            default:
                return false;
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
    public void giveLoan(Letter form , char typeOfLoan){
        Customer c = branchWork.bank.findCustomer(form.getSenderId());
        switch(typeOfLoan){
            case '0':
                c.getLoans().add(new NormalLoan(form.amount,form.duration,c,Account.searchAccount(c.getAccounts(),form.accountNumber)));
                break;
            case '1':
                c.getLoans().add(new FacilitiesLoan(form.amount,form.duration,c,Account.searchAccount(c.getAccounts(),form.accountNumber)));
                break;
            default:
                throw new IllegalArgumentException ("this type of loan is not acceptable");
        }
        c.getLoans().add(new NormalLoan(form.amount,form.duration,c,Account.searchAccount(c.getAccounts(),form.accountNumber)));
    }
    @Override
    public String toString(){
        return "\n\t\t Manager \n"+
                showAllInformation()+
                "\nsalary : "+ salary +
                "\nEmployee Identity : "+ employeeIdentity +
                "\nbranch work id : " + branchWork.getId();
    }
}
