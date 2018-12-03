package cs465.illinois.edu.dogonthequad;

import android.content.Intent;
import android.net.Uri;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import cs465.illinois.edu.dogonthequad.DataModels.Meetup;

import static cs465.illinois.edu.dogonthequad.MapActivity.MEETUP_KEY;

public class Util {

    private static String MAPS_ACTIVITY_FORMAT_STRING = "geo:%f,%f?q=%f,%f";

    public static Uri getMapsActivityUri(LatLng location) {
        return Uri.parse(String.format(
                Util.MAPS_ACTIVITY_FORMAT_STRING,
                location.latitude,
                location.longitude,
                location.latitude,
                location.longitude));
    }

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
