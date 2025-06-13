package BANK;

public class Teller extends Employee {
    private static int counter = 0;
    private double salary;

    public Teller(Branch branchWork) {
        this.branchWork = branchWork;
        this.employeeIdentity = "T" + branchWork.getId() + counter++;
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

    // بررسی درخواست مشتری برای حذف حساب یا وام و ارسال به معاون
    public void agreeWithRequest(String requestType, Customer customer) {
        if (requestType.equalsIgnoreCase("loan")) {
            AssistantManager assistant = getAssistantManager();
            if (assistant != null) {
                assistant.receiveMessage("درخواست وام مشتری: " + customer.getCustomerId());
            } else {
                System.out.println("معاون شعبه یافت نشد.");
            }
        } else if (requestType.equalsIgnoreCase("close account")) {
            boolean hasLoan = false; // فرض: بررسی دقیق وام باید در حساب‌ها انجام شه
            if (hasLoan) {
                customer.addMessage("درخواست حذف حساب رد شد: دارای وام فعال هستید.");
            } else {
                AssistantManager assistant = getAssistantManager();
                if (assistant != null) {
                    assistant.receiveMessage("درخواست حذف حساب برای مشتری: " + customer.getCustomerId());
                } else {
                    System.out.println("معاون شعبه یافت نشد.");
                }
            }
        } else {
            System.out.println("نوع درخواست پشتیبانی نمی‌شود.");
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
        return "Teller [ID=" + employeeIdentity + ", Salary=" + salary + ", Branch=" + branchWork.getId() + "]";
    }
}