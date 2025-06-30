package BANK;
import java.util.Date;
import java.util.Calendar;
import java.util.ArrayList;
public class Bank {
    private static int counter;
    private static MyDate today;
    private String bankName;
    private String bankId;
    private Double totalAmountOfMoney;
    private ArrayList<Branch> branches;
    protected ArrayList<Employee> employees;
    protected ArrayList<Customer> customers;
    public Bank(){
        counter = 0;
        this.bankId = "B"+counter;
        this.bankName = "Bank";
        this.totalAmountOfMoney = 0.0;
        this.branches = new ArrayList<>();
        this.employees = new ArrayList<>();
        this.customers = new ArrayList<>();
    }
    public Bank(String bankName){
        counter = 0;
        this.bankId = "B"+counter;
        this.bankName = bankName;
        this.totalAmountOfMoney = 0.0;
        this.branches = new ArrayList<>();
        this.employees = new ArrayList<>();
        this.customers = new ArrayList<>();
    }
    //get methods
    public String getBankName() {
        return bankName;
    }
    public Customer findCustomer(String id) throws IllegalArgumentException {
        //it's work correctly
        for (Customer c : customers) {
            if (c.getCustomerId().equals(id)) {
                return c;
            }
        }
        throw new IllegalArgumentException("Customer not found");
    }
    /**this return a account with just account number*/
    public final Account seekForAccount(String accNumber){
        /*
        i think it can improve if we give 4 last digit of accNumber same as id of customer CX
        it cause a speed search
        */
        for (Customer c : customers) {
            for (Account a : c.getAccounts()) {
                if (a.getAccountNumber().equals(accNumber)) {
                    return a;
                }
            }
        }
        throw new IllegalArgumentException("Account not found");
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
    public void createBranch(String name ){
        branches.add(new Branch(this, name));
    }
    public void addBranch(Branch branch){
        this.branches.add(branch);
    }
    public void addEmployee(Employee e){
        this.employees.add(e);
    }
    public void addCustomer(Customer c){
        customers.add(c);
    }
    public void deleteCustomer(Customer c){
        customers.remove(c);
    }
    public void deleteCustomer(String id){
        customers.remove(findCustomer(id));
    }
    public void setCustomers(ArrayList<Customer> customers) {
        this.customers = customers;
    }
    public void setBranches(ArrayList<Branch> branches) {
        this.branches = branches;
    }
    public void setEmployees(ArrayList<Employee> employees) {
        this.employees = employees;
    }
    public void indicateAllEmployeesOfBranches(String branchId){
        for (Branch b : branches) {
            System.out.println(b.getManager()+b.getDeputy()+b.getTellers());
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
    //          this part is for handel the all money in bank
    public Double getTotalAmountOfMoney() {
        return totalAmountOfMoney;
    }
    public void setTotalAmountOfMoney(Double totalAmountOfMoney) throws IllegalArgumentException {
        if (totalAmountOfMoney < 0) throw new IllegalArgumentException("Total amount cannot be negative");
        this.totalAmountOfMoney = totalAmountOfMoney;
    }
    public void addToTotalAmountOfMoney(Double amount){
        this.totalAmountOfMoney += amount;
    }
    //              end of TotalAmount...
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
    public boolean confirmCustomer(String cID){
        for (Customer c : customers) {
            if (c.getCustomerId().equals(cID)) return true;
        }
        return false;
    }
    public Bank returnBank(){
        return this;
    }
    @Override
    public String toString(){
        StringBuilder info = new StringBuilder();
        for (Branch b : branches) {
            info.append(b.toString());
        }
        return info.toString();
    }
}