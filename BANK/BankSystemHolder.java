public class BankSystemHolder {
    private static Bank bank;

    public static void setBank(Bank b) { bank = b; }
    public static Bank getBank() {
        if (bank == null) throw new IllegalStateException("بانک هنوز مقداردهی نشده است.");
        return bank;
    }
}
