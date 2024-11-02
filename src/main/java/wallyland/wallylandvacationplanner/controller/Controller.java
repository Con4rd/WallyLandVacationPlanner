package wallyland.wallylandvacationplanner.controller;

import wallyland.wallylandvacationplanner.model.Model;
import wallyland.wallylandvacationplanner.view.View;
import wallyland.wallylandvacationplanner.view.UserInterface;

public class Controller {
    private UserController userController;
    private AdminController adminController;
    private Model model;
    private View view;
    
    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
        
        // Initialize controllers
        this.userController = new UserController();
        this.adminController = new AdminController();
        
        initializeListeners();
    }
    
    private void initializeListeners() {
        view.getUserInterface().addPurchaseListener(new UserInterface.PurchaseListener() {
            @Override
            public boolean onPurchase(String userId, String itemType, String itemId, int quantity) {
                boolean success = userController.purchaseItem(userId, itemType, itemId, quantity);
                if (success) {
                    view.getUserInterface().showSuccessMessage("Purchase successful!");
                } else {
                    view.getUserInterface().showErrorMessage("Purchase failed. Please try again.");
                }
                return success;
            }
        });
    }
}
