package cs465.illinois.edu.dogonthequad;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;

import static cs465.illinois.edu.dogonthequad.MapActivity.MEETUP_KEY;

public class CreateMeetupLocationActivity extends CreateMeetupActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize(R.layout.activity_create_meetup_location);

        mMeetup.mLocation = new PointF(100, 200);
    }


}

