import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        User user = SignIn.signIn(sc);
        
        if (user instanceof Client) {
            ClientMenu.run(sc, (Client) user);
        } 
        else {
            AdminMenu.run(sc, (Admin) user);
        }

        sc.close();
    }
}