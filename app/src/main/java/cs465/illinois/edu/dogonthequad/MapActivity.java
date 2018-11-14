package cs465.illinois.edu.dogonthequad;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.Console;

public class MapActivity extends Activity implements View.OnClickListener {

    public static final String MEETUP_KEY = "meetup";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        Button createMeetupButton = (Button) findViewById(R.id.create_meetup_button);
        createMeetupButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.create_meetup_button) {
            // Launch the create meetup location activity
            Intent intent = new Intent(this, CreateMeetupLocation.class);
            Meetup meetup = new Meetup();
            String json = new Gson().toJson(meetup);
            Log.d(MEETUP_KEY,"Starting meetup creation, " + json);
            intent.putExtra(MEETUP_KEY, json);
            startActivity(intent);
        }
    }
}
