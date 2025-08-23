import java.util.ArrayList;
import java.util.List;

public class Bank {
    private String name;
    private List<Customer> customers;
    private List<Employee> employees;
    private List<BaseLoan> loans;
    private List<Request> requests;
    private List<Response> responses;

    private static List<String> usedAccountNumbers = new ArrayList<>();

    public Bank(String name) {
        this.name = name;
        this.customers = new ArrayList<>();
        this.employees = new ArrayList<>();
        this.loans = new ArrayList<>();
        this.requests = new ArrayList<>();
        this.responses = new ArrayList<>();
    }

    public String getName() { return name; }

    public void addCustomer(Customer customer) {
        customer.setBank(this);
        customers.add(customer);
    }

    public void addEmployee(Employee employee) { employees.add(employee); }
    public void addLoan(BaseLoan loan) { loans.add(loan); }
    public void addRequest(Request request) { requests.add(request); }
    public void addResponse(Response response) { responses.add(response); }

    public List<Customer> getCustomers() { return customers; }
    public List<Employee> getEmployees() { return employees; }
    public List<BaseLoan> getLoans() { return loans; }
    public List<Request> getRequests() { return requests; }
    public List<Response> getResponses() { return responses; }
    
    public static Account findAccount(String accountNumber) {
        for (Customer c : BankSystemHolder.getBank().getCustomers()) {
            for (Account a : c.getAccounts()) {
                if (a.getAccountNumber().equals(accountNumber)) {
                    return a;
                }
            }
        }
        return null;
    }

    public static boolean isAccountNumberUsed(String accountNumber) {
        return usedAccountNumbers.contains(accountNumber);
    }

    public static void markAccountNumberUsed(String accountNumber) {
        usedAccountNumbers.add(accountNumber);
    }

    public void showCustomers() {
        System.out.println("👥 لیست مشتریان:");
        for (Customer c : customers) {
            System.out.println(c);
        }
    }

    public void showBranchesAndEmployees() {
        System.out.println("🏢 لیست کارمندان بانک:");
        for (Employee e : employees) {
            System.out.println(e);
        }
    }

    @Override
    public String toString() {
        return "Bank: " + name +
                " | Customers: " + customers.size() +
                " | Employees: " + employees.size() +
                " | Loans: " + loans.size();
    }
}
