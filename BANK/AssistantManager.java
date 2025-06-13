package BANK;

public class BranchManager extends Employee {
    private static int counter = 0;
    private double salary;

    public BranchManager(Branch branchWork) {
        this.branchWork = branchWork;
        this.employeeIdentity = "M" + branchWork.getId() + counter++;
        setSalary();
        branchWork.addEmployee(this);
    }

    @Override
    public void setSalary() {
        this.salary = 2 * BaseSalary;
    }

    public double getSalary() {
        return salary;
    }

    public void agreeWithRequest(String message) {
        System.out.println("مدیر تایید کرد: " + message);
    }

    @Override
    public String toString() {
        return "Branch Manager [ID=" + employeeIdentity + ", Salary=" + salary + ", Branch=" + branchWork.getId() + "]";
    }
}
