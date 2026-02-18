public class BankAccount {

    private String owner;
    private double balance;
    private int accountNumber;

    public BankAccount(String owner, int accountNumber, double initialDeposit) {
        if (initialDeposit < 0) {
            throw new IllegalArgumentException("Initial deposit cannot be negative.");
        }
        this.owner         = owner;
        this.accountNumber = accountNumber;
        this.balance       = initialDeposit;
    }

    public String getOwner()         { return owner; }
    public int    getAccountNumber() { return accountNumber; }
    public double getBalance()       { return balance; }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive.");
        }
        balance += amount;
        System.out.printf("  ✓ Deposited $%.2f. New balance: $%.2f%n", amount, balance);
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive.");
        }
        if (amount > balance) {
            throw new IllegalArgumentException(
                String.format("Insufficient funds. Balance: $%.2f, Requested: $%.2f", balance, amount)
            );
        }
        balance -= amount;
        System.out.printf("  ✓ Withdrew $%.2f. New balance: $%.2f%n", amount, balance);
    }

    public void transfer(BankAccount target, double amount) {
        System.out.println("  Transferring $" + String.format("%.2f", amount) +
                           " to " + target.getOwner() + "'s account...");
        this.withdraw(amount);
        target.deposit(amount);
    }

    public void printStatement() {
        System.out.println("\n  ================================");
        System.out.println("  Account Summary");
        System.out.println("  ================================");
        System.out.printf("  Owner   : %s%n", owner);
        System.out.printf("  Acct #  : %04d%n", accountNumber);
        System.out.printf("  Balance : $%.2f%n", balance);
        System.out.println("  ================================\n");
    }
}
