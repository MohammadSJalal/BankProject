package BANK;
import java.util.*;

public class Branch {
    public static char counter = 'a';
    private StringBuilder id = new StringBuilder("");
    protected String name;
    protected Bank bank;
    private BranchManager manager;
    public String cardIdentity;
    protected String eightDigitT1;
    protected String eightDigitT2;
    protected String eightDigitT3;
    int loanCounter;
    HashMap<String , BaseLoan> loans;
    protected HashMap<String,AssistantManager> deputy;
    protected HashMap<String,Teller> tellers;
    protected HashMap<String,Customer> clients;
    public Branch(Bank bank) {
        this.name = "Branch";
        this.bank = bank;
        this.id.append(bank.getBankId()+bank.addACharToLastId(bank.branchCounter,'a','z'));
        this.bank.addBranch(this);
        this.deputy = new HashMap<>();
        this.tellers = new HashMap();
        this.clients = new HashMap();
        this.loans = new HashMap<>();
        this.cardIdentity = Account.createNDigitString(Bank.findIndex(id.toString(),'a','z')-1,4);
        this.eightDigitT1 = "00000000";
        this.eightDigitT2 = "00000000";
        this.eightDigitT3 = "00000000";
        loanCounter = 0;
    }
    public Branch(Bank bank , String name) {
        this.name = name + ' ';
        this.bank = bank;
        this.id.append(bank.getBankId()+bank.addACharToLastId(bank.branchCounter,'a','z'));
        this.bank.addBranch(this);
        this.deputy = new HashMap<>();
        this.tellers = new HashMap();
        this.clients = new HashMap();
        this.loans = new HashMap<>();
        this.cardIdentity = Account.createNDigitString(Bank.findIndex(id.toString(),'a','z')-1,4);
        this.eightDigitT1 = "00000000";
        this.eightDigitT2 = "00000000";
        this.eightDigitT3 = "00000000";
        loanCounter = 0;
    }
    public static String getIdOfBank(String branchID){
        StringBuilder id = new StringBuilder(branchID);
        for (char c : branchID.toCharArray()) {
            if (c <= 'Z' && c >='A')id.append(c);
            break;
        }
        return id.toString();
    }
    public void setName(String name) {
        this.name = name;
    }
    public BranchManager getManager() { return manager; }
    public void setManager(BranchManager manager) { this.manager = manager; }
    public String getName() {
        return name;
    }
    public final Customer getSpecialCustomer(String id) {
        return clients.get(id);
    }
    /**
     * this function is for set all employee to data of bank and branch it cause the having all information about it
     * @param employee
     * @throws IllegalArgumentException
     */
    public final void setEmployeeToList(Employee employee) throws IllegalArgumentException {
        if (employee instanceof Teller){
            tellers.put(employee.getId(),(Teller) employee);
        }
        else if (employee instanceof AssistantManager){
            deputy.put(employee.getId(),(AssistantManager) employee);
        }
        else if (employee instanceof BranchManager){
            this.manager = (BranchManager) employee;
        }
        else throw new IllegalArgumentException("Employee is not a valid employee to set to array list");
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
    public final Person getSpecialPerson(String id) throws IncorrectID {
        switch (Bank.splitID(id).get(2).toString().charAt(0)) {
            case 'C':
                return clients.get(id);
            case 'A':
                return deputy.get(id);
            case 'T':
                return tellers.get(id);
            case 'M':
                return manager;
        }
        throw new IncorrectID("Incorrect ID");
    }
    public static String findID(String id){
        return Bank.splitID(id).get(0).toString()+Bank.splitID(id).get(1).toString();
    }
    public final Teller foundLessBusyTaller() throws IllegalArgumentException {
        // it is work correctly
        ArrayList<Teller> lessBusyTeller = new ArrayList<>();
        for (Teller e : tellers.values()) {
            if (e.workLoad() == 0) return e;
            if (lessBusyTeller.size() == 0) lessBusyTeller.add(e);
            else if (lessBusyTeller.get(0).workLoad() > e.workLoad() ){
                lessBusyTeller.removeFirst();
                lessBusyTeller.add(e);
            }
        }
        return lessBusyTeller.get(0);
    }
    public final AssistantManager foundLessBusyDeputy() throws IllegalArgumentException {
        // it is work correctly
        ArrayList<AssistantManager> lessBusyDeputy = new ArrayList<>();
        for (AssistantManager e : deputy.values()) {
            if (lessBusyDeputy.size() == 0) lessBusyDeputy.add(e);
            else if (lessBusyDeputy.get(0).workLoad() > e.workLoad() ){
                lessBusyDeputy.removeFirst();
                lessBusyDeputy.add(e);
            }
        }
        return lessBusyDeputy.get(0);
    }
    public String getDeputyInfo(){
        StringBuilder deputys = new StringBuilder();
        for (AssistantManager e : deputy.values()) {
            deputys.append(e.toString());
        }
        return deputys.toString();
    }
    public HashMap<String , AssistantManager> getDeputy(){
        return deputy;
    }
    public HashMap<String , Teller> getTellers(){
        return tellers;
    }
    public final String getEightDigits(char firstDigit , ArrayList<Account> accounts)throws IllegalArgumentException {
        if (firstDigit >= '1' && firstDigit <= '3') {
            String temp;
            switch (firstDigit) {
                case '1':
                    temp = eightDigitT1;
                    eightDigitT1 = Bank.addString(eightDigitT2);
                    return temp;
                case '2':
                    temp = eightDigitT2;
                    eightDigitT2 = Bank.addString(eightDigitT3);
                    return temp;
                case '3':
                    temp = eightDigitT3;
                    eightDigitT3 = Bank.addString(eightDigitT1);
                    return temp;
            }
        }
        else { //im here **
            switch (firstDigit) {
                case '4','7':
                    return Account.searchAccount(accounts,'1').getAccountNumber().substring(8);
                case '5','8':
                    return Account.searchAccount(accounts,'2').getAccountNumber().substring(8);
                case '6','9':
                    return Account.searchAccount(accounts,'3').getAccountNumber().substring(8);
            }
        }
        throw new IllegalArgumentException("something went wrong in given first digit");
    }
    public final String getTellersInfo() {
        StringBuilder tl = new StringBuilder();
        for(Teller t : tellers.values()) {
            tl.append(t.toString());
        }
        return tl.toString();
    }
    public String getId() {
        return id.toString();
    }
    /** this function check the id of branch is valid or not*/
    public static boolean validID(String id){
        StringBuilder idOfBank = new StringBuilder();
        boolean continued = true;
        for (char i : id.toCharArray()) {
            if (i >= 'A' && i <= 'Z' && continued) idOfBank.append(i);
            if (i>='a' && i<='z') continued = false;
            else return false;
        }
        if (Bank.getBank(idOfBank.toString()) == null) throw new IncorrectID("Incorrect ID");
        return true;
    }
    public static String decorator(String text) {
        String a = "--------------------------------------------------";
        return "\n" + a + "\n" + text + "\n" + a + "\n";
    }
    @Override
    public String toString() {
        if (this.name.equals("")) {
            return decorator("\nbranch id : " + id+
                    "\nbank : " + bank +"\nlast id : "+
                    bank.branchCounter+"\n\t\t All employee"+
                    manager + getDeputyInfo() + getTellersInfo()) ;
        }
        else {
            return decorator("\nname of branch "
                    + name + "\n branch id :"+ id+"\nbank name: "+
                    bank.getBankName() + manager + getDeputyInfo() +
                    getTellersInfo())  ;
        }
    }

}
