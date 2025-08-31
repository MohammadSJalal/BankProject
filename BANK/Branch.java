import java.util.*;

public class Branch {
    private static int branchCounter = 1;
    private final int branchId;
    private final String name;
    private final List<Employee> employees = new ArrayList<>();
    private final List<Customer> customers = new ArrayList<>();
    private final List<Account> accounts = new ArrayList<>();
    private BranchManager manager;

    public Branch(String name) {
        this.name = name;
        this.branchId = branchCounter++;
    }

    public int getId(){ return branchId; }
    public String getName(){ return name; }
    public List<Employee> getEmployees(){ return Collections.unmodifiableList(employees); }
    public List<Customer> getCustomers(){ return Collections.unmodifiableList(customers); }
    public List<Account> getAccounts(){ return Collections.unmodifiableList(accounts); }
    public BranchManager getManager(){ return manager; }

    public void addEmployeeDirect(Employee e) {
        employees.add(e);
        if (e instanceof BranchManager) this.manager = (BranchManager) e;
    }

    public void removeEmployeeDirect(Employee e) {
        employees.remove(e);
        if (e instanceof BranchManager) this.manager = null;
    }

    public void addCustomer(Customer c) {
        if (!customers.contains(c)) {
            customers.add(c);
            c.setBranch(this);
        }
    }

    public void removeCustomer(Customer c) { customers.remove(c); c.setBranch(null); }

    public void addAccount(Account a) { if (!accounts.contains(a)) accounts.add(a); }

    public void removeAccount(Account a) { accounts.remove(a); }

    public Employee findEmployeeById(String id) {
        for (Employee e : employees) if (e.getEmployeeIdentity().equals(id)) return e;
        return null;
    }

    public void showCustomers(){
        if (customers.isEmpty()) { System.out.println("👥 مشتری‌ای در این شعبه ثبت نشده."); return; }
        System.out.println("👥 مشتریان شعبه " + name + ":");
        for (Customer c : customers) System.out.println("- " + c);
    }

    @Override
    public String toString() { return "🏢 شعبه: " + name + " (ID: " + branchId + ")"; }
}
