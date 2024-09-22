/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wallyland.wallylandvacationplanner.model;

/**
 *
 * @author njthe
 */
/**
 * The Admin class represents an administrator who can view statistics.
 */
public class Admin {
    private String adminId;

    /**
     * Constructs an Admin instance with the specified admin ID.
     *
     * @param adminId the unique identifier for the administrator
     */
    public Admin(String adminId) {
        this.adminId = adminId;
    }

    /**
     * Displays the statistics including total visitors and total sales.
     *
     * @param statistics the Statistics object containing the data to be displayed
     */
    public void viewStatistics(Statistics statistics) {
        System.out.println("Total Visitors: " + statistics.getTotalVisitors());
        System.out.println("Total Sales: " + statistics.getTotalSales());
    }
}