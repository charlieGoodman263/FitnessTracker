import java.util.Scanner;

public class AdminMenu {
        public static void run(Scanner sc, Admin admin) {
        while (true) {
            System.out.println("\n1) View sessions\n2) Add session\n3) View personal bests\n4) Update personal best\n5) Sign out");
            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1": listSessions(client); break;
                case "2": addSession(sc, client); break;
                case "3": viewPBs(client); break;
                case "4": updatePB(sc, client); break;
                case "5": return;
                default: System.out.println("Choose 1-5");
            }
        }
    }
}
