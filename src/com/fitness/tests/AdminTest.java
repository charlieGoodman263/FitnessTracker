package com.fitness.tests;

import com.fitness.model.Admin;

public class AdminTest {
    public static void run() {
        Admin admin = new Admin("0", "admin", "password");
        assert admin.getUserID() == 0;
        assert "admin".equals(admin.getUserName());
        admin.setUserName("head");
        assert "head".equals(admin.getUserName());
        // Simple getter/setter assertions keep the small model class exercise visible during testing.
        System.out.println("AdminTest passed");
    }

    public static void main(String[] args) {
        run();
    }
}
