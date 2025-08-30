import java.util.Scanner;
import java.util.Random;

public class Main {
    private static int toiletUsageCounter = 0;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Bank bank = new Bank("بانک جدی");
        BankSystemHolder.setBank(bank);


        Branch mainBranch = new Branch("Tehran");
        BranchManager bm = new BranchManager(mainBranch);
        AssistantManager am = new AssistantManager(mainBranch);
        bank.addBranch(mainBranch);

        showRandomMotivationalQuote();

        while (true) {
            System.out.println("\n🎉 به سیستم مدیریت بانک خوش اومدی!");
            System.out.println("1️⃣ ورود مشتری");
            System.out.println("2️⃣ ساخت مشتری جدید 🆕");
            System.out.println("3️⃣ نمایش شعب و کارمندان 🏢");
            System.out.println("4️⃣ نمایش مشتریان 👥");
            System.out.println("5️⃣ جلو بردن زمان 📅");
            System.out.println("6️⃣ ورود مدیر شعبه 👔");
            System.out.println("7️⃣ خروج ❌");
            System.out.print("انتخاب شما: ");

            int choice = input.nextInt();
            input.nextLine();

            switch (choice) {
                case 1 -> loginCustomer(input, bank);
                case 2 -> createCustomer(input, bank);
                case 3 -> bank.showBranchesAndEmployees();
                case 4 -> bank.showCustomers();
                case 5 -> timeSkipMenu(input);
                case 6 -> managerMenu(input, bank);
                case 7 -> {
                    System.out.println("👋 خداحافظ! روز خوبی داشته باشی.");
                    return;
                }
                default -> System.out.println("❌ گزینه نامعتبره! لطفاً عدد درست وارد کن.");
            }
        }
    }


    private static void showRandomMotivationalQuote() {
        String[] quotes = {
                "💡 امروزت رو با لبخند شروع کن. حتی اگه حسابت خالیه!",
                "🚀 هیچ وامی بزرگ‌تر از تلاش خودت نیست!",
                "🌟 موفقیت از پس‌انداز شروع می‌شه، نه از رابطه!",
                "💰 حتی یه ریال هم می‌تونه شروع یه امپراتوری باشه.",
                "🤝 لبخندت سرمایه‌ی اولته، نگهش دار."
        };
        int idx = new Random().nextInt(quotes.length);
        System.out.println("\n" + quotes[idx]);
    }


    private static void loginCustomer(Scanner input, Bank bank) {
        System.out.print("کد ملی مشتری رو وارد کن: ");
        String nationalCode = input.nextLine();

        Customer found = null;
        for (Customer c : bank.getCustomers()) {
            if (c.getNationalCode().equals(nationalCode)) {
                found = c;
                break;
            }
        }

        if (found != null) {
            customerMenu(input, bank, found);
        } else {
            System.out.println("❌ مشتری با این کد ملی یافت نشد.");
        }
    }


    private static void createCustomer(Scanner input, Bank bank) {
        System.out.print("نام: ");
        String name = input.nextLine();

        System.out.print("نام خانوادگی: ");
        String family = input.nextLine();

        System.out.print("سال تولد: ");
        int year = input.nextInt();
        System.out.print("ماه تولد: ");
        int month = input.nextInt();
        System.out.print("روز تولد: ");
        int day = input.nextInt();
        input.nextLine();

        System.out.print("کد ملی (10 رقمی): ");
        String nationalCode = input.nextLine();

        System.out.print("شماره موبایل (11 رقمی): ");
        String phone = input.nextLine();

        System.out.print("آدرس: ");
        String address = input.nextLine();

        try {
            Customer c = new Customer(name, family, new Date(year, month, day), nationalCode, phone, address);
            bank.addCustomer(c);
            System.out.println("✅ مشتری جدید با کد " + c.getCustomerId() + " ساخته شد.");
        } catch (Exception e) {
            System.out.println("❌ خطا: " + e.getMessage());
        }
    }

    // 🔹 منوی مشتری
    private static void customerMenu(Scanner input, Bank bank, Customer customer) {
        while (true) {
            System.out.println("\n📱 منوی مشتری (" + customer.getName() + "):");
            System.out.println("1️⃣ ایجاد حساب جدید 🆕");
            System.out.println("2️⃣ انتقال پول 💸");
            System.out.println("3️⃣ مشاهده حساب‌ها 📊");
            System.out.println("4️⃣ مشاهده پیام‌ها 📬");
            System.out.println("5️⃣ درخواست وام 💳");
            System.out.println("6️⃣ بستن حساب ❌");
            System.out.println("7️⃣ درخواست چای 🍵");
            System.out.println("8️⃣ رفتن به دستشویی 🚽");
            System.out.println("9️⃣ بازگشت 🔙");
            System.out.print("انتخاب شما: ");

            int choice = input.nextInt();
            input.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.print("نوع حساب رو وارد کن (جاری / کوتاه / قرض): ");
                    String type = input.nextLine();
                    System.out.print("موجودی اولیه رو وارد کن: ");
                    int balance = input.nextInt();
                    input.nextLine();
                    try {
                        customer.createAccount(type, balance);
                    } catch (Exception e) {
                        System.out.println("❌ خطا: " + e.getMessage());
                    }
                }
                case 2 -> {
                    if (customer.getAccounts().isEmpty()) {
                        System.out.println("❌ هیچ حسابی وجود نداره.");
                        break;
                    }
                    customer.viewAccounts();
                    System.out.print("شماره حساب مبدا: ");
                    String fromAcc = input.nextLine();
                    System.out.print("شماره حساب مقصد: ");
                    String toAcc = input.nextLine();
                    System.out.print("مبلغ: ");
                    int amount = input.nextInt();
                    input.nextLine();

                    Account from = Bank.findAccount(fromAcc);
                    Account to = Bank.findAccount(toAcc);

                    if (from != null && to != null) {
                        customer.transferMoney(from, to, amount);
                    } else {
                        System.out.println("❌ حساب مبدا یا مقصد یافت نشد.");
                    }
                }
                case 3 -> customer.viewAccounts();
                case 4 -> {
                    System.out.println("📬 پیام‌های شما:");
                    if (customer.getInboxMessages().isEmpty()) {
                        System.out.println("پیامی موجود نیست.");
                    } else {
                        for (String msg : customer.getInboxMessages()) {
                            System.out.println("- " + msg);
                        }
                    }
                }
                case 5 -> {
                    Branch branch = bank.getBranches().get(0);
                    Teller teller = new Teller(branch);
                    teller.agreeWithRequest("loan", customer);
                }
                case 6 -> {
                    if (customer.getAccounts().isEmpty()) {
                        System.out.println("❌ حسابی برای بستن وجود نداره.");
                    } else {
                        customer.viewAccounts();
                        System.out.print("شماره حسابی که می‌خوای ببندی رو وارد کن: ");
                        String accNum = input.nextLine();
                        Branch branch = bank.getBranches().get(0);
                        Teller teller = new Teller(branch);
                        teller.agreeWithRequest("close account", customer);
                        customer.closeAccount(accNum);
                    }
                }
                case 7 -> teaBoyMenu();
                case 8 -> useToilet();
                case 9 -> { return; }
                default -> System.out.println("❌ گزینه نامعتبره! لطفاً عدد درست وارد کن.");
            }
        }
    }


    private static void managerMenu(Scanner input, Bank bank) {
        System.out.print("شناسه مدیر شعبه را وارد کنید: ");
        String managerId = input.nextLine();

        BranchManager manager = null;
        for (Branch b : bank.getBranches()) {
            if (b.getManager() != null && b.getManager().getEmployeeIdentity().equals(managerId)) {
                manager = b.getManager();
                break;
            }
        }

        if (manager == null) {
            System.out.println("❌ مدیر شعبه‌ای با این شناسه پیدا نشد.");
            return;
        }

        while (true) {
            System.out.println("\n👔 منوی مدیر شعبه:");
            System.out.println("1️⃣ مشاهده درخواست‌ها");
            System.out.println("2️⃣ تایید درخواست‌ها");
            System.out.println("3️⃣ مشاهده کارمندان شعبه");
            System.out.println("4️⃣ حذف کارمند");
            System.out.println("5️⃣ مشاهده مشتریان شعبه");
            System.out.println("6️⃣ بازگشت");
            System.out.print("انتخاب: ");

            int choice = input.nextInt();
            input.nextLine();
            switch (choice) {
                case 1 -> bank.showRequests();
                case 2 -> {
                    System.out.print("شناسه درخواست برای تایید: ");
                    int reqId = input.nextInt();
                    input.nextLine();
                    Request req = bank.findRequestById(reqId);
                    if (req != null) manager.finalizeRequest(req);
                    else System.out.println("❌ درخواست پیدا نشد.");
                }
                case 3 -> manager.showEmployees();
                case 4 -> {
                    System.out.print("شناسه کارمند برای حذف: ");
                    String empId = input.nextLine();
                    Employee e = manager.getBranchWork().findEmployeeById(empId);
                    if (e != null) manager.removeEmployee(e);
                    else System.out.println("❌ کارمند پیدا نشد.");
                }
                case 5 -> manager.getBranchWork().showCustomers();
                case 6 -> { return; }
                default -> System.out.println("❌ گزینه نامعتبر.");
            }
        }
    }


    private static void teaBoyMenu() {
        System.out.println("\n🍵 آبدارچی با یک لبخند گرم چای را تقدیم کرد 😊");
    }


    private static void useToilet() {
        toiletUsageCounter++;
        if (toiletUsageCounter >= 3) {
            System.out.println("🚨 سوء استفاده مالیاتی از دستشویی شناسایی شد! گزارش به مدیر ارسال شد.");
            toiletUsageCounter = 0;
            return;
        }

        double chance = Math.random();
        if (chance < 0.3) {
            System.out.println("😳 کسی شما رو دید! شما برای ۲ دقیقه از عملیات بانکی محروم شدید 🚫");
        } else {
            System.out.println("✅ با موفقیت از دستشویی استفاده کردید! امیدواریم سبک شده باشید 😌");
        }
    }


    private static void timeSkipMenu(Scanner input) {
        System.out.print("⏩ چند ماه جلو بریم؟ ");
        int skip = input.nextInt();
        TimeManager.skipMonths(skip);
    }
}
