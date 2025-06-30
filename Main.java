import BANK.*;
import java.util.Date;
import java.util.Scanner;
import java.util.*;
public class Main {
    public static Scanner scan = new Scanner(System.in);
    public static void main(String[] args) {
        Bank bank = new Bank();
        Branch branch = new Branch(bank);
        BranchManager m = new BranchManager(branch);
        AssistantManager d = new AssistantManager(branch);
        AssistantManager d1 = new AssistantManager(branch);
        Teller t = new Teller(branch);
        Teller t1 = new Teller(branch);
        Teller t2 = new Teller(branch);
        Customer c = new Customer(branch);
        c.createAccountRequest("01",200,new MyDate(2025,6,7));
        System.out.println(bank.getTotalAmountOfMoney());
        c.createAccountRequest("02",2000,new MyDate(2025,6,7));
        System.out.println(bank.getTotalAmountOfMoney());
        Customer c1 = new Customer(branch);
        c1.createAccountRequest("02",200,new MyDate(2025,6,7));
        System.out.println(bank.getTotalAmountOfMoney());
        c.transferMoney(c.getAccounts().get(0),"02202506070002",150);
        System.out.println(bank.getTotalAmountOfMoney());
        //System.out.println(c);
    }
}
final class Bash{
    public static ArrayList<Bank> banks = new ArrayList<>();
    private static char command;
    public static Scanner input = Main.scan;
    public static void Home(){
        boolean exit = false;
        while(command != 'E' && !exit){
            System.out.println("Home:");
            command = input.next().charAt(0);
            switch (command){
                case 'c':
                    exit = customerBash();
                    break;
                case 't':
                    exit = tellerBash();
                    break;
                case 'd':
                    exit = deputyBash();
                    break;
                case 'm':
                    exit = managerBash();
                    break;
                case 'B':
                    exit = bankBash();
                    break;
                case 'b':
                    exit = branchBash();
                    break;
            }
        }
    }
    public static boolean bankBash(){
        System.out.println("Home/BankBash:");
        return true;
    }
    public static boolean branchBash(){
        System.out.println("Home/BranchBash:");
        return true;
    }
    public static boolean managerBash(){
        System.out.println("Home/ManagerBash:");
        return true;
    }
    public static boolean deputyBash(){
        System.out.println("Home/DeputyBash:");
        return true;
    }
    public static boolean tellerBash(){
        System.out.println("Home/TellerBash:");
        String cmd = input.nextLine();
        if (command == 'E' ) return false;
        return true;
    }
    public static boolean customerBash(){
        System.out.println("Home/customerBash\n");
        System.out.println("welcome how can i help you ? \n");
        while(true){
            String cmd = input.nextLine();
            switch (cmd){
                case "O","o","open account":
                    if (openAccount()) return true;
                    break;
                case "D","delete account":


            }
            return true;
        }
    }
    public static boolean openAccount() {
        System.out.println("Home/customerBash/openAccount\n");
        System.out.print("enter your Id (as customer like C2): ");
        String id;
        while (true) {
            try {
                id = input.next();
                char firstChar = id.charAt(0);
                if (firstChar == 'E') return true;
                if (firstChar == '.') return false;
                int number = Integer.parseInt(id.substring(1));
                if (firstChar != 'C') throw new IncorrectID("this haven't been a customer ID");
                else if (number > Customer.getCustomerCounter() || number < 0)
                    throw new IllegalArgumentException("customer id does not exist it out of range");
                break;
            } catch (IncorrectID e) {
                System.out.println("you Id must have C in first of customer id");
            } catch (IllegalArgumentException e) {
                System.out.println("number section is not valid");
            }
        }
        System.out.print("enter your type of account \n1->currentAccount\n2->short term saving account\n3->charity loan account : ");
        int t = input.nextInt();
        while (t != 3 && t != 1 && t != 2) {
            System.out.print("you entered wrong number");
            t = input.nextInt();
        }
        String type = "0" + t;
        System.out.print("enter your initial balance : ");
        int initialBalance = input.nextInt();
        while (initialBalance < 0) {
            System.out.print("initial balance can not be negative please try again: ");
            initialBalance = input.nextInt();
        }
        //i stuck
        Customer c = null;
        for (Bank b : banks) {
            try {
                c = b.findCustomer(id);
                break;
            } catch (IllegalArgumentException e) {
                continue;
            }
        }
        c.createAccountRequest(type, initialBalance, setDate());
        return false;
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
                "W : for withdraw money\n"+
                "L : for loan money\n"+
                "A : for access this usually use for employees\n"+
                ". : for bach already stage\n"+
                "E : for exit"
        );
    }
}