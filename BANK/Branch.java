package BANK;
public class Branch {
    public static char counter = 'a';
    public static StringBuilder lastId = new StringBuilder("");
    private StringBuilder id = new StringBuilder("");
    private BranchManager manager;
    private AssistantManager deputy;
    protected Employee [] employees;
    public Branch() {
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
        return "branch id=" + id + ", manager=" + manager + ", deputy=" + deputy;
    }
    public void showInformation() {
        System.out.println(this);
    }
}
