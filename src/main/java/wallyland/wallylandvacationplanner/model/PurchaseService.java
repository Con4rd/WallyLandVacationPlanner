/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 * Handles the purchasing of tickets, food, and drinks
 * This service processes transactions and updates inventory
 */    
   

package wallyland.wallylandvacationplanner.model;

import java.util.*;

public class PurchaseService {
    // Temporary storage for available items
    private Map<String, GenericItem> availableItems;
    // Temporary storage for purchase history
    private List<Purchase> purchaseHistory;
    
    public PurchaseService() {
        availableItems = new HashMap<>();
        purchaseHistory = new ArrayList<>();
        initializeTemporaryItems();
    }
    
    private void initializeTemporaryItems() {
        // Add some sample items
        availableItems.put("T001", new GenericItem("T001", "Adult Day Pass", "TICKET", 89.99));
        availableItems.put("T002", new GenericItem("T002", "Child Day Pass", "TICKET", 79.99));
        availableItems.put("F001", new GenericItem("F001", "Hamburger", "FOOD", 12.99));
        availableItems.put("F002", new GenericItem("F002", "Pizza", "FOOD", 14.99));
        availableItems.put("D001", new GenericItem("D001", "Soft Drink", "DRINK", 4.99));
        availableItems.put("D002", new GenericItem("D002", "Bottled Water", "DRINK", 3.99));
    }
    
    public boolean processPurchase(String userId, String itemType, String itemId, int quantity) {
        GenericItem item = availableItems.get(itemId);
        if (item == null || !item.getType().equals(itemType)) {
            return false;
        }
        
        // Create and store the purchase record
        Purchase purchase = new Purchase(userId, item, quantity);
        purchaseHistory.add(purchase);
        
        // For now, always return true as we're not checking inventory
        return true;
    }
    
    public List<GenericItem> getAvailableItems(String type) {
        return availableItems.values().stream()
            .filter(item -> item.getType().equals(type))
            .toList();
    }
    
    public double calculateTotal(String itemId, int quantity) {
        GenericItem item = availableItems.get(itemId);
        if (item != null) {
            return item.getPrice() * quantity;
        }
        return 0.0;
    }
    
    public List<Purchase> getPurchaseHistory(String userId) {
        return purchaseHistory.stream()
            .filter(purchase -> purchase.getUserId().equals(userId))
            .toList();
    }
}

// Temporary class to represent any item before specific implementations
class GenericItem {
    private String id;
    private String name;
    private String type; // "TICKET", "FOOD", or "DRINK"
    private double price;
    
    public GenericItem(String id, String name, String type, double price) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.price = price;
    }
    
    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getType() { return type; }
    public double getPrice() { return price; }
    
    @Override
    public String toString() {
        return name + " ($" + String.format("%.2f", price) + ")";
    }
}

// Class to store purchase records
class Purchase {
    private String userId;
    private GenericItem item;
    private int quantity;
    private Date purchaseDate;
    
    public Purchase(String userId, GenericItem item, int quantity) {
        this.userId = userId;
        this.item = item;
        this.quantity = quantity;
        this.purchaseDate = new Date();
    }
    
    // Getters
    public String getUserId() { return userId; }
    public GenericItem getItem() { return item; }
    public int getQuantity() { return quantity; }
    public Date getPurchaseDate() { return purchaseDate; }
    public double getTotal() { return item.getPrice() * quantity; }
}
