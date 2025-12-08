import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
        int userId = client.getUserID();
        ArrayList<String> sessionNames = new ArrayList<>();
        // try-catch block to handle exceptions
        try {
            File curDir = new File("./userSessions/" + userId);
            File[] filesList = curDir.listFiles();

            if (filesList == null || filesList.length == 0) {
                return new ArrayList<>(); // no sessions found
            } 

            for(File f : filesList){ //  collects all the session logs in the user's directory
                if(f.isFile()) {
                    String name = f.getName();
                    int dot = name.lastIndexOf('.');

                    sessionNames.add(dot > 0 ? name.substring(0, dot) : name); // adds the session to sessionNames without the file extension (or if there is none then just the name) 
                }
            }
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }

        File userData;
        ArrayList<String> lines;
        ArrayList<Session> sessions = new ArrayList<>();

        for (String sessionName : sessionNames) {
            userData = new File("./userSessions/" + userId + "/" + sessionName); // looks through each session file
            lines = new ArrayList<>();

            try (Scanner myReader = new Scanner(userData)) {
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    lines.add(data); // arraylist full of all the info on that line
                }
            } catch (FileNotFoundException e) {
                System.err.println(e.getMessage());
            }

            HashMap<Exercise, Integer> exercises = new HashMap<Exercise, Integer>(); // hashmap of exercise against rpe (same format as that of the session class)
            for (int i = 1; i < lines.size(); i++) { // indexed from 1 as line 0 is used as a template
                String[] sessionInfo = lines.get(i).split("[:,]"); // splits each line by colons and commas into an array
                exercises.put(
                        new Exercise(
                                sessionInfo[0],
                                sessionInfo[1],
                                Integer.parseInt(sessionInfo[2])),
                        Integer.valueOf(sessionInfo[3])); // adds all the info in the form that the session constructor takes into the hashmap
            }
            sessions.add(new Session(sessionName, exercises));
        }
        return sessions;
    }
}
