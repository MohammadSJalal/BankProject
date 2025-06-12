package BANK;

import java.util.ArrayList; import java.util.List;

// کلاس پایه برای همه‌ی کارمندای بانک public abstract class Employee { protected String employeeIdentity; // شناسه‌ی منحصربه‌فرد کارمند protected final double BaseSalary = 1000.00; // حقوق پایه protected Branch branchWork; // شعبه‌ای که کارمند توش فعاله protected List<String> messages = new ArrayList<>(); // صندوق پیام کارمند

// متد انتزاعی برای تنظیم حقوق که باید تو کلاسای فرزند پیاده‌سازی بشه
public abstract void setSalary();

// این متد به ما اجازه می‌ده پیام جدید بگیریم و ذخیره کنیم
public void receiveMessage(String message) {
    messages.add(message);
}

// این متد پیام‌های دریافتی‌مو نشون می‌ده
public void showMessages() {
    System.out.println("پیام‌های دریافتی کارمند با شناسه: " + employeeIdentity);
    for (String msg : messages) {
        System.out.println("- " + msg);
    }
}

public String getEmployeeIdentity() {
    return employeeIdentity;
}

public Branch getBranchWork() {
    return branchWork;
}

public void setBranchWork(Branch branch) {
    this.branchWork = branch;
}

}

// ادامه کلاس بانک package BANK;

import java.util.ArrayList; import java.util.List;

public class Bank { private String bankName; private List<Branch> branches; // لیست همه‌ی شعب بانک private List<Employee> employees; // لیست همه‌ی کارمندا private List<Customer> customers; // لیست همه‌ی مشتریا

public Bank(String bankName) {
    this.bankName = bankName;
    this.branches = new ArrayList<>();
    this.employees = new ArrayList<>();
    this.customers = new ArrayList<>();
}

// این متد یه شعبه جدید می‌سازه و به بانک اضافه‌ش می‌کنه
public Branch createBranch(String branchName) {
    Branch newBranch = new Branch(branchName);
    branches.add(newBranch);
    System.out.println("شعبه جدید با نام '" + branchName + "' و شناسه '" + newBranch.getId() + "' ایجاد شد.");
    return newBranch;
}

// اینجا ما یه کارمند به بانک و شعبه اضافه می‌کنیم
public void addEmployee(Employee employee, Branch branch) {
    employees.add(employee);
    employee.setBranchWork(branch); // ست کردن شعبه کاری کارمند
    branch.addEmployee(employee); // اضافه کردن به لیست کارمندای همون شعبه
    System.out.println("کارمند با شناسه '" + employee.getEmployeeIdentity() + "' به شعبه '" + branch.getId() + "' اضافه شد.");
}

// ثبت یه مشتری جدید تو بانک و اتصال به بانک
public void registerCustomer(Customer customer) {
    customers.add(customer);
    customer.setBank(this); // ارتباط دوطرفه بین مشتری و بانک
    System.out.println("مشتری با شناسه '" + customer.getCustomerId() + "' ثبت شد.");
}

// نشون دادن همه کارمندای یه شعبه خاص
public void indicateAllEmployeesOfBranch(String branchId) {
    for (Branch b : branches) {
        if (b.getId().equals(branchId)) {
            System.out.println("لیست کارمندای شعبه '" + branchId + "':");
            for (Employee emp : b.getEmployees()) {
                System.out.println(emp);
            }
            return;
        }
    }
    System.out.println("شعبه‌ای با این شناسه پیدا نشد.");
}

// نمایش همه مشتری‌های بانک
public void showAllCustomers() {
    System.out.println("لیست همه مشتری‌ها:");
    for (Customer c : customers) {
        System.out.println(c);
    }
}

// جستجوی شعبه بر اساس ID
public Branch findBranchById(String branchId) {
    for (Branch b : branches) {
        if (b.getId().equals(branchId)) {
            return b;
        }
    }
    return null;
}

public List<Branch> getBranches() {
    return branches;
}

public List<Employee> getEmployees() {
    return employees;
}

public List<Customer> getCustomers() {
    return customers;
}

}

