package wallyland.wallylandvacationplanner.view;

import wallyland.wallylandvacationplanner.model.PurchaseService;
import wallyland.wallylandvacationplanner.model.Food;
import wallyland.wallylandvacationplanner.model.Drinks;
import wallyland.wallylandvacationplanner.model.Ticket;
import wallyland.wallylandvacationplanner.controller.ActivityManager;
import wallyland.wallylandvacationplanner.view.ActivityPanel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import wallyland.wallylandvacationplanner.model.PurchaseService.Purchase; // Import Purchase class

public class UserInterface extends JFrame {

    private void openAdminInterface() {
    AdminInterface adminInterface = new AdminInterface();
    adminInterface.setVisible(true);
}
    // Interface for purchase events


    public interface PurchaseListener {
        boolean onPurchase(String userId, String itemType, String itemId, int quantity);
    }

    // UI Components
    private ActivityManager activityManager;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JMenuBar menuBar;

    // Event listener
    private PurchaseListener purchaseListener;

    // Reference to PurchaseService
    private PurchaseService purchaseService;

    // Payment attempt counter
    private int paymentAttemptCount = 0; // Moved here

    public UserInterface(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
        this.activityManager = new ActivityManager();
        initializeComponents();
        setupMenuBar();
        setupLayout();
        displayUserDashboard();
    }

