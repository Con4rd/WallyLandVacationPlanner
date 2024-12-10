package wallyland.wallylandvacationplanner.model;

/**
 * Represents a food.
 * This class stores and manages food details.
 */
public class Food {
    private String foodId;
    private String name;
    private double price;
    private boolean isValid;

    public Food(String foodId, String name, double price, boolean isValid) {
        this.foodId = foodId;
        this.name = name;
        this.price = price;
        this.isValid = isValid;
    }

    public String getFoodId() { return foodId; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public boolean isValid() { return isValid; }

    @Override
    public String toString() {
        return name + " ($" + String.format("%.2f", price) + ") " + (isValid ? "Available" : "Out of Stock");
    }
}
