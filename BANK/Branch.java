package BANK;
import java.util.ArrayList;
import java.util.regex.*;
public class Branch {
    public static char counter = 'a';
    public static StringBuilder lastId = new StringBuilder("");
    private StringBuilder id = new StringBuilder("");
    protected String name;
    private BranchManager manager;
    private AssistantManager deputy;
    protected ArrayList<Employee> employees;
    public Branch() {
        this.id.append(addACharToLastId(lastId));
        this.employees = new ArrayList<>();
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void getRequest(String request) {
        /*
        we save request with format message.date.T mean message for taller
        */
        Pattern p = Pattern.compile("^(\\w+),(\\d+),[T|M|A]");
        Matcher m = p.matcher(request);
        boolean found = m.find();
        if (found) {
            char typeEmployee = request.charAt(request.length()-1);
            switch (typeEmployee) {
                case 'A':
                case 'T':

                case 'M':
                default:
            }
        }
        else throw new IllegalArgumentException("the request is invalid we must have such format\nmessage,date,A");
    }
    /*
    public Employee foundLessBusyEmployee(String typeOfEmployee) {
        for (Employee e : employees) {
            if (e.getId().equals(typeOfEmployee)) {

            }
        }
    }
    */
    public Branch(String name){
        this.name = name;
        this.id.append(addACharToLastId(lastId));
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
    public String getId() {
        return id.toString();
    }
    @Override
    public String toString() {
        if (this.name.equals("")) {
            return "branch id=" + id + ", manager=" + manager + ", deputy=" + deputy;
        }
        else {
            return "name of branch " + name + " , branch id :"+ id +", manager=" + manager + ", deputy=" + deputy;
        }
    }
    public void showInformation() {
        System.out.println(this);
    }
    public ArrayList<Employee> getEmployees() {
        return employees;
    }
}
