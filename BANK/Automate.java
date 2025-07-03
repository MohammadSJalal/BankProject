package BANK;
import java.util.Scanner;
public final class Automate {
    public Scanner input = new Scanner(System.in);
    private Bank bank;
    /**
     * this specify that automate work based today that entered by hand or not with real time
     * true -> real time
     * false -> must be enter by hand
     */
    private boolean stateOfAutomate;
    public Automate(Bank bank , boolean stateOfAutomate ) {
        this.bank = bank;
        this.stateOfAutomate = stateOfAutomate;
    }
    public void setStateOfAutomate( boolean stateOfAutomate ) {
        this.stateOfAutomate = stateOfAutomate;
    }
    public boolean getStateOfAutomate() {
        return stateOfAutomate;
    }

    /**
     * this function is main function of automate that must be called in every
     * operate of customer like withdraw , deposit , ... it cause the loan and payment
     * correctly checked in proper time pay and get back installment
     * @param today this can be give by hand or automatically
     */
    public void checkStateOfAutomate(MyDate today) {
        if (stateOfAutomate) {
            checkTime(today);
        }
        else {
            checkTimeByHand(today);
        }
    }
    public void checkTime(MyDate date)throws IllegalArgumentException {
        int diff = MyDate.calculateSubOfDate(Bank.getToday(),date);
        if (diff > 0) {
            bank.setToday();
            workBank(date);
        }
    }
    public void checkTimeByHand(MyDate date) {
        System.out.println("warning this section is automate section that based your enter date decide to work" +
                "\nand also notice that you enter the day in the past : ");
        MyDate d = setDate();
        int diff = MyDate.calculateSubOfDate(Bank.getToday(),date);
        while (diff < 0) {
            System.out.println("please enter a valid date this date is in future : ");
            d = setDate();
            diff = MyDate.calculateSubOfDate(Bank.getToday(),date);
        }
        bank.setToday(date);
        workBank(date);
    }
    public MyDate setDate() {
        int day , month, year;
        while(true) {
            try {
                System.out.print("please enter the day : ");
                day = input.nextInt();
                System.out.print("please enter the month : ");
                month = input.nextInt();
                System.out.print("please enter the year : ");
                year = input.nextInt();
                MyDate today = new MyDate(day, month, year);
                return today;
            }
            catch(IllegalArgumentException e) {
                System.out.println("Please enter a valid date there is problem");
            }
        }
    }
    public void workBank(MyDate today) {
        for (Branch br : bank.getBranches().values()) {
            for (BaseLoan l : br.loans.values()) {
                switch(l.getState()){
                    case 1:
                        if (MyDate.calculateSubOfDate(l.getDateOfGettingLoan() , today) >= 10)
                            l.giveLoan(today);
                        break;
                    case 2:
                        l.checkPayment(today);
                        break;
                    case 3:
                        bank.getQueueLoans().remove(l);
                        break;
                }
            }
        }
        for (Account acc : bank.limitedAccounts){

        }
    }
}
