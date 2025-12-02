public class Exercise {
    private String exerciseName;
    private String exerciseType; // ie x repetitions or x seconds etc.
    private int reps;

    // Constructor
    public Exercise(String exerciseName, String exerciseType, int reps) {
        this.exerciseName = exerciseName;
        this.exerciseType = exerciseType;
        this.reps = reps;
    }

    // Getters & Setters
    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public String getExerciseType() {
        return exerciseType;
    }

    public void setExerciseType(String exerciseType) {
        this.exerciseType = exerciseType;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    @Override
    public String toString() {
        return "Exercise " +
                "Name: '" + exerciseName + '\n' +
                "exerciseType: '" + exerciseType + '\n' +
                exerciseType + ": " + reps;
    }
}
