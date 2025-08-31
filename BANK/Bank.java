import java.util.ArrayList;
import java.util.List;

public class Bank {
    private String name;
    private final List<Account> accounts = new ArrayList<>();
    private final List<Customer> customers = new ArrayList<>();
    private final List<Employee> employees = new ArrayList<>();
    private final List<Branch> branches = new ArrayList<>();
    private final List<Report> reports = new ArrayList<>();
    private final List<BaseLoan> loans = new ArrayList<>();
    private final List<Response> responses = new ArrayList<>();
    private final List<Request> requests = new ArrayList<>();   

    public Bank(String name) { this.name = name; }

    // ----------------- حساب -----------------
    public void addAccount(Account acc) { accounts.add(acc); }
    public Account findAccount(String number) {
        for (Account a : accounts) if (a.getAccountNumber().equals(number)) return a;
        return null;
    }
    public List<Account> getAccounts() { return accounts; }

    // ----------------- مشتری -----------------
    public void addCustomer(Customer c) { customers.add(c); }
    public List<Customer> getCustomers() { return customers; }

    // ----------------- کارمند -----------------
    public void addEmployee(Employee e) { employees.add(e); }
    public List<Employee> getEmployees() { return employees; }

    // ----------------- شعبه -----------------
    public void addBranch(Branch b) { branches.add(b); }
    public List<Branch> getBranches() { return branches; }

    // ----------------- گزارش -----------------
    public void addReport(Report r) { reports.add(r); }
    public List<Report> getReports() { return reports; }

    // ----------------- وام -----------------
    public void addLoan(BaseLoan loan) { loans.add(loan); }
    public List<BaseLoan> getLoans() { return loans; }

    // ----------------- پاسخ -----------------
    public void addResponse(Response res) { responses.add(res); }
    public List<Response> getResponses() { return responses; }

    // ----------------- درخواست -----------------
    public void addRequest(Request req) { requests.add(req); }   // ✅ اضافه شد
    public List<Request> getRequests() { return requests; }      // ✅ اضافه شد

    // ----------------- ابزارها -----------------
    public long getTotalCustomerBalances() {
        long sum = 0;
        for (Account a : accounts) sum += a.getBalance();
        return sum;
    }

    public void applyMonthlyInterestToAll() {
        for (Account a : accounts) a.applyMonthlyInterest();
    }

    // ----------------- متفرقه -----------------
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @Override
    public String toString() {
        return "🏦 بانک: " + name + " | مشتریان: " + customers.size() +
                " | کارمندان: " + employees.size() +
                " | حساب‌ها: " + accounts.size() +
                " | گزارش‌ها: " + reports.size() +
                " | درخواست‌ها: " + requests.size();
    }
}

