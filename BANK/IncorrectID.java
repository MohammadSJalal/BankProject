package BANK;

public class IncorrectID extends RuntimeException {
    public IncorrectID(String message) {
        super(message);
    }
}
