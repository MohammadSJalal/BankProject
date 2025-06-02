package BANK;
public class BranchManager extends Employee {
    public static int counter = 0;
    private double salary;
    public BranchManager(Branch branchWork) {
        super();
        this.branchWork = branchWork;
        this.employeeIdentity = "M"+ branchWork.getId() + counter;
        counter++;
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
    @Override
    public String toString(){
        return "level Branch Manager\nemployee id : " + this.employeeIdentity + "\nsalary : " + this.salary + "\nbranch work id : " + this.branchWork.toString();
    }
    public void agreeWithRequest(){

    }
}
