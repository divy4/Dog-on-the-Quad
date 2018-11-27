package cs465.illinois.edu.dogonthequad.DataModels;

import java.util.ArrayList;

/**
 * DataPreset class contains all information representing the mState of the current application
 */
public class DataPreset {

    private ArrayList<Dog> dogs;
    private ArrayList<User> users;
    private ArrayList<Meetup> meetups;
    private User currentUser;

    public DataPreset(ArrayList<Dog> dogs, ArrayList<User> users, ArrayList<Meetup> meetups, User currentUser) {
        this.dogs = dogs;
        this.users = users;
        this.meetups = meetups;
        this.currentUser = currentUser;
    }

    public ArrayList<Dog> getAllDogs() {
        return dogs;
    }

    public ArrayList<User> getAllUsers() {
        return users;
    }

    public ArrayList<Meetup> getAllMeetups() {
        return meetups;
    }

    public User getCurrentUser() {
        return currentUser;
    }

}
