package BANK;
public class Teller extends Employee {
    public static int counter = 0;
    private double salary;
    public Teller(){
        super();
        this.employeeIdentity = "T"+counter;
    }
    @Override
    public void setSalary(){
        this.salary = BaseSalary;
    }
}
