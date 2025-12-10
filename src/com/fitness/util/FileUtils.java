package com.fitness.util;

import com.fitness.model.Client;
import com.fitness.model.Exercise;
import com.fitness.model.Session;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileUtils {
    private static final Pattern logFilePattern = Pattern.compile("^(.+)-(\\d+)\\.txt$");
    private static final String renamePath = "userSessions/templates";
    private static final String templateExtension = ".txt";

    /**
     * Appends a user entry to `userLoginInfo.txt`.
     *
     * @param appendedText already-formatted id:username:password line.
     */
    public static void appendUser(String appendedText) {
        try (FileWriter myWriter = new FileWriter("userLoginInfo.txt", true)) {
            myWriter.write("\n" + appendedText);
        } catch (IOException e) {
            System.out.println("An error occurred: " + e);
        }
    }

    /**
     * Returns every line from the login file so callers can parse the saved users.
     */
    public static ArrayList<String> retrieveUsers() {
        File userData = new File("userLoginInfo.txt");
        ArrayList<String> ret = new ArrayList<>();

        try (Scanner myReader = new Scanner(userData)) {
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                ret.add(data);
            }
        } catch (FileNotFoundException e) {
            System.out.println("No user data, creating directory now...");
            try {
                userData.createNewFile();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        return ret;
    }

    /**
     * Reads the logged session files for a client (ignoring templates) and returns them as {@link Session} objects.
     */
    public static ArrayList<Session> retrieveUserSessions(Client client) {
        File dir = ensureUserDir(client);
        File[] files = dir.listFiles();
        ArrayList<Session> sessions = new ArrayList<>();

        if (files == null) {
            return sessions;
        }
        
        Arrays.sort(files, (a, b) -> a.getName().compareTo(b.getName()));

        for (File file : files) {
            if (!file.isFile() || !isLogFile(file.getName())) {
                continue;
            }
            // Guard to only get timestamped logs so templates remain separated from history.
            Session session = parseSessionFile(file, false);
            if (session != null) {
                sessions.add(session);
            }
        }
        return sessions;
    }

    /**
     * Returns every template defined inside the shared templates directory.
     */
    public static ArrayList<Session> retrieveSharedSessionTemplates() {
        File dir = ensureTemplatesDir();
        File[] files = dir.listFiles();
        ArrayList<Session> templates = new ArrayList<>();

        if (files == null) {
            return templates;
        }

        for (File file : files) {
            if (!file.isFile()) {
                continue;
            }
            Session session = parseSessionFile(file, true);
            if (session != null) {
                templates.add(session);
            }
        }
        return templates;
    }

    /**
     * Stores a template session under the shared catalog so clients can log it later.
     */
    public static boolean saveTemplateSession(Session session) {
        File templateFile = templateFile(session.getName());
        try (FileWriter writer = new FileWriter(templateFile, false)) {
            writer.write("exerciseName,exerciseType,reps,weight:RPE\n");
            // Store the template in the same format as completed logs so parsing stays simple.
            for (Map.Entry<Exercise, Integer> entry : session.getExerciseList().entrySet()) {
                Exercise exercise = entry.getKey();
                writer.write(String.format("%s,%s,%d,%.2f:%d%n",
                        exercise.getExerciseName(),
                        exercise.getExerciseType(),
                        exercise.getReps(),
                        exercise.getWeight(),
                        entry.getValue()));
            }
            return true;
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    /**
     * Removes a template file by name.
     */
    public static boolean deleteTemplate(String sessionName) {
        File file = templateFile(sessionName);
        return file.exists() && file.delete();
    }

    /**
     * Loads the personal best file for a client into memory.
     */
    public static HashMap<Exercise, Double> retrievePersonalBests(Client client) {
        File dir = ensureUserDir(client);
        File pbFile = new File(dir, "pbs.txt");
        HashMap<Exercise, Double> pbs = new HashMap<>();

        if (!pbFile.exists()) {
            return pbs;
        }

        try (Scanner reader = new Scanner(pbFile)) {
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                if (line.isEmpty()) {
                    continue;
                }
            String[] halves = line.split(":", 2);
            if (halves.length < 2) {
                continue;
            }
            String[] metadata = halves[0].split(",");
            if (metadata.length < 3) {
                continue;
            }
            int reps = Integer.parseInt(metadata[2]);
            double weight = Double.parseDouble(halves[1]);
            // Keep PB info as a map.
            pbs.put(new Exercise(metadata[0], metadata[1], reps), weight);
            }
        } catch (FileNotFoundException ignored) {
        }
        return pbs;
    }

    /**
     * Saves the PB map.
     */
    public static void savePersonalBests(Client client, HashMap<Exercise, Double> personalBests) {
        File dir = ensureUserDir(client);
        File pbFile = new File(dir, "pbs.txt");
        try (FileWriter writer = new FileWriter(pbFile, false)) {
            for (Map.Entry<Exercise, Double> entry : personalBests.entrySet()) {
                Exercise exercise = entry.getKey();
                writer.write(String.format("%s,%s,%d:%f%n",
                        exercise.getExerciseName(),
                        exercise.getExerciseType(),
                        exercise.getReps(),
                        entry.getValue()));
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Saves a completed session log for the client with a timestamped filename.
     */
    public static void saveSessionResult(Client client, String sessionName, HashMap<Exercise, Integer> results) {
        File dir = ensureUserDir(client);
        String filename = String.format("%s-%d.txt", sessionName, System.currentTimeMillis());
        File sessionFile = new File(dir, filename);
        // Timestamped file names keep logs unique and sortable.
        try (FileWriter writer = new FileWriter(sessionFile, false)) {
            writer.write("exerciseName,exerciseType,reps,weight:RPE\n");
            for (Map.Entry<Exercise, Integer> entry : results.entrySet()) {
                Exercise exercise = entry.getKey();
                writer.write(String.format("%s,%s,%d,%.2f:%d%n",
                        exercise.getExerciseName(),
                        exercise.getExerciseType(),
                        exercise.getReps(),
                        exercise.getWeight(),
                        entry.getValue()));
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Reconstructs a {@link Session} from a flat-text file.
     *
     * @param template true when the file lives under templates rather than logs.
     */
    private static Session parseSessionFile(File file, boolean template) {
        ArrayList<String> lines = new ArrayList<>();
        try (Scanner reader = new Scanner(file)) {
            while (reader.hasNextLine()) {
                lines.add(reader.nextLine());
            }
        } catch (FileNotFoundException e) {
            return null;
        }

        if (lines.size() <= 1) {
            return null;
        }

        HashMap<Exercise, Integer> exercises = new HashMap<>();
        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (line.isEmpty()) {
                continue;
            }
            String[] parts = line.split(":");
            // Each line follows the 'meta:RPE' format so split into the exercise metadata and the RPE value separately.
            if (parts.length < 2) {
                continue;
            }
            String[] metadata = parts[0].split(",");
            if (metadata.length < 3) {
                continue;
            }
            int reps = Integer.parseInt(metadata[2]);
            double weight = metadata.length > 3 ? Double.parseDouble(metadata[3]) : 0.0;
            int rpe = Integer.parseInt(parts[1]);
            exercises.put(new Exercise(metadata[0], metadata[1], reps, weight), rpe);
        }

        if (exercises.isEmpty()) {
            return null;
        }

        String sessionName = determineSessionName(file.getName(), template);
        return new Session(sessionName, exercises);
    }

    /**
     * Gets the session display name depending on whether we are parsing a template or a log.
     */
    private static String determineSessionName(String fileName, boolean template) {
        if (template) {
            return removeExtension(fileName);
        }
        Matcher matcher = logFilePattern.matcher(fileName);
        if (matcher.matches()) {
            return matcher.group(1);
        }
        return removeExtension(fileName);
    }

    /**
     * Removes the file extension from a name.
     */
    private static String removeExtension(String fileName) {
        int dot = fileName.lastIndexOf('.');
        return dot > 0 ? fileName.substring(0, dot) : fileName;
    }

    /**
     * Returns true when a filename matches the timestamped log convention.
     */
    private static boolean isLogFile(String fileName) {
        return logFilePattern.matcher(fileName).matches();
    }

    /**
     * Ensures the client-specific directory under `userSessions` exists.
     */
    private static File ensureUserDir(Client client) {
        File dir = new File("userSessions/" + client.getUserID());
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    /**
     * Creates the shared templates directory if it is missing.
     */
    private static File ensureTemplatesDir() {
        File dir = new File(renamePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    /**
     * Resolves the filesystem path for a shared template.
     */
    private static File templateFile(String sessionName) {
        String safeName = sanitizeTemplateName(sessionName);
        return new File(ensureTemplatesDir(), safeName + templateExtension);
    }

    /**
     * Converts text into a safe filename for templates.
     */
    private static String sanitizeTemplateName(String name) {
        String sanitized = name.trim().replaceAll("[^a-zA-Z0-9_-]", "_");
        return sanitized.isEmpty() ? "session_template" : sanitized;
    }

    /**
     * Checks whether a template already exists.
     */
    public static boolean templateExists(String sessionName) {
        return templateFile(sessionName).exists();
    }
}
