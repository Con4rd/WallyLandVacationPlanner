
/**
 * Handles the purchasing of tickets, food, and drinks
 * This service processes transactions and updates inventory
 */      
package wallyland.wallylandvacationplanner.model;

import java.util.*;

public class PurchaseService {
    // Temporary storage for available items
    private Map<String, Object> availableItems; // Use Object to hold different item types
    // Temporary storage for purchase history
    private List<Purchase> purchaseHistory;
    // Temporary storage for cart items
    private List<Purchase> cart; // List to store items added to cart

    public PurchaseService() {
        availableItems = new HashMap<>();
        purchaseHistory = new ArrayList<>();
        cart = new ArrayList<>(); // Initialize cart
        initializeTemporaryItems();
    }

    private void initializeTemporaryItems() {
        // Add some sample items
        availableItems.put("T001", new Ticket("T001", "Adult Day Pass", 89.99, true));
        availableItems.put("T002", new Ticket("T002", "Child Day Pass", 79.99, true));
        availableItems.put("F001", new Food("F001", "Hamburger", 12.99, true));
        availableItems.put("F002", new Food("F002", "Pizza", 14.99, true));
        availableItems.put("D001", new Drinks("D001", "Soft Drink", 4.99, true));
        availableItems.put("D002", new Drinks("D002", "Bottled Water", 3.99, true));
    }

    public boolean processPurchase(String userId, String itemType, String itemId, int quantity) {
        Object item = availableItems.get(itemId);
        if (item == null) {
            return false; // Item does not exist
        }

        double price = 0.0;
        if (item instanceof Ticket) {
            Ticket ticket = (Ticket) item;
            if (!ticket.isValid()) return false;
            price = ticket.getPrice();
        } else if (item instanceof Food) {
            Food food = (Food) item;
            if (!food.isValid()) return false;
            price = food.getPrice();
        } else if (item instanceof Drinks) {
            Drinks drink = (Drinks) item;
            if (!drink.isValid()) return false;
            price = drink.getPrice();
        } else {
            return false; // Unsupported item type
        }

        // Create and store the purchase record
        Purchase purchase = new Purchase(userId, item, quantity);
        purchaseHistory.add(purchase);
      
        return true; // Purchase is processed successfully
    }

    // New method to add items to the cart
    public boolean addToCart(String userId, String itemId, int quantity) {
        Object item = availableItems.get(itemId);
        if (item == null) {
            return false; // Item does not exist
        }

        Purchase cartItem = new Purchase(userId, item, quantity);
        cart.add(cartItem); // Add to the cart
        return true; // Item added to cart successfully
    }

    // New method to get cart items
    public List<Purchase> getCartItems(String userId) {
        return cart.stream()
            .filter(purchase -> purchase.getUserId().equals(userId))
            .toList();
    }

    public List<Object> getAvailableItems(String type) {
        return availableItems.values().stream()
            .filter(item -> {
                if (type.equals("TICKET") && item instanceof Ticket) return true;
                if (type.equals("FOOD") && item instanceof Food) return true;
                if (type.equals("DRINK") && item instanceof Drinks) return true;
                return false;
            })
            .toList();
    }

    //added for cart removal/updates
    public boolean removeFromCart(String userId, Purchase purchaseToRemove) {
        return cart.removeIf(purchase ->
                purchase.getUserId().equals(userId) &&
                        purchase.getItem() == purchaseToRemove.getItem() &&
                        purchase.getQuantity() == purchaseToRemove.getQuantity()
        );
    }

    public double calculateTotal(String itemId, int quantity) {
        Object item = availableItems.get(itemId);
        if (item instanceof Ticket) {
            return ((Ticket) item).getPrice() * quantity;
        } else if (item instanceof Food) {
            return ((Food) item).getPrice() * quantity;
        } else if (item instanceof Drinks) {
            return ((Drinks) item).getPrice() * quantity;
        }
        return 0.0;
    }
    
   public void clearCart(String userId) {
    cart.removeIf(purchase -> purchase.getUserId().equals(userId));
}

    public List<Purchase> getPurchaseHistory(String userId) {
        return purchaseHistory.stream()
            .filter(purchase -> purchase.getUserId().equals(userId))
            .toList();
    }

    // Nested Purchase class
   public static class Purchase {
    private String userId;
    private Object item; // Could be Ticket, Food, or Drink
    private int quantity;

    public Purchase(String userId, Object item, int quantity) {
        this.userId = userId;
        this.item = item;
        this.quantity = quantity;
    }

    public String getUserId() {
        return userId;
    }

    public Object getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    /**
     * Calculates the total price of this purchase based on the item's price and quantity.
     * 
     * @return The total price for the purchase.
     */
    public double getTotalPrice() {
        double itemPrice = 0.0;

        if (item instanceof Ticket) {
            itemPrice = ((Ticket) item).getPrice();
        } else if (item instanceof Food) {
            itemPrice = ((Food) item).getPrice();
        } else if (item instanceof Drinks) {
            itemPrice = ((Drinks) item).getPrice();
        }

        return itemPrice * quantity;
    }

    public double getUnitPrice() {
        if (item instanceof Ticket) {
            return ((Ticket) item).getPrice();
        } else if (item instanceof Food) {
            return ((Food) item).getPrice();
        } else if (item instanceof Drinks) {
            return ((Drinks) item).getPrice();
        }
        return 0.0;
    }
    
    @Override
    public String toString() {
        if (item instanceof Ticket) {
            return ((Ticket) item).getType() + " - Quantity: " + quantity;
        } else if (item instanceof Food) {
            return ((Food) item).getName() + " - Quantity: " + quantity;
        } else if (item instanceof Drinks) {
            return ((Drinks) item).getName() + " - Quantity: " + quantity;
        }
        return "Unknown item - Quantity: " + quantity;
    }
   }

   //added for updates to cart prior to checkout, functionality to remove items (all, some, or none)
    public boolean updateCartItemQuantity(String userId, Purchase purchaseToUpdate, int newQuantity) {
        if (newQuantity < 0) return false;

        for (Purchase purchase : cart) {
            if (purchase.getUserId().equals(userId) && purchase.getItem() == purchaseToUpdate.getItem()) {
                if (newQuantity == 0) {
                    cart.remove(purchase);
                } else {
                    // Create a new Purchase with updated quantity
                    Purchase updatedPurchase = new Purchase(userId, purchase.getItem(), newQuantity);
                    cart.remove(purchase);
                    cart.add(updatedPurchase);
                }
                return true;
            }
        }
        return false;
    }


}

