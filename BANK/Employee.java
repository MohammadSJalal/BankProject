package BANK;
import java.util.ArrayList;
public abstract class Employee extends Person implements Message {
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
    public ArrayList<Letter> messages;
    public Employee() {
        super();
    }
    public Employee(String Name , String lastName, String address, String nationalCode, MyDate birthDate , String phone) {
        super(Name , lastName, birthDate , nationalCode , address , phone);
    }
    @Override
    public void sendMessage(Letter form) {
        if (form.getReceiverType() == '-');
        else if (form.getReceiverType() == 'C') branchWork.bank.findCustomer(form.getSenderId()).receiveMessage(form);
        else branchWork.searchEmployee(form.getReceiverType()).receiveMessage(form);
    }
    /** this is for first stage of proceed
     * all request must be in this format
     * 0X -> open account (that X can be 1 as current Account and so on)
     * 1 -> close account
     * 2X -> loan request (that X can be 0 as normal and 1 as facilities)
     * @throws IllegalArgumentException
     */
    @Override
    public void receiveMessage(Letter form) {
        messages.add(form);
        checkMessage();
    }
    public abstract void setSalary();
    public String getEmployeeIdentity() {
        return employeeIdentity;
    }
    /**
     * @return id of employee
     */
    public String getId(){
        return employeeIdentity;
    }
    /*
        this function return a boolean value as the result of agreement or
        disagreement in all classes that override it. function below
     */
    public final ArrayList<Letter> getMessages() {
        return messages;
    }
    public int workLoad(){
        /*
        this function return how much work is entrust to this employee
         */
        return messages.size();
    }
}
