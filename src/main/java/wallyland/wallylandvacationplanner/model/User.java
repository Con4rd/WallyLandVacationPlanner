package wallyland.wallylandvacationplanner.model;

/**
 * Represents a user of the WallyLand Vacation Planner system.
 * This class stores and manages user information.
 */
public class User {
    private String userId;
    private String username;
    private String email;

    /**
     * Constructs a new User with the specified details.
     *
     * @param userId   The unique identifier for the user.
     * @param username The user's chosen username.
     * @param email    The user's email address.
     */
    public User(String userId, String username, String email) {
        this.userId = userId;
        this.username = username;
        this.email = email;
    }

    // Getters and setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Displays user information in a formatted manner.
     */
    public void displayUserInfo() {
        System.out.println("User ID: " + userId);
        System.out.println("Username: " + username);
        System.out.println("Email: " + email);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
