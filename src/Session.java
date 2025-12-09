import java.util.HashMap;

public class Session {
    private String name;
    private HashMap<Exercise, Integer> exerciseList; // exercise : rpe

    /**
     * Creates an empty session template with a name.
     */
    public Session(String name) {
        this.name = name;
        exerciseList = new HashMap<>();
    }

    /**
     * Creates a session containing an already built exercise list.
     */
    public Session(String name, HashMap<Exercise, Integer> exerciseList) {
        this.name = name;
        this.exerciseList = exerciseList;
    }

    // Getters and setters
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

    /**
     * Appends a single exercise entry with its associated RPE.
     */
    public void appendExerciseList(Exercise exercise, int reps) {
        this.exerciseList.put(exercise, reps);
    }

    /**
     * Prints the session name and contained exercises for troubleshooting.
     */
    @Override
    public String toString() {
        return "Session " +
                "name: '" + name + '\n' +
                ", exerciseList: " + exerciseList + '\n';
    }
}
