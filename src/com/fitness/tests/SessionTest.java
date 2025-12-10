package com.fitness.tests;

import com.fitness.model.Exercise;
import com.fitness.model.Session;

import java.util.HashMap;

public class SessionTest {
    public static void run() {
        Session session = new Session("Template");
        Exercise exercise = new Exercise("Press", "reps", 8);
        session.appendExerciseList(exercise, 8);
        HashMap<Exercise, Integer> list = session.getExerciseList();
        assert list.containsKey(exercise);
        session.setName("Updated");
        assert "Updated".equals(session.getName());
        assert session.getAverageRpe() == 8.0;
    }

    public static void main(String[] args) {
        run();
        System.out.println("SessionTest passed");
    }
}
