package wallyland.wallylandvacationplanner.model;

/**
     * The Activity class represents an individual activity with a name, 
     * time slot, and scheduled status.
     */
public class Activity {
    private String activityId;
    private String name;
    private String timeSlot;
    private boolean isScheduled;
    private int capacity;
    private int currentCapacity;
    private boolean isOperational;
    private int waitTime; // in minutes

    /**
     * Constructs an Activity instance with the specified name and time slot.
     *
     * @param name the name of the activity
     * @param timeSlot the time slot of the activity
     */

    public Activity(String activityId, String name, String timeSlot, int capacity) {
        this.activityId = activityId;
        this.name = name;
        this.timeSlot = timeSlot;
        this.capacity = capacity;
            this.currentCapacity = 0;
            this.isScheduled = false;
            this.isOperational = true;
            this.waitTime = 0;
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
        if (currentCapacity < capacity && isOperational) {
            currentCapacity++;
            isScheduled = true;
            return true;
        }
        return false;
    }

    /**
     * Cancels the activity.
     */

    public void cancelActivity() {
        if (currentCapacity > 0) {
            currentCapacity--;
        }
        if (currentCapacity == 0) {
            isScheduled = false;
        }
    }


    public String getActivityId() { return activityId; }

    public int getCapacity() { return capacity; }
    public int getCurrentCapacity() { return currentCapacity; }
    public boolean isOperational() { return isOperational; }
    public int getWaitTime() { return waitTime; }

    public void setOperational(boolean operational) {
        isOperational = operational;
    }


    @Override
    public String toString() {
        return name + " (" + timeSlot + ") - " +
                "Capacity: " + currentCapacity + "/" + capacity +
                " Wait: " + waitTime + " min" +
                (isOperational ? "" : " [CLOSED]");
    }


}

