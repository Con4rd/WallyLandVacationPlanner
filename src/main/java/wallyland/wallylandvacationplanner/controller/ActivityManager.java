/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wallyland.wallylandvacationplanner.controller;

import wallyland.wallylandvacationplanner.model.Activity;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author njthe
 */
/**
     * The ActivityManager class is responsible for managing a list of activities,
     * allowing activities to be added or removed.
     */
    public class ActivityManager {
        private List<Activity> activities;

        /**
         * Constructs an ActivityManager instance and initializes the list of activities.
         */
        public ActivityManager() {
            activities = new ArrayList<>();
        }

        /**
         * Adds an activity to the list of managed activities.
         * 
         * @param activity the activity to be added
         */
        public void addActivity(Activity activity) {
            activities.add(activity);
        }

        /**
         * Removes an activity from the list of managed activities.
         * 
         * @param activity the activity to be removed
         */
        public void removeActivity(Activity activity) {
            activities.remove(activity);
        }
    }
