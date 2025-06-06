package BANK;

public class Teller extends Employee {
    private static int counter = 0;
    private double salary;

    public Teller(Branch branchWork) {
        super();
        this.branchWork = branchWork;
        this.employeeIdentity = "T" + branchWork.getId() + counter;
        counter++;
        setSalary();
    }

    @Override
    public void setSalary() {
        this.salary = BaseSalary;
    }

    public double getSalary() {
        return salary;
    }

    public void agreeWithRequest(String requestType, Customer customer) {
        System.out.println("Processing request from customer " + customer.getCustomerId());

        if (requestType.equalsIgnoreCase("loan")) {
            // فرض بر صحت اطلاعات ورودی (در عمل احراز هویت مشتری انجام می‌گیره)
            System.out.println("Sending loan request to Assistant Manager...");
            AssistantManager assistant = getAssistantManager();
            if (assistant != null) {
                assistant.receiveMessage("Loan request from customer: " + customer.getCustomerId());
            } else {
                System.out.println("Assistant Manager not found in branch.");
            }
        }

        else if (requestType.equalsIgnoreCase("close account")) {
            boolean hasActiveLoan = false;
            for (Account acc : customer.getAccounts()) {
                // اینجا باید بررسی واقعی وجود وام انجام شود
                // فرض می‌کنیم بررسی شده و وامی نیست
                hasActiveLoan = false;
            }

            if (hasActiveLoan) {
                customer.addMessage("درخواست حذف حساب رد شد: دارای وام فعال هستید.");
                System.out.println("Close account request rejected for customer " + customer.getCustomerId());
            } else {
                AssistantManager assistant = getAssistantManager();
                if (assistant != null) {
                    assistant.receiveMessage("Close account request from customer: " + customer.getCustomerId());
                } else {
                    System.out.println("Assistant Manager not found in branch.");
                }
            }
        }

        else {
            System.out.println("Unsupported request type.");
        }
    }

    private AssistantManager getAssistantManager() {
        for (Employee e : branchWork.getEmployees()) {
            if (e instanceof AssistantManager) {
                return (AssistantManager) e;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Teller\n" +
               "ID: " + employeeIdentity + "\n" +
               "Salary: " + salary + "\n" +
               "Branch ID: " + branchWork.getId();
    }
}