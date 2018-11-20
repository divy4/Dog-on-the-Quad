package cs465.illinois.edu.dogonthequad;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Vector;

import static cs465.illinois.edu.dogonthequad.MapActivity.MEETUP_KEY;

public class CreateMeetupActivity extends Activity implements View.OnClickListener{

    public static final Vector<Class> ACTIVITIES = new Vector(Arrays.asList(
        CreateMeetupLocationActivity.class,
        CreateMeetupEndTimeActivity.class,
        CreateMeetupSelectDogsActivity.class,
        CreateMeetupPhotoActivity.class,
        CreateMeetupReviewActivity.class,
        MeetupInProgressActivity.class
    ));

    public static final Vector<Class> ACTIVITIES_NOSELECTDOG = new Vector(Arrays.asList(
        CreateMeetupLocationActivity.class,
        CreateMeetupEndTimeActivity.class,
        CreateMeetupPhotoActivity.class,
        CreateMeetupReviewActivity.class,
        MeetupInProgressActivity.class
    ));

    public Meetup mMeetup;
    private Button mNextButton;
    private boolean mIsNextEnabled;

    /**
     * Sets up the CreateMeetupActivity
     * Handles the next button behavior and loading in the recieved Meetup from the intent.
     * @param resourceId opens the given layout resource file
     */
    protected void initialize(int resourceId) {
        setContentView(resourceId);

        //all CreateMeetupActivities begin by receiving an intent from the previous activity in the flow
        //the intent contains a meetup stored as JSON
        Intent i = getIntent();
        String json = i.getStringExtra(MEETUP_KEY);
        Log.d(MEETUP_KEY, "In CreateMeetup, received meetup: " + json);
        mMeetup = new Gson().fromJson(json, Meetup.class);

        enableNextButton();
        try {
            mNextButton = findViewById(R.id.meetup_next_button);
            mNextButton.setOnClickListener(this);

            if (mMeetup.inReview) {
                mNextButton.setText(R.string.return_to_edit);
            } else {
                mNextButton.setText(R.string.next);
            }
        } catch (RuntimeException e){
            Log.e(this.getLocalClassName(), "Unable to find nextButton, if this is not the review activity something is wrong");
        }

        try {
            ProgressBar progressBar = findViewById(R.id.meetup_progress_bar);
            progressBar.setProgress(getNextActivityIndex() * 100 / (getActivityList().size() - 1));
        } catch (RuntimeException e){
            Log.e(this.getLocalClassName(), "No progress bar found, cannot set progress");
        }
    }

    protected void enableNextButton() {
        mIsNextEnabled = true;
    }

    protected void disableNextButton() {
        mIsNextEnabled = false;
    }

    /**
     * Click listen handler for the next button
     * Opens the next activity, or the review activity if the meetup is in a review stage
     * @param view the clicked view
     */
    @Override
    public void onClick(View view) {
        if(mIsNextEnabled && view.getId() == R.id.meetup_next_button){
            Intent intent = new Intent(this, getNextActivity());
            //TODO if returning to the review screen, probably remove this screen and the old review screen. Otherwise, back button behavior will be wonky af
            String json = new Gson().toJson(mMeetup);
            intent.putExtra(MEETUP_KEY, json);
            startActivity(intent);
        }

    }

    public Class getNextActivity() {
        return getActivityList().get(getNextActivityIndex());
    }

    public int getNextActivityIndex() {
        Class subclass = this.getClass();
        int index = getActivityList().indexOf(subclass);

        if(index == -1){
            Log.e(this.getLocalClassName(), "Unable to find class in classlist");
        }

        return index + 1;
    }

    public Vector<Class> getActivityList() {
        return ACTIVITIES; //TODO include check for number of dogs in the current user's profile
    }
}
