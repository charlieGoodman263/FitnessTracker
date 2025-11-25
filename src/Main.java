import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Users users = new Users();
        System.out.println("Welcome!");
        User user;

        do {
            System.out.print("Sign in (1), Sign up (2), or Exit (3): ");
            boolean valid = false;
            do {
                String userChoice = sc.nextLine(); // String to allow for invalid inputs so that they can be handled in the switch
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
                        } catch (RuntimeException e) {
                            System.out.println(e);
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