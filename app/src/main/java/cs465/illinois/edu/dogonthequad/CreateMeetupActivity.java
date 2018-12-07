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

        try {
            mNextButton = findViewById(R.id.meetup_next_button);
            if (mMeetup.mState == MeetupState.setupEdit || mMeetup.mState == MeetupState.setupEdit) {
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
                case setupEdit:
                case inProgressEdit:
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

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.meetup_next_button || view.getId() == R.id.confirm_button) {
            executeNext();
        }
    }

    @Override
    public void onBackPressed() {
        executeBack();
    }

    protected void executeNext() {
        switch (mMeetup.mState) {
            case setupFirstPass:
            case setupReview:
                gotoNextActivity();
                break;
            case setupEdit:
            case inProgressReview:
            case inProgressEdit:
                endActivityWithResult();
                break;
            default:
                throw new RuntimeException("Invalid meetup state!");
        }
    }

    protected void executeBack() {
        switch (mMeetup.mState) {
            case setupEdit:
            case setupReview:
            case inProgressReview:
            case inProgressEdit:
                endActivityWithoutResult();
                break;
            case setupFirstPass:
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
        mMeetup.mState = getPrevState(mMeetup.mState);
    }

    private void gotoNextActivity() {
        Class nextActivity = getNextActivity();
        if (isClearingActivity(nextActivity)) {
            clearPreviousActivities();
        }
        gotoActivity(nextActivity, false);
    }

    protected void gotoActivity(Class activity, boolean forResult) {
        mMeetup.mState = getNextState(this.getClass(), activity);
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

    private MeetupState getNextState(Class currActivity, Class nextActivity) {
        MeetupState state = mMeetup.mState;
        if (currActivity == CreateMeetupPhotoActivity.class && nextActivity == CreateMeetupReviewActivity.class) {
            state = MeetupState.setupReview;
        } else if (currActivity == CreateMeetupReviewActivity.class && nextActivity == MeetupInProgressActivity.class) {
            state = MeetupState.inProgress;
        } else if (currActivity == CreateMeetupReviewActivity.class && nextActivity != MeetupInProgressActivity.class) {
            if (mMeetup.mState == MeetupState.setupReview) {
                state = MeetupState.setupEdit;
            } else {
                state = MeetupState.inProgressEdit;
            }
        }
        return state;
    }

    private MeetupState getPrevState(MeetupState lastState) {
        MeetupState state;
        switch (lastState) {
            case setupEdit:
                state = MeetupState.setupReview;
                break;
            case inProgressEdit:
                state = MeetupState.inProgressReview;
                break;
            default:
                state = mMeetup.mState;
        }
        return state;
    }
}