    private void initializeComponents() {
    setTitle("WallyLand Vacation Planner");
    setSize(800, 600);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null); // Center window
    setResizable(false); // Prevent resizing
    cardLayout = new CardLayout();
    mainPanel = new JPanel(cardLayout);
}

    private void setupMenuBar() {
        menuBar = new JMenuBar();

        // Create Shop menu
        JMenu shopMenu = new JMenu("Shop");
        JMenuItem ticketsItem = new JMenuItem("Tickets");
        JMenuItem foodItem = new JMenuItem("Food & Drinks");
        shopMenu.add(ticketsItem);
        shopMenu.add(foodItem);

        // Add actions to menu items
        ticketsItem.addActionListener(e -> showPurchaseForm("TICKET"));
        foodItem.addActionListener(e -> showPurchaseForm("FOOD"));

        // Create Cart menu
        JMenu cartMenu = new JMenu("Cart");
        JMenuItem viewCartItem = new JMenuItem("View Cart");
        JMenuItem checkoutItem = new JMenuItem("Checkout");
        cartMenu.add(viewCartItem);
        cartMenu.add(checkoutItem);

        // Add actions to cart items
        viewCartItem.addActionListener(e -> showCart());
        checkoutItem.addActionListener(e -> processCheckout());

        // Add a menu item in setupMenuBar():
        JMenu activitiesMenu = new JMenu("Activities");
        JMenuItem manageActivities = new JMenuItem("Manage Activities");
        manageActivities.addActionListener(e -> showActivityManagement());

        // Create Admin menu (new menu for Admin)
        JMenu adminMenu = new JMenu("Admin");
        JMenuItem openAdminInterfaceItem = new JMenuItem("Open Admin Interface");
        openAdminInterfaceItem.addActionListener(e -> openAdminInterface());
        adminMenu.add(openAdminInterfaceItem);

        menuBar.add(shopMenu);
        menuBar.add(cartMenu);
        menuBar.add(activitiesMenu);
        menuBar.add(adminMenu);  // Add Admin menu to the menu bar
        setJMenuBar(menuBar);
        activitiesMenu.add(manageActivities);
        
        
    }

    private void setupLayout() {
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);
    }

    public void displayUserDashboard() {
        JPanel dashboardPanel = new JPanel(new BorderLayout());

        JLabel welcomeLabel = new JLabel("Welcome to WallyLand!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));

        JLabel instructionsLabel = new JLabel(
            "<html><center>Use the menu above to:<br>" +
            "â€¢ Browse and purchase tickets<br>" +
            "â€¢ Order food and drinks<br>" +
            "â€¢ View your shopping cart</center></html>",
            SwingConstants.CENTER
        );

        dashboardPanel.add(welcomeLabel, BorderLayout.NORTH);
        dashboardPanel.add(instructionsLabel, BorderLayout.CENTER);

        mainPanel.add(dashboardPanel, "DASHBOARD");
        cardLayout.show(mainPanel, "DASHBOARD");
    }

    public void showPurchaseForm(String itemType) {
    JPanel purchasePanel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 5, 5, 5);

    // Item Type Selection
    gbc.gridx = 0; gbc.gridy = 0;
    purchasePanel.add(new JLabel("Item Type:"), gbc);

    gbc.gridx = 1;
    JComboBox<String> itemTypeCombo = new JComboBox<>(new String[]{"TICKET", "FOOD", "DRINK"});
    itemTypeCombo.setSelectedItem(itemType); // Set initial selection
    purchasePanel.add(itemTypeCombo, gbc);

    // Item Selection
    gbc.gridx = 0; gbc.gridy = 1;
    purchasePanel.add(new JLabel("Item:"), gbc);

    gbc.gridx = 1;
    JComboBox<Object> itemCombo = new JComboBox<>();
    updateItemCombo(itemCombo, itemType);
    purchasePanel.add(itemCombo, gbc);

    itemTypeCombo.addActionListener(e -> {
        String selectedType = (String) itemTypeCombo.getSelectedItem();
        updateItemCombo(itemCombo, selectedType);
    });

        // Quantity Selection
        gbc.gridx = 0; gbc.gridy = 2;
        purchasePanel.add(new JLabel("Quantity:"), gbc);

        gbc.gridx = 1;
        JSpinner quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
        purchasePanel.add(quantitySpinner, gbc);

        // Total Price Display
        gbc.gridx = 0; gbc.gridy = 3;
        JLabel totalLabel = new JLabel("Total: $0.00");
        purchasePanel.add(totalLabel, gbc);

        // Update total when quantity or item changes
        Runnable updateTotal = () -> {
            Object selectedItem = itemCombo.getSelectedItem();
            double total = 0.0;
            if (selectedItem instanceof Ticket) {
                total = ((Ticket) selectedItem).getPrice() * (Integer) quantitySpinner.getValue();
            } else if (selectedItem instanceof Food) {
                total = ((Food) selectedItem).getPrice() * (Integer) quantitySpinner.getValue();
            } else if (selectedItem instanceof Drinks) {
                total = ((Drinks) selectedItem).getPrice() * (Integer) quantitySpinner.getValue();
            }
            totalLabel.setText(String.format("Total: $%.2f", total));
        };

        itemCombo.addActionListener(e -> updateTotal.run());
        quantitySpinner.addChangeListener(e -> updateTotal.run());

        // Add to Cart Button
        gbc.gridx = 0; gbc.gridy = 4;
        JButton addToCartButton = new JButton("Add to Cart");
        addToCartButton.addActionListener(e -> {
            Object selectedItem = itemCombo.getSelectedItem();
            if (selectedItem != null) {
                String itemId = "";
                if (selectedItem instanceof Ticket) {
                    itemId = ((Ticket) selectedItem).getTicketId();
                } else if (selectedItem instanceof Food) {
                    itemId = ((Food) selectedItem).getFoodId();
                } else if (selectedItem instanceof Drinks) {
                    itemId = ((Drinks) selectedItem).getDrinkId();
                }
                purchaseService.addToCart("U001", itemId, (Integer) quantitySpinner.getValue());
                showSuccessMessage("Item added to cart!");
            }
        });
        purchasePanel.add(addToCartButton, gbc);

      // Purchase History Button (Updated)
    //gbc.gridx = 1; // Move to next column for Purchase History button
    //JButton purchaseHistoryButton = new JButton("View Purchase History");
   // purchaseHistoryButton.addActionListener(e -> {
        // Fetch and display the user's purchase history
       // showPurchaseHistory("U001");  // Pass user ID dynamically if needed
    //});
    //purchasePanel.add(purchaseHistoryButton, gbc);

    mainPanel.add(purchasePanel, "PURCHASE");
    cardLayout.show(mainPanel, "PURCHASE");
}

