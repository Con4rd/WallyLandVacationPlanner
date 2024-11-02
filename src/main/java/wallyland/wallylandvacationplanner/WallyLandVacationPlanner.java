package wallyland.wallylandvacationplanner;

import wallyland.wallylandvacationplanner.controller.Controller;
import wallyland.wallylandvacationplanner.model.Model;
import wallyland.wallylandvacationplanner.view.View;

public class WallyLandVacationPlanner {
    private Model model;
    private View view;
    private Controller controller;
    
    public WallyLandVacationPlanner() {
        model = new Model();
        view = new View();
        controller = new Controller(model, view);
        
        // Make the main window visible
        view.getUserInterface().setVisible(true);
    }
    
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            new WallyLandVacationPlanner();
        });
    }
}
