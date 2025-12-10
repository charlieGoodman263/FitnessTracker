package com.fitness.menu;

import com.fitness.model.User;
import com.fitness.util.Users;

import java.util.Scanner;

public class SignIn {
    /**
     * Prompts for credentials until a successful sign-in/sign-up or exit.
     */
    public static User signIn(Scanner sc) {
        Users users = new Users();
        System.out.println("Welcome!");
        while (true) {
            System.out.print("Sign in (1), Sign up (2), or Exit (3): ");
            String userChoice = sc.nextLine().trim();
            switch (userChoice) {
                case "1":
                    System.out.print("Username: ");
                    String userName = sc.nextLine().trim();
                    System.out.print("Password: ");
                    String password = sc.nextLine().trim();
                    try {
                        return users.signIn(userName, password);
                    } catch (RuntimeException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "2":
                    users.signUp(sc);
                    break;
                case "3":
                    return null;
                default:
                    System.out.println("Please enter 1, 2, or 3.");
            }
        }
    }
}
