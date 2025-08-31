import java.util.*;

public class Teller extends Employee {
    private static int counter = 1;
    private double salary;

    public Teller(Branch branchWork) {
        this.branchWork = branchWork;
        this.employeeIdentity = "T-" + branchWork.getId() + "-" + counter++;
        setSalary();
        branchWork.addEmployeeDirect(this);
    }

    @Override
    public void setSalary() { this.salary = BaseSalary; }

    public void handleRequest(String requestType, Customer customer) {
        handleRequest(requestType, customer, null);
    }

    public void handleRequest(String requestType, Customer customer, String payload) {
        Request req = new Request(requestType, customer, payload);
        Bank bank = customer.getBank();
        if (bank != null) bank.addRequest(req);
        AssistantManager assistant = getAssistantManager();
        if (assistant != null) {
            assistant.addRequest(req);
            assistant.receiveMessage("📢 درخواست جدید: " + req);
            System.out.println("✅ درخواست به معاون شعبه ارسال شد.");
        } else {
            customer.addMessage("ℹ️ درخواست ثبت شد اما معاون شعبه پیدا نشد.");
            if (bank != null) bank.addReport(new Report("Request", "No assistant to handle request #" + req.getId(), false));
        }
    }

    private AssistantManager getAssistantManager() {
        for (Employee e : branchWork.getEmployees()) if (e instanceof AssistantManager) return (AssistantManager) e;
        return null;
    }

    @Override
    public String toString() {
        return "Teller{ID='" + employeeIdentity + "', Branch=" + (branchWork!=null?branchWork.getName():"null") + "}";
    }
}
