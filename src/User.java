public class User {
    protected int userID;
    protected String userName;
    protected String password;

    /**
     * Creates a user entity using the string fields coming from the storage file.
     *
     * @param userID   numeric id as text.
     * @param userName chosen display name.
     * @param password credentials.
     */
    public User(String userID, String userName, String password) {
        this.userID = Integer.parseInt(userID);
        this.userName = userName;
        this.password = password;
    }

    // Getters and setters
    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }


    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Displays the user information in one block for debugging.
     */
    @Override
    public String toString() {
        return "User ID: " + userID + "\n"
                + "Username: " + userName + "\n"
                + "Password: " + password + "\n";
    }

    /**
     * Two users are considered equal when their IDs match.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof User) {
            User user = (User) obj;
            return this.userID == user.userID;
        }
        return false;
    }
}
