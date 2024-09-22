/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wallyland.wallylandvacationplanner.model;

/**
 * Represents a feedback given by a user.
 * This class stores feedback details.
 */
public class Feedback {
    private String feedbackNo;
    private String userId;
    private String message;
    private int rating;

    /**
     * Constructs a new Feedback.
     *
     * @param feedbackNo The number of the feedback.
     * @param userId     The user who submitted the feedback.
     * @param message    The feedback content.
     * @param rating     The rating given by the user.
     */
    public Feedback(String feedbackNo, String userId, String message, int rating) {
        this.feedbackNo = feedbackNo;
        this.userId = userId;
        this.message = message;
        this.rating = rating;
    }

    // Getters and setters

    public String getFeedbackNo() {
        return feedbackNo;
    }

    public void setFeedbackNo(String feedbackNo) {
        this.feedbackNo = feedbackNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
    
}
