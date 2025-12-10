package com.fitness.app;

import com.fitness.menu.AdminMenu;
import com.fitness.menu.ClientMenu;
import com.fitness.menu.SignIn;
import com.fitness.model.Client;
import com.fitness.model.User;

import java.util.Scanner;

public class Main {
    /**
     * Starts the whole proj, reusing a single {@link Scanner} so menus can loop until the user exits.
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            User user = SignIn.signIn(sc);
            if (user == null) {
                break;
            }

            if (user instanceof Client) {
                ClientMenu cm = new ClientMenu((Client) user);
                cm.run(sc);
            } else {
                AdminMenu.run(sc);
            }
        }
        sc.close();
    }
}
