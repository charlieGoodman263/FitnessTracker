public class Exercise {
    private String exerciseName;
    private String exerciseType; // ie x repetitions or x seconds etc.
    private int reps;
    private double weight;

    // Constructors
    public Exercise(String exerciseName, String exerciseType, int reps) {
        this.exerciseName = exerciseName;
        this.exerciseType = exerciseType;
        this.reps = reps;
        this.weight = 0.0;
    }

    public Exercise(String exerciseName, String exerciseType, int reps, double weight) {
        this.exerciseName = exerciseName;
        this.exerciseType = exerciseType;
        this.reps = reps;
        this.weight = weight;
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

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Exercise " +
                "Name: '" + exerciseName + '\n' +
                "exerciseType: '" + exerciseType + '\n' +
                exerciseType + ": " + reps +
                " weight: " + weight;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Exercise) {
            Exercise exercise = (Exercise) obj;
            return this.exerciseName.equals(exercise.exerciseName) &&
                    this.exerciseType.equals(exercise.exerciseType) &&
                    this.reps == exercise.reps;
        }
        return false;
    }
}
