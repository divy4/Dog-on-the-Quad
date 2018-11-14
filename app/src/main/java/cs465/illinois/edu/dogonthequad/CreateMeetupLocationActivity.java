package cs465.illinois.edu.dogonthequad;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;

public class CreateMeetupLocationActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_meetup_location);

        Intent i = getIntent();
        String json = i.getStringExtra(MapActivity.MEETUP_KEY);
        Log.d(MapActivity.MEETUP_KEY, "In CreateMeetupLocation, received meetup: " + json);
        Meetup m = new Gson().fromJson(json, Meetup.class);
    }
}
