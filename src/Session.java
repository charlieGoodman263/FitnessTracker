import java.util.HashMap;

public class Session {
    private String name;
    private HashMap<Exercise, Integer> exerciseList; // exercise : rpe

    // Constructors
    public Session(String name) {
        this.name = name;
        exerciseList = new HashMap<>();
    }

    public Session(String name, HashMap<Exercise, Integer> exerciseList) {
        this.name = name;
        this.exerciseList = exerciseList;
    }

    // Getters & Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<Exercise, Integer> getExerciseList() {
        return exerciseList;
    }

    public void setExerciseList(HashMap<Exercise, Integer> exerciseList) {
        this.exerciseList = exerciseList;
    }

    public void appendExerciseList(Exercise exercise, int reps) {
        this.exerciseList.put(exercise, reps);
    }

    @Override
    public String toString() {
        return "Session " +
                "name: '" + name + '\n' +
                ", exerciseList: " + exerciseList + '\n';
    }
}
