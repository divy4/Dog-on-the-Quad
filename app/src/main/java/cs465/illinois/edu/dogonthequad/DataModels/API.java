package cs465.illinois.edu.dogonthequad.DataModels;

import java.util.ArrayList;

import cs465.illinois.edu.dogonthequad.Dog;

/**
 * MockAPI static class that we can use to get data as if it was returned by a server
 */
public class API {

    private static DataPreset currentPreset;
    private static ArrayList<DataPreset> presets;

    /**
     * Called once on application startup to create all presets and ready API for future calls
     */
    public static void loadData() {
        // load in an empty preset data here

    }

    /**
     *
     * @return
     */
    public static ArrayList<Dog> getDogs() {
        return currentPreset.getAllDogs();
    }

}
