package BANK;

public class BranchManager extends Employee { private static int counter = 0; private double salary;

public BranchManager(Branch branchWork) {
    this.branchWork = branchWork;
    this.employeeIdentity = "M" + branchWork.getId() + counter++;
    setSalary();
    branchWork.addEmployee(this);
}

@Override
public void setSalary() {
    this.salary = 2 * BaseSalary;
}

public double getSalary() {
    return salary;
}

public void agreeWithRequest(String message) {
    System.out.println("مدیر تایید کرد: " + message);
}

public void finalizeRequest(Request request) {
    Customer customer = request.getRequester();
    boolean approved = false;
    String type = request.getType().toLowerCase();
    String msg;

    switch (type) {
        case "loan final approval" -> {
            // ساده‌سازی: وام عادی با مبلغ ثابت
            if (!customer.hasActiveLoan()) {
                BaseLoan loan = new NormalLoan(500_000_000, 12, customer);
                customer.getBank().addLoan(loan);
                msg = "وام برای مشتری " + customer.getCustomerId() + " تایید و ثبت شد.";
                approved = true;
            } else {
                msg = "مشتری دارای وام فعال است. درخواست رد شد.";
            }
        }
        case "close account final approval" -> {
            // در عمل باید شماره حساب خاصی مشخص شود، اینجا فرض ساده‌سازی
            customer.getAccounts().clear();
            msg = "همه حساب‌های مشتری " + customer.getCustomerId() + " بسته شدند.";
            approved = true;
        }
        default -> {
            msg = "نوع درخواست پشتیبانی نمی‌شود.";
        }
    }

    Response response = new Response(request, approved, msg);
    customer.getBank().addResponse(response);
    customer.addMessage(response.toString());
}

@Override
public String toString() {
    return "Branch Manager [ID=" + employeeIdentity + ", Salary=" + salary + ", Branch=" + branchWork.getId() + "]";
}

}

