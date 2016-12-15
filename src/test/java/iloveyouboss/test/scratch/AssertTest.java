package iloveyouboss.test.scratch;

import iloveyouboss.Account;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

public class AssertTest {


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
}
