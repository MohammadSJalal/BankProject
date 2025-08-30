public class BranchManager extends Employee {
    private static int counter = 1;
    private double salary;

    public BranchManager(Branch branchWork) {
        this.branchWork = branchWork;
        this.employeeIdentity = "M-" + branchWork.getId() + "-" + counter++;
        setSalary();
        branchWork.addEmployee(this); // خودش رو به لیست کارمندهای شعبه اضافه می‌کنه
    }

    @Override
    public void setSalary() {
        this.salary = BaseSalary * 2; // حقوق مدیر دو برابر پایه است
    }

    public double getSalary() {
        return salary;
    }


    public void finalizeRequest(Request request) {
        Customer customer = request.getRequester();
        String type = request.getType();
        String msg;
        boolean approved = false;

        switch (type) {
            case "loan final approval" -> {
                if (!customer.hasActiveLoan()) {
                    NormalLoan loan = new NormalLoan(500_000_000, 12, customer);
                    customer.getBank().addLoan(loan);
                    msg = "✅ وام برای مشتری " + customer.getCustomerId() + " تایید شد.";
                    approved = true;
                } else {
                    msg = "❌ مشتری قبلاً وام فعال دارد!";
                }
            }
            case "close account final approval" -> {
                if (!customer.getAccounts().isEmpty()) {
                    customer.getAccounts().clear();
                    msg = "✅ تمام حساب‌های مشتری " + customer.getCustomerId() + " بسته شدند.";
                    approved = true;
                } else {
                    msg = "❌ مشتری حسابی برای بستن ندارد.";
                }
            }
            default -> msg = "❌ نوع درخواست پشتیبانی نمی‌شود.";
        }

        Response response = new Response(request, approved, msg);
        customer.getBank().addResponse(response);
        customer.addMessage(response.toString());
        System.out.println(msg);
    }


    public void showEmployees() {
        if (branchWork.getEmployees().isEmpty()) {
            System.out.println("👥 هیچ کارمندی در این شعبه ثبت نشده.");
            return;
        }
        System.out.println("👥 لیست کارمندان شعبه:");
        for (Employee e : branchWork.getEmployees()) {
            System.out.println("- " + e);
        }
    }

    public void removeEmployee(Employee e) {
        branchWork.removeEmployee(e);
        System.out.println("❌ کارمند " + e.getEmployeeIdentity() + " از شعبه حذف شد.");
    }


    public void showCustomers() {
        branchWork.showCustomers();
    }

    @Override
    public String toString() {
        return "BranchManager{" +
                "ID='" + employeeIdentity + '\'' +
                ", Branch=" + branchWork.getName() +
                '}';
    }
}
