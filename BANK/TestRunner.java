package BANK;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.junit.runner.JUnitCore;
import org.junit.runner.notification.Failure;

import org.junit.runner.Result;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;

public class TestRunner {
    public static void main(String[] args) throws InitializationError {
        TestSuite suite = new TestSuite(BankTest.class,BranchTest.class,CurrentAccountTest.class);
        Result result = new JUnitCore().run(suite);
        for(Failure f : result.getFailures()) {
            System.out.println(f.getMessage());
        }
        System.out.println(result.wasSuccessful());
    }
}
