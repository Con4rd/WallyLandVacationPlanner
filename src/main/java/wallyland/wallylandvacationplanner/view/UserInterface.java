/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wallyland.wallylandvacationplanner.view;

import javax.swing.*;
import java.awt.*;

public class UserInterface extends JFrame {
    // Interface for purchase events
    public interface PurchaseListener {
        boolean onPurchase(String userId, String itemType, String itemId, int quantity);
    }
    
    // UI Components
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JMenuBar menuBar;
    
    // Event listener
    private PurchaseListener purchaseListener;
    
    public UserInterface() {
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
        ticketsItem.addActionListener(e -> showPurchaseForm());
        foodItem.addActionListener(e -> displayUserDashboard());
        
        // Create Cart menu
        JMenu cartMenu = new JMenu("Cart");
        JMenuItem viewCartItem = new JMenuItem("View Cart");
        JMenuItem checkoutItem = new JMenuItem("Checkout");
        cartMenu.add(viewCartItem);
        cartMenu.add(checkoutItem);
        
        // Add actions to cart items
        viewCartItem.addActionListener(e -> showPanel("CART"));
        checkoutItem.addActionListener(e -> processCheckout());
        
        menuBar.add(shopMenu);
        menuBar.add(cartMenu);
        setJMenuBar(menuBar);
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);
    }
    
    // Methods from class diagram
    public void displayUserDashboard() {
        JPanel dashboardPanel = new JPanel(new BorderLayout());
        
        JLabel welcomeLabel = new JLabel("Welcome to WallyLand!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        
        JLabel instructionsLabel = new JLabel(
            "<html><center>Use the menu above to:<br>" +
            "• Browse and purchase tickets<br>" +
            "• Order food and drinks<br>" +
            "• View your shopping cart</center></html>",
            SwingConstants.CENTER
        );
        
        dashboardPanel.add(welcomeLabel, BorderLayout.NORTH);
        dashboardPanel.add(instructionsLabel, BorderLayout.CENTER);
        
        mainPanel.add(dashboardPanel, "DASHBOARD");
        cardLayout.show(mainPanel, "DASHBOARD");
    }
    
    public void showBookingForm() {
        JPanel bookingPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Booking form components will be added here
        
        mainPanel.add(bookingPanel, "BOOKING");
        cardLayout.show(mainPanel, "BOOKING");
    }
    
    public void displayRealTimeUpdates() {
        JPanel updatesPanel = new JPanel(new BorderLayout());
        updatesPanel.add(new JLabel("Real-time updates will appear here", SwingConstants.CENTER));
        
        mainPanel.add(updatesPanel, "UPDATES");
        cardLayout.show(mainPanel, "UPDATES");
    }
    
    public void showPurchaseForm() {
    JPanel purchasePanel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 5, 5, 5);
    
    // Item Type Selection
    gbc.gridx = 0; gbc.gridy = 0;
    purchasePanel.add(new JLabel("Item Type:"), gbc);
    
    gbc.gridx = 1;
    JComboBox<String> itemTypeCombo = new JComboBox<>(new String[]{"TICKET", "FOOD", "DRINK"});
    purchasePanel.add(itemTypeCombo, gbc);
    
    // Item Selection
    gbc.gridx = 0; gbc.gridy = 1;
    purchasePanel.add(new JLabel("Item:"), gbc);
    
    gbc.gridx = 1;
    JComboBox<GenericItem> itemCombo = new JComboBox<>();
    updateItemCombo(itemCombo, "TICKET"); // Initial items
    purchasePanel.add(itemCombo, gbc);
    
    // Update items when type changes
    itemTypeCombo.addActionListener(e -> {
        updateItemCombo(itemCombo, (String)itemTypeCombo.getSelectedItem());
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
        GenericItem selectedItem = (GenericItem)itemCombo.getSelectedItem();
        if (selectedItem != null) {
            int quantity = (Integer)quantitySpinner.getValue();
            double total = selectedItem.getPrice() * quantity;
            totalLabel.setText(String.format("Total: $%.2f", total));
        }
    };
    
    itemCombo.addActionListener(e -> updateTotal.run());
    quantitySpinner.addChangeListener(e -> updateTotal.run());
    
    // Purchase Button
    gbc.gridx = 0; gbc.gridy = 4;
    gbc.gridwidth = 2;
    JButton purchaseButton = new JButton("Purchase");
    purchaseButton.addActionListener(e -> {
        GenericItem selectedItem = (GenericItem)itemCombo.getSelectedItem();
        if (selectedItem != null) {
            triggerPurchase(
                "U001", // Temporary hardcoded user ID
                selectedItem.getType(),
                selectedItem.getId(),
                (Integer)quantitySpinner.getValue()
            );
        }
    });
    purchasePanel.add(purchaseButton, gbc);
    
    mainPanel.add(purchasePanel, "PURCHASE");
    cardLayout.show(mainPanel, "PURCHASE");
}
    
    private void updateItemCombo(JComboBox<GenericItem> itemCombo, String type) {
    itemCombo.removeAllItems();
    // This will need to be connected to the PurchaseService
    // For now, add some sample items
    if (type.equals("TICKET")) {
        itemCombo.addItem(new GenericItem("T001", "Adult Day Pass", "TICKET", 89.99));
        itemCombo.addItem(new GenericItem("T002", "Child Day Pass", "TICKET", 79.99));
    } else if (type.equals("FOOD")) {
        itemCombo.addItem(new GenericItem("F001", "Hamburger", "FOOD", 12.99));
        itemCombo.addItem(new GenericItem("F002", "Pizza", "FOOD", 14.99));
    } else if (type.equals("DRINK")) {
        itemCombo.addItem(new GenericItem("D001", "Soft Drink", "DRINK", 4.99));
        itemCombo.addItem(new GenericItem("D002", "Bottled Water", "DRINK", 3.99));
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
        // Placeholder for checkout process
        showSuccessMessage("Checkout process will be implemented here");
    }
}
