import java.util.ArrayList;
import java.util.List;

public class Branch {
    private static char counter = 'a';
    private static StringBuilder lastId = new StringBuilder("");
    private StringBuilder id = new StringBuilder("");
    protected String name;
    private BranchManager manager;
    private AssistantManager deputy;
    private List<Employee> employees = new ArrayList<>();

    public Branch() {
        this.id.append(addACharToLastId(lastId));
    }

    public Branch(String name) {
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
        } else {
            lastChar++;
            subId.append(lastChar);
        }
        return subId;
    }

    public void addEmployee(Employee e) {
        employees.add(e);
        if (e instanceof BranchManager) manager = (BranchManager) e;
        if (e instanceof AssistantManager) deputy = (AssistantManager) e;
    }

    public String getId() {
        return id.toString();
    }

    @Override
    public String toString() {
        if (this.name == null || this.name.equals("")) {
            return "branch id=" + id + ", manager=" + manager + ", deputy=" + deputy;
        } else {
            return "name of branch " + name + " , branch id :" + id + ", manager=" + manager + ", deputy=" + deputy;
        }
    }

    public void showInformation() {
        System.out.println(this);
    }

    public List<Employee> getEmployees() {
        return employees;
    }
}
