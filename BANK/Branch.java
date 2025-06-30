package BANK;
import java.util.ArrayList;
import java.util.regex.*;
public class Branch extends Bank {
    public static char counter = 'a';
    public static StringBuilder lastId = new StringBuilder("");
    private StringBuilder id = new StringBuilder("");
    protected String name;
    protected Bank bank;
    private BranchManager manager;
    protected ArrayList<AssistantManager> deputy;
    protected ArrayList<Teller> tellers;
    public Branch(Bank bank) {
        this.name = "Branch";
        this.bank = bank;
        this.bank.addBranch(this);
        this.employees = new ArrayList<>();
        this.deputy = new ArrayList<>();
        this.tellers = new ArrayList<>();
    }
    public Branch(Bank bank , String name) {
        this.name = name;
        this.id.append(addACharToLastId(lastId));
        this.bank = bank;
        this.bank.addBranch(this);
        this.employees = new ArrayList<>();
        this.deputy = new ArrayList<>();
        this.tellers = new ArrayList<>();
    }
    public void setName(String name) {
        this.name = name;
    }
    public BranchManager getManager() { return manager; }
    public void setManager(BranchManager manager) { this.manager = manager; }
    public String getName() {
        return name;
    }

    /**
     * this function is for set all employee to data of bank and branch it cause the having all information about it
     * @param employee
     * @throws IllegalArgumentException
     */
    public final void setEmployeeToList(Employee employee) throws IllegalArgumentException {
        if (employee instanceof Teller){
            tellers.add((Teller) employee);
        }
        else if (employee instanceof AssistantManager){
            deputy.add((AssistantManager) employee);
        }
        else if (employee instanceof BranchManager){
            this.manager = (BranchManager) employee;
        }
        else throw new IllegalArgumentException("Employee is not a valid employee to set to array list");
        this.employees.add(employee);
    }
    /**
     this function convert massage of customer to specify request like :
     0 -> open account
     1 -> close account
     2X -> loan request (that X can be 0 as normal and 1 as facilities
     ...
     */
    final Employee searchEmployee(char typeEmployee) throws IllegalArgumentException {
        switch (typeEmployee) {
            case 'A':
                return foundLessBusyDeputy();
            case 'T':
                return foundLessBusyTaller();
            case 'M':
                return manager;
            default:
                throw new IllegalArgumentException("something went wrong in searchEmployee in branch");
        }
    }
    @Override
    public void addEmployee(Employee employee) {
        employees.add(employee);
        switch(employee.getId().charAt(0)) {
            case 'A':
                deputy.add((AssistantManager) employee)  ;
            case 'T':
                tellers.add((Teller) employee);
            case 'M':
                if (manager == null) {
                    manager = (BranchManager) employee;
                }
        }
    }

    /**
     * this function send a form to another employee
     * @param typeEmployee
     * @param letter this is letter or form for another employee
     * @return true if work correctly
     * @throws IllegalArgumentException if not match with typeEmployee
     */
    public final boolean sendToEmployee(char typeEmployee , Letter letter) throws IllegalArgumentException {
        //there is a problem with return because inside of function we throw or return it is uncostomed
        switch(typeEmployee) {
            case 'A':
                foundLessBusyDeputy().receiveMessage(letter);
                return true;
            case 'T':
                foundLessBusyTaller().receiveMessage(letter);
                return true;
            case 'M':
                if (manager == null) {
                    throw new IllegalArgumentException("seems like you are trying to access a manager but we haven't manager");
                }
                manager.receiveMessage(letter);
                return true;
            default:
                throw new IllegalArgumentException("there are problem with type employee");
        }
    }
    public Teller foundLessBusyTaller() throws IllegalArgumentException {
        // it is work correctly
        ArrayList<Teller> lessBusyTeller = new ArrayList<>();
        for (Teller e : tellers) {
            if (lessBusyTeller.size() == 0) lessBusyTeller.add(e);
            else if (lessBusyTeller.get(0).workLoad() > e.workLoad() ){
                lessBusyTeller.removeFirst();
                lessBusyTeller.add(e);
            }
        }
        return lessBusyTeller.get(0);
    }
    public AssistantManager foundLessBusyDeputy() throws IllegalArgumentException {
        // it is work correctly
        ArrayList<AssistantManager> lessBusyDeputy = new ArrayList<>();
        for (AssistantManager e : deputy) {
            if (lessBusyDeputy.size() == 0) lessBusyDeputy.add(e);
            else if (lessBusyDeputy.get(0).workLoad() > e.workLoad() ){
                lessBusyDeputy.removeFirst();
                lessBusyDeputy.add(e);
            }
        }
        return lessBusyDeputy.get(0);
    }
    public static StringBuilder addACharToLastId(StringBuilder subId) {
        if (subId.length() == 0) {
            return subId.append('a');
        }
        char lastChar = subId.charAt(subId.length() - 1);
        subId.deleteCharAt(lastId.length() - 1);
        if (lastChar == 'z') {
            return addACharToLastId(subId).append("a");
        }
        else {
            lastChar++;
            subId.append(lastChar);
        }
        return subId;
    }
    public String getDeputy(){
        StringBuilder deputys = new StringBuilder();
        for (AssistantManager e : deputy) {
            deputys.append(e.toString());
        }
        return deputys.toString();
    }
    public String getTellers(){
        StringBuilder tl = new StringBuilder();
        for(Teller t : tellers){
            tl.append(t.toString());
        }
        return tl.toString();
    }
    public String getId() {
        return id.toString();
    }
    @Override
    public String toString() {
        if (this.name.equals("")) {
            return "\nbranch id : " + id+"\nbank : " + bank +"\nlast id : "+lastId+"\n\t\t All employee"+ manager + getDeputy() + getTellers() ;
        }
        else {
            return "\nname of branch " + name + "\n branch id :"+ id+"\nbank name: "+ bank.getBankName() + manager + getDeputy() + getTellers()  ;
        }
    }

}
