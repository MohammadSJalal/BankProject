package BANK;

public class AssistantManager extends Employee {
    private static int counter = 0;
    private double salary;

    public AssistantManager(Branch branchWork) {
        this.branchWork = branchWork;
        this.employeeIdentity = "A" + branchWork.getId() + counter++;
        setSalary();
        branchWork.addEmployee(this);
    }

    @Override
    public void setSalary() {
        this.salary = 1.5 * BaseSalary;
    }

    public double getSalary() {
        return salary;
    }

    public void agreeWithRequest(String requestType, Customer customer) {
        if (requestType.equalsIgnoreCase("loan")) {
            boolean hasLoan = false;
            if (hasLoan) {
                customer.addMessage("درخواست وام رد شد: وام فعال دارید.");
            } else {
                BranchManager manager = getBranchManager();
                if (manager != null) {
                    manager.receiveMessage("تایید نهایی وام برای مشتری: " + customer.getCustomerId());
                }
            }
        }
    }

    private BranchManager getBranchManager() {
        for (Employee e : branchWork.getEmployees()) {
            if (e instanceof BranchManager) {
                return (BranchManager) e;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Assistant Manager [ID=" + employeeIdentity + ", Salary=" + salary + ", Branch=" + branchWork.getId() + "]";
    }
}