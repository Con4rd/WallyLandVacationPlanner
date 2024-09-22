/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wallyland.wallylandvacationplanner.model;

/**
 * Represents a food.
 * This class stores and manages food details.
 */
public class Food {
    private String foodId;
    private String name;
    private boolean isValid;
    private double price;

    /**
     * Constructs a new food.
     *
     * @param foodId The unique identifier of the food.
     * @param name   The name of the food.
     * @param price  The price of the food.
     * @param isValid  The validity of the food(if it's available or out of stock).
     */
    public Food(String foodId, String name,double price, boolean isValid) {
        this.foodId = foodId;
        this.name = name;
        this.price = price;
        this.isValid = isValid;
    }

    // Getters and Setters
    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
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