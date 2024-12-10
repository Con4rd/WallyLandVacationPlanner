package wallyland.wallylandvacationplanner.model;

import java.util.ArrayList;
import java.util.List;

public class Model {
    private List<User> users;
    private PurchaseService purchaseService;
    
    public Model() {
        users = new ArrayList<>();
        purchaseService = new PurchaseService();
        // Add some sample users for testing
        initializeSampleData();
    }
    
    private void initializeSampleData() {
        users.add(new User("U001", "john_doe", "john@example.com"));
        users.add(new User("U002", "jane_doe", "jane@example.com"));
    }
    
    public User getUserById(String userId) {
        return users.stream()
                   .filter(user -> user.getUserId().equals(userId))
                   .findFirst()
                   .orElse(null);
    }
    
    public void addUser(User user) {
        users.add(user);
    }
    
    public PurchaseService getPurchaseService() {
        return purchaseService;
    }
    
    // Core business logic methods
    public void processTransaction() {
        // To be implemented
    }
    
    public void validatePayment() {
        // To be implemented
    }
    
  
}
