package wallyland.wallylandvacationplanner;

import wallyland.wallylandvacationplanner.controller.Controller;
import wallyland.wallylandvacationplanner.model.Model;
import wallyland.wallylandvacationplanner.view.View;
import wallyland.wallylandvacationplanner.model.PurchaseService;

public class WallyLandVacationPlanner {
    private Model model;
    private View view;
    private Controller controller;
    
    public WallyLandVacationPlanner() {
        model = new Model();
        PurchaseService purchaseService = new PurchaseService(); // Initialize PurchaseService
        view = new View(purchaseService); // Pass PurchaseService to View
        controller = new Controller(model, purchaseService); // Pass PurchaseService to Controller
        
        // Make the main window visible
        view.getUserInterface().setVisible(true);
    }
    
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            new WallyLandVacationPlanner();
        });
    }
}
