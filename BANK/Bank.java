package BANK;
import java.util.Date;
import java.util.Calendar;
import java.util.ArrayList;
public class Bank {
    private static MyDate today;
    private String bankName;
    private ArrayList<Branch> branches;
    protected ArrayList<Employee> employees;
    protected ArrayList<Customer> customers;
    public Bank(){
        this.branches = new ArrayList<>();
        this.employees = new ArrayList<>();
        this.customers = new ArrayList<>();
    }
    //get methods
    public String getBankName() {
        return bankName;
    }
    public ArrayList<Branch> getBranches() {
        return branches;
    }
    public ArrayList<Employee> getEmployees() {
        return employees;
    }
    public ArrayList<Customer> getCustomers() {
        return customers;
    }
    public void createBranch(){
        branches.add(new Branch());
    }
    public void cteateBranchWithName(String branchName){
        branches.add(new Branch(branchName));
    }
    public void addBranch(Branch branch){
        this.branches.add(branch);
    }
    public void addEmployee(Employee e){
        employees.add(e);
    }
    public void addCustomer(Customer c){
        customers.add(c);
    }
    public void indicateAllEmployeesOfBranch(String branchId){
        for (Branch b : branches) {
            if (b.getId().equals(branchId)) {
                b.getEmployees().clear();
            }
        }
    }
    public void showAllCustomers(){
        for (Customer c : customers) {
            System.out.println(c.toString());
        }
    }
    public static MyDate getToday() {
        setToday();
        return today;
    }
    public static void setToday() {
        // Get current date
        Date date = new Date();
        // Create calendar instance and set the time
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // Get day, month, year
        int day = calendar.get(Calendar.DAY_OF_MONTH);      // 6 for June 6
        int month = calendar.get(Calendar.MONTH) + 1;       // 6 for June (0-based index)
        int year = calendar.get(Calendar.YEAR);             // e.g., 2025
        today = new MyDate(year, month, day);
    }
    public Account searchAccount(String accountNumber) throws IllegalArgumentException{
        for (Customer c : customers) {
            for (Account a : c.getAccounts()) {
                if (a.getAccountNumber().equals(accountNumber)) {
                    return a;
                }
            }
        }
        throw new IllegalArgumentException("it is not a account inside of Bank");
    }
}