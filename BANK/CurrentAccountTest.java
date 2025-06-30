package BANK;
import org.junit.Before;

import org.junit.Test;

import static org.junit.Assert.*;

public class CurrentAccountTest {
    Bank bank;
    Branch branch;
    BranchManager m;
    AssistantManager d;
    AssistantManager d1;
    Teller t;
    Teller t1;
    Teller t2;
    Customer c;
    Customer c1;
    Customer c2;
    Customer c3;
    @Before
    public void setUp() throws Exception {
        bank = new Bank();
        branch = new Branch(bank);
        m = new BranchManager(branch);
        d = new AssistantManager(branch);
        d1 = new AssistantManager(branch);
        t = new Teller(branch);
        t1 = new Teller(branch);
        t2 = new Teller(branch);
        c = new Customer(branch);
        c1 = new Customer(branch);
        System.out.println(c.getCustomerId());
        c.createAccountRequest("01",200,new MyDate(2025,6,27));
    }
    @Test
    public void createAccountNumber() {
        assertEquals("120250627",c.getAccounts().get(0).getAccountNumber());
    }
    public void tearDown() throws Exception {}
}
