package BANK;

public class Teller extends Employee { private static int counter = 0; private double salary;

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

public void agreeWithRequest(String requestType, Customer customer) {
    if (requestType.equalsIgnoreCase("loan")) {
        if (customer.hasActiveLoan()) {
            customer.addMessage("درخواست وام رد شد: شما دارای وام فعال هستید.");
            return;
        }

        Request request = new Request("Loan", customer);
        customer.getBank().addRequest(request);
        AssistantManager assistant = getAssistantManager();

        if (assistant != null) {
            assistant.receiveMessage("درخواست وام مشتری: " + customer.getCustomerId());
        } else {
            customer.addMessage("درخواست شما ثبت شد اما معاون شعبه یافت نشد.");
        }
    } else if (requestType.equalsIgnoreCase("close account")) {
        if (customer.hasActiveLoan()) {
            customer.addMessage("درخواست حذف حساب رد شد: شما دارای وام فعال هستید.");
            return;
        }

        Request request = new Request("CloseAccount", customer);
        customer.getBank().addRequest(request);
        AssistantManager assistant = getAssistantManager();

        if (assistant != null) {
            assistant.receiveMessage("درخواست حذف حساب برای مشتری: " + customer.getCustomerId());
        } else {
            customer.addMessage("درخواست شما ثبت شد اما معاون شعبه یافت نشد.");
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

