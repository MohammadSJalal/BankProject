package BANK;

public class AccountIsBlocked extends RuntimeException {
    public AccountIsBlocked(String message) {
        super(message);
    }
}
