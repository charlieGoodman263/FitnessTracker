package com.fitness.tests;

import com.fitness.model.Admin;
import com.fitness.model.User;
import com.fitness.util.Users;

public class UsersTest {
    public static void run() {
        Users users = new Users();
        User admin = users.signIn("admin", "password");
        assert admin instanceof Admin;
        User found = users.findByUsername("admin");
        assert found != null;
    }

    public static void main(String[] args) {
        run();
        System.out.println("UsersTest passed");
    }
}
