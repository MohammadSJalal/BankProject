import BANK.*;

import java.util.Scanner;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static Scanner scan = new Scanner(System.in);
    public static void main(String[] args) {
        Bank bank = new Bank("National");
        Bank bank1 = new Bank("Blue");
        Branch br = new Branch(bank);
        Branch br1 = new Branch(bank);
        Branch br2 = new Branch(bank);
        Branch br3 = new Branch(bank1);
        Branch br4 = new Branch(bank1);
        Branch br5 = new Branch(bank1);
        BranchManager m = new BranchManager(br);
        BranchManager m1 = new BranchManager(br1);
        BranchManager m2 = new BranchManager(br2);
        BranchManager m3 = new BranchManager(br3);
        BranchManager m4 = new BranchManager(br4);
        BranchManager m5 = new BranchManager(br5);
        AssistantManager a = new AssistantManager(br);
        AssistantManager a1 = new AssistantManager(br);
        AssistantManager a2 = new AssistantManager(br1);
        AssistantManager a3 = new AssistantManager(br1);
        AssistantManager a4 = new AssistantManager(br2);
        AssistantManager a5 = new AssistantManager(br2);
        AssistantManager a6 = new AssistantManager(br3);
        AssistantManager a7 = new AssistantManager(br3);
        AssistantManager a8 = new AssistantManager(br4);
        AssistantManager a9 = new AssistantManager(br4);
        AssistantManager a10 = new AssistantManager(br5);
        AssistantManager a11 = new AssistantManager(br5);
        Teller t = new Teller(br);
        Teller t1 = new Teller(br);
        Teller t2 = new Teller(br);
        Teller t3 = new Teller(br);
        Teller t4 = new Teller(br1);
        Teller t5 = new Teller(br1);
        Teller t6 = new Teller(br1);
        Teller t7 = new Teller(br1);
        Teller t8 = new Teller(br2);
        Teller t9 = new Teller(br2);
        Teller t10 = new Teller(br2);
        Teller t11 = new Teller(br2);
        Teller t12 = new Teller(br3);
        Teller t13 = new Teller(br3);
        Teller t14 = new Teller(br3);
        Teller t15 = new Teller(br3);
        Teller t16 = new Teller(br4);
        Teller t17 = new Teller(br4);
        Teller t18 = new Teller(br5);
        Teller t19 = new Teller(br5);
        Teller t20 = new Teller(br5);
        Teller t21 = new Teller(br5);
        Customer c = new Customer(br);
        Customer c1 = new Customer(br1);
        c1.createAccountRequest("01",10000,new MyDate(2020,7,10));
        c.createAccountRequest("01",1000,new MyDate(2020,7,10));
        System.out.println(c.getAccounts().get(0).getAccountNumber());
        c.requestLoan(12,new MyDate(2025,1,20),c.getAccounts().get(0),'0');
//        while (true){
//            System.out.print("please enter ID or name : ");
//            name = scan.next();
//            if(name.equals("e"))break;
//            Bank.find(name);
//        }
        //Bash.Home();
    }
}



