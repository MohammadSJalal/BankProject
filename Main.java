import BANK.*;
public class Main {
    public static void main(String[] args) {
        Branch [] branches = new Branch[100];
        for (int i = 0; i < 100; i++) {
            branches[i] = new Branch();
            branches[i].showInformation();
        }

    }
}