import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ClientMenu {
    private Client client;

    public ClientMenu(Client client) {
        this.client = client;
    }

    public void run(Scanner sc) {
        while (true) {
            System.out.println("\n1) View sessions\n2) Log a session\n3) View personal bests\n4) Sign out");
            //sc.next();
            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1": 
                    listSessions(); 
                    break;
                case "2": 
                    logSession(sc); 
                    break;
                case "3": 
                    viewPBs(); 
                    break;
                case "4": 
                    return;
                default:
                    System.out.println("Choose 1-4");
            }
        }
    }

    private void listSessions() {
        ArrayList<Session> sessions = client.getSessions();

        if (sessions.isEmpty()) {
            System.out.println("No sessions logged yet.");
            return;
        }
        for (Session session : sessions) {
            System.out.println("- " + session.getName() + ": " + session.getExerciseList().size() + " exercises");
        }
    }
    
    private void logSession(Scanner sc) {
        // create session log method, need to implement a new database in txt format for templates of sessions that could be the parent of the session class?
    }
    
    private void viewPBs() {
        HashMap<Exercise, Double> pbs = client.getPersonalBests();

        if (pbs.isEmpty()) {
            System.out.println("No PBs yet.");
            return;
        }
        
        for (HashMap.Entry<Exercise, Double> entry : pbs.entrySet()) {
            Exercise exercise = entry.getKey();
            String name = exercise.getExerciseName();
            int reps = exercise.getReps();
            String type = exercise.getExerciseType();
            Double pb = entry.getValue();

            System.out.println(name + ": " + pb + " for " + type +  reps);
        }
    }
}