final class Bash{
    public static ArrayList<Bank> banks = new ArrayList<>();
    public static Bank bank;
    public static President president = new President();
    public static Branch branch;
    private static char command;
    public static Scanner input = Main.scan;
    public static void Home() {
        boolean exit = false;
        while(command != 'E' && !exit){
            System.out.println("Home:\nnotice you can use 'help' , 'h' or 'H' key word to see the list of commands in every sections\n" +
                    "and if there nothing please enter as President");
            System.out.print("please enter your ID (doesn't enter the h , help or anything else .\nAaP0 is for administer\nAaH0 is for help) ");
            String id = getValidEmployeeID();
            Person p;
            if (id.equals("AaP0")) p = president;
            else {
                bank = (Bank) Bank.find(Bank.findIDOfBank(id));
                branch = (Branch) Bank.find(id);
                p = findTypeOfPerson(id);
            }
            switch (getEmployeeType(id)) {
                case 'C':
                    exit = customerBash((Customer)p);
                    break;
                case 'T':
                    exit = tellerBash((Teller)p);
                    break;
                case 'A':
                    exit = deputyBash((AssistantManager)p);
                    break;
                case 'M':
                    exit = managerBash((BranchManager) p);
                    break;
                case 'P':
                    exit = samBash();
                    break;
                case 'h':
                    System.out.println("you can just enter a char like these \n" +
                            "T : for teller and you entered to teller command line\n" +
                            "A : for deputy bank command line \n" +
                            "notice you just need to enter the first letter of what you want for example P -> president\n" +
                            "the president is a person that can perform everything in our program");
                    break;
                default:
                    System.out.println("wrong command!!\n" +
                            "you can enter the 'help' if you don't know what you must enter");
                    break;
            }
        }
    }
    public static boolean samBash(){
        while(true){
            System.out.println("Home/samBash:");
            System.out.print("please enter your command : ");
            String cmd = input.nextLine();
            switch(cmd){
                case "create":
                    create();
                    break;
                case "show":
                    show();
                case "help","h","H":
                    System.out.print("create : for create bank and atc\n" +
                            "show : for show specific information or total information\n" +
                            "\n for back already stage enter . " +
                            "\n for exit enter E");
                    break;
                case "E":
                    return true;
                case ".":
                    return false;
                default:
                    return false;
            }
        }
    }
    public static boolean show(){
        String message = "please enter your command : ";
        while (true){
            System.out.println("Home/samBash/show:");
            System.out.print(message);
            String cmd = input.nextLine();
            message = "please enter your command : ";
            String id;
            switch(cmd){
                case "all loans" , "al":
                    president.showAllLoans();
                    break;
                case "all banks","aB":
                    president.showAllBanks();
                    break;
                case "all branches","ab":
                    president.showAllBranch();
                    break;
                case "all employees","ae":
                    president.showAllManager();
                    president.showAllDeputies();
                    president.showAllTellers();
                    break;
                case "all managers","am":
                    president.showAllManager();
                    break;
                case "all deputies", "ad":
                    president.showAllDeputies();
                    break;
                case "all tellers","at":
                    president.showAllManager();
                    break;
                case "all accounts","aa":
                    president.showAllAccounts();
                    break;
                case "all total money","atm":
                    president.showTotalMoneyInEveryBank();
                    break;
                case "specific bank","sB":
                    id = getID('B');
                    if (id.equals(".")) return false;
                    president.showSpecificBank(id);

                    break;
                case "specific branch","sb":
                    id = getID('b');
                    if (id.equals("E")) return true;
                    else if (id.equals(".")) return false;
                    president.showSpecificBranch(id);
                    break;
                case "specific manager","sm":
                    id = getID('M');
                    if (id.equals("E")) return true;
                    else if (id.equals(".")) return false;
                    president.showSpecificEmployee(id);
                    break;
                case "specific deputies","sd":
                    id = getID('A');
                    if (id.equals("E")) return true;
                    else if (id.equals(".")) return false;
                    president.showSpecificEmployee(id);
                    break;
                case "specific teller","st":
                    id = getID('T');
                    if (id.equals("E")) return true;
                    else if (id.equals(".")) return false;
                    president.showSpecificEmployee(id);
                    break;
                case "specific account","sa":
                    String accNum = getAccountNumber();
                    if (accNum.equals("E"))return true;
                    else if (accNum.equals(".")) return false;
                    president.showSpecificAccount(accNum);
                    break;
                case "help","h","H":
                    System.out.println("all loans -> for show all loans\n" +
                            "specific loan -> for show specific loan (with id)\n"+
                            "all accounts -> for show all accounts\n" +
                            "specific account -> for show specific account\n"+
                            "all employees -> for show all employees\n" +
                            "all total money bank\n" +
                            "specific total money of bank\n" +
                            "this patter is also for message bank branch and so on");
                    return false;
                case ".":
                    return false;
                case "E":
                    return true;
                default:
                    message = "wrong command please try again : ";
                    break;
            }
        }
    }
    public static boolean create(){
        while(true){
            System.out.println("Home/samBash/create:");
            System.out.print("please enter what do you want to create : (for example bank)");
            String cmd = input.nextLine();
            switch(cmd){
                case "bank", "B":
                    createBank();
                    break;
                case "branch","b":
                    createBranch();
                    break;
                case "manager" , "deputies" , "teller" , "e":
                    createEmployee(cmd);
                case "customer":
                    createEmployee(cmd);
                    break;
                case "E":
                    return true;
                case ".":
                    return false;
                default:
                    System.out.println("wrong command");
                    break;
            }
        }
    }
    public static boolean createEmployee(String typeOfEmployee){
        System.out.println("Home/samBash/create/create "+ typeOfEmployee +": ");
        System.out.println("please enter your choice for select how creating the "+typeOfEmployee +"\n" +
                "1.you can select a branch then we create it\n" +
                "2.you must enter the name last name national code birthday address and so many data that you will be become aware during creat it");
        int [] legalChoices = {1,2};
        int choice = getChoice(legalChoices);
        switch (choice){
            case 1:
                return createEmployee1(typeOfEmployee);
            case 2:
                return createEmployee2(typeOfEmployee);
            default:
                return false;
        }
    }
    public static boolean createEmployee1(String typeOfEmployee){
        System.out.println("Home/samBash/create/create "+ typeOfEmployee +"/choice1 : ");
        System.out.print("now you must enter the id of the bank or name of the bank");
        Bank bank = (Bank)Bank.find(Bank.findIDOfBank(input.nextLine()));
        Branch br = getBranch(bank);
        setEmployee(br,typeOfEmployee.charAt(0) , null,null,null,null,null,null);
        return false;
    }
    public static boolean createEmployee2(String typeOfEmployee){
        System.out.println("Home/samBash/create/create "+ typeOfEmployee +"/choice2 : ");
        System.out.print("please enter the id of the bank or name of the bank");
        String id = input.nextLine();
        if (id.equals("E")) return true;
        else if (id.equals(".")) return false;
        Bank bank = (Bank)Bank.find(Bank.findIDOfBank(id));
        Branch br = getBranch(bank);
        System.out.print("please enter the name of the manager : ");
        String name = input.nextLine();
        System.out.print("please enter the last name of the manager : ");
        String lastName = input.nextLine();
        MyDate birthDay = getDateOfUser();
        System.out.print("please enter the national code of the manager : ");
        String nationalCode = input.nextLine();
        System.out.print("please enter the address of the manager : ");
        String address = input.nextLine();
        System.out.print("please enter the phone number of the manager : ");
        String phone = input.nextLine();
        setEmployee(br,typeOfEmployee.charAt(0),name,lastName,birthDay,nationalCode,address, phone);
        return false;
    }
    public static boolean createBranch(){
        System.out.println("Home/samBash/create/createBranch:");
        System.out.print("you can make two type of \n1. branches branch with name\n2.branch without name\n" +
                "notice the branch with name have advantage that you can access to it with it name\nplease enter your choice : ");
        int choice = input.nextInt();
        switch(choice){
            case 1:
                return createBranch1();
            case 2:
                return createBranch2();
            default:
                return false;
        }
    }
    public static boolean createBranch1(){
        System.out.println("Home/samBash/create/createBranch/createBranch1:");
        System.out.print("please enter name of branch : ");
        String name = getValidNameOfInstitute(1); //this must be aspect
        if (name.charAt(0) == '.') return false;
        if (name.charAt(0) == 'E') return true;
        Bank bank = getBank();
        Branch br = new Branch(bank , name);
        bank.addBranch(br);
        return false;
    }
    public static boolean createBranch2(){
        System.out.println("Home/samBash/create/createBranch2:");
        Bank bank = getBank();
        Branch br = new Branch(bank);
        bank.addBranch(br);
        System.out.print("\nbranch was created\n");
        System.out.println(br.toString());
        return false;
    }
    public static boolean createBank() throws IllegalArgumentException{
        System.out.println("Home/samBash/create/createBank:");
        System.out.print("we have three type of create bank\n1:just you give name and every thing will be default" +
                "\n2: you give name and state of automate then the automate will be on or off with you command" +
                "\n3: you give name state of automate and fees\nnow just enter you number that you want : ");
        int type = input.nextInt();
        while (type > 3 || type < 1){
            System.out.print("sadly you enter incorrect type it must be between 1 and 3 : ");
            type = input.nextInt();
        }
        switch(type){
            case 1:
                return createBank1();
            case 2:
                return createBank2();
            case 3:
                return createBank3();
        }
        throw new IllegalArgumentException("something went wrong in side of ceate bank");
    }
    public static boolean createBank1(){
        System.out.println("Home/samBash/create/createBank/createBankType1:");
        String name = getValidNameOfInstitute(1);
        if (name.charAt(0) == '.') return false;
        if (name.charAt(0) == 'E') return true;
        Bank bank = new Bank(name,true);
        System.out.print("bank was created");
        System.out.print(bank);
        return false;
    }
    public static boolean createBank2(){
        System.out.println("Home/samBash/create/createBank/createBankType2:");
        String name = getValidNameOfInstitute(2);
        if (name.charAt(0) == '.') return false;
        if (name.charAt(0) == 'E') return true;
        System.out.println("please enter the state of the automate : (true or false)");
        boolean state = getStateOfAutomate();
        Bank bank = new Bank(name, state);
        System.out.print("bank was created");
        System.out.print(bank);
        return false;
    }
    public static boolean createBank3(){
        System.out.println("Home/samBash/create/createBank/createBankType3:");
        String name = getValidNameOfInstitute(3);
        if (name.charAt(0) == '.') return false;
        if (name.charAt(0) == 'E') return true;
        boolean state = getStateOfAutomate();
        double [] fees = new double[3];
        for (int i = 0; i < 3; i++){
            System.out.print("please enter the fee you want to apply the transfer  : ");
            fees[i] = input.nextDouble();
        }
        Bank bank = new Bank(name, state, fees);
        System.out.print("bank was created");
        System.out.print(bank);
        return false;
    }
    public static Bank getBank(){
        System.out.print("please enter name of Bank or ID of bank : ");
        String idOfBank = input.nextLine();
        Bank bank ;
        while (!detectTheNameOfBank(idOfBank)){
            System.out.print("please enter name of Bank correctly : ");
            idOfBank = input.nextLine();
        }
        while(true){
            try{
                bank = (Bank)Bank.find(idOfBank);
                break;
            }
            catch(IncorrectName e){
                System.out.print("please enter name of Bank correctly already name doesn't exist : ");
                idOfBank = input.nextLine();
            }
        }
        return (Bank) Bank.find(idOfBank);
    }
    public static Branch getBranch(Bank bank){
        System.out.print("now you must enter the id of the branch or name of the branch");
        String id = input.nextLine();
        Branch br;
        while(true){
            try{
                br = (Branch) bank.find(id);
                break;
            }
            catch(IncorrectID e){
                System.out.println("Wrong branch ID try again");
                id = input.nextLine();
            }
        }
        return br;
    }
    public static void setEmployee(Branch br, char typeOfEmployee , String name , String lastName , MyDate birthDay ,String nationalCode, String address , String phone) throws IllegalArgumentException{
        switch(typeOfEmployee){
            case 'm':
                BranchManager m;
                if (br.getManager() == null) {
                    if (name == null) {
                        m = new BranchManager(br);
                        System.out.print("please write this id of your employee for access to it : " + m.getId());
                    }
                    else {
                        m = new BranchManager(name,lastName,birthDay,nationalCode,address,phone,br);
                        System.out.print("please write this id of your employee for access to it : " + m.getId());
                    }
                }
                else{
                    System.out.print("the branch already has an manager you want change it ? [y/n] ");
                    String choice = input.nextLine();
                    while (!(choice.equalsIgnoreCase("y") ||
                            choice.equalsIgnoreCase("yes") ||
                            choice.equalsIgnoreCase("n") ||
                            choice.equalsIgnoreCase("no"))){
                        System.out.print("please enter y or n or yes or no as command for continue the process : ");
                        choice = input.nextLine();
                    }
                    if (choice.equalsIgnoreCase("y") || choice.equalsIgnoreCase("yes")){
                        if (name == null) {
                            m = new BranchManager(br);
                            System.out.print("please write this id of your employee for access to it : " + m.getId());
                        }
                        else {
                            m = new BranchManager(name,lastName,birthDay,nationalCode,address,phone,br);
                            System.out.print("please write this id of your employee for access to it : " + m.getId());
                        }
                    }
                    else {
                        System.out.println("the already manager has been still manager");
                    }
                }
                break;
            case 'a':
                if (name == null) {
                    AssistantManager a = new AssistantManager(br);
                    System.out.print("please write this id of your employee for access to it : "+a.getId());
                }
                else {
                    AssistantManager a = new AssistantManager(name,lastName,birthDay,nationalCode,address,phone,br);
                    System.out.print("please write this id of your employee for access to it easily : "+a.getId());
                }
                break;
            case 't':
                if (name == null) {
                    Teller t = new Teller(br);
                    System.out.print("please write this id of your employee for access to it : "+t.getId());
                }
                else {
                    Teller t = new Teller(name,lastName,birthDay,nationalCode,address,phone,br);
                    System.out.print("please write this id of your employee for access to it easily : "+t.getId());

                }
                break;
            default:
                throw new IllegalArgumentException("something went wrong in setEmployee");
        }
    }
    public static boolean getStateOfAutomate(){
        while(true){
            try{
                System.out.print("please enter the state of the automate : ");
                boolean state = input.nextBoolean();
                return state;
            }
            catch(MissingFormatArgumentException e){
                System.out.println("please enter the state of the automate correctly : ");
            }
        }
    }
    public static String getValidNameOfInstitute(int func){
        System.out.print("\nplease enter name of the bank : ");
        String name = input.next();
        while (!checkValidityOfNameInstitute(name)){
            System.out.print("pay attention you name can not be all capital letter or length of name can not be smaller that 4" +
                    " also you can not add number in side of it and it must have atleast one capital letter in first and all other must" +
                    " be noncapital letter"+"\nthis name < "+name+" > is illegal" + "\nsadly you enter incorrect name please try again : ");
            name = input.next();
            if (name.length() == 1 && name.charAt(0)=='.' || name.charAt(0)=='E') return name;
        }
        return name;
    }
    public static boolean detectTheNameOfBank(String name){
        if (name.length() < 3) return false;
        for (int i = 0; i < name.length(); i++){
            if (name.charAt(i) >= 'A' && name.charAt(i) <= 'Z' && i > 1) return false;
            else if (name.charAt(i) >= 'a' && name.charAt(i) <= 'z') return true;
        }
        return false;
    }
    public static boolean checkValidityOfNameInstitute(String bankName){
        if (bankName.length() < 4) return false;
        Pattern p = Pattern.compile("^[A-Z][a-z]+$");
        Matcher m = p.matcher(bankName);
        if (m.find()) return true;
        else return false;
    }
    public static boolean bankBash(){
        System.out.println("Home/BankBash:");
        return false;
    }
    public static boolean branchBash(Branch branch){
        System.out.println("Home/BranchBash:");
        while (true){
            System.out.print("please enter your command : ");
            String cmd = input.nextLine();
            switch(cmd){
                case "show Employees":
                    return showEmployees(branch);
                default:
                    System.out.println("wrong command");
                    break;
            }
        }
    }
    public static boolean showEmployees(Branch b){
        System.out.println("Home/BranchBash/ShowEmployees: ");
        while(true){
            System.out.print("please enter your specific employee that you want to see : for example T -> teller . W as whole");
            char type = input.next().charAt(0);
            switch(type){
                case 'T','t':
                    System.out.print(b.getTellersInfo());
                    return false;
                case 'A','a':
                    System.out.print(b.getDeputyInfo());
                    return false;
                case 'M','m':
                    System.out.println(b.getManager());
                    return false;
                case 'W','w':
                    System.out.println(b.getManager());
                    System.out.println(b.getDeputyInfo());
                    System.out.println(b.getTellersInfo());
                    return false;
                case '.':
                    return false;
                case 'E':
                    return true;
                default:
                    System.out.print("please correct form");
            }
        }
    }
    public static boolean managerBash(BranchManager manager){
        System.out.println("Home/ManagerBash:");
        System.out.println("please enter your command : ");
        String cmd = input.next();
        switch(cmd){
            case "show messages" , "Show" , "S","s":
                manager.showMessage();
                return false;
            case "work","W","w":
                manager.checkMessage();
            case "show info" , "SI":
                System.out.println(manager);
                return false;
            case "help":
                System.out.println("show messages , Show , S , s -> for show messages" +
                        "\nwork , W , w -> for working for example you can be as teller and work" +
                        "\n");
                return false;
            case ".":
                return false;
            case "E":
                return true;
        }
        return false;
    }
    public static boolean deputyBash(AssistantManager deputy){
        System.out.println("Home/DeputyBash:");
        System.out.println("please enter your command : ");
        String cmd = input.nextLine();
        switch(cmd){
            case "show messages" , "Show" , "S","s":
                deputy.showMessage();
                return false;
            case "work","W","w":
                deputy.checkMessage();
            case "show info" , "SI":
                System.out.println(deputy);
                return false;
            case ".":
                return false;
            case "E":
                return true;
        }
        return false;
    }
    //          this section is for teller bash
    public static boolean tellerBash(Teller teller){
        System.out.println("Home/TellerBash:");
        System.out.println("please enter your command : ");
        String cmd = input.nextLine();
        switch(cmd){
            case "show messages" , "Show" , "S","s":
                teller.showMessage();
                return false;
            case "work","W","w":
                teller.checkMessage();
            case "show info" , "SI":
                System.out.println(teller);
                return false;
            case ".":
                return false;
            case "E":
                return true;
        }
        return false;
    }
    //          there are for customer bash , all function and every thing that you see
    public static boolean customerBash(Customer customer){
        System.out.println("Home/customerBash\n");
        System.out.println("welcome how can i help you ? \n");
        while(true){
            String cmd = input.nextLine();
            switch (cmd){
                case "O","o","open account":
                    return openAccount(customer);
                case "D","delete account":
                    return deleteAccount(customer);
                case "deposit" , "S" ,"save money":
                    return saveMoney(customer);
                case "W","withdraw" , "w":
                    return getCash(customer);
                case "I","show message":
                    return indicateAllMessage(customer);
                case "L","loan request":
                    return LoanRequest(customer);
                case "help":
                    help();
                    break;
                case ".":
                    return false;
                default:
                    System.out.println("Invalid command");
                    break;
            }
        }
    }
    public static boolean openAccount(Customer customer) {
        System.out.println("Home/customerBash/openAccount\n");
        System.out.print("enter your type of account \n1->currentAccount\n2->short term saving account\n3->charity loan account : ");
        String type = "0" + typeOfAccount();
        System.out.print("enter your initial balance : ");
        int initialBalance = input.nextInt();
        while (initialBalance < 0) {
            System.out.print("initial balance can not be negative please try again: ");
            initialBalance = input.nextInt();
        }
        customer.createAccountRequest(type, initialBalance, setDate());
        return false;
    }
    public static boolean LoanRequest(Customer customer) {
        System.out.println("Home/customerBash/LoanRequest\n");
        String id = getID('C');
        if (id.equals("E")) return true;
        if (id.equals(".")) return false;
        Customer c = (Customer) Bank.find(id);
        double amount = getAmount();
        int durationInMonth = (int)getAmount();
        MyDate d = setDate();
        int type = typeOfAccount();
        Account acc = c.getAccounts().get(type);
        int [] legalTypeOfLoan = {1,2};
        System.out.println("guide \n1 -> normal loan\n2 -> facilities loan :");
        int typeOfLoan = getChoice(legalTypeOfLoan);
        c.requestLoan(amount , durationInMonth, d , acc ,(typeOfLoan+"").charAt(0));
        return false;
    }
    public static boolean indicateAllMessage(Customer customer) {
        System.out.println("Home/customerBash/indicateAllMessage\n");
        String id = getID('C');
        if (id.equals("E")) return true;
        if (id.equals(".")) return false;
        Customer c = (Customer) Bank.find(id);
        c.showAllMessages();
        return false;
    }
    public static boolean getCash(Customer customer) {
        System.out.println("Home/customerBash/getCash\n");
        String id = getID('C');
        if (id.equals("E")) return true;
        if (id.equals(".")) return false;
        Customer c = (Customer) Bank.find(id);
        int type = typeOfAccount();
        int amount = input.nextInt();
        boolean receipt = getReceipt();
        c.withdraw(type,amount,receipt,1);
        return false;
    }
    public static boolean saveMoney(Customer customer) {
        System.out.println("Home/customerBash/saveMoney\n");
        int [] legalValue = {1,2};
        String id = getID('C');
        if (id.equals("E")) return true;
        if (id.equals(".")) return false;
        Customer c = (Customer) Bank.find(id);
        System.out.println("Home/customerBash/saveMoney\nyou have several way to" +
                "deposit money \n1.first with give account number\n2.second with give type of account:");
        int choice = getChoice(legalValue);
        int type = typeOfAccount();
        double amount = getAmount();
        boolean receipt = getReceipt();
        c.deposit(type, amount, receipt,1);
        return false;
    }
    public static int typeOfAccount() {
        System.out.println("Home/customerBash/creatAccount/typeOfAccount\n");
        System.out.println("please enter the type of account");
        int t = input.nextInt();
        while (t != 3 && t != 1 && t != 2) {
            System.out.print("you entered wrong number");
            t = input.nextInt();
        }
        return t;
    }
    public static boolean getReceipt(){
        boolean receipt = false;
        while (true){
            try{
                System.out.print("you want receipt : ");
                receipt = input.nextBoolean();
                break;
            }
            catch (InputMismatchException e){
                System.out.println("invalid input please try again");
            }
        }
        return receipt;
    }
    public static String getValidEmployeeID() {
        Scanner scanner = new Scanner(System.in);
        // Updated regex: Bank (uppercase letters, any length) + Branch (lowercase letters)
        // + Type (1 uppercase letter) + Number (digits)
        String patternEmployee = "^[A-Z]+[a-z]+[A-Z]\\d+$";
        String patternBranch = "^[A-Z]+[a-z]+$";
        String patternBank = "^[A-Z]+$";
        String id;
        while (true) {
            System.out.print("Enter ID: ");
            id = scanner.nextLine();
            if (id.equals("help")){
                System.out.print("\n\tyou must obey of order of creating for first time you must enter as president with " +
                        "\nAaP0 id then create Bank , branch , Manager , Assistant , teller , customer respectively." +
                        "\nnotice without doing this you cannot get loan transfer money or anything else we give you a id " +
                        "\nfor your new object please save it you can access to your object with it is essential part\n\n ");
                continue;
            }
            if (id.matches(patternEmployee)) {
                System.out.println("Valid ID employee found.");
                return id;
            }
            else if (id.matches(patternBranch)) {
                System.out.println("Valid Branch.");
                return id;
            }
            else if (id.matches(patternBank)) {
                System.out.println("Valid Bank.");
                return id;
            }
            else {
                System.out.println("Invalid ID! Try again.");
            }
        }
    }
    public static double getAmount() {
        double amount = 0;
        while (true){
            try {
                System.out.print("enter your amount for deposit money : ");
                amount = input.nextDouble();
                if (amount < 0) throw new NegativeValue("negative amount is not allowed");
                break;
            }
            catch (InputMismatchException e){
                System.out.println("invalid input");
            }
            catch (NegativeValue e){
                System.out.println("invalid negative value");
            }
        }
        return amount;
    }
    public static Person findTypeOfPerson(String id){
        Branch br =(Branch) Bank.find(id);
        return br.getSpecialPerson(id);
    }
    public static int getChoice(int [] legalValues) {
        int choice = 0;
        boolean mistake = false;
        do{
            if (mistake) System.out.println("enter your choice correctly already input is wrong");
            System.out.print("enter your choice: ");
            choice = input.nextInt();
            mistake = true;
        } while(haveSuchElement(choice , legalValues));
        return choice;
    }
    public static Customer seekForCustomer(String id) {
        Customer c = (Customer) Bank.find(id);
        return c;
    }
    public static boolean deleteAccount(Customer c) {
        System.out.println("Home/customerBash/deleteAccount/removeAccount\n");
        System.out.println("please enter the account number");
        String accNum = input.next();
        if (accNum.equals("E")) return true;
        else if (accNum.equals(".")) return false;
        while (!findIndex(c,accNum)){
            System.out.println("please enter correct account number");
            accNum = input.next();
        }
        return false;
    }
    public static boolean findIndex(Customer c, String accNum) {
        for (Account a : c.getAccounts()) {
            if (a.getAccountNumber().equals(accNum)) {
                c.getAccounts().remove(a);
                return true;
            }
        }
        return false;
    }
    public static String getID(char type) throws IllegalArgumentException {
        String id ;
        while (true) {
            try {
                id = input.next();
                if (id.equals("E")) return "E";
                if (id.equals(".")) return  ".";
                switch (type) {
                    case 'B':
                        if (Bank.validID(id)) return id;
                        else throw new IncorrectID("id of bank doesn't correct");
                    case 'b':
                        if (Branch.validID(id)) return id;
                        else throw new IncorrectID("id of branch doesn't correct");
                    case 'C','T','A','M':
                        if (Employee.validID(id)) return id;
                        else throw new IncorrectID("id of employee or customer doesn't correct");
                    default:
                        throw new IllegalArgumentException ("invalid type of account");
                }
            } catch (IncorrectID e) {
                System.out.println(e.getMessage());
            }
        }
    }
    public static MyDate setDate() {
        System.out.print("enter the password : ");
        String password = input.next();
        if (password.equals("SAM")) {
            System.out.print("enter year : ");
            int year = input.nextInt();
            System.out.print("enter month : ");
            int month = input.nextInt();
            System.out.print("enter day : ");
            int day = input.nextInt();
            return new MyDate(year, month, day);
        }
        return MyDate.today();
    }
    public static boolean haveSuchElement(int value , int [] values) {
        for (int i : values) {
            if (i == value) return true;
        }
        return false;
    }
    public static MyDate getDateOfUser() {
        System.out.print("enter your birthday in this format (XXXX/xx/xx) : ");
        String date = input.next();
        String [] list = date.split("/");
        return new MyDate(Integer.parseInt(list[0]),Integer.parseInt(list[1]),Integer.parseInt(list[2]));
    }
    public static char getEmployeeType(String employeeID) {
        for (int i = employeeID.length() - 1; i >= 0; i--) {
            if (Character.isUpperCase(employeeID.charAt(i))) {
                return employeeID.charAt(i);
            }
        }
        throw new IllegalArgumentException("Invalid employee ID format");
    }
    public static String getAccountNumber() {
        System.out.print("enter your account number:  ");
        while (true) {
            String account = input.next();
            if (account.length() == 16 && allDigit(account)) return account;
            else System.out.print("\ninvalid account number\ntry again : ");
        }
    }
    public static boolean allDigit(String accNum){
        for (int i = 0; i < accNum.length(); i++) {
            if (!Character.isDigit(accNum.charAt(i))) return false;
        }
        return true;
    }
    public static void help(){
        System.out.println("help : for explanation\n" +
                "t : for enter as teller\n" +
                "c : for enter as customer\n" +
                "d (stand for deputy) : for enter as assistant manager\n" +
                "m : for enter as manager\n"+
                "O : for opening account\n"+
                "D : for delete Account\n"+
                "T : for transfer money\n"+
                "S (stand for saving) : for deposit money\n"+
                "I (stand for indicate) : shows all message\n"+
                "W : for withdraw money\n"+
                "L : for loan money\n"+
                "A : for access this usually use for employees\n"+
                ". : for bach already stage\n"+
                "E : for exit"
        );
    }
}
