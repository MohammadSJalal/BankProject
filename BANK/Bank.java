import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Bank {
    private String name;
    private List<Customer> customers;
    private List<Branch> branches;
    private List<Request> requests;
    private List<Response> responses;
    private List<BaseLoan> loans;
    private static Set<String> usedAccountNumbers = new HashSet<>();

    public Bank(String name) {
        this.name = name;
        this.customers = new ArrayList<>();
        this.branches = new ArrayList<>();
        this.requests = new ArrayList<>();
        this.responses = new ArrayList<>();
        this.loans = new ArrayList<>();
    }


    public void addCustomer(Customer customer) {
        customers.add(customer);
        customer.setBank(this);
    }

    public List<Customer> getCustomers() { return customers; }

    public void showCustomers() {
        if (customers.isEmpty()) {
            System.out.println("👥 هیچ مشتری در بانک ثبت نشده است.");
            return;
        }
        System.out.println("👥 لیست مشتریان بانک:");
        for (Customer c : customers) System.out.println("- " + c);
    }


    public void addBranch(Branch branch) { branches.add(branch); }

    public List<Branch> getBranches() { return branches; }

    public void showBranchesAndEmployees() {
        if (branches.isEmpty()) {
            System.out.println("🏢 هیچ شعبه‌ای ثبت نشده است.");
            return;
        }
        System.out.println("🏢 شعب و کارمندان:");
        for (Branch b : branches) {
            System.out.println(b);
            for (Employee e : b.getEmployees()) {
                System.out.println("   - " + e);
            }
        }
    }


    public void addRequest(Request req) { requests.add(req); }

    public List<Request> getRequests() { return requests; }

    public void showRequests() {
        if (requests.isEmpty()) {
            System.out.println("📄 هیچ درخواستی ثبت نشده.");
            return;
        }
        System.out.println("📄 لیست درخواست‌ها:");
        for (Request r : requests) System.out.println(r);
    }

    public Request findRequestById(int id) {
        for (Request r : requests) {
            if (r.getId() == id) return r; // نکته: getId() نه getRequestId()
        }
        return null;
    }

    public void addResponse(Response response) { responses.add(response); }

    public List<Response> getResponses() { return responses; }


    public void addLoan(BaseLoan loan) { loans.add(loan); } // نکته: BaseLoan

    public List<BaseLoan> getLoans() { return loans; }


    public static boolean isAccountNumberUsed(String accNum) {
        return usedAccountNumbers.contains(accNum);
    }

    public static void markAccountNumberUsed(String accNum) {
        usedAccountNumbers.add(accNum);
    }


    public static Account findAccount(String accNum) {
        Bank bank = BankSystemHolder.getBank();
        for (Customer c : bank.getCustomers()) {
            for (Account a : c.getAccounts()) {
                if (a.getAccountNumber().equals(accNum)) return a;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "بانک " + name + " با " + customers.size() + " مشتری و " + branches.size() + " شعبه.";
    }
}
