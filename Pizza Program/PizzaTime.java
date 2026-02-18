import java.util.Scanner;

public class PizzaTime {
    public static void main(String[] args) {
        Scanner kbd = new Scanner(System.in);
        
        System.out.println();        
        System.out.print("|========== WELCOME TO PIZZA PLAZA! =========|\n");
        System.out.print(PizzaBuilder.sizeOptions());
        System.out.print(PizzaBuilder.crustOptions());
        System.out.print(PizzaBuilder.toppingOptions());

        System.out.print("\n\nEnter your desired pizza size (8, 14, 16, or 18): ");
        int userSize = kbd.nextInt();
        kbd.nextLine();
        
        System.out.print("\nEnter crust type: ");
        String userCrust = kbd.nextLine();
        
        System.out.print("\nAll toppings cost $1.50 each. \n");
        System.out.print("\nHow many toppings do you want? (may not exceed 10): ");
        int numToppings = kbd.nextInt();
        kbd.nextLine();
        
        String[] userToppings = new String[numToppings];

        for (int i = 0; i < numToppings; i++) {
            System.out.print("\nEnter topping #" + (i + 1) + ": ");
            userToppings[i] = kbd.nextLine();
            while (!PizzaBuilder.toppingOptions().toLowerCase().contains(userToppings[i].toLowerCase())) {
                System.out.print("Invalid topping. Please enter a valid topping #" + (i + 1) + ": ");
                userToppings[i] = kbd.nextLine();
            }
        }
        
        System.out.print("\nDo you want extra cheese for only $2.00 more?! (yes/no): ");
        String cheeseResponse = kbd.nextLine().toLowerCase();
        boolean userExtraCheese = cheeseResponse.equals("yes");
        
        PizzaBuilder myPizza = new PizzaBuilder(userSize, userCrust, userToppings, userExtraCheese);
        
        System.out.println("\n" + myPizza.toString());
         
        kbd.close();
    }
}