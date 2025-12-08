import java.util.ArrayList;
import java.util.HashMap;

public class Client extends User{
    private HashMap<Exercise, Double> personalBests;
    private ArrayList<Session> sessions;

    public Client(String userID, String userName, String password, HashMap<Exercise, Double> personalBests, ArrayList<Session> sessions) {
        super(userID, userName, password);
        this.personalBests = personalBests;
        this.sessions = sessions;
    }

    public Client(String userID, String userName, String password) {
        super(userID, userName, password);
        this.personalBests = new HashMap<>();
        this.sessions = new ArrayList<>();
        loadTrackingData();
    }

    public void loadTrackingData() {
        this.sessions = FileUtils.retrieveUserSessions(this);
        this.personalBests = FileUtils.retrievePersonalBests(this);
    }

    public void recordSessionResult(String sessionName, HashMap<Exercise, Integer> results) {
        FileUtils.saveSessionResult(this, sessionName, results);
        this.sessions = FileUtils.retrieveUserSessions(this);
    }

    public void updatePersonalBest(Exercise exercise, double weight) {
        Exercise key = findExerciseKey(exercise);
        double current = personalBests.getOrDefault(key, 0.0);
        if (weight > current) {
            personalBests.put(key, weight);
            FileUtils.savePersonalBests(this, personalBests);
        }
    }

    private Exercise findExerciseKey(Exercise exercise) {
        for (Exercise key : personalBests.keySet()) {
            if (key.equals(exercise)) {
                return key;
            }
        }
        return exercise;
    }

    // Getters & Setters
    public HashMap<Exercise, Double> getPersonalBests() {
        return personalBests;
    }

    public void newPersonalBest(Exercise exercise, Double best) {
        this.personalBests.put(exercise, best);
    }

    public ArrayList<Session> getSessions() {
        return sessions;
    }

    public void setSessions(ArrayList<Session> sessions) {
        this.sessions = sessions;
    }

    @Override
    public String toString() {
        return super.toString()
                + "Personal Bests: " + personalBests + "\n";
    }
}
