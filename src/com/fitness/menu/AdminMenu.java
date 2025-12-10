package com.fitness.menu;

import com.fitness.model.Client;
import com.fitness.model.Exercise;
import com.fitness.model.Session;
import com.fitness.model.User;
import com.fitness.util.FileUtils;
import com.fitness.util.Users;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class AdminMenu {
    /**
     * Main menu loop exposed to admins.
     */
    public static void run(Scanner sc) {
        Users users = new Users();
        while (true) {
            System.out.println("\n1) List users\n2) View a client's sessions\n3) Manage templates\n4) Sign out");
            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1":
                    System.out.println(users);
                    break;
                case "2":
                    viewClientSessions(sc, users);
                    break;
                case "3":
                    manageTemplates(sc);
                    break;
                case "4":
                    return;
                default:
                    System.out.println("Choose 1-4");
            }
        }
    }

    /**
     * Prints the selected client’s sessions and PBs.
     */
    private static void viewClientSessions(Scanner sc, Users users) {
        System.out.print("Enter client username: ");
        String target = sc.nextLine().trim();
        User user = users.findByUsername(target);
        if (!(user instanceof Client)) {
            System.out.println("Not a client or not found.");
            return;
        }

        Client client = (Client) user;
        ArrayList<Session> sessions = client.getSessions(); 
        // Reusing the client's cached sessions keeps the admin view consistent with what the client sees.
        if (sessions.isEmpty()) {
            System.out.println("No sessions logged.");
        } else {
            for (Session session : sessions) {
                System.out.println("- " + session.getName() + ": " + session.getExerciseList().size() + " exercises. Average RPE: " + String.format("%.2f", session.getAverageRpe()) );
            }
        }
        HashMap<Exercise, Double> pbs = client.getPersonalBests();
        // Listing PBs alongside the sessions gives a quick picture of their performances.
        
        System.out.println("PBs: " + pbs);
    }

    /**
     * Menu that allows template inspection, creation, and removal.
     */
    private static void manageTemplates(Scanner sc) {
        while (true) {
            System.out.println("\nTemplate Manager");
            System.out.println("1) List templates");
            System.out.println("2) Add template");
            System.out.println("3) Remove template");
            System.out.println("4) Back");
            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1":
                    listTemplates();
                    break;
                case "2":
                    addTemplate(sc);
                    break;
                case "3":
                    removeTemplate(sc);
                    break;
                case "4":
                    return;
                default:
                    System.out.println("Choose 1-4");
            }
        }
    }

    /**
     * Displays all templates currently stored in the root.
     */
    private static void listTemplates() {
        ArrayList<Session> templates = FileUtils.retrieveSharedSessionTemplates();
        if (templates.isEmpty()) {
            System.out.println("No templates available.");
            return;
        }
        for (int i = 0; i < templates.size(); i++) {
            // Display the shared templates so admins can manage the catalog easily.
            System.out.println((i + 1) + ") " + templates.get(i).getName());
        }
    }

    /**
     * Creates a new shared template by prompting the user.
     */
    private static void addTemplate(Scanner sc) {
        String name = promptNonEmpty(sc, "Template name: ");
        if (FileUtils.templateExists(name)) {
            System.out.print("Template already exists, overwrite (y/n)? ");
            String answer = sc.nextLine().trim().toLowerCase();
            if (!answer.equals("y") && !answer.equals("yes")) {
                System.out.println("Canceled.");
                return;
            }
        }
        int count = promptInt(sc, "Number of exercises: ");
        HashMap<Exercise, Integer> exercises = new HashMap<>();
        for (int i = 0; i < count; i++) {
            // Capture exercise details so the template can replay the workout structure later.
            System.out.println("\nExercise " + (i + 1));
            String exerciseName = promptNonEmpty(sc, "Name: ");
            String type = promptNonEmpty(sc, "Type (e.g., reps or seconds): ");
            int reps = promptInt(sc, "Target reps: ");
            double weight = promptDouble(sc, "Suggested weight: ");
            int rpe = promptInt(sc, "Suggested RPE (0 if unset): ");
            Exercise exercise = new Exercise(exerciseName, type, reps, weight);
            exercises.put(exercise, rpe);
        }
        Session template = new Session(name, exercises);
        if (FileUtils.saveTemplateSession(template)) {
            System.out.println("Template saved.");
        } else {
            System.out.println("Failed to save template.");
        }
    }

    /**
     * Deletes a template by selecting it from the list.
     */
    private static void removeTemplate(Scanner sc) {
        ArrayList<Session> templates = FileUtils.retrieveSharedSessionTemplates();
        if (templates.isEmpty()) {
            System.out.println("No templates to remove.");
            return;
        }
        listTemplates();
        int selected = promptInt(sc, "Template number to remove: ");
        if (selected < 1 || selected > templates.size()) {
            System.out.println("Invalid selection.");
            return;
        }
        Session template = templates.get(selected - 1);
        // Use the shared template name so the associated file is deleted correctly.
        if (FileUtils.deleteTemplate(template.getName())) {
            System.out.println("Removed template " + template.getName());
        } else {
            System.out.println("Could not remove template.");
        }
    }

    /**
     * Repeats a prompt until the admin provides a non-empty response.
     */
    private static String promptNonEmpty(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = sc.nextLine().trim();
            if (!line.isEmpty()) {
                return line;
            }
            System.out.println("Value cannot be empty.");
        }
    }

    /**
     * Keeps prompting until a valid integer is provided.
     */
    private static int promptInt(Scanner sc, String prompt) {
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
     * Keeps prompting until a valid double is provided.
     */
    private static double promptDouble(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}
