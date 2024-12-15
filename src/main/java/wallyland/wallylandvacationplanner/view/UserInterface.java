package wallyland.wallylandvacationplanner.view;

import wallyland.wallylandvacationplanner.model.*;
import wallyland.wallylandvacationplanner.controller.ActivityManager;
import wallyland.wallylandvacationplanner.model.Feedback;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.time.format.DateTimeFormatter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
        
        // Add Feedback menu item to the menu bar
        JMenu feedbackMenu = new JMenu("Feedback");
        JMenuItem feedbackItem = new JMenuItem("Submit Feedback");
        feedbackItem.addActionListener(e -> displayFeedbackForm());
        feedbackMenu.add(feedbackItem);


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

        //Create Purchase History in cartMenu
        JMenuItem historyItem = new JMenuItem("Purchase History");
        historyItem.addActionListener(e -> showPurchaseHistory());
        cartMenu.add(historyItem);


        menuBar.add(shopMenu);
        menuBar.add(cartMenu);
        menuBar.add(activitiesMenu);
        menuBar.add(feedbackMenu);
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
            "Browse and purchase tickets<br>" +
            "Order food and drinks<br>" +
            "View your shopping cart</center></html>",
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

    mainPanel.add(purchasePanel, "PURCHASE");
    cardLayout.show(mainPanel, "PURCHASE");
}

    private void showPurchaseHistory() {
        String userId = "U001"; // Should be dynamic in real implementation
        List<Receipt> receipts = purchaseService.getUserReceipts(userId);

        JDialog historyDialog = new JDialog(this, "Purchase History", true);
        historyDialog.setLayout(new BorderLayout());

        DefaultListModel<Receipt> listModel = new DefaultListModel<>();
        receipts.forEach(listModel::addElement);

        JList<Receipt> receiptList = new JList<>(listModel);
        receiptList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                                                          int index, boolean isSelected, boolean cellHasFocus) {
                Receipt receipt = (Receipt) value;
                String text = String.format("Receipt %s - %s - $%.2f",
                        receipt.getReceiptId(),
                        receipt.getPurchaseDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                        receipt.getTotalAmount());
                return super.getListCellRendererComponent(list, text, index, isSelected, cellHasFocus);
            }
        });

        receiptList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    Receipt selected = receiptList.getSelectedValue();
                    if (selected != null) {
                        showReceipt(selected);
                    }
                }
            }
        });

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> historyDialog.dispose());

        historyDialog.add(new JScrollPane(receiptList), BorderLayout.CENTER);
        historyDialog.add(closeButton, BorderLayout.SOUTH);

        historyDialog.setSize(400, 400);
        historyDialog.setLocationRelativeTo(this);
        historyDialog.setVisible(true);
    }
    
     // Method to display the feedback form
    public void displayFeedbackForm() {
        JPanel feedbackPanel = new JPanel();
        feedbackPanel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Submit Your Feedback", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        feedbackPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(3, 1));

        // Feedback message text area
        JTextArea messageArea = new JTextArea(4, 30);
        JScrollPane messageScrollPane = new JScrollPane(messageArea);
        formPanel.add(messageScrollPane);

        // Rating combo box
        String[] ratings = {"1", "2", "3", "4", "5"};
        JComboBox<String> ratingComboBox = new JComboBox<>(ratings);
        formPanel.add(ratingComboBox);

        // Submit button
        JButton submitButton = new JButton("Submit Feedback");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the user's input
                String message = messageArea.getText().trim();
                int rating = Integer.parseInt((String) ratingComboBox.getSelectedItem());

                if (!message.isEmpty()) {
                    // Create new feedback and add it to the Feedback list
                    String feedbackNo = "F" + (Feedback.getFeedbackList().size() + 1);
                    Feedback feedback = new Feedback(feedbackNo, "U001", message, rating);
                    Feedback.addFeedback(feedback);

                    // Show confirmation
                    JOptionPane.showMessageDialog(UserInterface.this, "Feedback submitted successfully!");
                    messageArea.setText("");  // Clear message area
                    ratingComboBox.setSelectedIndex(0);  // Reset the rating combo box
                } else {
                    JOptionPane.showMessageDialog(UserInterface.this, "Please enter a message.");
                }
            }
        });
        formPanel.add(submitButton);

        feedbackPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(feedbackPanel, "FEEDBACK_FORM");
        cardLayout.show(mainPanel, "FEEDBACK_FORM");
    }


    public void showCart() {
        String userId = "U001"; // Ideally, this should be dynamic
        List<Purchase> purchases = purchaseService.getCartItems(userId);

        JPanel cartPanel = new JPanel(new BorderLayout());
        JPanel itemsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Add headers
        String[] headers = {"Item", "Price", "Quantity", "Subtotal", "Action"};
        Font boldFont = new Font(getFont().getFontName(), Font.BOLD, getFont().getSize());

        for (int i = 0; i < headers.length; i++) {
            gbc.gridx = i;
            gbc.gridy = 0;
            JLabel headerLabel = new JLabel(headers[i], SwingConstants.LEFT);
            headerLabel.setFont(boldFont);
            itemsPanel.add(headerLabel, gbc);
        }

        if (purchases.isEmpty()) {
            gbc.gridy = 1;
            gbc.gridx = 0;
            gbc.gridwidth = 5;
            itemsPanel.add(new JLabel("Your cart is empty."), gbc);
        } else {
            int row = 1;
            for (Purchase purchase : purchases) {
                gbc.gridy = row;
                gbc.gridwidth = 1;

                // Item name
                gbc.gridx = 0;
                String itemName;
                if (purchase.getItem() instanceof Ticket) {
                    itemName = ((Ticket) purchase.getItem()).getType();
                } else if (purchase.getItem() instanceof Food) {
                    itemName = ((Food) purchase.getItem()).getName();
                } else if (purchase.getItem() instanceof Drinks) {
                    itemName = ((Drinks) purchase.getItem()).getName();
                } else {
                    itemName = "Unknown Item";
                }
                itemsPanel.add(new JLabel(itemName), gbc);

                // Unit price
                gbc.gridx = 1;
                JLabel priceLabel = new JLabel(String.format("$%.2f", purchase.getUnitPrice()));
                itemsPanel.add(priceLabel, gbc);

                // Quantity adjustment panel
                gbc.gridx = 2;
                JPanel quantityPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
                SpinnerNumberModel spinnerModel = new SpinnerNumberModel(
                        purchase.getQuantity(), 0, 99, 1);
                JSpinner quantitySpinner = new JSpinner(spinnerModel);
                quantitySpinner.setPreferredSize(new Dimension(60, 25));

                JButton decreaseButton = new JButton("-");
                JButton increaseButton = new JButton("+");

                // Subtotal label (will be updated with quantity changes)
                JLabel subtotalLabel = new JLabel(String.format("$%.2f", purchase.getTotalPrice()));

                // Update handlers for quantity changes
                Runnable updatePrice = () -> {
                    int newQuantity = (int) quantitySpinner.getValue();
                    if (newQuantity >= 0) {
                        double newSubtotal = purchase.getUnitPrice() * newQuantity;
                        subtotalLabel.setText(String.format("$%.2f", newSubtotal));
                        updateCartQuantity(userId, purchase, newQuantity);
                        updateTotalPrice(purchases); // Update the total price display
                    }
                };

                decreaseButton.addActionListener(e -> {
                    int currentValue = (int) quantitySpinner.getValue();
                    if (currentValue > 0) {
                        quantitySpinner.setValue(currentValue - 1);
                        updatePrice.run();
                    }
                });

                increaseButton.addActionListener(e -> {
                    int currentValue = (int) quantitySpinner.getValue();
                    quantitySpinner.setValue(currentValue + 1);
                    updatePrice.run();
                });

                quantitySpinner.addChangeListener(e -> updatePrice.run());

                quantityPanel.add(decreaseButton);
                quantityPanel.add(quantitySpinner);
                quantityPanel.add(increaseButton);
                itemsPanel.add(quantityPanel, gbc);

                // Subtotal
                gbc.gridx = 3;
                itemsPanel.add(subtotalLabel, gbc);

                // Remove button
                gbc.gridx = 4;
                JButton removeButton = new JButton("Remove");
                removeButton.addActionListener(e -> {
                    if (purchaseService.updateCartItemQuantity(userId, purchase, 0)) {
                        showSuccessMessage("Item removed from cart!");
                        showCart(); // Refresh the cart display
                    } else {
                        showErrorMessage("Failed to remove item from cart.");
                    }
                });
                itemsPanel.add(removeButton, gbc);

                row++;
            }

            // Add total price row
            gbc.gridy = row;
            gbc.gridx = 0;
            gbc.gridwidth = 3;
            gbc.anchor = GridBagConstraints.EAST;
            JLabel totalLabel = new JLabel("Total:", SwingConstants.RIGHT);
            totalLabel.setFont(boldFont);
            itemsPanel.add(totalLabel, gbc);

            gbc.gridx = 3;
            gbc.gridwidth = 2;
            double totalPrice = purchases.stream()
                    .mapToDouble(Purchase::getTotalPrice)
                    .sum();
            JLabel totalPriceLabel = new JLabel(String.format("$%.2f", totalPrice), SwingConstants.LEFT);
            totalPriceLabel.setFont(boldFont);
            itemsPanel.add(totalPriceLabel, gbc);
        }

        // Add scrolling capability
        JScrollPane scrollPane = new JScrollPane(itemsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        cartPanel.add(scrollPane, BorderLayout.CENTER);

        // Checkout button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton checkoutButton = new JButton("Proceed to Checkout");
        checkoutButton.setEnabled(!purchases.isEmpty());
        checkoutButton.addActionListener(e -> processCheckout());
        buttonPanel.add(checkoutButton);
        cartPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(cartPanel, "CART");
        cardLayout.show(mainPanel, "CART");
    }

    //helper method for updatecart with showCart()
    private void updateCartQuantity(String userId, Purchase purchase, int newQuantity) {
        if (purchaseService.updateCartItemQuantity(userId, purchase, newQuantity)) {
            if (newQuantity == 0) {
                showSuccessMessage("Item removed from cart!");
            } else {
                showSuccessMessage("Quantity updated!");
            }
            showCart(); // Refresh the cart display
        } else {
            showErrorMessage("Failed to update quantity.");
        }
    }

    // Helper method to update the total price display
    private void updateTotalPrice(List<Purchase> purchases) {
        double totalPrice = purchases.stream()
                .mapToDouble(Purchase::getTotalPrice)
                .sum();
        // Find the total price label and update it
        Component[] components = mainPanel.getComponents();
        for (Component component : components) {
            if (component instanceof JPanel) {
                JPanel panel = (JPanel) component;
                if (panel.getLayout() instanceof GridBagLayout) {
                    Component[] panelComponents = panel.getComponents();
                    for (Component c : panelComponents) {
                        if (c instanceof JLabel && ((JLabel) c).getText().startsWith("$")) {
                            ((JLabel) c).setText(String.format("$%.2f", totalPrice));
                            break;
                        }
                    }
                }
            }
        }
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
            // Generate receipt
            Receipt receipt = purchaseService.generateReceipt("U001", paymentMethod);

            // Clear the cart
            purchaseService.clearCart("U001");

            // Show receipt
            showReceipt(receipt);

            //refresh cart display
            showCart();

            showSuccessMessage("Payment successful! Receipt has been generated.");
        } else {
            showErrorMessage("Payment failed. Please try again.");
        }
    } else {
        showErrorMessage("Checkout canceled.");
    }
 }

    private void showReceipt(Receipt receipt) {
        JDialog receiptDialog = new JDialog(this, "Purchase Receipt", true);
        receiptDialog.setLayout(new BorderLayout());

        JTextArea receiptText = new JTextArea(receipt.formatReceipt());
        receiptText.setEditable(false);
        receiptText.setFont(new Font("Monospaced", Font.PLAIN, 12));

        JButton printButton = new JButton("Print Receipt");
        printButton.addActionListener(e -> {
            // Add printing functionality later if needed
            showSuccessMessage("Printing feature will be implemented soon!");
        });

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> receiptDialog.dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(printButton);
        buttonPanel.add(closeButton);

        receiptDialog.add(new JScrollPane(receiptText), BorderLayout.CENTER);
        receiptDialog.add(buttonPanel, BorderLayout.SOUTH);

        receiptDialog.setSize(400, 600);
        receiptDialog.setLocationRelativeTo(this);
        receiptDialog.setVisible(true);
    }

    public void showActivityManagement() {
        ActivityPanel activityPanel = new ActivityPanel(activityManager);
        mainPanel.add(activityPanel, "ACTIVITIES");
        cardLayout.show(mainPanel, "ACTIVITIES");
    }
}
