package BANK;

import java.util.ArrayList;

public final class AssistantManager extends Employee {
    private static int counter = 0;
    private double salary;

    public AssistantManager(Branch branchWork) {
        super();
        this.messages = new ArrayList<>();
        this.branchWork = branchWork;
        this.branchWork.bank.addEmployee(this);
        this.branchWork.setEmployeeToList(this);
        this.employeeIdentity = "A" + counter;
        counter++;
        setSalary();
    }
    public AssistantManager(String name,String lastName, MyDate birthday, String nationalCode , String address, String phoneNumber,Branch branchWork) {
        super(name , lastName , address , nationalCode ,birthday, phoneNumber);
        this.messages = new ArrayList<>();
        this.branchWork = branchWork;
        this.branchWork.bank.addEmployee(this);
        this.branchWork.setEmployeeToList(this);
        this.employeeIdentity = "A"+  counter;
        counter++;
        setSalary();
    }
    @Override
    public void setSalary() {
        this.salary = 1.5 * BaseSalary;
    }

    public double getSalary() {
        return salary;
    }


    private BranchManager getBranchManager() {
        for (Employee e : branchWork.getEmployees()) {
            if (e instanceof BranchManager) {
                return (BranchManager) e;
            }
        }
        return null;
    }
    //          message implementation part
    @Override
    public boolean checkMessage() {
        Letter form = messages.get(messages.size() - 1);
        for (Account i : branchWork.bank.findCustomer(form.getSenderId()).getAccounts()) {
            if (i.getHaveLoan(employeeIdentity.charAt(0))){
                form.setConfirmEmployee(employeeIdentity, "refuse this request you have current loan", 'C');
                sendMessage(form);
                deleteMessage(form);
                return false;
            }
        }
        return true;
    }
    @Override
    public void deleteMessage(Letter form) {
        for (int i = 0 ; i < messages.size() ; i++) {
            if (messages.get(i) == form) {
                messages.remove(form);
            }
        }
    }
    @Override
    public String toString() {
        if (name.equals("")) {
            return "\t\tAssistant Manager\n" +
                    "ID: " + employeeIdentity + "\n" +
                    "Salary: " + salary + "\n" +
                    "Branch ID: " + branchWork.getId();
        }
        else {
            return "\n\t\tAssistant Manager \n" +
                    showAllInformation() +
                    "\nsalary : " + salary +
                    "\nbranch ID : " + branchWork.getId();
        }
    }
}