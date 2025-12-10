package com.fitness.tests;

import com.fitness.model.Exercise;

public class ExerciseTest {
    public static void run() {
        Exercise template = new Exercise("Squat", "reps", 5);
        assert template.getWeight() == 0.0;
        template.setWeight(100.0);
        assert template.getWeight() == 100.0;
        Exercise clone = new Exercise("Squat", "reps", 5);
        assert template.equals(clone);
    }

    public static void main(String[] args) {
        run();
        System.out.println("ExerciseTest passed");
    }
}
