import java.util.Scanner;

public class SignIn {
    public static User signIn() {
        Scanner sc = new Scanner(System.in);
        Users users = new Users();
        System.out.println("Welcome!");
        User user;
        String userChoice;

        do {
            boolean valid = false;
            do {
                System.out.print("Sign in (1), Sign up (2), or Exit (3): ");
                userChoice = sc.next(); // String to allow for invalid inputs so that they can be handled in the switch
                switch (userChoice) {
                    case "1":
                        String userName;
                        String password;
                        valid = true;

                        try {
                            System.out.print("Username: ");
                            userName = sc.next();
                            System.out.print("Password: ");
                            password = sc.next();

                            user = users.signIn(userName, password);
                            return user;
                        } catch (RuntimeException e) {
                            System.out.println(e);
                            valid = false;
                        }
                        break;
                    case "2":
                        users.signUp();
                        valid = true;
                        break;
                    case "3":
                        System.exit(1);
                    default:
                        System.out.print("Please enter a valid input (1, 2, or 3): ");
                }
            } while (!(valid)); // runs until a valid response
        } while(true); // runs until case 3 is hit (system.exit)
    }
}
