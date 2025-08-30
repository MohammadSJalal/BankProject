public class AssistantManager extends Employee {
    private static int counter = 1;
    private double salary;

    public AssistantManager(Branch branchWork) {
        this.branchWork = branchWork;
        this.employeeIdentity = "A-" + branchWork.getId() + "-" + counter++;
        setSalary();
        branchWork.addEmployee(this);
    }

    @Override
    public void setSalary() {
        this.salary = BaseSalary * 1.5;
    }

    public double getSalary() {
        return salary;
    }


    public void handleRequest(String requestType, Customer customer) {
        if (requestType.equalsIgnoreCase("loan")) {
            if (customer.hasActiveLoan()) {
                customer.addMessage("❌ درخواست وام رد شد: شما وام فعال دارید.");
                return;
            }
            BranchManager manager = getBranchManager();
            if (manager != null) {
                manager.receiveMessage("📢 تایید نهایی وام برای مشتری: " + customer.getCustomerId());
                Request req = new Request("loan final approval", customer);
                customer.getBank().addRequest(req);
            } else {
                customer.addMessage("❌ مدیر شعبه پیدا نشد. درخواست ثبت نشد.");
            }

        } else if (requestType.equalsIgnoreCase("close account")) {
            BranchManager manager = getBranchManager();
            if (manager != null) {
                manager.receiveMessage("📢 تایید نهایی حذف حساب مشتری: " + customer.getCustomerId());
                Request req = new Request("close account final approval", customer);
                customer.getBank().addRequest(req);
            } else {
                customer.addMessage("❌ مدیر شعبه پیدا نشد. درخواست حذف حساب ثبت نشد.");
            }
        }
    }


    private BranchManager getBranchManager() {
        for (Employee e : branchWork.getEmployees()) {
            if (e instanceof BranchManager) return (BranchManager) e;
        }
        return null;
    }

    @Override
    public String toString() {
        return "AssistantManager{" +
                "ID='" + employeeIdentity + '\'' +
                ", Branch=" + branchWork.getId() +
                ", Salary=" + salary +
                '}';
    }
}
