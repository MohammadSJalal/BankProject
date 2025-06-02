package BANK;
import java.util.*;
public class Account {
    public static Scanner input = new Scanner(System.in);
    private String accountNumber;
    private double balance;
    private Date dateOfOpening;
    public Account(double balance,int year,int month,int day) {
        this.balance = balance;

    }
    public void setBalance(double balance) {
        String s = "are you kidding me !!";
        if (balance >= 0) {
            this.balance = balance;
        }
        else throw new IllegalArgumentException("are you kidding me !!! how balance is less than 0 ??");
    }
    public double getBalance() {
        return this.balance;
    }
    public String getAccountNumber() {
        return this.accountNumber;
    }
    public static Date getDateOfOpening(int year, int month, int day) {
        Date dateOfOpen = new Date(2025, 5 , 12);
        try{
            dateOfOpen.setDate(year, month, day);
        }
        catch(IllegalArgumentException illegalArgumentException){
            System.out.println("sadly the date is incorrect! please enter again");
            System.out.print("please enter year correctly: ");
            year = input.nextInt();
            System.out.print("please enter month correctly: ");
            month = input.nextInt();
            System.out.print("please enter day correctly: ");
            day = input.nextInt();
            getDateOfOpening(year,month,day);
        }
        catch(Exception e){
            System.out.println("there is a problem with the date of opening that not catch correctly");
        }
        return dateOfOpen;
    }
    public static void createAccountNumber() {
        /*
        we create a account number based :
            x    xxxx    xxxx    xxxx

         */
    }
}
