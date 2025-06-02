package BANK;
public class AssistantManager extends Employee {
    public static int counter = 0;
    private double salary;
    public AssistantManager(){
        super();
        this.employeeIdentity = "A" + counter;
        counter++;
    }
    @Override
    public void setSalary(){
        salary = 1.5 *BaseSalary;
    }
    public void agreeWithRequest(){

    }
}
