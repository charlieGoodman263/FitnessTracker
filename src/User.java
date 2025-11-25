import java.util.HashMap;

public class User {
    private String userName;
    private String password;
    private HashMap<Exercise, Double> personalBests;

    // Constructors
    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
        this.personalBests = new HashMap<Exercise, Double>();
    }

    // Getters & Setters
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public HashMap<Exercise, Double> getPersonalBests() {
        return personalBests;
    }

    public String toString() {
        return "Username: " + userName + "\n"
                + "Password: " + password + "\n"
                + "Personal Bests: " + personalBests + "\n";
    }

    public void newPersonalBest(Exercise exercise, Double best) {
        this.personalBests.put(exercise, best);
    }
}
