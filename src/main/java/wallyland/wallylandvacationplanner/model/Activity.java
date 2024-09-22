/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wallyland.wallylandvacationplanner.model;

/**
 *
 * @author njthe
 */
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author njthe
 */
/**
     * The Activity class represents an individual activity with a name, 
     * time slot, and scheduled status.
     */
    public class Activity {
        private String name;
        private String timeSlot;
        private boolean isScheduled;

        /**
         * Constructs an Activity instance with the specified name and time slot.
         * 
         * @param name the name of the activity
         * @param timeSlot the time slot of the activity
         */
        public Activity(String name, String timeSlot) {
            this.name = name;
            this.timeSlot = timeSlot;
            this.isScheduled = false;
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
        public void scheduleActivity() {
            this.isScheduled = true;
        }

        /**
         * Cancels the activity.
         */
        public void cancelActivity() {
            this.isScheduled = false;
        }
    }