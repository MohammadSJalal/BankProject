package BANK;

import java.util.ArrayList; import java.util.HashSet; import java.util.List; import java.util.Set;

public class Bank { private String name; private List<Branch> branches; private List<Customer> customers; private List<Employee> employees; private List<BaseLoan> allLoans; private static Set<String> usedAccountNumbers = new HashSet<>(); private List<Request> requests; private List<Response> responses;

public Bank(String name) {
    this.name = name;
    this.branches = new ArrayList<>();
    this.customers = new ArrayList<>();
    this.employees = new ArrayList<>();
    this.allLoans = new ArrayList<>();
    this.requests = new ArrayList<>();
    this.responses = new ArrayList<>();
}

public Branch createBranch(String branchName) {
    Branch branch = new Branch(branchName);
    branches.add(branch);
    return branch;
}

public void registerCustomer(Customer customer) {
    customers.add(customer);
    customer.setBank(this);
}

public void addLoan(BaseLoan loan) {
    allLoans.add(loan);
    loan.getBorrower().addLoan(loan);
}

public void addRequest(Request request) {
    requests.add(request);
}

public void addResponse(Response response) {
    responses.add(response);
}

public List<Branch> getBranches() {
    return branches;
}

public List<Customer> getCustomers() {
    return customers;
}

public List<Employee> getEmployees() {
    return employees;
}

public void addEmployee(Employee employee) {
    employees.add(employee);
}

public List<Request> getRequests() {
    return requests;
}

public List<Response> getResponses() {
    return responses;
}

public void showAllBranchesAndEmployees() {
    for (Branch b : branches) {
        System.out.println(b);
        if (b.getEmployees() != null) {
            for (Employee e : b.getEmployees()) {
                if (e != null) {
                    System.out.println("  - " + e);
                }
            }
        }
    }
}

public void showAllCustomers() {
    for (Customer c : customers) {
        System.out.println(c);
    }
}

public static boolean isAccountNumberUsed(String accountNumber) {
    return usedAccountNumbers.contains(accountNumber);
}

public static void markAccountNumberUsed(String accountNumber) {
    usedAccountNumbers.add(accountNumber);
}

}

