package cs465.illinois.edu.dogonthequad;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.IntentCompat;
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
            mNextButton = findViewById(R.id.confirm_button);
        }
        mNextButton.setOnClickListener(this);

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
        if(mIsNextEnabled && (view.getId() == R.id.meetup_next_button || view.getId() == R.id.confirm_button)) {
            gotoNextActivity(getNextActivity());
        }
    }

    private void gotoNextActivity(Class nextActivity) {
        if (isClearingActivity(nextActivity)) {
            clearPreviousActivities();
        }
        Intent intent = new Intent(this, nextActivity);
        String json = new Gson().toJson(mMeetup);
        intent.putExtra(MEETUP_KEY, json);
        startActivity(intent);
    }

    private void clearPreviousActivities() {
        Intent intent = new Intent(this, MapActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    private Class getNextActivity() {
        return getActivityList().get(getNextActivityIndex());
    }

    private boolean isClearingActivity(Class activity) {
        return activity == MeetupInProgressActivity.class;
    }

    private int getNextActivityIndex() {
        Class subclass = this.getClass();
        int index = getActivityList().indexOf(subclass);
        if (index == -1){
            Log.e(this.getLocalClassName(), "Unable to find class in classlist");
        }
        return index + 1;
    }

    private Vector<Class> getActivityList() {
        return ACTIVITIES; //TODO include check for number of dogs in the current user's profile
    }
}
