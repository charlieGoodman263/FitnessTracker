public class User {
    protected int userID;
    protected String userName;
    protected String password;

    // Constructors
    public User(String userID, String userName, String password) {
        this.userID = Integer.parseInt(userID);
        this.userName = userName;
        this.password = password;
    }

    // Getters & Setters
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

    @Override
    public String toString() {
        return "User ID: " + userID + "\n"
                + "Username: " + userName + "\n"
                + "Password: " + password + "\n";
    }


}