private void showPurchaseHistory(String userId) {
    List<Purchase> purchaseHistory = purchaseService.getPurchaseHistory(userId); // Assuming PurchaseService has this method

    JPanel historyPanel = new JPanel(new BorderLayout());
    JTextArea historyTextArea = new JTextArea(15, 50);
    historyTextArea.setEditable(false);

    if (purchaseHistory.isEmpty()) {
        historyTextArea.setText("No previous purchases.");
    } else {
        StringBuilder historyContents = new StringBuilder("Your Purchase History:\n\n");
        for (Purchase purchase : purchaseHistory) {
            historyContents.append(purchase.toString()).append("\n");
        }
        historyTextArea.setText(historyContents.toString());
    }

    historyPanel.add(new JScrollPane(historyTextArea), BorderLayout.CENTER);
    mainPanel.add(historyPanel, "PURCHASE_HISTORY");
    cardLayout.show(mainPanel, "PURCHASE_HISTORY");
    }
    public void showCart() {
    String userId = "U001"; // Ideally, this should be dynamic
    List<Purchase> purchases = purchaseService.getCartItems(userId);

    JPanel cartPanel = new JPanel(new BorderLayout());
    JTextArea cartTextArea = new JTextArea(15, 50);
    cartTextArea.setEditable(false);

    JButton checkoutButton = new JButton("Proceed to Checkout");

    if (purchases.isEmpty()) {
        cartTextArea.setText("Your cart is empty.");
        checkoutButton.setEnabled(false); // Disable button if cart is empty
    } else {
        StringBuilder cartContents = new StringBuilder("Items in your cart:\n\n");
        for (Purchase purchase : purchases) {
            cartContents.append(purchase.toString()).append("\n");
        }
        cartTextArea.setText(cartContents.toString());
        checkoutButton.setEnabled(true); // Enable button if cart has items
    }

    cartPanel.add(new JScrollPane(cartTextArea), BorderLayout.CENTER);
    checkoutButton.addActionListener(e -> processCheckout());
    cartPanel.add(checkoutButton, BorderLayout.SOUTH);

    mainPanel.add(cartPanel, "CART");
    cardLayout.show(mainPanel, "CART");
}

    private void updateItemCombo(JComboBox<Object> itemCombo, String type) {
        itemCombo.removeAllItems();
        // Update items from PurchaseService
        if (type.equals("TICKET")) {
            for (Object item : purchaseService.getAvailableItems("TICKET")) {
                itemCombo.addItem(item);
            }
        } else if (type.equals("FOOD")) {
            for (Object item : purchaseService.getAvailableItems("FOOD")) {
                itemCombo.addItem(item);
            }
        } else if (type.equals("DRINK")) {
            for (Object item : purchaseService.getAvailableItems("DRINK")) {
                itemCombo.addItem(item);
            }
        }
    }

    public void displaySchedule() {
        JPanel schedulePanel = new JPanel(new BorderLayout());
        schedulePanel.add(new JLabel("Schedule will appear here", SwingConstants.CENTER));

        mainPanel.add(schedulePanel, "SCHEDULE");
        cardLayout.show(mainPanel, "SCHEDULE");
    }

    // Panel management methods
    public void addPanel(JPanel panel, String name) {
        mainPanel.add(panel, name);
    }

    public void showPanel(String name) {
        cardLayout.show(mainPanel, name);
    }

    // Purchase handling methods
    public void addPurchaseListener(PurchaseListener listener) {
        this.purchaseListener = listener;
    }

    protected void triggerPurchase(String userId, String itemType, String itemId, int quantity) {
        if (purchaseListener != null) {
            if (purchaseListener.onPurchase(userId, itemType, itemId, quantity)) {
                showSuccessMessage("Purchase successful!");
            } else {
                showErrorMessage("Purchase failed. Please try again.");
            }
        }
    }

    public void showSuccessMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

 private void processCheckout() {
    List<Purchase> cartItems = purchaseService.getCartItems("U001");
    double totalPrice = cartItems.stream().mapToDouble(Purchase::getTotalPrice).sum();
    
     // Check if cart is empty
    if (totalPrice == 0) {
        showErrorMessage("Your cart is empty. Please add items to your cart before proceeding.");
        return; // Exit the method if the cart is empty
    }
    
    // Include total price in the dialog prompt
    String[] paymentOptions = {"Credit", "Debit", "App"};
    String paymentMethod = (String) JOptionPane.showInputDialog(
        this,
        String.format("Select Payment Method:\nTotal Price: $%.2f", totalPrice), // Display total price in the prompt
        "Checkout",
        JOptionPane.QUESTION_MESSAGE,
        null,
        paymentOptions,
        paymentOptions[0] // default selection
    );


    if (paymentMethod != null) { // User didn't cancel
        paymentAttemptCount++; // Increment the payment attempt count

        boolean paymentSuccess = (paymentAttemptCount % 3) != 0;
        if (paymentSuccess) {

            // Clear the cart after successful payment
            purchaseService.clearCart("U001");

            showSuccessMessage(String.format(
                "Payment successful! Total: $%.2f\nThank you for your purchase.", totalPrice));
        } else {
            showErrorMessage("Payment failed. Please try again.");
        }
    } else {
        showErrorMessage("Checkout canceled.");
    }
}

    public void showActivityManagement() {
        ActivityPanel activityPanel = new ActivityPanel(activityManager);
        mainPanel.add(activityPanel, "ACTIVITIES");
        cardLayout.show(mainPanel, "ACTIVITIES");
    }
}

