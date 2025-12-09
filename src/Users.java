import java.util.ArrayList;
import java.util.Scanner;

public class Users {
    private ArrayList<User> users = new ArrayList<User>();

    /**
     * Loads each user line from storage and builds the list.
     */
    public Users() {
        this.users = new ArrayList<User>();
        for (String user : FileUtils.retrieveUsers()) {
            String[] userData = user.split(":");

            if (!(userData[0].equals("0"))) { // if client
                 users.add(new Client(userData[0], userData[1], userData[2]));
            }
            else {
                users.add(new Admin(userData[0], userData[1], userData[2]));
            }
        }
    }

    /**
     * Dumps the current users via {@link User#toString()} for display.
     */
    public String toString() {
        String ret = "";
        for (User user : users) {
            ret += user;
        }
        return ret;
    }

    /**
     * Finds a matching user by credentials and throws when not found/invalid.
     */
    public User signIn(String userName, String password) throws RuntimeException {
        for (User user : users) {
            if (userName.equals(user.getUserName())) {
                if (password.equals(user.getPassword())) {
                    System.out.println("Success!");
                    return user;
                }
                else {
                    throw new RuntimeException("Invalid password!");
                }
            }
        }
        throw new RuntimeException("User doesn't exist.");
    }

    /**
     * Prompts for a username/password and adds the resulting client.
     */
    public void signUp(Scanner sc) {
        String userName;
        while (true) {
            // Repeat until a username is provided that does not match an existing account.
            System.out.print("Please enter your username: ");
            userName = sc.nextLine().trim();
            if (userName.isEmpty()) {
                System.out.println("Username cannot be empty.");
                continue;
            }
            boolean exists = false;
            for (User user : users) {
                if (userName.equals(user.getUserName())) {
                    exists = true;
                    System.out.println("Username already exists.");
                    break;
                }
            }
            if (!exists) { 
                break;
            }
        }
        String password;
        do {
            // Keep asking until the user provides a non-empty password.
            System.out.print("Please enter a password: ");
            password = sc.nextLine().trim();
        } while (password.isEmpty());

        int nextId = users.get(users.size() - 1).getUserID() + 1;
        String userID = Integer.toString(nextId);

        Client newClient = new Client(userID, userName, password);
        users.add(newClient);
        FileUtils.appendUser(userID + ":" + userName + ":" + password);
        
        System.out.println("Account created.");
    }

    /**
     * Finds a user by their username or returns null if missing.
     */
    public User findByUsername(String username) {
        for (User user : users) {
            if (user.getUserName().equals(username)) {
                return user;
            }
        }
        return null;
    }
}
