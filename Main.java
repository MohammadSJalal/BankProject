import BANK.*;
import java.util.Date;
public class Main {
    public static void main(String[] args) {
        Bank studnts = new Bank();
        Customer saeed = new Customer("mohammad","jalal");
        Customer sorena = new Customer("sorena","mohammadnia");
        Account myAccount = new CurrentAccount(saeed,studnts);
        Account As = new CurrentAccount(sorena,studnts);
        saeed.deposit(100000.0,myAccount);
        System.out.println(As);
        myAccount.transferMoney(500,"120256130001");
        System.out.println(myAccount);
        System.out.println(As);
//        Customer saeed = new Customer("mohammad","jalal");
//        Customer sorena = new Customer("sorena","mohammadnia");
//        System.out.println(saeed);
//        System.out.println(sorena);
//        Account sorenaAccount = new CharitableLoan(sorena);
//        System.out.println(sorena);
//        System.out.println(sorenaAccount);
//        Account myAccount = new CurrentAccount(saeed);
//        myAccount.setBalance(10000000);
//        System.out.println(myAccount);
//        myAccount.withdraw(1000);
//        System.out.println(myAccount);

    }
}