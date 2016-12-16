package iloveyouboss.test.scratch;

import iloveyouboss.Account;
import iloveyouboss.InsufficientFundsException;
import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;


public class AssertFestTest {


    private Account account;

    @Before
    public void setUp() throws Exception {
        account = new Account("Mrs an account name");
    }

    @Test
    public void hasPositiveBalance() {
        account.deposit(50);
        assertThat(account.hasPositiveBalance()).isTrue();
    }

    @Test
    public void depositIncreasesBalance() {
        int initialBalance = account.getBalance();
        account.deposit(100);
        assertThat(account.getBalance() > initialBalance).isTrue();
    }

    @Test
    public void accountNameStartsWithM() {
        assertThat(account.getAccountName().startsWith("M"));
    }

    @Test
    public void accountNameNotNull() {
        assertThat(account.getAccountName()).isNotNull();
    }

    @Test
    public void insufficientFundsMessageContainsAmount() throws Throwable {
        try {
            account.withdraw(100);
        } catch (InsufficientFundsException e) {
            // e.printStackTrace();
            assertThat(e.getMessage()).contains("amount:- 100");
        }
    }
}
