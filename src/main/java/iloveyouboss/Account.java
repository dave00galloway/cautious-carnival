package iloveyouboss;


public class Account {
    private final String accountName;
    private int balance;

    public Account(String accountName) {

        this.accountName = accountName;
    }

    public void deposit(int amount) {

        this.balance += amount;
    }

    public boolean hasPositiveBalance() {
        return balance > 0;
    }

    public int getBalance() {
        return balance;
    }

    public String getAccountName() {
        return accountName;
    }

    public int withdraw(int amount) throws InsufficientFundsException {
        if (balance >= amount) {
            balance -= amount;
            return amount;
        }
        throw new InsufficientFundsException("amount:- " + amount + " is greater than balance: " + balance);
    }
}
