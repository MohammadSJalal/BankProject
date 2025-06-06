package BANK;
public abstract class Employee {
    protected String employeeIdentity;
    /*
    we set id such M as Manager employee and T as Taller employee that we set first letter as first id then
    two digit for numbering for example :
        Ma10 -> level : boss
                branch : 0 or first branch of the Bank
                10 : 10th hired boss in Bank
     */
    protected final double BaseSalary = 1000.00;
    protected Branch branchWork;
    public String [] massages;
    public abstract void setSalary();
}
