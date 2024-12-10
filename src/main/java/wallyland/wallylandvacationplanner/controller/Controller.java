package wallyland.wallylandvacationplanner.controller;

import wallyland.wallylandvacationplanner.model.Model;
import wallyland.wallylandvacationplanner.view.View;
import wallyland.wallylandvacationplanner.view.UserInterface;
import wallyland.wallylandvacationplanner.model.PurchaseService;

public class Controller {
    private UserController userController;
    private AdminController adminController;
    private Model model;
    private View view;

    // Add PurchaseService parameter
    public Controller(Model model, PurchaseService purchaseService) {
        this.model = model;
        this.view = new View(purchaseService); // Pass the PurchaseService here

        // Initialize controllers with PurchaseService
        this.userController = new UserController(purchaseService);
        this.adminController = new AdminController(); // Ensure this has the right constructor if needed

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
