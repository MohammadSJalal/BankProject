package BANK;
/*
* now there are a lot of problem because they get average of account inside of the normal constractor that is not reasonable
* we usually must send then it just apply it
*/
/**
 * کلاس پایه برای مدیریت ساختار و رفتار انواع وام‌ها
 * این کلاس abstract هست یعنی خودش مستقیماً قابل ساخت نیست،
 * بلکه باید توسط کلاس‌های فرزند (مثلاً NormalLoan یا FacilityLoan) گسترش پیدا کنه.
 */
public abstract class BaseLoan {
    /**account that customer get loan with it*/
    Account acc;
    // مبلغ وام (عدد صحیح مثبت)
    protected Double amount;
    // this is id for loan and track it easily
    String loanId;
    // مدت زمان بازپرداخت به ماه
    protected int durationInMonths;

    // سهم دولت از بازپرداخت (درصد بین 0 تا 100)
    protected int governmentShare;

    // سهم مشتری از بازپرداخت (درصد بین 0 تا 100)
    protected int customerShare;
    protected int bankShare;
    // درصد جریمه برای هر ماه تأخیر
    protected int penaltyPercent;
    //process of loan;
    private int state;
    // مشتری‌ای که وام رو دریافت کرده
    protected Customer borrower;
    protected Customer CoSigner;
    protected char typeOfAccount1;
    protected Customer Guarantor;
    protected char typeOfAccount2;
    /**this is a variable that count months for pay */
    private int paidMonths;
    private boolean [] mapPaidMonths;
    /**percent of profit per year*/
    private double percentageOfInterest;
    // it a const instalment for pay
    private double instalment;

    private MyDate dateOfGettingLoan;
    private int delayDays;
    /**
     * سازنده‌ی کلاس وام
     * در این سازنده اطلاعات اصلی وام مقداردهی می‌شوند.
     * برخی چک‌ها برای اطمینان از درستی داده‌ها انجام می‌شود.
     */
    public BaseLoan(double amount , int durationInMonths,MyDate dateOfGettingLoan,int bankShare, int governmentShare,
                     int customerShare, int penaltyPercent, Customer borrower ,
                     Account accountGetLoan,double percentageOfInterest , char typeLoan) throws IllegalArgumentException{
        if (amount <= 0 || durationInMonths <= 0) {
            throw new IllegalArgumentException("Amount and duration must be positive.");
        }

        if (governmentShare + customerShare != 100) {
            throw new IllegalArgumentException("Government and customer shares must sum to 100.");
        }

        if (penaltyPercent < 0 || penaltyPercent > 100) {
            throw new IllegalArgumentException("Penalty percent must be between 0 and 100.");
        }
        if (percentageOfInterest < 0 || percentageOfInterest > 100) throw new IllegalArgumentException("Percentage of interest must be between 0 and 100.");
        this.amount = amount;
        this.durationInMonths = durationInMonths;
        this.governmentShare = governmentShare;
        this.customerShare = customerShare;
        this.penaltyPercent = penaltyPercent;
        this.borrower = borrower;
        Branch br = (Branch) Bank.find(borrower.getCustomerId());
        this.loanId = borrower.getCustomerId()+"."+ br.loanCounter;
        br.loans.put(loanId, this);
        this.dateOfGettingLoan = dateOfGettingLoan;
        this.acc = accountGetLoan;
        this.percentageOfInterest = percentageOfInterest;
        this.bankShare = bankShare;
        paidMonths = 1;
        mapPaidMonths = new boolean[durationInMonths];
        delayDays = 0;
    }
    BaseLoan(double amount ,double installment ,MyDate dateOfGettingLoan, int bankShare, int governmentShare,
             int customerShare, int penaltyPercent, Customer borrower ,
             Account accountGetLoan,double percentageOfInterest , char typeLoan){
        if (amount <= 0 || durationInMonths <= 0) {
            throw new IllegalArgumentException("Amount and duration must be positive.");
        }

        if (governmentShare + customerShare != 100) {
            throw new IllegalArgumentException("Government and customer shares must sum to 100.");
        }

        if (penaltyPercent < 0 || penaltyPercent > 100) {
            throw new IllegalArgumentException("Penalty percent must be between 0 and 100.");
        }
        this.amount = amount;
        this.instalment = installment;
        this.bankShare = bankShare;
        this.governmentShare = governmentShare;
        this.customerShare = customerShare;
        this.penaltyPercent = penaltyPercent;
        this.borrower = borrower;
        Branch br = Bank.findBranchWithIDOfEmployee(borrower.getCustomerId());
        this.loanId = borrower.getCustomerId()+"."+ br.loanCounter;
        br.loans.put(loanId, this);
        this.acc = accountGetLoan;
        this.dateOfGettingLoan = dateOfGettingLoan;
        this.percentageOfInterest = percentageOfInterest;
        paidMonths = 2;
        mapPaidMonths = new boolean[durationInMonths];
        delayDays = 0;
    }
    /**this function proportional with input data it calculate installment
     * @return  amount of money that bank pay for loan total amount with calculate everything
     */
    public final double calculation() throws IllegalArgumentException{
        double givenAmountByBank =amount * bankShare * 0.01;
        double givenAmountByGovernment = amount * (100 - bankShare) * governmentShare * 0.0001;
        amount *= bankShare * 0.01;
        if (instalment != 0) calculateMonthForBack(instalment);
        else if (durationInMonths != 0) calculateInstallment(durationInMonths);
        return givenAmountByBank + givenAmountByGovernment;
    }
    // متد getter برای مبلغ وام
    public final double getAmount() {
        return amount;
    }
    public final int getState(){
        return state;
    }
    public final void giveLoan(MyDate dateOfGive) throws IllegalArgumentException{
        dateOfGettingLoan = dateOfGive;
        state = 1;
        acc.deposit(calculation());
    }
    // متد getter برای مدت بازپرداخت
    public int getDurationInMonths() {
        return durationInMonths;
    }

