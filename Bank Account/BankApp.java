import java.util.Scanner;
import java.util.Random;

public class BankApp {

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine().trim();

        System.out.println("==========================================");
        System.out.println("         Welcome to BigBank!");
        System.out.println("==========================================");
        System.out.println("  Hello, " + name + "! Your accounts are ready.\n");

        Random rand = new Random();
        int checkingNum     = 1000 + rand.nextInt(9000);
        int savingsNum      = 1000 + rand.nextInt(9000);
        double checkingBal  = Math.round((100 + rand.nextDouble() * 4900) * 100.0) / 100.0;
        double savingsBal   = Math.round((500 + rand.nextDouble() * 9500) * 100.0) / 100.0;

        BankAccount checking = new BankAccount(name, checkingNum, checkingBal);
        BankAccount savings  = new BankAccount(name, savingsNum,  savingsBal);

        System.out.println("  Your accounts:");
        System.out.printf("  Checking (#%d): $%.2f%n", checking.getAccountNumber(), checking.getBalance());
        System.out.printf("  Savings  (#%d): $%.2f%n%n", savings.getAccountNumber(), savings.getBalance());

        boolean running = true;

        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    checking.printStatement();
                    break;

                case "2":
                    savings.printStatement();
                    break;

                case "3":
                    System.out.println("\n  -- Deposit to Checking --");
                    double depositAmount = getAmount();
                    if (depositAmount > 0) {
                        try {
                            checking.deposit(depositAmount);
                        } catch (IllegalArgumentException e) {
                            System.out.println("  Error: " + e.getMessage());
                        }
                    }
                    break;

                case "4":
                    System.out.println("\n  -- Withdraw from Checking --");
                    double withdrawAmount = getAmount();
                    if (withdrawAmount > 0) {
                        try {
                            checking.withdraw(withdrawAmount);
                        } catch (IllegalArgumentException e) {
                            System.out.println("  Error: " + e.getMessage());
                        }
                    }
                    break;

                case "5":
                    System.out.println("\n  -- Transfer: Checking → Savings --");
                    double transferAmount = getAmount();
                    if (transferAmount > 0) {
                        try {
                            checking.transfer(savings, transferAmount);
                        } catch (IllegalArgumentException e) {
                            System.out.println("  Error: " + e.getMessage());
                        }
                    }
                    break;

                case "6":
                    System.out.println("\n  -- Transfer: Savings → Checking --");
                    double transferBack = getAmount();
                    if (transferBack > 0) {
                        try {
                            savings.transfer(checking, transferBack);
                        } catch (IllegalArgumentException e) {
                            System.out.println("  Error: " + e.getMessage());
                        }
                    }
                    break;

                case "7":
                    System.out.println("  Thank you for banking with BigBank, " + name + ". Goodbye!");
                    running = false;
                    break;

                default:
                    System.out.println("  Invalid option. Please enter 1-7.");
            }
        }

        scanner.close();
    }

    static void printMenu() {
        System.out.println("""

          1. View Checking account
          2. View Savings account
          3. Deposit to Checking
          4. Withdraw from Checking
          5. Transfer Checking → Savings
          6. Transfer Savings → Checking
          7. Exit
        """);
        System.out.print("  Choose an option (1-7): ");
    }

    static double getAmount() {
        System.out.print("  Enter amount ($): ");
        try {
            double amount = Double.parseDouble(scanner.nextLine().trim());
            if (amount <= 0) {
                System.out.println("  Amount must be greater than $0.");
                return -1;
            }
            return amount;
        } catch (NumberFormatException e) {
            System.out.println("  Invalid amount. Please enter a number.");
            return -1;
        }
    }
}
