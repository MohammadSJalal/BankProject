import java.util.ArrayList;
import java.util.List;

public class Branch {
    private static int branchCounter = 1;
    private int branchId;
    private String name;
    private List<Employee> employees;
    private List<Customer> customers;
    private BranchManager manager;

    public Branch(String name) {
        this.name = name;
        this.branchId = branchCounter++;
        this.employees = new ArrayList<>();
        this.customers = new ArrayList<>();
    }


    public int getId() {
        return branchId;
    }

    public String getName() {
        return name;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public BranchManager getManager() {
        return manager;
    }


    public void addEmployee(Employee e) {
        employees.add(e);
        if (e instanceof BranchManager bm) {
            this.manager = bm;
        }
    }

    public void removeEmployee(Employee e) {
        employees.remove(e);
        if (e instanceof BranchManager) {
            this.manager = null;
        }
    }

    public Employee findEmployeeById(String id) {
        for (Employee e : employees) {
            if (e.getEmployeeIdentity().equals(id)) return e;
        }
        return null;
    }


    public void addCustomer(Customer c) {
        if (!customers.contains(c)) customers.add(c);
    }

    public void removeCustomer(Customer c) {
        customers.remove(c);
    }

    public void showCustomers() {
        if (customers.isEmpty()) {
            System.out.println("👥 مشتری‌ای در این شعبه ثبت نشده.");
            return;
        }
        System.out.println("👥 مشتریان شعبه " + name + ":");
        for (Customer c : customers) System.out.println("- " + c);
    }

    @Override
    public String toString() {
        return "🏢 شعبه: " + name + " (ID: " + branchId + ")";
    }
}
