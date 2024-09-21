package wallyland.wallylandvacationplanner;

import wallyland.wallylandvacationplanner.controller.UserController;

public class WallyLandVacationPlanner {
    public static void main(String[] args) {
        UserController userController = new UserController();
        boolean purchaseResult = userController.purchaseItem("user123", "TICKET", "day_pass", 2);
        System.out.println("Purchase result: " + (purchaseResult ? "Successful" : "Failed"));
    }
}
