/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wallyland.wallylandvacationplanner.model;

/**
 * Represents a ticket.
 * This class stores and manages ticket details such as type and validity.
 */
public class Ticket {
    private String ticketId;
    private String type;
    private boolean isValid;
    private double price;

    /**
     * Constructs a new Ticket.
     *
     * @param ticketId The unique identifier of each ticket.
     * @param type     The type of the ticket (e.g., day pass, annual pass).
     * @param price    The price of the ticket.
     * @param isValid  The validity of the ticket.
     */
    public Ticket(String ticketId, String type,double price, boolean isValid) {
        this.ticketId = ticketId;
        this.type = type;
        this.price = price;
        this.isValid = isValid;
    }

    //   Getters and setters
    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
     @Override
    public String toString() {
        return ticketId + " " + type + " -- $" + String.format("%.2f", price) + isValid;
    }
}
