import java.util.ArrayList;
import java.util.List;

public abstract class Employee {
    protected String employeeIdentity;
    protected final double BaseSalary = 1000.00;
    protected Branch branchWork;
    protected List<String> messages = new ArrayList<>();

    public abstract void setSalary();

    public void receiveMessage(String message) {
        messages.add(message);
    }

    public void showMessages() {
        System.out.println("پیام‌های دریافتی کارمند با شناسه: " + employeeIdentity);
        for (String msg : messages) {
            System.out.println("- " + msg);
        }
    }

    public String getEmployeeIdentity() {
        return employeeIdentity;
    }

    public Branch getBranchWork() {
        return branchWork;
    }

    public void setBranchWork(Branch branch) {
        this.branchWork = branch;
    }
}
