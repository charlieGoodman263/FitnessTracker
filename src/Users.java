import java.util.ArrayList;
import java.util.Scanner;

public class Users {
    private ArrayList<User> users = new ArrayList<User>();

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

    public String toString() {
        String ret = "";
        for (User user : users) {
            ret += user;
        }
        return ret;
    }

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


    public void signUp() {
        Scanner sc = new Scanner(System.in);
        String userName;
        String password;
        boolean exists = false;
        do {
            exists = false;
            System.out.print("Please enter your username: ");
            userName = sc.nextLine();

            for (User user : users) { // checking user doesn't already exist
                if (userName.equals(user.getUserName())) {
                    exists = true;
                    break;
                }
            }
        } while(exists); // runs until a unique username is provided);
        System.out.print("Please enter a password: ");
        password = sc.nextLine();

        
        String userID = "" + users.get(users.size() - 1).getUserID() + 1; // retrieves the userID of the last user in the file and adds 1, then converts back to a string
        users.add(new User(userID, userName, password));
        FileUtils.appendUser(userID + ":" + userName + ":" + password);

        System.out.println("Account created.");
        sc.close();
    }
}
