package cs465.illinois.edu.dogonthequad;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;

import java.lang.reflect.Type;

import static cs465.illinois.edu.dogonthequad.MapActivity.MEETUP_KEY;

public class CreateMeetupActivity extends Activity implements View.OnClickListener{

    public Meetup mMeetup;
    public Class mNextActivity;

    /**
     * Sets up the CreateMeetupActivity
     * Handles the next button behavior and loading in the recieved Meetup from the intent.
     * @param resourceId opens the given layout resource file
     * @param nextActivity keeps track of the next activity in the create meetup flow
     */
    protected void initialize(int resourceId, Class nextActivity) {
        setContentView(resourceId);

        //all CreateMeetupActivities begin by receiving an intent from the previous activity in the flow
        //the intent contains a meetup stored as JSON
        Intent i = getIntent();
        String json = i.getStringExtra(MEETUP_KEY);
        Log.d(MEETUP_KEY, "In CreateMeetup, received meetup: " + json);
        mMeetup = new Gson().fromJson(json, Meetup.class);

        mNextActivity = nextActivity;

        try {
            Button nextButton = findViewById(R.id.meetup_next_button);
            nextButton.setOnClickListener(this);

            if (mMeetup.inReview) {
                nextButton.setText(R.string.return_to_edit);
            } else {
                nextButton.setText(R.string.next);
            }
        } catch (RuntimeException e){
            Log.e(this.getLocalClassName(), "Unable to find nextButton, if this is not the review activity something is wrong");
        }
    }

    /**
     * Click listen handler for the next button
     * Opens the next activity, or the review activity if the meetup is in a review stage
     * @param view the clicked view
     */
    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.meetup_next_button){
            Intent intent = new Intent(this, mMeetup.inReview ? CreateMeetupReviewActivity.class : mNextActivity);
            //TODO if returning to the review screen, probably remove this screen and the old review screen. Otherwise, back button behavior will be wonky af
            String json = new Gson().toJson(mMeetup);
            intent.putExtra(MEETUP_KEY, json);
            startActivity(intent);
        }

    }
}
