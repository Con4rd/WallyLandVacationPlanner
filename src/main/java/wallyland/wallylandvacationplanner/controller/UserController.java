/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wallyland.wallylandvacationplanner.controller;
import wallyland.wallylandvacationplanner.model.PurchaseService;


/**
 * Controls all user-related operations in the WallyLand Vacation Planner.
 * This class handles the business logic for multiple use cases related to user interactions.
 */
public class UserController {
    private PurchaseService purchaseService;

    public UserController() {
        this.purchaseService = new PurchaseService();
    }

    /**
     * Processes the purchase of tickets, food, or drinks.
     * Implements Use Case 1: Buy tickets, food, and/or drink.
     *
     * @param userId    The ID of the user making the purchase.
     * @param itemType  The type of item being purchased (e.g., "TICKET", "FOOD", "DRINK").
     * @param itemId    The ID of the specific item being purchased.
     * @param quantity  The number of items to purchase.
     * @return true if the purchase was successful, false otherwise.
     */
    public boolean purchaseItem(String userId, String itemType, String itemId, int quantity) {
        return purchaseService.processPurchase(userId, itemType, itemId, quantity);
    }
}
