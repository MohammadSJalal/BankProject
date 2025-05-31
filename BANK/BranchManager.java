package BANK;
public class BranchManager extends Employee {
    public static int counter = 0;
    private double salary;
    public BranchManager(Branch branchWork) {
        super();
        this.employeeIdentity = "M"+ branchWork.getId() + counter;
        counter++;
    }
    @Override
    public void setSalary(){
        this.salary = 2 * BaseSalary;
    }

}
