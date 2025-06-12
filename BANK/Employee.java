package BANK;

import java.util.ArrayList;
import java.util.List;

// کلاس پایه برای همه‌ی کارمندای بانک
public abstract class Employee {
    protected String employeeIdentity; // شناسه‌ی منحصربه‌فرد کارمند
    protected final double BaseSalary = 1000.00; // حقوق پایه
    protected Branch branchWork; // شعبه‌ای که کارمند توش فعاله
    protected List<String> messages = new ArrayList<>(); // صندوق پیام کارمند

    // متد انتزاعی برای تنظیم حقوق که باید تو کلاسای فرزند پیاده‌سازی بشه
    public abstract void setSalary();

    public void receiveMessage(String message) {
        messages.add(message);
    }

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