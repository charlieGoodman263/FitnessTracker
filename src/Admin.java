public class Admin extends User{
    /**
     * Creates an administrator account.
     *
     * @param userID   the admin id as text.
     * @param userName the admin username.
     * @param password the admin password.
     */
    public Admin(String userID, String userName, String password) {
        super(userID, userName, password);
    }
}
