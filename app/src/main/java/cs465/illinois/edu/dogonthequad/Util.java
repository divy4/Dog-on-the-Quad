package cs465.illinois.edu.dogonthequad;

import android.content.Intent;

import com.google.gson.Gson;

import cs465.illinois.edu.dogonthequad.DataModels.Meetup;

import static cs465.illinois.edu.dogonthequad.MapActivity.MEETUP_KEY;

public class Util {

    public static Meetup getMeetupFromIntent(Intent i) {
        String json = i.getStringExtra(MEETUP_KEY);
        return new Gson().fromJson(json, Meetup.class);
    }

    public static Intent addMeetupToIntent(Intent i, Meetup m) {
        String json = new Gson().toJson(m);
        i.putExtra(MEETUP_KEY, json);
        return i;
    }

}
