package wallyland.wallylandvacationplanner.controller;

import wallyland.wallylandvacationplanner.model.Activity;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * The ActivityManager class is responsible for managing a list of activities,
 * allowing activities to be added, removed, and updated.
 * The activities are now managed using a HashMap.
 */
public class ActivityManager {
    private Map<String, Activity> activities;

    public ActivityManager() {
        activities = new HashMap<>();
        initializeActivities(); // Add some sample activities
    }

    /**
     * Initializes some sample activities.
     */
    private void initializeActivities() {
        addActivity(new Activity("R001", "Rollercoaster", "9AM-10PM", 30));
        addActivity(new Activity("R002", "Other Attraction", "10AM-9PM", 40));
        addActivity(new Activity("R003", "Ferris Wheel", "9AM-8PM", 50));
    }

    /**
     * Adds an activity to the list.
     * 
     * @param activity the activity to be added
     */
    public void addActivity(Activity activity) {
        activities.put(activity.getActivityId(), activity);
    }

    /**
     * Removes an activity from the list.
     * 
     * @param activity the activity to be removed
     */
    public void removeActivity(Activity activity) {
        activities.remove(activity.getActivityId());
    }

    /**
     * Retrieves an activity by its ID.
     * 
     * @param activityId the ID of the activity to retrieve
     * @return the activity associated with the ID, or null if not found
     */
    public Activity getActivity(String activityId) {
        return activities.get(activityId);
    }

    /**
     * Returns a list of all activities.
     * 
     * @return a list of all activities
     */
    public List<Activity> getAllActivities() {
        return new ArrayList<>(activities.values());
    }

    /**
     * Schedules an activity by its ID.
     * 
     * @param activityId the ID of the activity to schedule
     * @return true if the activity was scheduled successfully, false otherwise
     */

    public boolean scheduleActivity(String activityId) {
        Activity activity = activities.get(activityId);
        if (activity != null) {
            // Check if the activity is operational and has capacity
            if (activity.isScheduled()) {
                return false; // Already scheduled
            }
            if (activity.getDailyScheduled() < activity.getDailyCapacity() && activity.isOperational()) {
                activity.scheduleActivity();
                return true; // Activity successfully scheduled
            }
        }
        return false; // Could not schedule activity
    }

public void cancelActivity(String activityId) {
    Activity activity = activities.get(activityId);
    if (activity != null) {
        activity.cancelActivity();
    }
}

    /**
     * Updates the details of an activity.
     * 
     * @param activity the activity to update
     */
    public void updateActivity(Activity activity) {
        if (activities.containsKey(activity.getActivityId())) {
            activities.put(activity.getActivityId(), activity);
        } else {
            System.out.println("Activity with ID " + activity.getActivityId() + " does not exist.");
        }
    }

    /**
     * Retrieves an activity by its ID. Same as getActivity, but named for clarity.
     * 
     * @param activityId the ID of the activity to retrieve
     * @return the activity with the specified ID, or null if not found
     */
    public Activity getActivityById(String activityId) {
        return activities.get(activityId);
    }

    /**
     * Returns a list of all scheduled activities.
     * 
     * @return a list of all scheduled activities
     */
    public List<Activity> getScheduledActivities() {
        List<Activity> scheduledActivities = new ArrayList<>();
        for (Activity activity : activities.values()) {
            if (activity.isScheduled()) {
                scheduledActivities.add(activity);
            }
        }
        return scheduledActivities;
    }
}
