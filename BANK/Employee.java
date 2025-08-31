import java.util.*;

public abstract class Employee {
    protected String employeeIdentity;
    protected final double BaseSalary = 1000.00;
    protected Branch branchWork;
    protected final List<String> messages = new ArrayList<>();

    public abstract void setSalary();

    public void receiveMessage(String message){ messages.add(message); }
    public void showMessages(){
        System.out.println("پیام‌های دریافتی کارمند: " + employeeIdentity);
        for (String m : messages) System.out.println("- " + m);
    }

    public String getEmployeeIdentity(){ return employeeIdentity; }
    public Branch getBranchWork(){ return branchWork; }
    public void setBranchWork(Branch branch){ this.branchWork = branch; }
}