    // متد getter برای درصد جریمه تأخیر
    public int getPenaltyPercent() {
        return penaltyPercent;
    }
    public MyDate getDateOfGettingLoan() {
        return dateOfGettingLoan;
    }
    public Account getAccountGetLoan() {
        //this is account that costumer get with it loan
        return acc;
    }
    public boolean checkAccount(Account account) {
        //this method check sended account is get loan account ?
        return acc.equals(account);
    }
    /**
     * this function calculate installment and their formula is (P.r.(1+r)^n)/((1+r)^n - 1)
     * P : principal loan amount
     * r : monthly interest rate
     * n : number of month
     */
    private void calculateInstallment(int months) {
        //normal loan usually are double of average of account
        double P = amount ;
        double r = percentageOfInterest / 1200;
        instalment = (P * r * Math.pow(1 + r, months)) / (Math.pow(1 + r, months) - 1);
    }

    /**
     * the set state function show state of loan and you can track the process of it
     * 0 : it has not accept by manager yet
     * 1 : it is accept by manager and now is queue and it still not paid to customer
     * 2 : it paid to customer and customer must pay installment
     * 3 : it mean the  customer have not any debt
     * @param state it is a integer number between 0 till three
     */
    public final void setSate(int state) throws IllegalArgumentException{
        if (state > 3 || state < 0) throw new IllegalArgumentException("Sate must be between 0 and 2.");
        this.state = state;
    }
    public final void calculateMonthForBack(double instalment){
        double P = amount ;
        double r = percentageOfInterest/1200;
        double months = (Math.log(instalment/(instalment - P * r)))/(Math.log(1 + r));
        //there is a problem that can make mistake inside of my code because i round it
        durationInMonths = (int) Math.floor(months);
        //not it's correct
        calculateInstallment(durationInMonths);
    }
    // متد getter برای مشتری دریافت‌کننده‌ی وام
    public Customer getBorrower() {
        return borrower;
    }
    /**
     * متد abstract برای محاسبه جریمه تأخیر
     * کلاس‌های فرزند باید این متد را پیاده‌سازی کنند
     *this method first calculate then apply it to instalment
     *  چون ممکن است نرخ جریمه یا نحوه محاسبه آن متفاوت باشد.
     */
    public void calculatePenalty(int days){
        delayDays = days;
        instalment += (penaltyPercent/30)*days*instalment;
    }
    private void overdue(int days , boolean key ){
        if (key) {
            try {
                Letter l = borrower.searchLetter("notice");
                l.setMessage(
                        "Dear borrower : "+borrower.getName()+"\n"+
                                "sadly you have overdue for payment of the loan the we are apply the interest to your loan" +
                                "\n and you must should pay back the loan instalment in first opportunity on other wise we " +
                                "\napply harder instructions to your account"
                );
            }
            catch (IllegalArgumentException e) {
                String message = "Dear borrower : "+borrower.getName()+"\n"+
                        "sadly you have overdue for payment of the loan the we are apply the interest to your loan" +
                        "\n and you must should pay back the loan instalment in first opportunity on other wise we " +
                        "\napply harder instructions to your account";
                Letter l = borrower.searchLetter("notice");
                l.setMessage(message);
            }
            if(days >= 5 && days < 10){
                acc.setLimit(instalment/1.5);
            }
            else if(days >= 10 && days < 20){
                acc.setBlocked(true);
            }
            else if (days >= 20){
                for (Account a : borrower.getAccounts()) {
                    a.setBlocked(true);
                }
            }
        } //below section is for future develop
        else if ( days <= 10 && days > 0) applyLimit('2',amount/2,typeOfAccount1,false , false);
        else if (days > 10 && days <= 15) applyLimit('3',amount/2,typeOfAccount2,false , true);
        else if (days > 15 && days <= 20) applyLimit('1',0,acc.getAccountNumber().charAt(0),true , false);
        else applyLimit('0',0,typeOfAccount1,true , true);
    }
    private void applyLimit(char type, double amount, char TOF ,boolean blocked , boolean force){
        if (blocked && force) {
            applyLimit('1', amount, TOF, true , false);
            applyLimit('2', amount, TOF, true , false);
            applyLimit('3', amount, TOF, true , false);
        }
        else {
            switch (type) {
                case '1':
                    limit(borrower, amount, TOF, blocked);
                    sendNotice(TOF,blocked,'1');
                    break;
                case '2':
                    limit(CoSigner, amount, TOF, blocked);
                    sendNotice(TOF,blocked,'2');
                    break;
                case '3':
                    limit(Guarantor, amount, TOF, blocked);
                    sendNotice(TOF,blocked,'3');
                    break;
            }
        }
    }

