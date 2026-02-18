import java.util.Random;
import java.util.Scanner;

public class NumberGuessingGame {

    static final int MIN = 1;
    static final int MAX = 100;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random   = new Random();

        System.out.println("========================================");
        System.out.println("        Number Guessing Game");
        System.out.println("========================================");
        System.out.println("I'm thinking of a number between " + MIN + " and " + MAX + ".");

        boolean playAgain = true;

        while (playAgain) {
            int secretNumber = random.nextInt(MAX - MIN + 1) + MIN;
            int attempts     = 0;
            boolean guessed  = false;

            while (!guessed) {
                System.out.print("\nYour guess: ");

                if (!scanner.hasNextInt()) {
                    System.out.println("  Please enter a whole number.");
                    scanner.next();
                    continue;
                }

                int guess = scanner.nextInt();
                attempts++;

                if (guess < MIN || guess > MAX) {
                    System.out.println("  Out of range! Guess between " + MIN + " and " + MAX + ".");
                } else if (guess < secretNumber) {
                    System.out.println("  Too low! Try higher.");
                } else if (guess > secretNumber) {
                    System.out.println("  Too high! Try lower.");
                } else {
                    System.out.println("\n  Correct! The number was " + secretNumber + ".");
                    System.out.println("  You got it in " + attempts + " attempt" + (attempts == 1 ? "" : "s") + "!");
                    guessed = true;
                }
            }

            System.out.print("\nPlay again? (y/n): ");
            String answer = scanner.next().trim().toLowerCase();
            playAgain = answer.equals("y") || answer.equals("yes");
        }

        System.out.println("Thanks for playing!");
        scanner.close();
    }
}
