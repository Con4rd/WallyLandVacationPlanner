package wallyland.wallylandvacationplanner.model;
import java.util.Objects;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a feedback given by a user.
 * This class stores feedback details.
 */
public class Feedback {
    private String feedbackNo;
    private String userId;
    private String message;
    private int rating; // Rating between 1 and 5
    private static List<Feedback> feedbackList = new ArrayList<>(); // Static list to hold all feedback

    // Constructor for creating a new feedback
    public Feedback(String feedbackNo, String userId, String message, int rating) {
        this.feedbackNo = feedbackNo;
        this.userId = userId;
        this.message = message;
        setRating(rating); // Ensure rating is valid
    }

    // Getter and Setter methods for Feedback properties
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
        if (rating < 1) {
            this.rating = 1; // Minimum rating
        } else if (rating > 5) {
            this.rating = 5; // Maximum rating
        } else {
            this.rating = rating;
        }
    }
    
    // Getter for the static feedbackList
    public static List<Feedback> getFeedbackList() {
        return feedbackList;
    }

    // Static method to add feedback to the list
    public static void addFeedback(Feedback feedback) {
        feedbackList.add(feedback);
    }
    
    public static void addDummyFeedback() {
        // Add dummy feedback only if it's not already present
        if (feedbackList.isEmpty()) {
            addFeedback(new Feedback("F001", "U001", "Great experience!", 5));
            addFeedback(new Feedback("F002", "U002", "Needs improvement.", 3));
            addFeedback(new Feedback("F003", "U003", "Excellent service.", 5));
        }
    }

 public static String getAllFeedbackString() {
    // Make sure the dummy feedback is added first
    Feedback.addDummyFeedback();

    StringBuilder feedbackString = new StringBuilder();

    // Loop through the list of feedback and append each feedback's string representation
    for (Feedback feedback : feedbackList) {
        feedbackString.append(feedback.toString()).append("\n\n");
    }

    return feedbackString.toString();
}

    // Method to display feedback as a string
    @Override
    public String toString() {
        return "Feedback No: " + feedbackNo + "\nUser ID: " + userId + "\nMessage: " + message + "\nRating: " + rating;
    }
}
