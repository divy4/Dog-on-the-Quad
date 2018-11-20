package cs465.illinois.edu.dogonthequad;

import android.app.Application;

import org.json.JSONException;

import java.io.IOException;

import cs465.illinois.edu.dogonthequad.DataModels.API;

// This class is required so that can load in all the API data at app launch
public class DogQuadApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Call API.loadData once to load in all presets
        try {
            API.loadData(this.getApplicationContext());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
