import java.util.HashMap;

public class Client extends User{
    private HashMap<Exercise, Double> personalBests;

    public Client(String userName, String password, String userID, HashMap<Exercise, Double> personalBests) {
        super(userName, password, userID);
        this.personalBests = personalBests;
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
