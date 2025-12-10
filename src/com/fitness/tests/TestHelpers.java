package com.fitness.tests;

import java.io.File;

class TestHelpers {
    static void deleteUserDirectory(String userId) {
        File dir = new File("userSessions", userId);
        deleteRecursively(dir);
    }

    private static void deleteRecursively(File file) {
        if (!file.exists()) {
            return;
        }
        if (file.isDirectory()) {
            File[] children = file.listFiles();
            if (children != null) {
                for (File child : children) {
                    deleteRecursively(child);
                }
            }
        }
        file.delete();
    }
}
