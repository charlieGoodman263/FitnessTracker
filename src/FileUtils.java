import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class FileUtils {
    public static void appendUser(String appendedText) {
        try (FileWriter myWriter = new FileWriter("userLoginInfo.txt", true)) {
            myWriter.write("\n" + appendedText);
            //System.out.println("Successfully appended to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred: " + e);
        }
    }

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

    public static ArrayList<Session> retrieveUserSessions(Client client) {
        File dir = ensureUserDir(client);
        File[] files = dir.listFiles();
        ArrayList<Session> sessions = new ArrayList<>();

        if (files == null || files.length == 0) {
            return sessions;
        }

        for (File file : files) {
            if (!file.isFile() || file.getName().equals("pbs.txt")) {
                continue;
            }
            ArrayList<String> lines = new ArrayList<>();
            try (Scanner reader = new Scanner(file)) {
                while (reader.hasNextLine()) {
                    lines.add(reader.nextLine());
                }
            } catch (FileNotFoundException ignored) {
                continue;
            }

            HashMap<Exercise, Integer> exercises = new HashMap<>();
            for (int i = 1; i < lines.size(); i++) {
                String[] parts = lines.get(i).split(":");
                String[] metadata = parts[0].split(",");
                int reps = Integer.parseInt(metadata[2]);
                double weight = metadata.length > 3 ? Double.parseDouble(metadata[3]) : 0.0;
                int rpe = Integer.parseInt(parts[1]);
                exercises.put(new Exercise(metadata[0], metadata[1], reps, weight), rpe);
            }

            String fileName = file.getName();
            int dot = fileName.lastIndexOf('.');
            String sessionName = dot > 0 ? fileName.substring(0, dot) : fileName;
            sessions.add(new Session(sessionName, exercises));
        }
        return sessions;
    }

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
                String[] halves = line.split(":", 2);
                String[] metadata = halves[0].split(",");
                double weight = Double.parseDouble(halves[1]);
                int reps = Integer.parseInt(metadata[2]);
                pbs.put(new Exercise(metadata[0], metadata[1], reps), weight);
            }
        } catch (FileNotFoundException ignored) {
        }
        return pbs;
    }

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

    public static void saveSessionResult(Client client, String sessionName, HashMap<Exercise, Integer> results) {
        File dir = ensureUserDir(client);
        String filename = String.format("%s-%d.txt", sessionName, System.currentTimeMillis());
        File sessionFile = new File(dir, filename);
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

    private static File ensureUserDir(Client client) {
        File dir = new File("userSessions/" + client.getUserID());
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }
}
