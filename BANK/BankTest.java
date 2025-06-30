package BANK;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BankTest  {
    Bank bank;
    Branch branch;
    BranchManager m;
    AssistantManager d;
    AssistantManager d1;
    AssistantManager d2;
    Teller t;
    Teller t1;
    Teller t2;
    Teller t3;
    Teller t4;
    Customer c;
    Customer c2;
    Customer c3;
    Customer c4;
    Customer c5;
    Customer c6;
    Customer c7;
    Customer c8;
    Customer c9;
    Customer c10;
    Customer c11;
    @Before
    public void setUp() {
        bank = new Bank();
        branch = new Branch(bank);
        m = new BranchManager(branch);
        d = new AssistantManager(branch);
        d1 = new AssistantManager(branch);
        d2 = new AssistantManager(branch);
        t = new Teller(branch);
        t1 = new Teller(branch);
        t2 = new Teller(branch);
        t3 = new Teller(branch);
        t4 = new Teller(branch);
        c = new Customer(branch);
        c2 = new Customer(branch);
        c3 = new Customer(branch);
        c4 = new Customer(branch);
        c5 = new Customer(branch);
        c6 = new Customer(branch);
        c7 = new Customer(branch);
        c8 = new Customer(branch);
        c9 = new Customer(branch);
        c10 = new Customer(branch);
        c11 = new Customer(branch);

        t.getMessages().add(new Letter());
        t.getMessages().add(new Letter());
        t.getMessages().add(new Letter());

        t1.getMessages().add(new Letter());

        t2.getMessages().add(new Letter());
        t2.getMessages().add(new Letter());
        t2.getMessages().add(new Letter());
        t2.getMessages().add(new Letter());

        t3.getMessages().add(new Letter());
        t3.getMessages().add(new Letter());
        t3.getMessages().add(new Letter());
        t3.getMessages().add(new Letter());
        t3.getMessages().add(new Letter());

        t4.getMessages().add(new Letter());
        t4.getMessages().add(new Letter());
        t4.getMessages().add(new Letter());
        t4.getMessages().add(new Letter());
        t4.getMessages().add(new Letter());
    }
    @Test
    public void findCustomer() {
        Assert.assertEquals(c10, bank.findCustomer("C9"));
    }

}