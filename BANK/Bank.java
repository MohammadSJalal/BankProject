package BANK;
import java.util.Date;
import java.util.Calendar;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Bank {
    public static HashMap< Integer , Bank> banks = new HashMap();
    public static String counter = "000";
    private String cardIdentity;
    private static MyDate today;
    private String bankName;
    private static StringBuilder lastId = new StringBuilder();
    private String bankId;
    private Double totalAmountOfMoney;
    protected String accountCounterT1;
    protected String accountCounterT2;
    protected String accountCounterT3;
    protected double [] fees ;
    private LinkedList<BaseLoan> queueLoans;
    private HashMap<String ,Branch> branches;
    public ArrayList<Account> limitedAccounts;
    public int customerCount;
    public int tellerCounter;
    public int assistantCounter;
    public int managerCounter;
    public StringBuilder branchCounter;
    private Automate automate ;

    /**
     * i suggest not use this because i usually use this for test
     */
    public Bank(){
        this.bankId = addACharToLastId(lastId,'A','Z').toString();
        this.bankName = "Bank ";
        this.cardIdentity = counter;
        this.totalAmountOfMoney = 0.0;
        this.branches = new HashMap<>();
        this.queueLoans = new LinkedList<>();
        this.limitedAccounts = new ArrayList<>();
        this.automate = new Automate(this,true);
        this.fees = new double[3];
        this.fees[0] = 0.0;
        this.fees[1] = 0.0;
        this.fees[2] = 0.0;
        this.branchCounter = new StringBuilder();
        this.accountCounterT1 = "0000";
        this.accountCounterT2 = "0000";
        this.accountCounterT3 = "0000";
        setToday();
        banks.put(Integer.valueOf(counter) + 1, this);
        counter = addString(counter);
    }
    public Bank(String bankName){
        this.bankId = addACharToLastId(lastId,'A','Z').toString();
        this.bankName = bankName + " ";
        this.cardIdentity = counter;
        this.totalAmountOfMoney = 0.0;
        this.branches = new HashMap<>();
        this.queueLoans = new LinkedList<>();
        this.limitedAccounts = new ArrayList<>();
        this.automate = new Automate(this,true);
        this.fees = new double[3];
        this.fees[0] = 0.0;
        this.fees[1] = 0.0;
        this.fees[2] = 0.0;
        this.accountCounterT1 = "0000";
        this.accountCounterT2 = "0000";
        this.accountCounterT3 = "0000";
        this.branchCounter = new StringBuilder();
        setToday();
        banks.put(Integer.valueOf(counter) + 1, this);
        counter = addString(counter);
    }
    public Bank(String bankName, boolean isAutomate){
        this.bankId = addACharToLastId(lastId,'A','Z').toString();
        this.bankName = bankName+ " ";
        this.cardIdentity = counter;
        this.totalAmountOfMoney = 0.0;
        this.branches = new HashMap<>();
        this.queueLoans = new LinkedList<>();
        this.limitedAccounts = new ArrayList<>();
        this.automate = new Automate(this,isAutomate);
        this.fees = new double[3];
        this.fees[0] = 0.0;
        this.fees[1] = 0.0;
        this.fees[2] = 0.0;
        this.accountCounterT1 = "0000";
        this.accountCounterT2 = "0000";
        this.accountCounterT3 = "0000";
        this.branchCounter = new StringBuilder();
        setToday();
        banks.put(Integer.valueOf(counter) + 1, this);
        counter = addString(counter);
    }
    public Bank(String bankName , boolean isAutomate , double [] fees){
        this.bankId = addACharToLastId(lastId,'A','Z').toString();
        this.bankName = bankName+ " ";
        this.cardIdentity = counter;
        this.totalAmountOfMoney = 0.0;
        this.branches = new HashMap<>();
        this.queueLoans = new LinkedList<>();
        this.limitedAccounts = new ArrayList<>();
        this.automate = new Automate(this,isAutomate);
        if (fees.length == 3) {
            this.fees = fees;
        }
        this.branchCounter = new StringBuilder();
        this.accountCounterT1 = "0000";
        this.accountCounterT2 = "0000";
        this.accountCounterT3 = "0000";
        setToday();
        //index of bank inside of the hashmap is begining of 1 not 0
        banks.put(Integer.valueOf(counter) + 1, this);
        counter = addString(counter);
    }
    //function for ID
    public static StringBuilder addACharToLastId(StringBuilder subId, char first , char last) {
        if (subId.length() == 0) {
            return subId.append(first);
        }
        char lastChar = subId.charAt(subId.length() - 1);
        subId.deleteCharAt(subId.length() - 1);
        if (lastChar == last) {
            return addACharToLastId(subId,first,last).append(first+"");
        }
        else {
            lastChar++;
            subId.append(lastChar);
        }
        return subId;
    }
    //          this section is for manage banks
    public static void destroyBank(Bank bank) {
        for (Bank b : banks.values()) {
            if (b == bank) {
                banks.remove(Integer.parseInt(b.getCardIdentity()));
            }
        }
    }
    public static Bank getBank(String bankID){
        return banks.get(findIndex(bankID,'A','Z'));
    }
    /**this function check the ID of bank is valid or not*/
    public static boolean validID(String id){
        for (char i : id.toCharArray()) {
            if (i < 'A' || i > 'Z') return false;
        }
        return true;
    }
    //              there are all method that find anything

    /**
     * this method find everything in my program everything !!!!
     * be careful because if you enter the name of the bank without any space at the end program throw
     * exception since the function just can separate them with this different between name of bank and
     * id of branch
     * @param id
     * @return
     */
    public static Object find(String id){
        ArrayList<StringBuilder> ID = splitID(id);
        switch (ID.size()) {
            case 1:
                if (ID.get(0).toString().charAt(ID.get(0).length()-1) == ' ') {
                    Bank b = findBankBasedName(ID.get(0).toString());
                    System.out.println(b);
                    return b;
                }
                else{
                    Bank b = findBank(ID.get(0).toString());
                    System.out.println(b);
                    return b;
                }
            case 2:
                Branch b = findBranch(id);
                return b;
            case 4:
                Person p = findPerson(id);
                System.out.println(p);
                switch (ID.get(2).toString()){
                    case "M":
                        BranchManager m = (BranchManager) p;
                        System.out.println(m);
                        return m;
                    case "A":
                        AssistantManager a = (AssistantManager) p;
                        System.out.println(a);
                        return a;
                    case "T":
                        Teller t = (Teller) p;
                        System.out.println(t);
                        return t;
                    case "C":
                        Customer c = (Customer) p;
                        System.out.println(c);
                        return c;
                    default:
                        throw new IncorrectID("it is strange that it haven't type person!!");
                }
        }
        throw new IllegalArgumentException("we can't find any object !");
    }
    /**
     * this function find the bank based on bank id notice that you can pass id of employee
     * or name of bank but we recommend not pass the id of branch because there is potential of
     * conflict with name so be careful
     * @param id
     * @return
     * @throws IllegalArgumentException
     */
    private static Bank findBank(String id) throws IllegalArgumentException , IncorrectID{
        //it is ok it work correctly
        ArrayList<StringBuilder> splitId = splitID(id);
        Pattern pattern = Pattern.compile("^\\d+$");
        Matcher matcher = pattern.matcher(splitId.get(0).toString());
        if (splitId.size() == 1 && splitId.get(0).toString().charAt(splitId.get(0).toString().length()-1) == ' ') {
            return findBankBasedName(id);
        }
        else if (splitId.size() == 1) {
            return banks.get(findIndex(id,'A','Z'));
        }
        else if (matcher.matches()) {
            // because it started with 1 we add +1 to card id
            return banks.get(Integer.parseInt(id) + 1);
        }
        else if (splitId.size() >= 2) {
            return banks.get(findIndex(splitId.get(0).toString(),'A','Z'));
        }
        else throw new IncorrectID("ID have problem!!");
    }
    private static Branch findBranch(String id) throws IncorrectID{
        ArrayList<StringBuilder> splitId = splitID(id);
        return Bank.findBank(splitId.get(0).toString()).branches.get(splitId.get(0).toString()+splitId.get(1).toString());
    }
    private static Bank findBankBasedName(String name) throws IncorrectName {
        for (Bank b : banks.values()) {
            if (b.getBankName().equals(name)) return b;
        }
        throw new IncorrectName("bank name is invalid");
    }
    public static char findTypeOfEmployee(String id) throws IncorrectID {
        for (int i = id.length() - 1; i >= 0; i--) {
            if (id.charAt(i) >= 'A' && id.charAt(i) <= 'Z') return id.charAt(i);
        }
        throw new IncorrectID("ID has problem!!");
    }
    /**
     * this function can find the branch based on it id or name we prefer the id
     * @param id this is id or name
     * @return branch that we find
     * @throws IncorrectID
     */
    private final Branch findBranchWithName(String id) throws IncorrectID {
        String branchID = splitID(id).get(0).toString();

        //this if is search based name
        if (id.charAt(0) >= 'A' && id.charAt(0) <= 'Z' && id.charAt(1) >= 'a' && id.charAt(1) <= 'z') {
            for (Branch b : branches.values()) {
                if (b.getName().equals(id)) {
                    return b;
                }
            }
        }//this else is based id
        else return branches.get(id);
        throw new IncorrectID("branch id is invalid");
    }
    public static ArrayList<StringBuilder> splitID(String id){
        boolean bankSection = true;
        ArrayList <StringBuilder> seperate = new ArrayList <>();
        StringBuilder bankID = new StringBuilder();
        StringBuilder branchID = new StringBuilder();
        StringBuilder employeeType = new StringBuilder();
        StringBuilder numberEmployee = new StringBuilder();
        if (id.charAt(id.length()-1) == ' '){
            bankID.append(id);
            seperate.add(bankID);
            return seperate;// im here
        }
        int j = 0;
        for (char i : id.toCharArray()) {
            if (i >= 'A' && i <= 'Z' && bankSection){
                bankID.append(i);
                if (j+1 < id.length()){
                    if (id.charAt(j+1) >= 'a' && id.charAt(j+1) <= 'z'){
                        seperate.add(bankID);
                        bankSection = false;
                    }
                }
                else if (j+1 == id.length()){
                    seperate.add(bankID);
                }
            }
            else if (i >= 'a' && i <= 'z'){
                branchID.append(i);
                if (j+1 < id.length()){
                    if (id.charAt(j+1) >= 'A' && id.charAt(j+1) <= 'Z') seperate.add(branchID);
                }
                else if (j+1 == id.length()) seperate.add(branchID);
            }
            else if ( i >= 'A' && i <= 'Z' && !bankSection) {
                employeeType.append(i);
                seperate.add(employeeType);
            }
            else if (i >='0' && i <= '9'){
                numberEmployee.append(i);
                if (j+1 == id.length()){
                    seperate.add(numberEmployee);
                }
            }
            j++;
        }
        return seperate;
    }
    /**
     * this function detect the id is for which of object and then return a value like this
     * 0 ==> means the id is belong to bank
     * 1 ==> means the id is belong to brach
     * 2 ==> means the id is belong to employee or customer
     * @param id
     * @return
     */
    private static int findOwnerOfID(String id) {
        Pattern employeePattern = Pattern.compile("^[A-Z]+[a-z]+[C|T|A|M]\\d+$");
        Pattern branchPattern  = Pattern.compile("^[A-Z]+[a-z]+$");
        Pattern bankPattern = Pattern.compile("^[A-Z]+$");
        Matcher matcherE = employeePattern.matcher(id);
        Matcher matcherB = branchPattern.matcher(id);
        Matcher matcherBank = bankPattern.matcher(id);
        if (matcherE.matches()) {
            return 2;
        }
        else if (matcherB.matches()) {
            return 1;
        }
        else if (matcherBank.matches()) {
            return 0;
        }
        throw new IncorrectID("it is not valid ID for whole of this program!!");
    }
    /**
     * this function get id of bank then calculate this bank was how many
     * @param id id of bank like AC
     * @return if id was AC then this function return 1*(26^1) + 3*(26^0) -> 29
     * @throws IncorrectID
     */
    public static int findIndex(String id , char firstInterval , char lastInterval) throws IncorrectID {
        int power = 0;
        ArrayList<StringBuilder> ID = splitID(id);
        int result = 0;
        switch (findOwnerOfID(id)){
            case 0:
                power = ID.get(0).length() -1;
                break;
            case 1:
                power = ID.get(1).length() -1;
                break;
            case 2:
                throw new IncorrectID("illegal employee we cannot parse it");
            default:
                throw new IncorrectID("illegal ID");
        }
        for (char c : id.toCharArray()) {
            if (c < firstInterval || c > lastInterval) continue;
            result += (int)Math.pow(lastInterval - firstInterval + 1, power--) * (c - firstInterval + 1);
        }
        return result;
    }
    /**
     * this function find the Person and return it
     * @param id of person
     * @return a employee or customer
     * @throws IncorrectID
     */
    private static Person findPerson(String id) throws IncorrectID {
        ArrayList<StringBuilder> splitID = splitID(id);
        for (StringBuilder i : splitID) {
            if (i.isEmpty()) throw new IncorrectID("ID is invalid it isn't for employee or custoer");
        }
        return Bank.findBank(splitID.get(0).toString()).branches.get(splitID.get(0).toString() + splitID.get(1).toString()).getSpecialPerson(id);
    }
    public final Customer findCustomer(String id) throws IllegalArgumentException {
        //it's work correctly
        return (Customer) findPerson(id);
    }
    public static String findIDOfBank(String id) throws IncorrectID {
        StringBuilder bankId = new StringBuilder();
        for (int i = 0; i < id.length(); i++) {
            if (id.charAt(i) >= 'A' && id.charAt(i) <= 'Z') {
                bankId.append(id.charAt(i));
            }
            else break;
        }
        if (bankId.length() == 0) throw new IncorrectID("ID is wrong in the beginning we must have capital letters");
        return bankId.toString();
    }
    public static Bank findBankWithIDOfEmployee(String id) throws IncorrectID {
        return Bank.findBank(findIDOfBank(id));
    }
    public static Branch findBranchWithIDOfEmployee(String id) throws IncorrectID {
        return Bank.findBankWithIDOfEmployee(id).findBranchWithName(Bank.splitID(id).get(0).toString() + Bank.splitID(id).get(1).toString());
    }
    /**
     * this function find id of branch among the whole id of employee or customer
     * @param id this must be id of person
     * @return id of branch
     */
    public static String findIDOfBranch(String id){
        StringBuilder result = new StringBuilder();
        for (char i : id.toCharArray()){
            if( i >= 'a' && i <= 'z') result.append(i);
        }
        if (result.length() == 0) throw new IncorrectID("something went wrong with this ID");
        return result.toString();
    }
    //get methods
    public final String getBankName() {
        return bankName;
    }
    public final String getBankId() {
        return bankId;
    }

    public final String getCardIdentity() {
        return cardIdentity;
    }
    public final LinkedList<BaseLoan> getQueueLoans() {
        return queueLoans;
    }
    public final void showQueue(){
        for (BaseLoan i : queueLoans) {
            System.out.println(i);
        }
    }

    public HashMap<String,Branch> getBranches() {
        return branches;
    }
    public void addBranch(Branch branch){
        this.branches.put(branch.getId(),branch);
    }
    public final void setStateAutomate(boolean stateAutomate) {
        this.automate.setStateOfAutomate(stateAutomate);
    }
    public void indicateAllEmployeesOfBranches(String branchId){
        for (Branch b : branches.values()) {
            System.out.println(b.getManager()+b.getDeputyInfo()+b.getTellersInfo());
        }
    }
    public void showAllCustomers(){
        for (Branch b : branches.values()) {
            for (Customer c : b.clients.values()) {
                System.out.println(c);
            }
        }
    }
    //              ***section for handel today***
    public static MyDate getToday(){
        return today;
    }
    /**
     * this version of set today set today with global date
     */
    public final void setToday() {
        // Get current date
        Date date = new Date();
        // Create calendar instance and set the time
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // Get day, month, year
        int day = calendar.get(Calendar.DAY_OF_MONTH);      // 6 for June 6
        int month = calendar.get(Calendar.MONTH) + 1;       // 6 for June (0-based index)
        int year = calendar.get(Calendar.YEAR);             // e.g., 2025
        today = new MyDate(year, month, day);
    }
    public final void setToday(int year, int month, int day) {
        today = new MyDate(year, month, day);
    }
    public final void setToday(MyDate t) {
        today = t;
    }

    //          ***this part is for handel the all money in bank***

    public final Double getTotalAmountOfMoney() {
        return totalAmountOfMoney;
    }
    public final void showTotalAmountOfMoney(){
        System.out.println(totalAmountOfMoney);
    }
    public void setTotalAmountOfMoney(Double totalAmountOfMoney) throws IllegalArgumentException {
        if (totalAmountOfMoney < 0) throw new IllegalArgumentException("Total amount cannot be negative");
        this.totalAmountOfMoney = totalAmountOfMoney;
    }
    //loan handeling
    public void pullLoan(){
        queueLoans.removeFirst();
    }
    public void pushLoan(BaseLoan loan){
        queueLoans.add(loan);
    }
    public void addToTotalAmountOfMoney(Double amount){
        this.totalAmountOfMoney += amount;
    }
    //              end of TotalAmount...
    public boolean confirmCustomer(String cID) {
        Customer c = (Customer) find(cID);
        if(c == null) return false;
        else return true;
    }

    /**
     *this function is for give the loans and all of check of course we dont implement it !!!
     */
    public final void workBank(){
        for (BaseLoan l : queueLoans){
            switch(l.getState()){
                case 1:
                    if (MyDate.calculateSubOfDate(l.getDateOfGettingLoan() , today) >= 10)
                        l.giveLoan(today);
                    break;
                case 2:
                    l.checkPayment(today);
                    break;
                case 3:
                    queueLoans.remove(l);
                    break;
            }
        }
    }

    //              ***this section is for counting the card number***

    public static String addString(String str) {
        int a = Integer.parseInt(str);
        a++;
        if (lenNumber(a) > str.length()) return makeZeros(str.length());
        return makeZeros(str.length() - lenNumber(a)) + a;
    }
    public static String makeZeros(int n) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < n; i++) {
            result.append("0");
        }
        return result.toString();
    }
    public static int lenNumber(int n) {
        int l = 0;
        while (n > 0) {
            n/=10;
            l++;
        }
        return l;
    }
    public final void showLoans(){
        for (BaseLoan loan : queueLoans) {
            System.out.println(decorator(loan.toString()));
        }
    }
    public final void showAllAccounts(){
        for (Branch b : branches.values()) {
            for (Customer c : b.clients.values()) {
                for (Account a : c.getAccounts()) {
                    System.out.println(a);
                }
            }
        }
    }
    public final String dataOfLoans(){
        String result = "";
        for (BaseLoan loan : queueLoans) {
            result += loan.toString();
            result += "\n";
        }
        return result;
    }
    public final String decorator(String s){
        return "\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~BANK~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n"+ s
                +"\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n";
    }
    @Override
    public String toString(){
        StringBuilder info = new StringBuilder();
        info.append("Bank Name: " + bankName
        + "\ntotal amount of money: " + totalAmountOfMoney
        +"\nbank ID : " + bankId
        +"\nfees: \n\tfor withdraw : " + fees[0] +"$"+
                "\n\tfor transform money between two account that are in same bank : " + fees[1] +"$"+
                "\n\ttransform money  between two different bank : " + fees[2]+"$"
        +"\ntoday: " + today
        +"\nstate of auto : "+automate.getStateOfAutomate()
        +"\n\t\tLOANS\n" + dataOfLoans());
        for (Branch b : branches.values()) {
            info.append(b.toString());
        }
        return decorator(info.toString());
    }
}