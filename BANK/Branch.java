package BANK;
public class Branch {
    public static char counter = 'a';
    private char id;
    private BranchManager manager;
    private Employee deputy;
    protected Employee [] employees;
    public Branch() {
        this.id = counter++;
    }
    public char getId() {
        return id;
    }
}
