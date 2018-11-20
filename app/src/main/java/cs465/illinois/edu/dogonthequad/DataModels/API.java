package cs465.illinois.edu.dogonthequad.DataModels;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
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
    private static final String CURRENT_USER_KEY = "currentUser";

    /**
     * Called once on application startup to create all presets and ready API for future calls
     */
    public static void loadData(Context ctx) {
        // load in all preset data here

    }

    private ArrayList<String> getPresetFileNames(Context ctx) throws IOException {
        String[] fileNames = ctx.getAssets().list("");

        ArrayList<String> presetNames = new ArrayList<>();
        for (String filename : fileNames) {
            if (filename.contains("preset")) {
                presetNames.add(filename);
            }
        }

        return presetNames;
    }

    private DataPreset loadPreset(Context ctx, String filename) throws IOException, JSONException {
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
        int mID = preset.getAsJsonObject(CURRENT_USER_KEY).getAsInt();
        UUID currUserID = new UUID(0, mID);
        // Get all users and set the current one
        ArrayList<User> users = new ArrayList<>();
        User currentUser = new User();
        JsonArray usersJSON = preset.getAsJsonArray(USERS_KEY);
        for (JsonElement userJSON : usersJSON) {
            User user = gson.fromJson(userJSON, User.class);
            if (user.getmId().equals(currUserID)) {
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

}
