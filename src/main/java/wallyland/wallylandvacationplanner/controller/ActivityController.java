/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wallyland.wallylandvacationplanner.controller;

import wallyland.wallylandvacationplanner.model.Activity;

/**
 *
 * @author njthe
 */
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
        activity.scheduleActivity();
        activityManager.addActivity(activity);
    }

    /**
     * Cancels the given activity and removes it from the activity manager.
     * 
     * @param activity the activity to be canceled
     */
    public void cancelActivity(Activity activity) {
        activity.cancelActivity();
        activityManager.removeActivity(activity);
    }
}