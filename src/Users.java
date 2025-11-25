import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Users {
    private ArrayList<User> users = new ArrayList<User>();

    public Users() {
        this.users = new ArrayList<User>();
        for (String user : FileUtils.retrieveUserData()) {
            String[] userData = user.split(":");
            users.add(new User(userData[0], userData[1]));
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
        boolean valid = true;
        String password;
        do {
            System.out.print("Please enter your username: ");
            userName = sc.nextLine();

            for (User user : users) { // checking user doesn't already exist
                if (userName.equals(user.getUserName())) {
                    valid = false;
                }
            }
        } while(!(valid));
        System.out.print("Please enter a password: ");
        password = sc.nextLine();

        users.add(new User(userName, password));
        FileUtils.appendToFile(userName + ":" + password);

        System.out.println("Account created.");
    }
}
