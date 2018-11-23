package cs465.illinois.edu.dogonthequad.DataModels;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.UUID;

import cs465.illinois.edu.dogonthequad.Dog;
import cs465.illinois.edu.dogonthequad.Meetup;
import cs465.illinois.edu.dogonthequad.User;

/**
 * MockAPI static class that we can use to get data as if it was returned by a server
 */
public class API {

    private static DataPreset currentPreset;
    private static ArrayList<DataPreset> presets;

    // Static fields to store string value of keys in json preset file
    private static final String MEETUPS_KEY = "meetups";
    private static final String DOGS_KEY = "dogs";
    private static final String USERS_KEY = "users";
    private static final String CURRENT_USER_KEY = "currentUserID";

    /**
     * Called once on application startup to create all presets and ready API for future calls
     */
    public static void loadData(Context ctx) throws IOException {
        // load in all preset data here
        presets = new ArrayList<>();
        ArrayList<String> presetFiles = getPresetFileNames(ctx);
        for (String presetFile : presetFiles) {
            DataPreset preset = generateEmptyPreset();
            try {
                preset = loadPreset(ctx, presetFile);
            } catch (Exception e) {
                Toast.makeText(ctx, "Error loading preset: " + presetFile + ", defaulting to empty preset", Toast.LENGTH_LONG).show();
            }
            presets.add(preset);
        }
        if (presets.size() > 0) {
            currentPreset = presets.get(0);
        } else {
            currentPreset = generateEmptyPreset();
        }
    }

    private static DataPreset generateEmptyPreset() {
        ArrayList<Dog> dogs = new ArrayList<>();
        ArrayList<Meetup> meetsup = new ArrayList<>();
        ArrayList<User> users = new ArrayList<>();
        User currentUser = new User();
        return new DataPreset(dogs, users, meetsup, currentUser);
    }

    private static ArrayList<String> getPresetFileNames(Context ctx) throws IOException {
        String[] fileNames = ctx.getAssets().list("");

        ArrayList<String> presetNames = new ArrayList<>();
        for (String filename : fileNames) {
            if (filename.contains("preset")) {
                presetNames.add(filename);
            }
        }

        return presetNames;
    }

    private static DataPreset loadPreset(Context ctx, String filename) throws IOException, IllegalArgumentException {
        InputStream is = ctx.getAssets().open(filename);
        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();
        String json = new String(buffer, "UTF-8");
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonObject preset = parser.parse(json).getAsJsonObject();

        // Get all meetups
        ArrayList<Meetup> meetups = new ArrayList<>();
        JsonArray meetsupsJSON = preset.getAsJsonArray(MEETUPS_KEY);
        for (JsonElement meetupJSON : meetsupsJSON) {
            meetups.add(gson.fromJson(meetupJSON, Meetup.class));
        }
        // Get all dogs
        ArrayList<Dog> dogs = new ArrayList<>();
        JsonArray dogsJSON = preset.getAsJsonArray(DOGS_KEY);
        for (JsonElement dogJSON : dogsJSON) {
            dogs.add(gson.fromJson(dogJSON, Dog.class));
        }
        // Get current user id
        JsonPrimitive currentUserMIDJSON = preset.getAsJsonPrimitive(CURRENT_USER_KEY);
        UUID currUserID = gson.fromJson(currentUserMIDJSON, UUID.class);
        // Get all users and set the current one
        ArrayList<User> users = new ArrayList<>();
        User currentUser = new User();
        JsonArray usersJSON = preset.getAsJsonArray(USERS_KEY);
        for (JsonElement userJSON : usersJSON) {
            User user = gson.fromJson(userJSON, User.class);
            if (user.getId().equals(currUserID)) {
                currentUser = user;
            }
        }
        return new DataPreset(dogs, users, meetups, currentUser);
    }

    /**
     * @return Returns all dogs in current preset
     */
    public static ArrayList<Dog> getDogs() {
        return currentPreset.getAllDogs();
    }

    /**
     * @return Returns all users in current preset
     */
    public static ArrayList<User> getUsers() {
        return currentPreset.getAllUsers();
    }

    /**
     * @return Returns all meetups in current preset
     */
    public static ArrayList<Meetup> getMeetups() {
        return currentPreset.getAllMeetups();
    }

    /**
     * @return Returns current user in preset
     */
    public static User getCurrentUser() {
        return currentPreset.getCurrentUser();
    }

}
