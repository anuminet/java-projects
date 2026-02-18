import java.util.Arrays;

public class PizzaBuilder {

    private int selectedSize;
    private String selectedCrust;
    private String[] selectedToppings;  // This is an array!
    private boolean hasExtraCheese;

    public boolean hasTopping;
    public double calculatePrice;
    public double basePrice;
    public double toppingPrice;
    public double cheesePrice;

    public PizzaBuilder(int size, String crust, String[] toppings, boolean extraCheese) {
        this.selectedSize = size;
        this.selectedCrust = crust;
        this.selectedToppings = toppings;
        this.hasExtraCheese = extraCheese;
    }
    public int getSelectedSize() {
        return selectedSize;
    }
    public String getSelectedCrust() {
        return selectedCrust;
    }
    public String[] getSelectedToppings() {
        return selectedToppings;
    }
    public boolean getHasExtraCheese() {
        return hasExtraCheese;
    }
    public int getToppingCount() {
        return selectedToppings.length;
    }
    
    public void printToppings() {
        System.out.println("Your toppings:");
        for (int i = 0; i < selectedToppings.length; i++) {
            System.out.println("  " + (i + 1) + ". " + selectedToppings[i]);
        }
    }

    public boolean hasTopping(String toppingName) {

        for (int i = 0; i < selectedToppings.length; i++) {
            if (selectedToppings[i].equalsIgnoreCase(toppingName)) {
                return true;
            }
        }
        return false;
    }
    // Calculate price based on size and toppings
    public double calculatePrice() {
        double basePrice;
        if (selectedSize == 8) {
            basePrice = 8.99;
        } else if (selectedSize == 14) {
            basePrice = 12.99;
        } else if (selectedSize == 16) {
            basePrice = 15.99;
        } else if (selectedSize == 18) {
            basePrice = 18.99;
        } else {
            basePrice = 10.99;
        }
        toppingPrice = selectedToppings.length * 1.50;
        cheesePrice = hasExtraCheese ? 2.00 : 0.00;
        
        return basePrice + toppingPrice + cheesePrice;
    }
    public String toString() {
        String result = "=== YOUR PIZZA ===\n";
        result = result + "Size: " + selectedSize + " inches\n";
        result = result + "Crust: " + selectedCrust + "\n";
        result = result + "Toppings (" + selectedToppings.length + "): " + Arrays.toString(selectedToppings) + "\n";
        result = result + "Extra Cheese: " + (hasExtraCheese ? "Yes" : "No") + "\n";
        result = result + "Total Price: $" + calculatePrice();
        return result;
    }
    public static String sizeOptions() {
        return "| Available Sizes:                           |\n|\t\t  (Kids) 8 inches:  $8.99    |\n|\t\t        14 inches:  $12.99   |\n|\t\t        16 inches:  $15.99   |\n|\t\t        18 inches:  $18.99   |\n";
    }
    public static String crustOptions() {
        return "| Available Crusts:                          |\n|\t\t      Thin                   |\n|\t\t      Deep Dish              |\n|\t\t      Regular                |\n|\t\t      Stuffed                |\n";
    }
    public static String toppingOptions() {
        return "| Available Toppings:                        |\n|\t\t      Pepperoni              |\n|\t\t      Mushrooms              |\n|\t\t      Onions                 |\n|\t\t      Sausage                |\n|\t\t      Bacon                  |\n|\t\t      Ham                    |\n|\t\t      Black olives           |\n|\t\t      Green peppers          |\n|\t\t      Pineapple              |\n|\t\t      Spinach                |\n|============================================|";
    }
}