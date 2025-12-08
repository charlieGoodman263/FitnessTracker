import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class AdminMenu {
    public static void run(Scanner sc) {
        Users users = new Users();
        while (true) {
            System.out.println("\n1) List users\n2) View a client's sessions\n3) Sign out");
            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1":
                    System.out.println(users);
                    break;
                case "2":
                    System.out.print("Enter client username: ");
                    String target = sc.nextLine();
                    User user = users.findByUsername(target); // add this helper
                    if (!(user instanceof Client)) {
                        System.out.println("Not a client or not found.");
                        break;
                    }

                    Client client = (Client) user;

                    ArrayList<Session> sessions = client.getSessions();
                    if (sessions.isEmpty()) {
                        System.out.println("No sessions logged.");
                    } else {
                        sessions.forEach(s -> System.out.println(s.getName() + " -> " + s.getExerciseList().size() + " exercises"));
                    }
                    HashMap<Exercise, Double> pbs = client.getPersonalBests();
                    System.out.println("PBs: " + pbs);
                    break;
                case "3": return;
                default: System.out.println("Choose 1-3");
            }
        }
    }
}
