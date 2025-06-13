package BANK;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Bank bank = new Bank("بانک پروژه‌ای من");
        Branch branch = bank.createBranch("شعبه مرکزی");

        BranchManager manager = new BranchManager(branch);
        AssistantManager assistant = new AssistantManager(branch);
        Teller teller = new Teller(branch);

        while (true) {
            System.out.println("\n===== سیستم بانکداری =====");
            System.out.println("1. ثبت مشتری جدید");
            System.out.println("2. ایجاد حساب برای مشتری");
            System.out.println("3. نمایش حساب‌های مشتری");
            System.out.println("4. انتقال وجه بین دو حساب");
            System.out.println("5. درخواست وام");
            System.out.println("6. نمایش پیام‌های مشتری");
            System.out.println("7. خروج");
            System.out.println("8. پیش‌بردن زمان (Time Skip)");
            System.out.print("انتخاب شما: ");
            int choice = input.nextInt();
            input.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.print("نام: ");
                    String name = input.nextLine();
                    System.out.print("نام خانوادگی: ");
                    String family = input.nextLine();
                    System.out.print("تاریخ تولد (yyyy-mm-dd): ");
                    String[] date = input.nextLine().split("-");
                    System.out.print("کد ملی: ");
                    String national = input.nextLine();
                    System.out.print("شماره موبایل: ");
                    String phone = input.nextLine();
                    System.out.print("آدرس: ");
                    String address = input.nextLine();
                    Customer c = new Customer(name, family,
                            new Date(Integer.parseInt(date[0]),
                                    Integer.parseInt(date[1]),
                                    Integer.parseInt(date[2])),
                            national, phone, address);
                    bank.registerCustomer(c);
                }
                case 2 -> {
                    System.out.print("کد مشتری: ");
                    String id = input.nextLine();
                    Customer c = findCustomerById(bank, id);
                    if (c != null) {
                        System.out.print("نوع حساب (جاری/کوتاه/قرض): ");
                        String type = input.nextLine();
                        System.out.print("موجودی اولیه: ");
                        int bal = input.nextInt();
                        input.nextLine();
                        c.createAccount(type, bal);
                    }
                }
                case 3 -> {
                    System.out.print("کد مشتری: ");
                    String id = input.nextLine();
                    Customer c = findCustomerById(bank, id);
                    if (c != null) c.viewAccounts();
                }
                case 4 -> {
                    System.out.print("کد مشتری: ");
                    String id = input.nextLine();
                    Customer c = findCustomerById(bank, id);
                    if (c != null) {
                        System.out.print("شماره حساب مبدا: ");
                        String fromId = input.nextLine();
                        System.out.print("شماره حساب مقصد: ");
                        String toId = input.nextLine();
                        System.out.print("مبلغ: ");
                        int amount = input.nextInt();
                        input.nextLine();
                        Account from = findAccount(c, fromId);
                        Account to = findAccountFromAll(bank, toId);
                        if (from != null && to != null) {
                            c.transferMoney(from, to, amount);
                        } else {
                            System.out.println("حساب‌ها پیدا نشدند.");
                        }
                    }
                }
                case 5 -> {
                    System.out.print("کد مشتری: ");
                    String id = input.nextLine();
                    Customer c = findCustomerById(bank, id);
                    if (c != null) {
                        teller.agreeWithRequest("loan", c);
                        assistant.agreeWithRequest("loan", c);
                        manager.agreeWithRequest("وام مشتری تایید شد.");
                    }
                }
                case 6 -> {
                    System.out.print("کد مشتری: ");
                    String id = input.nextLine();
                    Customer c = findCustomerById(bank, id);
                    if (c != null) {
                        for (String msg : c.getInboxMessages()) System.out.println(msg);
                    }
                }
                case 7 -> {
                    System.out.println("خروج از برنامه...");
                    return;
                }
                case 8 -> {
                    System.out.print("ماه چندم رو جلو ببریم؟ ");
                    int skip = input.nextInt();
                    input.nextLine();
                    TimeManager.skipMonths(skip);
                }
                default -> System.out.println("گزینه نامعتبر.");
            }
        }
    }

    public static Customer findCustomerById(Bank bank, String id) {
        for (Customer c : bank.getCustomers()) {
            if (c.getCustomerId().equalsIgnoreCase(id)) return c;
        }
        System.out.println("مشتری پیدا نشد.");
        return null;
    }

    public static Account findAccount(Customer c, String id) {
        for (Account a : c.getAccounts()) {
            if (a.getAccountNumber().equalsIgnoreCase(id)) return a;
        }
        return null;
    }

    public static Account findAccountFromAll(Bank bank, String id) {
        for (Customer c : bank.getCustomers()) {
            for (Account a : c.getAccounts()) {
                if (a.getAccountNumber().equalsIgnoreCase(id)) return a;
            }
        }
        return null;
    }
}