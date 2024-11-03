/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wallyland.wallylandvacationplanner.controller;

public class UserController {
     private PurchaseService purchaseService;

    // Constructor to initialize PurchaseService
    public UserController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    public void handleUserRequests(String userId) {
        // Placeholder for handling different user requests
        System.out.println("Handling user requests for user: " + userId);
        // Logic to determine which user action to take could go here
    }

    public void processBookings(String userId, String itemType, String itemId, int quantity) {
        if (purchaseItem(userId, itemType, itemId, quantity)) {
            System.out.println("Booking processed successfully for user: " + userId);
        } else {
            System.out.println("Booking failed for user: " + userId);
        }
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
        // Validate input parameters
        if (userId == null || userId.isEmpty()) {
            System.out.println("Invalid user ID.");
            return false;
        }

        if (itemType == null || itemType.isEmpty()) {
            System.out.println("Invalid item type.");
            return false;
        }

        if (quantity <= 0) {
            System.out.println("Quantity must be greater than zero.");
            return false;
        }

        // Call the PurchaseService to process the purchase
        boolean purchaseSuccessful = purchaseService.processPurchase(userId, itemType, itemId, quantity);

        // Log the outcome
        if (purchaseSuccessful) {
            System.out.println("Purchase successful for user " + userId);
        } else {
            System.out.println("Purchase failed for user " + userId);
        }

        return purchaseSuccessful;
    }

    public void managePurchases(String userId) {
        // Display purchase history for the user
        List<Purchase> userPurchases = purchaseService.getPurchaseHistory(userId);
        if (userPurchases.isEmpty()) {
            System.out.println("No purchases found for user: " + userId);
        } else {
            System.out.println("Purchase history for user: " + userId);
            for (Purchase purchase : userPurchases) {
                System.out.println(purchase);
            }
        }
    }

    public void handleScheduling() {
        // Logic for handling user scheduling can be implemented here
        System.out.println("Handling user scheduling.");
    }

    public void provideRealTimeUpdates() {
        // Logic to provide real-time updates (e.g., updates on purchases, bookings)
        System.out.println("Providing real-time updates.");
    }
}
