package wallyland.wallylandvacationplanner.controller;

import wallyland.wallylandvacationplanner.model.Activity;
import java.util.List;

/**
 * The ActivityController class manages activities by utilizing an instance of 
 * ActivityManager to schedule and cancel activities.
 */
public class ActivityController {
    private ActivityManager activityManager;

    /**
     * Constructs an ActivityController instance and initializes the ActivityManager.
     */
    public ActivityController() {
        activityManager = new ActivityManager();
    }

    /**
     * Schedules the given activity and adds it to the activity manager.
     * 
     * @param activity the activity to be scheduled
     */
    public void scheduleActivity(Activity activity) {
        if (activity != null && !activity.isScheduled()) {
            activity.scheduleActivity();
            activityManager.addActivity(activity);
            System.out.println("Activity scheduled: " + activity.getName());
        } else {
            System.out.println("Activity is either null or already scheduled.");
        }
    }

    /**
     * Cancels the given activity and removes it from the activity manager.
     * 
     * @param activity the activity to be canceled
     */
    public void cancelActivity(Activity activity) {
        if (activity != null && activity.isScheduled()) {
            activity.cancelActivity();
            activityManager.removeActivity(activity);
            System.out.println("Activity canceled: " + activity.getName());
        } else {
            System.out.println("Activity is either null or not scheduled.");
        }
    }

    /**
     * Fetches a list of all scheduled activities.
     * 
     * @return list of all scheduled activities
     */
    public List<Activity> getScheduledActivities() {
        return activityManager.getScheduledActivities();
    }

    /**
     * Searches for an activity by its ID.
     * 
     * @param activityId the ID of the activity to search for
     * @return the activity if found, null otherwise
     */
    public Activity getActivityById(String activityId) {
        return activityManager.getActivityById(activityId);
    }

    /**
     * Updates the details of an activity.
     * 
     * @param activity the activity with updated details
     */
    public void updateActivity(Activity activity) {
        if (activity != null) {
            activityManager.updateActivity(activity);
            System.out.println("Activity updated: " + activity.getName());
        } else {
            System.out.println("Activity is null and cannot be updated.");
        }
    }
}
