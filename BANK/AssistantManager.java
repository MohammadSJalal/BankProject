package BANK;

import java.util.ArrayList;

public final class AssistantManager extends Employee {
    private double salary;

    public AssistantManager(Branch branchWork) {
        super(branchWork);
        this.employeeIdentity =  branchWork.getId()+"A" + branchWork.bank.assistantCounter++;
        branchWork.setEmployeeToList(this);
        setSalary();
    }
    public AssistantManager(String name,String lastName, MyDate birthday, String nationalCode , String address, String phoneNumber,Branch branchWork) {
        super(branchWork,name , lastName , address , nationalCode ,birthday, phoneNumber);
        this.employeeIdentity =  branchWork.getId()+"A"+  branchWork.bank.assistantCounter++;
        branchWork.setEmployeeToList(this);
        setSalary();
    }
    @Override
    public void setSalary() {
        this.salary = 1.5 * BaseSalary;
    }

    public double getSalary() {
        return salary;
    }
    //          message implementation part
    @Override
    public void checkMessage() {
        Letter form = messages.get(messages.size() - 1);
        for (Account i : ((Customer)Bank.find(form.getSenderId())).getAccounts()) {
            if (i.getHaveLoan(employeeIdentity.charAt(0))){
                form.setConfirmEmployee(employeeIdentity, "refuse this request you have current loan", 'C');
                sendMessage(form);
                deleteMessage(form);
            }
        }
        form.setConfirmEmployee(employeeIdentity,"customer have not any loan and they request is acceptable for this stage",'M');
        sendMessage(form);
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
            return "\n\n\t\tASSISTANT MANAGER\n" +
                    "ID: " + employeeIdentity + "\n" +
                    "Salary: " + salary + "\n" +
                    "Branch ID: " + branchWork.getId();
        }
        else {
            return "\n\n\t\tASSISTANT MANAGER\n" +
                    showAllInformation() +
                    "\nsalary : " + salary +
                    "\nbranch ID : " + branchWork.getId();
        }
    }
}