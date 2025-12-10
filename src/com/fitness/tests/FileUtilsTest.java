package com.fitness.tests;

import com.fitness.model.Client;
import com.fitness.model.Exercise;
import com.fitness.model.Session;
import com.fitness.util.FileUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

public class FileUtilsTest {
    public static void run() throws Exception {
        String templateName = "test_template_" + System.nanoTime();
        Session template = new Session(templateName);
        template.appendExerciseList(new Exercise("Builder", "reps", 5), 5);
        assert FileUtils.saveTemplateSession(template);
        assert FileUtils.templateExists(templateName);
        assert FileUtils.deleteTemplate(templateName);
        assert !FileUtils.templateExists(templateName);

        String userId = "7777";
        Client client = new Client(userId, "filetest", "pw");
        HashMap<Exercise, Integer> results = new HashMap<>();
        Exercise exercise = new Exercise("Log", "reps", 6, 40.0);
        results.put(exercise, 8);
        FileUtils.saveSessionResult(client, "fileTest", results);
        Path dir = Paths.get("userSessions", userId);
        assert Files.exists(dir);
        TestHelpers.deleteUserDirectory(userId);
        System.out.println("FileUtilsTest passed");
    }

    public static void main(String[] args) throws Exception {
        run();
    }
}
