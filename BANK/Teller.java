public class Teller extends Employee {
    private static int counter = 1;
    private double salary;

    public Teller(Branch branchWork) {
        this.branchWork = branchWork;
        this.employeeIdentity = "T-" + branchWork.getId() + "-" + counter++;
        setSalary();
        branchWork.addEmployee(this);
    }

    @Override
    public void setSalary() {
        this.salary = BaseSalary; 
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
            Request request = new Request("loan", customer);
            customer.getBank().addRequest(request);

            AssistantManager assistant = getAssistantManager();
            if (assistant != null) {
                assistant.receiveMessage("📢 درخواست وام مشتری: " + customer.getCustomerId());
            } else {
                customer.addMessage("ℹ️ درخواست ثبت شد اما معاون شعبه پیدا نشد.");
            }

        } else if (requestType.equalsIgnoreCase("close account")) {
            if (customer.hasActiveLoan()) {
                customer.addMessage("❌ درخواست حذف حساب رد شد: شما وام فعال دارید.");
                return;
            }
            Request request = new Request("close account", customer);
            customer.getBank().addRequest(request);

            AssistantManager assistant = getAssistantManager();
            if (assistant != null) {
                assistant.receiveMessage("📢 درخواست حذف حساب مشتری: " + customer.getCustomerId());
            } else {
                customer.addMessage("ℹ️ درخواست ثبت شد اما معاون شعبه پیدا نشد.");
            }
        } else {
            System.out.println("❌ نوع درخواست پشتیبانی نمی‌شود.");
        }
    }


    private AssistantManager getAssistantManager() {
        for (Employee e : branchWork.getEmployees()) {
            if (e instanceof AssistantManager) return (AssistantManager) e;
        }
        return null;
    }

    @Override
    public String toString() {
        return "Teller{" +
                "ID='" + employeeIdentity + '\'' +
                ", Branch=" + branchWork.getId() +
                ", Salary=" + salary +
                '}';
    }
}
