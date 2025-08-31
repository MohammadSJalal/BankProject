import java.util.*;

public class BranchManager extends Employee {
    private static int counter = 1;
    private double salary;
    private final List<Request> inbox = new ArrayList<>();

    public BranchManager(Branch branchWork) {
        this.branchWork = branchWork;
        this.employeeIdentity = "M-" + branchWork.getId() + "-" + counter++;
        setSalary();
        branchWork.addEmployeeDirect(this);
    }

    @Override
    public void setSalary() { this.salary = BaseSalary * 2; }

    public void addRequest(Request req) { inbox.add(req); }

    public void showInbox() {
        if (inbox.isEmpty()) {
            System.out.println("📭 اینباکس مدیر خالی است.");
            return;
        }
        for (Request r : inbox) System.out.println(r);
    }

    // ----------------- لیست مشتریان -----------------
    public void showCustomers() {
        if (branchWork.getCustomers().isEmpty()) {
            System.out.println("👥 هیچ مشتری در این شعبه وجود ندارد.");
            return;
        }
        System.out.println("👥 مشتریان شعبه " + branchWork.getName() + ":");
        for (Customer c : branchWork.getCustomers()) {
            System.out.println(" - " + c);
        }
    }

    // ----------------- لیست کارمندان -----------------
    public void showEmployees() {
        if (branchWork.getEmployees().isEmpty()) {
            System.out.println("👨‍💼 هیچ کارمندی در این شعبه وجود ندارد.");
            return;
        }
        System.out.println("👨‍💼 کارمندان شعبه " + branchWork.getName() + ":");
        for (Employee e : branchWork.getEmployees()) {
            System.out.println(" - " + e);
        }
    }

    // ----------------- تایید/رد درخواست -----------------
    public void finalizeRequest(int reqId) {
        Request request = null;
        for (Request r : inbox) if (r.getId() == reqId) { request = r; break; }
        if (request == null) throw new IllegalArgumentException("❌ درخواست پیدا نشد.");

        inbox.remove(request);
        Customer customer = request.getRequester();
        String type = request.getType();
        String payload = request.getPayload();

        Bank bank = customer.getBank();
        boolean approved = false;
        String msg;

        if (type.contains("loan")) {
            if (customer.hasActiveLoan()) throw new IllegalStateException("❌ مشتری قبلاً وام فعال دارد.");
            int loanAmount = 500_000_000;
            if (bank.getTotalCustomerBalances() < loanAmount) throw new IllegalStateException("❌ موجودی کل بانک کافی نیست.");
            NormalLoan loan = new NormalLoan(customer, loanAmount, 12);
            customer.addLoan(loan);
            bank.addLoan(loan);
            msg = "✅ وام عادی تایید شد.";
            approved = true;
        } else if (type.contains("facility loan")) {
            if (customer.hasActiveLoan()) throw new IllegalStateException("❌ مشتری قبلاً وام فعال دارد.");
            int loanAmount = 1_000_000_000;
            if (bank.getTotalCustomerBalances() < loanAmount) throw new IllegalStateException("❌ موجودی کل بانک کافی نیست.");
            FacilityLoan loan = new FacilityLoan(customer, loanAmount, 24);
            customer.addLoan(loan);
            bank.addLoan(loan);
            msg = "✅ وام تسهیلاتی تایید شد.";
            approved = true;
        } else {
            throw new UnsupportedOperationException("❌ نوع درخواست پشتیبانی نمی‌شود.");
        }

        Response res = new Response(request, approved, msg);
        customer.addMessage(res.toString());
        bank.addResponse(res);
    }

    @Override
    public String toString() {
        return "BranchManager{ID='" + employeeIdentity + "', Branch=" + (branchWork!=null?branchWork.getName():"null") + "}";
    }
}
