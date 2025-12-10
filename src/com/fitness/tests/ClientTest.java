package com.fitness.tests;

import com.fitness.model.Client;
import com.fitness.model.Exercise;

import java.util.HashMap;

public class ClientTest {
    public static void run() throws Exception {
        String userId = "8888";
        Client client = new Client(userId, "clienttest", "pw");
        Exercise exercise = new Exercise("Press", "reps", 8, 50.0);
        HashMap<Exercise, Integer> results = new HashMap<>();
        results.put(exercise, 8);
        client.recordSessionResult("clientTestSession", results);
        assert !client.getSessions().isEmpty();
        client.updatePersonalBest(exercise, 55.0);
        assert client.getPersonalBests().containsKey(exercise);
        TestHelpers.deleteUserDirectory(userId);
        // Logs and PB updates both hit disk, so we clean up to keep future test runs isolated.
        System.out.println("ClientTest passed");
    }

    public static void main(String[] args) throws Exception {
        run();
    }
}
