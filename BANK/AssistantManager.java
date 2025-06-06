package BANK;

public class AssistantManager extends Employee {
    private static int counter = 0;
    private double salary;

    public AssistantManager(Branch branchWork) {
        super();
        this.branchWork = branchWork;
        this.employeeIdentity = "A" + branchWork.getId() + counter;
        counter++;
        setSalary();
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
            boolean hasActiveLoan = false;
            for (Account acc : customer.getAccounts()) {
                // فرض: بررسی وجود وام فعال (نیاز به پیاده‌سازی دقیق‌تر در کلاس Account)
                // مثلاً acc.hasActiveLoan() == true
                // فعلاً شبیه‌سازی‌شده:
                hasActiveLoan = false; // جایگزین با شرط واقعی در صورت نیاز
            }

            if (hasActiveLoan) {
                customer.addMessage("وام جدید رد شد: وام فعال دارید.");
                System.out.println("درخواست وام مشتری " + customer.getCustomerId() + " رد شد (دارای وام فعال).");
            } else {
                // ارسال به مدیر برای تایید نهایی
                BranchManager manager = getBranchManager();
                if (manager != null) {
                    manager.receiveMessage("تایید نهایی وام برای مشتری " + customer.getCustomerId());
                    System.out.println("درخواست وام مشتری " + customer.getCustomerId() + " به مدیر شعبه ارجاع شد.");
                } else {
                    System.out.println("شعبه مدیر ندارد.");
                }
            }
        } else {
            System.out.println("نوع درخواست پشتیبانی نمی‌شود.");
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
        return "Assistant Manager\n" +
               "ID: " + employeeIdentity + "\n" +
               "Salary: " + salary + "\n" +
               "Branch ID: " + branchWork.getId();
    }
}