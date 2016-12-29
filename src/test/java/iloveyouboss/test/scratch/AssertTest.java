package iloveyouboss.test.scratch;

import iloveyouboss.Account;
import iloveyouboss.InsufficientFundsException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertTrue;

public class AssertTest {


    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private Account account;

    @Before
    public void setUp() throws Exception {
        account = new Account("Mrs an account name");
    }

    @Test
    public void hasPositiveBalance() {
        account.deposit(50);
        assertTrue(account.hasPositiveBalance());
    }

    @Test
    public void depositIncreasesBalance() {
        int initialBalance = account.getBalance();
        account.deposit(100);
        assertTrue(account.getBalance() > initialBalance);
    }

    @Test
    public void accountNameStartsWithM() {
        assertThat(account.getAccountName(), startsWith("M"));
    }

    @Test
    public void accountNameNotNull() {
        assertThat(account.getAccountName(), is(not(nullValue())));
    }

    @Test(expected = InsufficientFundsException.class)
    public void throwsWhenWithDrawingTooMuch() throws Throwable {
        account.withdraw(100);
    }

    @Test
    public void insufficientFundsMessageContainsAmout() throws Throwable {
        thrown.expect(InsufficientFundsException.class);
        thrown.expectMessage("amount:- 100");
        account.withdraw(100);
    }

    @Test
    @Ignore
    public void readsFromTestFile() throws Throwable {
        String filename = "test.txt";
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        writer.write("test data");
        writer.close();
        // ...
        Files.deleteIfExists(new File(filename).toPath());
    }
}
