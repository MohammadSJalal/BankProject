package BANK;

import java.util.ArrayList; import java.util.List; import java.util.Random; import java.util.Scanner;

public class Main { private static List<String> usedAccountNumbers = new ArrayList<>(); private static int toiletUsageCounter = 0;

public static void main(String[] args) {
    Scanner input = new Scanner(System.in);
    Bank bank = new Bank("بانک جدی");

    showRandomMotivationalQuote();

    while (true) {
        System.out.println("\n🎉 به سیستم مدیریت بانک خوش اومدی!");
        System.out.println("1️⃣ ورود مشتری");
        System.out.println("2️⃣ استفاده از پارتی بازی ⏩");
        System.out.println("3️⃣ خروج ❌");
        System.out.print("انتخاب شما: ");

        int choice = input.nextInt();
        input.nextLine();

        switch (choice) {
            case 1 -> customerMenu(input, bank);
            case 2 -> timeSkipMenu();
            case 3 -> {
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
        "🌟 موفقیت از پس‌انداز شروع می‌شه، نه از پارتی‌بازی!",
        "💰 حتی یه ریال هم می‌تونه شروع یه امپراتوری باشه.",
        "🤝 لبخندت سرمایه‌ی اولته، نگهش دار."
    };
    int idx = new Random().nextInt(quotes.length);
    System.out.println("\n" + quotes[idx]);
}

private static void customerMenu(Scanner input, Bank bank) {
    while (true) {
        System.out.println("\n📱 منوی مشتری:");
        System.out.println("1️⃣ ایجاد حساب جدید 🆕");
        System.out.println("2️⃣ انتقال پول 💸");
        System.out.println("3️⃣ مشاهده پیام‌ها 📬");
        System.out.println("4️⃣ درخواست چای 🍵");
        System.out.println("5️⃣ رفتن به دستشویی 🚽");
        System.out.println("6️⃣ بازگشت 🔙");
        System.out.print("انتخاب شما: ");

        int choice = input.nextInt();
        input.nextLine();

        switch (choice) {
            case 1 -> System.out.println("🆕 ساخت حساب هنوز پیاده‌سازی نشده.");
            case 2 -> System.out.println("💸 انتقال پول هنوز پیاده‌سازی نشده.");
            case 3 -> System.out.println("📬 مشاهده پیام‌ها هنوز پیاده‌سازی نشده.");
            case 4 -> teaBoyMenu();
            case 5 -> useToilet();
            case 6 -> {
                return;
            }
            default -> System.out.println("❌ گزینه نامعتبره! لطفاً عدد درست وارد کن.");
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

private static void timeSkipMenu() {
    Scanner input = new Scanner(System.in);
    System.out.print("⏩ چند ماه جلو بریم؟ ");
    int skip = input.nextInt();
    TimeManager.skipMonths(skip);
}

private static boolean isValidNationalCode(String code) {
    if (code == null || code.length() != 10) return false;
    for (char c : code.toCharArray()) {
        if (!Character.isDigit(c)) return false;
    }
    return true;
}

private static boolean isValidPhoneNumber(String phone) {
    if (phone == null || phone.length() != 11) return false;
    if (phone.charAt(0) != '0') return false;
    for (char c : phone.toCharArray()) {
        if (!Character.isDigit(c)) return false;
    }
    return true;
}

public static boolean isAccountNumberUsed(String accountNumber) {
    for (String acc : usedAccountNumbers) {
        if (acc.equals(accountNumber)) return true;
    }
    return false;
}

public static void markAccountNumberUsed(String accountNumber) {
    usedAccountNumbers.add(accountNumber);
}

}

