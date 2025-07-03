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

    }
    public Employee(Branch branchWork) {
        this.branchWork = branchWork;
        this.messages = new ArrayList<>();
    }
    public Employee(Branch branchWork,String Name , String lastName, String address, String nationalCode, MyDate birthDate , String phone) {
        super(Name , lastName, birthDate , nationalCode , address , phone);
        this.branchWork = branchWork;
        this.messages = new ArrayList<>();
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
    @Override
    public Letter searchLetter(String subject) throws IllegalArgumentException {
        for (Letter letter : messages) {
            if (letter.getSubject().equals(subject)) return letter;
        }
        throw new IllegalArgumentException("we haven't such letter");
    }
    public abstract void setSalary();
    /**
     * @return id of employee
     */
    public String getId(){
        return employeeIdentity;
    }
    public static boolean validID(String id){
        int sectionID = 0;
        StringBuilder idBank = new StringBuilder();
        StringBuilder idBranch = new StringBuilder();
        for (char c : id.toCharArray()) {
            if (c >= 'A' && c <= 'Z' && sectionID == 0) idBank.append(c);
            else if (c >= 'a' && c <= 'z'){
                idBranch.append(c);
                sectionID = 1;
            }
            else if (c >= 'A' && c <= 'Z' && sectionID == 1) sectionID = 2;
            else if (c >= '0' && c <= '9' && sectionID == 2) sectionID = 3;
            else return false;
        }
        if (Bank.getBank(idBank.toString()) == null) return false;
        if (Bank.getBank(idBank.toString()).getBranches().get(idBranch.toString()) == null) return false;
        return true;
    }
    /*
        this function return a boolean value as the result of agreement or
        disagreement in all classes that override it. function below
     */
    public final ArrayList<Letter> getMessages() {
        return messages;
    }
    public void showMessage() {
        for (Letter letter : messages) {
            System.out.println(letter);
        }
    }
    public int workLoad(){
        /*
        this function return how much work is entrust to this employee
         */
        return messages.size();
    }
}
