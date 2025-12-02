import java.util.ArrayList;
import java.util.HashMap;

public class Client extends User{
    private HashMap<Exercise, Double> personalBests;
    private ArrayList<Session> sessions;


    public Client(String userName, String password, String userID, HashMap<Exercise, Double> personalBests, ArrayList<Session> sessions) {
        super(userName, password, userID);
        this.personalBests = personalBests;
        this.sessions = sessions;
    }
    public Client(String userName, String password, String userID) {
        super(userName, password, userID);
        this.personalBests = new HashMap<>();
        this.sessions = new ArrayList<>();

    }

    // Getter & Setter
    public HashMap<Exercise, Double> getPersonalBests() {
        return personalBests;
    }
    public void newPersonalBest(Exercise exercise, Double best) {
        this.personalBests.put(exercise, best);
    }

    @Override
    public String toString() {
        return super.toString()
                + "Personal Bests: " + personalBests + "\n";
    }
}
