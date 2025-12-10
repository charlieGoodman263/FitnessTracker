package com.fitness.tests;

import com.fitness.model.User;

public class UserTest {
    public static void run() {
        User user = new User("7", "tester", "pass");
        assert user.getUserID() == 7;
        assert "tester".equals(user.getUserName());
        user.setUserName("renamed");
        assert "renamed".equals(user.getUserName());
        user.setPassword("newpass");
        assert "newpass".equals(user.getPassword());
        System.out.println("UserTest passed");
    }

    public static void main(String[] args) {
        run();
    }
}
