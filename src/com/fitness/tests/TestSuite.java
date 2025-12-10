package com.fitness.tests;

public class TestSuite {
    public static void main(String[] args) throws Exception {
        AdminTest.run();
        UserTest.run();
        ExerciseTest.run();
        SessionTest.run();
        FileUtilsTest.run();
        ClientTest.run();
        UsersTest.run();
        System.out.println("All tests executed");
        // Central runner to execute every small unit test in one pass.
    }
}
