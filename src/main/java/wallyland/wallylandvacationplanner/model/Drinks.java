package wallyland.wallylandvacationplanner.model;

/**
 * Represents a drink.
 * This class stores and manages drinks details.
 */
public class Drinks {
    private String drinkId;
    private String name;
    private double price;
    private boolean isValid;

    public Drinks(String drinkId, String name, double price, boolean isValid) {
        this.drinkId = drinkId;
        this.name = name;
        this.price = price;
        this.isValid = isValid;
    }

    public String getDrinkId() { return drinkId; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public boolean isValid() { return isValid; }

    @Override
    public String toString() {
        return name + " ($" + String.format("%.2f", price) + ") " + (isValid ? "Available" : "Out of Stock");
    }
}

