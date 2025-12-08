import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ClientMenu {
    private Client client;

    public ClientMenu(Client client) {
        this.client = client;
    }

    public void run(Scanner sc) {
        while (true) {
            System.out.println("\n1) View sessions\n2) Log a session\n3) View personal bests\n4) Sign out");
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
        ArrayList<Session> sessions = client.getSessions();
        if (sessions.isEmpty()) {
            System.out.println("You don't have any session templates yet.");
            return;
        }

        System.out.println("Choose a session to log:");
        for (int i = 0; i < sessions.size(); i++) {
            System.out.println((i + 1) + ") " + sessions.get(i).getName());
        }

        int selected = promptInt(sc, "Session number: ");
        if (selected < 1 || selected > sessions.size()) {
            System.out.println("Invalid selection.");
            return;
        }

        Session template = sessions.get(selected - 1);
        HashMap<Exercise, Integer> results = new HashMap<>();
        for (Exercise exercise : template.getExerciseList().keySet()) {
            System.out.println("Logging " + exercise.getExerciseName() + " (" + exercise.getExerciseType()+ ": " + exercise.getReps() + ")");
            double weight = promptDouble(sc, "Weight used: ");
            int rpe = promptInt(sc, "RPE (1-10): ");

            Exercise completed = new Exercise(
                    exercise.getExerciseName(),
                    exercise.getExerciseType(),
                    exercise.getReps(),
                    weight);

            results.put(completed, rpe);
            client.updatePersonalBest(completed, weight);
        }

        client.recordSessionResult(template.getName(), results);
        System.out.println("Session logged and PBs updated where needed.");
    }

    private int promptInt(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
    }

    private double promptDouble(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid weight.");
            }
        }
    }
    
    private void viewPBs() {
        HashMap<Exercise, Double> pbs = client.getPersonalBests();

        if (pbs.isEmpty()) {
            System.out.println("No PBs yet.");
            return;
        }
        
        for (Map.Entry<Exercise, Double> entry : pbs.entrySet()) {
            Exercise exercise = entry.getKey();
            String name = exercise.getExerciseName();
            int reps = exercise.getReps();
            String type = exercise.getExerciseType();
            Double pb = entry.getValue();

            System.out.println(name + ": " + pb + " for " + reps + " " + type);
        }
    }
}
