package cs465.illinois.edu.dogonthequad;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import java.util.Arrays;
import java.util.Vector;

import cs465.illinois.edu.dogonthequad.DataModels.Meetup;
import cs465.illinois.edu.dogonthequad.DataModels.MeetupState;

public class CreateMeetupActivity extends Activity implements View.OnClickListener{

    public static final Vector<Class> ACTIVITIES = new Vector(Arrays.asList(
        CreateMeetupLocationActivity.class,
        CreateMeetupEndTimeActivity.class,
        CreateMeetupSocialLevelActivity.class,
        CreateMeetupSelectDogsActivity.class,
        CreateMeetupPhotoActivity.class,
        CreateMeetupReviewActivity.class,
        MeetupInProgressActivity.class
    ));

    public static final Vector<Class> ACTIVITIES_NOSELECTDOG = new Vector(Arrays.asList(
        CreateMeetupLocationActivity.class,
        CreateMeetupEndTimeActivity.class,
        CreateMeetupSocialLevelActivity.class,
        CreateMeetupPhotoActivity.class,
        CreateMeetupReviewActivity.class,
        MeetupInProgressActivity.class
    ));

    public static final int REQUEST_EDIT = 1;

    public Meetup mMeetup;
    private Button mBackButton;
    private Button mNextButton;

    /**
     * Sets up the CreateMeetupActivity
     * Handles the next button behavior and loading in the recieved Meetup from the intent.
     * @param resourceId opens the given layout resource file
     */
    protected void initialize(int resourceId) {
        setContentView(resourceId);

        // load meetup from previous activity
        mMeetup = Util.getMeetupFromIntent(getIntent());

        mBackButton = findViewById(R.id.back_button);
        if (mBackButton == null) {
            mBackButton = findViewById(R.id.cancel_button);
        }
        if (mBackButton != null) {
            mBackButton.setOnClickListener(this);
        }

        try {
            mNextButton = findViewById(R.id.meetup_next_button);
            if (mMeetup.mState == MeetupState.edit) {
                mNextButton.setText(R.string.return_to_edit);
            } else {
                mNextButton.setText(R.string.next);
            }
        } catch (RuntimeException e){
            mNextButton = findViewById(R.id.confirm_button);
        }

        if(mNextButton != null){
            mNextButton.setOnClickListener(this);
        }

        try {
            ProgressBar progressBar = findViewById(R.id.meetup_progress_bar);
            int progress;
            switch (mMeetup.mState) {
                case edit:
                    progress = (getActivityList().size() - 2) * 100 / (getActivityList().size() - 1);
                    break;
                default:
                    progress = getNextActivityIndex() * 100 / (getActivityList().size() - 1);
            }
            progressBar.setProgress(progress);
        } catch (RuntimeException e){
            Log.e(this.getLocalClassName(), "No progress bar found, cannot set progress");
        }
    }

    /**
     * Click listen handler for the next button
     * Opens the next activity, or the review activity if the meetup is in a review stage
     * @param view the clicked view
     */
    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.meetup_next_button || view.getId() == R.id.confirm_button) {
            executeNext();
        } else if (view.getId() == R.id.back_button) {
            executeBack();
        }
    }

    protected void executeNext() {
        switch (mMeetup.mState) {
            case setup:
            case review:
                gotoNextActivity();
                break;
            case edit:
                endActivityWithResult();
                break;
            default:
                throw new RuntimeException("Invalid meetup state!");
        }
    }

    protected void executeBack() {
        switch (mMeetup.mState) {
            case setup:
            case review:
                endActivityWithoutResult();
                break;
            case edit:
                endActivityWithResult();
                break;
            default:
                throw new RuntimeException("Invalid meetup state!");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            mMeetup = Util.getMeetupFromIntent(data);
        }
    }

    private void gotoNextActivity() {
        Class nextActivity = getNextActivity();
        if (isClearingActivity(nextActivity)) {
            clearPreviousActivities();
        }
        gotoActivity(nextActivity, false);
    }

    protected void gotoActivity(Class activity, boolean forResult) {
        mMeetup.mState = nextState(this.getClass(), activity);
        Intent intent = new Intent(this, activity);
        intent = Util.addMeetupToIntent(intent, mMeetup);
        if (forResult) {
            startActivityForResult(intent, REQUEST_EDIT);
        } else {
            startActivity(intent);
        }
    }

    protected void endActivityWithResult() {
        Intent result = new Intent();
        result = Util.addMeetupToIntent(result, mMeetup);
        setResult(Activity.RESULT_OK, result);
        finish();
    }

    protected void endActivityWithoutResult() {
        finish();
    }

    protected void clearPreviousActivities() {
        Intent intent = new Intent(this, MapActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        endActivityWithoutResult();
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
        return ACTIVITIES; //TODO include check for number of mDogs in the current user's profile
    }

    private MeetupState nextState(Class currActivity, Class nextActivity) {
        MeetupState state = mMeetup.mState;
        if (nextActivity == CreateMeetupReviewActivity.class) {
            state = MeetupState.review;
        } else if (currActivity == CreateMeetupReviewActivity.class) {
            if (nextActivity == MeetupInProgressActivity.class) {
                state = MeetupState.inProgress;
            } else {
                state = MeetupState.edit;
            }
        }
        return state;
    }
}
