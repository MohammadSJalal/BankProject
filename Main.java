import BANK.*;
import java.util.Date;
public class Main {
    public static void main(String[] args) {
        Customer saeed = new Customer("mohammad","jalal");
        Account myAccount = new CurrentAccount(saeed);
        System.out.println(saeed);
    }
}