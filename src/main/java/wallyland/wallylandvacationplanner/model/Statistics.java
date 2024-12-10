package wallyland.wallylandvacationplanner.model;

/**
 * The Statistics class maintains and provides access to visitor and sales data.
 */
public class Statistics {
    private int totalVisitors;
    private int totalSales;

    /**
     * Constructs a Statistics instance with initial values for visitors and sales set to zero.
     */
    public Statistics() {
        this.totalVisitors = 0;
        this.totalSales = 0;
    }

    /**
     * Increments the total number of visitors by one.
     */
    public void incrementVisitors() {
        totalVisitors++;
    }

    /**
     * Adds the specified amount to the total sales.
     *
     * @param amount the amount of sales to add
     */
    public void addSales(int amount) {
        totalSales += amount;
    }

    /**
     * Returns the total number of visitors.
     *
     * @return the total number of visitors
     */
    public int getTotalVisitors() {
        return totalVisitors;
    }

    /**
     * Returns the total sales amount.
     *
     * @return the total sales amount
     */
    public int getTotalSales() {
        return totalSales;
    }
}
