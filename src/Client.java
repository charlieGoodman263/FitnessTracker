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
