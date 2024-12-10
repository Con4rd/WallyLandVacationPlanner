package wallyland.wallylandvacationplanner.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

public class Receipt {
    private String receiptId;
    private String userId;
    private List<PurchaseService.Purchase> items;
    private double totalAmount;
    private LocalDateTime purchaseDate;
    private String paymentMethod;

    public Receipt(String userId, List<PurchaseService.Purchase> items,
                   double totalAmount, String paymentMethod) {
        this.receiptId = "RCP-" + UUID.randomUUID().toString().substring(0, 8);
        this.userId = userId;
        this.items = items;
        this.totalAmount = totalAmount;
        this.purchaseDate = LocalDateTime.now();
        this.paymentMethod = paymentMethod;
    }

    // Getters
    public String getReceiptId() { return receiptId; }
    public String getUserId() { return userId; }
    public List<PurchaseService.Purchase> getItems() { return items; }
    public double getTotalAmount() { return totalAmount; }
    public LocalDateTime getPurchaseDate() { return purchaseDate; }
    public String getPaymentMethod() { return paymentMethod; }

    public String formatReceipt() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        StringBuilder sb = new StringBuilder();

        sb.append("=================================\n");
        sb.append("           WALLYLAND             \n");
        sb.append("=================================\n");
        sb.append("Receipt ID: ").append(receiptId).append("\n");
        sb.append("Date: ").append(purchaseDate.format(formatter)).append("\n");
        sb.append("User ID: ").append(userId).append("\n");
        sb.append("---------------------------------\n");
        sb.append("Items:\n");

        for (PurchaseService.Purchase item : items) {
            sb.append(String.format("%-20s x%d\n",
                    item.toString(),
                    item.getQuantity()));
            sb.append(String.format("    Unit Price: $%.2f\n",
                    item.getUnitPrice()));
            sb.append(String.format("    Subtotal:   $%.2f\n",
                    item.getTotalPrice()));
        }

        sb.append("---------------------------------\n");
        sb.append(String.format("Total Amount: $%.2f\n", totalAmount));
        sb.append("Payment Method: ").append(paymentMethod).append("\n");
        sb.append("=================================\n");
        sb.append("          Thank You!             \n");
        sb.append("=================================\n");

        return sb.toString();
    }
}
