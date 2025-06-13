package BANK;

public class AssistantManager extends Employee { private static int counter = 0; private double salary;

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
        if (customer.hasActiveLoan()) {
            customer.addMessage("درخواست وام رد شد: شما دارای وام فعال هستید.");
            return;
        }
        BranchManager manager = getBranchManager();
        if (manager != null) {
            manager.receiveMessage("تایید نهایی وام برای مشتری: " + customer.getCustomerId());
            Request req = new Request("Loan Final Approval", customer);
            customer.getBank().addRequest(req);
        } else {
            customer.addMessage("مدیر شعبه یافت نشد. درخواست تایید نشد.");
        }
    } else if (requestType.equalsIgnoreCase("close account")) {
        BranchManager manager = getBranchManager();
        if (manager != null) {
            manager.receiveMessage("تایید نهایی حذف حساب مشتری: " + customer.getCustomerId());
            Request req = new Request("Close Account Final Approval", customer);
            customer.getBank().addRequest(req);
        } else {
            customer.addMessage("مدیر شعبه یافت نشد. درخواست حذف حساب تایید نشد.");
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