    /**
     * this function is for send the message to borrower and their guarantors
     * @param typeOfAccount this is account that we apply limition or block to it
     * @param blocked it show we want to block it or not
     * @param which it is a char of 49 till 51 or in other word '1' till '3' that show '1' -> borrower and so on respectively
     */
    private void sendNotice(char typeOfAccount , boolean blocked , char which){
        StringBuilder message = new StringBuilder();
        switch (which) {
            case '1':
                if (blocked) message.append("Dear "+borrower.getName()+"\nwe block your account because you have broken our construct");
                else message.append("Dear "+borrower.getName()+"\nwe apply limit to your account because you have broken our construct");
                borrower.searchLetter("notice").setMessage(message.toString());
                break;
            case '2':
                if (blocked) message.append("Dear "+CoSigner.getName()+"\nwe block your account because your Guarantor have broken our construct");
                else message.append("Dear "+CoSigner.getName()+"\nwe apply limit to your account because your Guarantor have broken our construct");
                CoSigner.searchLetter("notice").setMessage(message.toString());
                break;
            case '3':
                if (blocked) message.append("Dear "+Guarantor.getName()+"\nwe block your account because your Guarantor have broken our construct");
                else message.append("Dear "+Guarantor.getName()+"\nwe apply limit to your account because your Guarantor have broken our construct");
                Guarantor.searchLetter("notice").setMessage(message.toString());
                break;
        }
    }
    //this seems like redundant
    private void limit(Customer c, double limit ,char TOF ,boolean blocked){
        for (Account acc : c.getAccounts()) {
            acc.setBlocked(blocked);
            if (acc.getAccountNumber().charAt(0) == TOF && !blocked) acc.setLimit(limit);
        }
    }
    /**
     * متد abstract برای برگرداندن نوع وام به‌صورت رشته
     * به کلاس‌های فرزند اجازه می‌دهد نوع خاص وام خود را مشخص کنند
     * مثل: "Normal Loan" یا "Facility Loan"
     */
    public abstract String getLoanType();
    private void blockAccounts(Customer c , boolean becomeBlock){
        for (Account a : c.getAccounts()){
            a.setBlocked(becomeBlock);
        }
    }
    /**
     * this function check for time to pay installment if it is func check the customer have
     * enough balance or not if have it get if not it apply penalty and send warning message for customer
     * and if the delay time of pay become greater than 5 we block all account of customer and block all account of Co-signers if
     * delay time pass the 30 day and take this amount with other bank we find all account of customer and Co-signers
     */
    public void checkPayment(MyDate today){
        int diff = MyDate.calculateSubOfDate(dateOfGettingLoan,today) - delayDays;
        if (diff < paidMonths * MyDate.daysPerMonth[dateOfGettingLoan.getMonth()]) ;
        else{
            try{
                acc.withdraw(instalment,2);
                limit(borrower,0,acc.getAccountNumber().charAt(0),false);
                mapPaidMonths[paidMonths] = true;
                calculateInstallment(durationInMonths);
                paidMonths++;
                if (paidMonths == durationInMonths) state = 3;
                delayDays = 0;
            }
            catch(NotEnoughBalance e){
                Letter noticeL = new Letter("your balance isn't enough for paid",today);
                calculatePenalty(diff);
                borrower.receiveMessage(noticeL);
                overdue(diff + delayDays , true );
            }
        }
    }
    /**
     * نمایش اطلاعات کلی وام
     * این متد قابل override است، ولی به‌صورت پیش‌فرض اطلاعات پایه را چاپ می‌کند.
     */
    @Override
    public String toString() {
        return "Loan Type: " + getLoanType() + "\n" +
                "Amount: " + amount + "\n" +
                "Duration: " + durationInMonths + " months\n" +
                "Government Share: " + governmentShare + "%\n" +
                "Customer Share: " + customerShare + "%\n" +
                "Penalty per month: " + penaltyPercent + "%\n" +
                "Borrower: " + (borrower != null ? borrower.getCustomerId() : "Unknown");
    }
}