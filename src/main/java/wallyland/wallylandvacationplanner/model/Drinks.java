/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wallyland.wallylandvacationplanner.model;

/**
 * Represents a drink.
 * This class stores and manages drinks details.
 */
public class Drinks {
    private String drinkId;
    private String name;
    private boolean isValid;
    private double price;

    /**
     * Constructs a new food.
     *
     * @param drinkId The unique identifier of the drink.
     * @param name   The name of the drink.
     * @param price  The price of the drink.
     * @param isValid  The validity of the drink(if it's available or out of stock).
     */
    public Drinks(String drinkId, String name,double price, boolean isValid) {
        this.drinkId = drinkId;
        this.name = name;
        this.price = price;
        this.isValid = isValid;
    }

    // Getters and Setters
    public String getFoodId() {
        return drinkId;
    }

    public void setFoodId(String foodId) {
        this.drinkId = foodId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isIsValid() {
        return isValid;
    }

    public void setIsValid(boolean isValid) {
        this.isValid = isValid;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}