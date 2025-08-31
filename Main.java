import java.util.Scanner;
import java.util.Random;

public class Main {
    private static int toiletUsageCounter = 0;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        Bank bank = new Bank("بانک جدی");
        BankSystemHolder.setBank(bank);

        Branch mainBranch = new Branch("Bazghandi bank of Shahrood");
        BranchManager bm = new BranchManager(mainBranch);
        AssistantManager am = new AssistantManager(mainBranch);
        Teller teller = new Teller(mainBranch);
        bank.addBranch(mainBranch);
        bank.addEmployee(bm);
        bank.addEmployee(am);
        bank.addEmployee(teller);

        showRandomMotivationalQuote();

        while (true) {
            System.out.println("\n🎉 به سیستم مدیریت بانک خوش آمدید!");
            System.out.println("1️⃣ ورود مشتری");
            System.out.println("2️⃣ ساخت مشتری جدید 🆕");
            System.out.println("3️⃣ گاد مود 👑");
            System.out.println("4️⃣ جلو بردن زمان 📅");
            System.out.println("5️⃣ ورود مدیر شعبه 👔");
            System.out.println("6️⃣ ورود معاون شعبه 📝");
            System.out.println("7️⃣ خروج ❌");
            System.out.print("انتخاب شما: ");

            int choice = safeNextInt(input);
            input.nextLine();

            switch (choice) {
                case 1 -> loginCustomer(input, bank, teller);
                case 2 -> createCustomer(input, bank, mainBranch);
                case 3 -> godModeMenu(input, bank);
                case 4 -> timeSkipMenu(input, bank);
                case 5 -> managerMenu(input, bank);
                case 6 -> assistantMenu(input, bank);
                case 7 -> {
                    System.out.println("👋 خداحافظ!");
                    return;
                }
                default -> System.out.println("❌ گزینه نامعتبر! لطفاً عدد درست وارد کنید.");
            }
        }
    }

    private static int safeNextInt(Scanner sc) {
        while (!sc.hasNextInt()) {
            sc.next();
            System.out.print("لطفاً عدد وارد کنید: ");
        }
        return sc.nextInt();
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

    // ---------------------- مشتری ----------------------
    private static void loginCustomer(Scanner input, Bank bank, Teller teller) {
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
            customerMenu(input, bank, found, teller);
        } else {
            System.out.println("❌ مشتری با این کد ملی یافت نشد.");
        }
    }

    private static void createCustomer(Scanner input, Bank bank, Branch branch) {
        try {
            System.out.print("نام: ");
            String name = input.nextLine();
            System.out.print("نام خانوادگی: ");
            String family = input.nextLine();
            System.out.print("سال تولد: ");
            int year = safeNextInt(input);
            System.out.print("ماه تولد: ");
            int month = safeNextInt(input);
            System.out.print("روز تولد: ");
            int day = safeNextInt(input);
            input.nextLine();
            System.out.print("کد ملی (10 رقمی): ");
            String nationalCode = input.nextLine();
            System.out.print("شماره موبایل (11 رقمی): ");
            String phone = input.nextLine();
            System.out.print("آدرس: ");
            String address = input.nextLine();


            java.util.Date birthDate = new java.util.Date(year - 1900, month - 1, day);

            Customer c = new Customer(name, family, birthDate, nationalCode, phone, address);
            bank.addCustomer(c);
            branch.addCustomer(c);
            System.out.println("✅ مشتری جدید با کد " + c.getCustomerId() +
                    " ساخته شد و به شعبه " + branch.getName() + " اضافه شد.");
        } catch (Exception e) {
            System.out.println("❌ خطا: " + e.getMessage());
        }
    }

    private static void customerMenu(Scanner input, Bank bank, Customer customer, Teller teller) {
        while (true) {
            System.out.println("\n📱 منوی مشتری (" + customer.getName() + "):");
            System.out.println("1️⃣ ایجاد حساب جدید 🆕");
            System.out.println("2️⃣ انتقال پول 💸");
            System.out.println("3️⃣ مشاهده حساب‌ها 📊");
            System.out.println("4️⃣ مشاهده پیام‌ها 📬");
            System.out.println("5️⃣ درخواست وام 💳");
            System.out.println("6️⃣ درخواست بستن حساب ❌");
            System.out.println("7️⃣ جستجوی حساب 🔍");
            System.out.println("8️⃣ پرداخت قسط 💵");
            System.out.println("9️⃣ بازگشت 🔙");
            System.out.print("انتخاب شما: ");

            int choice = safeNextInt(input);
            input.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.print("نوع حساب رو وارد کن (جاری / کوتاه / قرض): ");
                    String type = input.nextLine();
                    System.out.print("موجودی اولیه رو وارد کن: ");
                    int balance = safeNextInt(input);
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
                    int amount = safeNextInt(input);
                    input.nextLine();
                    customer.transferMoney(fromAcc, toAcc, amount);
                }
                case 3 -> customer.viewAccounts();
                case 4 -> {
                    System.out.println("📬 پیام‌های شما:");
                    if (customer.getInboxMessages().isEmpty()) System.out.println("پیامی موجود نیست.");
                    else for (String msg : customer.getInboxMessages()) System.out.println("- " + msg);
                }
                case 5 -> teller.handleRequest("loan", customer);
                case 6 -> {
                    if (customer.getAccounts().isEmpty()) {
                        System.out.println("❌ حسابی برای بستن وجود نداره.");
                    } else {
                        customer.viewAccounts();
                        System.out.print("شماره حسابی که می‌خوای ببندی رو وارد کن: ");
                        String accNum = input.nextLine();
                        customer.requestCloseAccount(accNum, teller);
                    }
                }
                case 7 -> {
                    System.out.print("شماره حساب موردنظر را وارد کنید: ");
                    String accNum = input.nextLine();
                    Account acc = bank.findAccount(accNum);
                    if (acc != null) {
                        System.out.println("✅ حساب پیدا شد: " + acc);
                    } else System.out.println("❌ هیچ حسابی با این شماره یافت نشد.");
                }
                case 8 -> {
                    customer.viewLoans();
                    if (customer.getLoans().isEmpty()) break;
                    System.out.print("شناسه وام: ");
                    String loanId = input.nextLine();
                    System.out.print("مبلغ قسط: ");
                    int amount = safeNextInt(input);
                    input.nextLine();
                    customer.payInstallment(loanId, amount);
                }
                case 9 -> {
                    return;
                }
                default -> System.out.println("❌ گزینه نامعتبره! لطفاً عدد درست وارد کن.");
            }
        }
    }

    // ---------------------- گاد مود ----------------------
    private static void godModeMenu(Scanner input, Bank bank) {
        System.out.println("\n👑 وارد گاد مود شدید!");
        while (true) {
            System.out.println("1️⃣ ایجاد شعبه جدید");
            System.out.println("2️⃣ نمایش شعبه‌ها و کارمندان");
            System.out.println("3️⃣ نمایش مشتریان هر شعبه");
            System.out.println("4️⃣ اعمال یک ماه (برای تست سود)");
            System.out.println("5️⃣ بازگشت");
            System.out.print("انتخاب: ");
            int choice = safeNextInt(input);
            input.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.print("نام شعبه جدید: ");
                    String name = input.nextLine();
                    Branch b = new Branch(name);
                    bank.addBranch(b);
                    new BranchManager(b);
                    new AssistantManager(b);
                    new Teller(b);
                    System.out.println("✅ شعبه جدید ساخته شد: " + name);
                }
                case 2 -> {
                    if (bank.getBranches().isEmpty()) System.out.println("❌ هیچ شعبه‌ای وجود ندارد.");
                    else for (Branch b : bank.getBranches()) {
                        System.out.println(b);
                        for (Employee e : b.getEmployees()) System.out.println(" - " + e);
                    }
                }
                case 3 -> {
                    for (Branch b : bank.getBranches()) {
                        System.out.println("🏢 شعبه " + b.getName() + ":");
                        b.showCustomers();
                    }
                }
                case 4 -> TimeManager.skipMonths(1);
                case 5 -> {
                    return;
                }
                default -> System.out.println("❌ گزینه نامعتبر!");
            }
        }
    }

    private static void timeSkipMenu(Scanner input, Bank bank) {
        System.out.print("⏩ چند ماه جلو بریم؟ ");
        int skip = safeNextInt(input);
        input.nextLine();
        TimeManager.skipMonths(skip);
    }

    // ---------------------- منوی مدیر ----------------------
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
            System.out.println("\n👔 منوی مدیر شعبه (" + manager.getBranchWork().getName() + "):");
            System.out.println("1️⃣ مشاهده مشتریان شعبه");
            System.out.println("2️⃣ مشاهده کارمندان شعبه");
            System.out.println("3️⃣ مشاهده اینباکس درخواست‌ها");
            System.out.println("4️⃣ تایید درخواست");
            System.out.println("5️⃣ افزودن کارمند جدید (مستقیم)");
            System.out.println("6️⃣ حذف کارمند (مستقیم)");
            System.out.println("7️⃣ بازگشت");
            System.out.print("انتخاب: ");

            int choice = safeNextInt(input);
            input.nextLine();

            switch (choice) {
                case 1 -> manager.showCustomers();
                case 2 -> manager.showEmployees();
                case 3 -> manager.showInbox();
                case 4 -> {
                    System.out.print("شماره درخواست برای تایید: ");
                    int id = safeNextInt(input);
                    input.nextLine();
                    manager.finalizeRequest(id);
                }
                case 5 -> {
                    System.out.println("چه نوع کارمندی می‌خواهید اضافه کنید؟");
                    System.out.println("1️⃣ تحویل‌دار (Teller)");
                    System.out.println("2️⃣ معاون شعبه (Assistant Manager)");
                    int empType = safeNextInt(input);
                    input.nextLine();
                    switch (empType) {
                        case 1 -> {
                            Teller t = new Teller(manager.getBranchWork());
                            bank.addEmployee(t);
                            System.out.println("✅ تحویل‌دار جدید ساخته شد: " + t.getEmployeeIdentity());
                        }
                        case 2 -> {
                            AssistantManager am = new AssistantManager(manager.getBranchWork());
                            bank.addEmployee(am);
                            System.out.println("✅ معاون شعبه جدید ساخته شد: " + am.getEmployeeIdentity());
                        }
                        default -> System.out.println("❌ نوع نامعتبر!");
                    }
                }
                case 6 -> {
                    System.out.print("شناسه کارمند برای حذف: ");
                    String empId = input.nextLine();
                    Employee e = manager.getBranchWork().findEmployeeById(empId);
                    if (e != null) {
                        manager.getBranchWork().removeEmployeeDirect(e);
                        System.out.println("❌ کارمند " + e.getEmployeeIdentity() + " از شعبه حذف شد.");
                    } else System.out.println("❌ کارمند پیدا نشد.");
                }
                case 7 -> {
                    return;
                }
                default -> System.out.println("❌ گزینه نامعتبر!");
            }
        }
    }

    // ---------------------- منوی معاون ----------------------
    private static void assistantMenu(Scanner input, Bank bank) {
        System.out.print("شناسه معاون شعبه را وارد کنید: ");
        String assistantId = input.nextLine();

        AssistantManager assistant = null;
        for (Branch b : bank.getBranches()) {
            for (Employee e : b.getEmployees()) {
                if (e instanceof AssistantManager am && am.getEmployeeIdentity().equals(assistantId)) {
                    assistant = am;
                    break;
                }
            }
        }

        if (assistant == null) {
            System.out.println("❌ معاون شعبه‌ای با این شناسه پیدا نشد.");
            return;
        }

        while (true) {
            System.out.println("\n📩 منوی معاون شعبه (" + assistant.getBranchWork().getName() + "):");
            System.out.println("1️⃣ مشاهده درخواست‌ها");
            System.out.println("2️⃣ تایید درخواست");
            System.out.println("3️⃣ مشاهده پیام‌های خود");
            System.out.println("4️⃣ بازگشت");
            System.out.print("انتخاب: ");

            int choice = safeNextInt(input);
            input.nextLine();

            switch (choice) {
                case 1 -> assistant.showInbox();
                case 2 -> {
                    System.out.print("شماره درخواست برای تایید: ");
                    int id = safeNextInt(input);
                    input.nextLine();
                    assistant.approveRequest(id);
                }
                case 3 -> assistant.showMessages();
                case 4 -> {
                    return;
                }
                default -> System.out.println("❌ گزینه نامعتبر!");
            }
        }
    }
}
