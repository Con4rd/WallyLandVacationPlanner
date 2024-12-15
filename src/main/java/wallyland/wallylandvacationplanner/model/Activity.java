package wallyland.wallylandvacationplanner.model;
import javax.swing.Timer;

/**
     * The Activity class represents an individual activity with a name, 
     * time slot, and scheduled status.
     */
public class Activity {
    private String activityId;
    private String name;
    private String description;
    private String timeSlot;
    private boolean isScheduled;
    private int rideCapacity;      // How many people can ride at once
    private int currentRiders;     // Current number of people on ride
    private int dailyCapacity;     // How many can ride per day
    private int dailyScheduled;    // How many have scheduled today
    private boolean isOperational;
    private int waitTime;          // in minutes
    private Timer waitTimeSimulator;

    /**
     * Constructs an Activity instance with the specified name and time slot.
     *
     * @param name the name of the activity
     * @param timeSlot the time slot of the activity
     */

    public Activity(String activityId, String name, String timeSlot, int rideCapacity) {
        this.activityId = activityId;
        this.name = name;
        this.timeSlot = timeSlot;
        this.rideCapacity = rideCapacity;
        this.currentRiders = 0;
        this.dailyCapacity = rideCapacity * 100; // Example: can handle 100x the ride capacity per day
        this.dailyScheduled = 0;
        this.isScheduled = false;
        this.isOperational = true;
        this.waitTime = 0;

        // Set descriptions based on activityId
        setDefaultDescription();

        // Initialize wait time simulator
        initializeWaitTimeSimulator();
    }

    /**
     * Returns the name of the activity.
     *
     * @return the name of the activity
     */

    public String getName() {
        return name;
    }

    /**
     * Returns the time slot of the activity.
     *
     * @return the time slot of the activity
     */
    public String getTimeSlot() {
            return timeSlot;
        }

    /**
     * Returns whether the activity is scheduled.
     *
     * @return true if the activity is scheduled, false otherwise
     */

    public boolean isScheduled() {
            return isScheduled;
        }

    /**
     * Schedules the activity.
     */

    public boolean scheduleActivity() {
        if (!isOperational) return false;
        if (dailyScheduled >= dailyCapacity) return false;

        dailyScheduled++;
        currentRiders = Math.min(currentRiders + 1, rideCapacity);
        isScheduled = true;
        return true;
    }

    /**
     * Cancels the activity.
     */

    public void cancelActivity() {
        if (dailyScheduled > 0) {
            dailyScheduled--;
            currentRiders = Math.max(0, currentRiders - 1);
            if (dailyScheduled == 0) {
                isScheduled = false;
            }
        }
    }



/*
    @Override
    public String toString() {
        return name + " (" + timeSlot + ") - " +
                "Capacity: " + currentCapacity + "/" + capacity +
                " Wait: " + waitTime + " min" +
                (isOperational ? "" : " [CLOSED]");
    }
*/
    //Updated
    @Override
    public String toString() {
        return String.format("%s (%s) - %d/%d scheduled | Wait: %d min%s",
                name,
                timeSlot,
                dailyScheduled,
                dailyCapacity,
                waitTime,
                isOperational ? "" : " [CLOSED]"
        );
    }


    private void setDefaultDescription() {
        switch (activityId) {
            case "R001":
                this.description = "Thrilling roller coaster with three inversions and a 90-foot drop! " +
                        "Experience speeds up to 60mph through twisting turns and loops.";
                break;
            case "R002":
                this.description = "A family-friendly boat adventure through mystical caves and " +
                        "enchanted waterways. Prepare to get a little wet!";
                break;
            case "R003":
                this.description = "Classic Ferris wheel offering breathtaking views of the entire park. " +
                        "Perfect for all ages and great photo opportunities.";
                break;
            default:
                this.description = "Experience the magic of WallyLand!";
        }
    }

    private void initializeWaitTimeSimulator() {
        waitTimeSimulator = new Timer(5000, e -> { // Updates every 5 seconds
            if (isOperational && dailyScheduled > 0) {
                // Calculate wait time based on current riders and capacity
                double utilizationRate = (double) currentRiders / rideCapacity;
                // Base wait time of 5-15 minutes, increased by utilization
                int baseWait = 5 + (int)(Math.random() * 10);
                waitTime = (int)(baseWait * (1 + utilizationRate));
            } else {
                waitTime = 0;
            }
        });
        waitTimeSimulator.start();
    }

    // Getters and setters

    public String getDescription() { return description; }
    public String getActivityId() { return activityId; }
    public boolean isOperational() { return isOperational; }
    public int getWaitTime() { return waitTime; }
    public int getRideCapacity() { return rideCapacity; }
    public int getCurrentRiders() { return currentRiders; }
    public int getDailyCapacity() { return dailyCapacity; }
    public int getDailyScheduled() { return dailyScheduled; }
    public void setOperational(boolean operational) {
        isOperational = operational;
        if (!operational) {
            waitTime = 0;
        }
    }


    // Clean up resources when activity is no longer needed
    public void cleanup() {
        if (waitTimeSimulator != null) {
            waitTimeSimulator.stop();
        }
    }



}

