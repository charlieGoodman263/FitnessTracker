package com.fitness.menu;

import com.fitness.model.Client;
import com.fitness.model.Exercise;
import com.fitness.model.Session;
import com.fitness.util.FileUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ClientMenu {
    private Client client;

    /**
     * Holds the currently signed-in client for menu.
     */
    public ClientMenu(Client client) {
        this.client = client;
    }

    /**
     * Displays the options until the client signs out.
     */
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

    /**
     * Prints every session this client has logged.
     */
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
    
    /**
     * Lets the client walk through a shared template and log actual weights/RPE.
     */
    private void logSession(Scanner sc) {
        ArrayList<Session> templates = FileUtils.retrieveSharedSessionTemplates();
        if (templates.isEmpty()) {
            System.out.println("No session templates are available.");
            return;
        }
        // Shared templates let every client reuse the same workouts without duplicating files.

        System.out.println("Choose a session template to log:");
        for (int i = 0; i < templates.size(); i++) {
            System.out.println((i + 1) + ") " + templates.get(i).getName());
        }

        int selected = promptInt(sc, "Session number: ");
        if (selected < 1 || selected > templates.size()) {
            System.out.println("Invalid selection.");
            return;
        }

        Session template = templates.get(selected - 1);
        HashMap<Exercise, Integer> results = new HashMap<>();
        for (Exercise exercise : template.getExerciseList().keySet()) {
            System.out.println("Logging " + exercise.getExerciseName() + " (" + exercise.getExerciseType() + ": " + exercise.getReps() + ")");
            double weight = promptDouble(sc, "Weight used: ");
            int rpe = promptInt(sc, "RPE (1-10): ");

            Exercise completed = new Exercise(
                    exercise.getExerciseName(),
                    exercise.getExerciseType(),
                    exercise.getReps(),
                    weight);

            results.put(completed, rpe);
            client.updatePersonalBest(completed, weight); // Keep the PB map in sync with the latest logged weight.
        }

        client.recordSessionResult(template.getName(), results);
        System.out.println("Session logged and PBs updated where needed.");
    }

    /**
     * Helper that repeats a prompt until a valid integer is entered.
     */
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

    /**
     * Helper that repeats a prompt until a valid double is entered.
     */
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
    
    /**
     * Prints the client’s personal bests in a readable layout.
     */
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
