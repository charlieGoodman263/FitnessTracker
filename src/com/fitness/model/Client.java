package com.fitness.model;

import com.fitness.util.FileUtils;

import java.util.ArrayList;
import java.util.HashMap;

public class Client extends User{
    private HashMap<Exercise, Double> personalBests;
    private ArrayList<Session> sessions;

    /**
     * Creates a full client record from stored maps (used when loading existing users).
     */
    public Client(String userID, String userName, String password, HashMap<Exercise, Double> personalBests, ArrayList<Session> sessions) {
        super(userID, userName, password);
        this.personalBests = personalBests;
        this.sessions = sessions;
    }

    /**
     * Creates a new client account and immediately loads any persisted data.
     */
    public Client(String userID, String userName, String password) {
        super(userID, userName, password);
        this.personalBests = new HashMap<>();
        this.sessions = new ArrayList<>();
        loadTrackingData();
    }

    /**
     * Refreshes the in-memory sessions/PBs from root.
     */
    public void loadTrackingData() {
        this.sessions = FileUtils.retrieveUserSessions(this);
        this.personalBests = FileUtils.retrievePersonalBests(this);
    }

    /**
     * Saves the completed session and reloads the log list.
     */
    public void recordSessionResult(String sessionName, HashMap<Exercise, Integer> results) {
        FileUtils.saveSessionResult(this, sessionName, results);
        this.sessions = FileUtils.retrieveUserSessions(this);
    }

    /**
     * Updates PB state and saves it to disk when the incoming weight beats the stored value.
     */
    public void updatePersonalBest(Exercise exercise, double weight) {
        Exercise key = findExerciseKey(exercise);
        double current = personalBests.getOrDefault(key, 0.0);
        if (weight > current) {
            personalBests.put(key, weight);
            FileUtils.savePersonalBests(this, personalBests);
        }
    }

    /**
     * Returns the key for an exercise so existing PBs can be updated.
     */
    private Exercise findExerciseKey(Exercise exercise) {
        for (Exercise key : personalBests.keySet()) {
            if (key.equals(exercise)) {
                return key;
            }
        }
        return exercise;
    }

    // Getters and setters
    
    public HashMap<Exercise, Double> getPersonalBests() {
        return personalBests;
    }

    public void newPersonalBest(Exercise exercise, Double best) {
        this.personalBests.put(exercise, best);
    }

    public ArrayList<Session> getSessions() {
        return sessions;
    }

    public void setSessions(ArrayList<Session> sessions) {
        this.sessions = sessions;
    }
}
