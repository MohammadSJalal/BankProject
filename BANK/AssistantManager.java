import java.util.*;

public class AssistantManager extends Employee {
    private static int counter = 1;
    private double salary;
    private final List<Request> inbox = new ArrayList<>();

    public AssistantManager(Branch branchWork) {
        this.branchWork = branchWork;
        this.employeeIdentity = "A-" + branchWork.getId() + "-" + counter++;
        setSalary();
        branchWork.addEmployeeDirect(this);
    }

    @Override
    public void setSalary() { this.salary = BaseSalary * 1.5; }

    public void addRequest(Request req) { inbox.add(req); }

    public void showInbox() {
        if (inbox.isEmpty()) { System.out.println("📭 اینباکس خالی است."); return; }
        System.out.println("📩 درخواست‌های دریافتی معاون:");
        for (Request r : inbox) System.out.println(r);
    }

    public void approveRequest(int reqId) {
        Request toApprove = null;
        for (Request r : inbox) { if (r.getId() == reqId) { toApprove = r; break; } }
        if (toApprove == null) { System.out.println("❌ درخواست پیدا نشد."); return; }

        
        Customer requester = toApprove.getRequester();
        String type = toApprove.getType();
        if (type.contains("loan")) {
            if (requester.hasActiveLoan()) {
                requester.addMessage("❌ درخواست وام رد شد: وام فعال دارید.");
                new Response(toApprove, false, "مشتری وام فعال دارد.");
                inbox.remove(toApprove);
                return;
            }
        } else if (type.contains("close account")) {
            if (requester.hasActiveLoan()) {
                requester.addMessage("❌ درخواست بستن حساب رد شد: وام فعال دارید.");
                new Response(toApprove, false, "مشتری وام فعال دارد، بستن حساب ممکن نیست.");
                inbox.remove(toApprove);
                return;
            }
        }

        
        BranchManager bm = branchWork.getManager();
        if (bm != null) {
            bm.addRequest(toApprove);
            bm.receiveMessage("📢 درخواست ارجاع‌شده از معاون: " + toApprove);
            inbox.remove(toApprove);
            System.out.println("✅ درخواست به مدیر شعبه ارجاع شد.");
        } else {
            requester.addMessage("❌ مدیر شعبه پیدا نشد؛ درخواست رد شد.");
            new Response(toApprove, false, "مدیر شعبه موجود نیست.");
            inbox.remove(toApprove);
        }
    }

    @Override
    public String toString() {
        return "AssistantManager{ID='" + employeeIdentity + "', Branch=" + (branchWork!=null?branchWork.getName():"null") + "}";
    }
}
