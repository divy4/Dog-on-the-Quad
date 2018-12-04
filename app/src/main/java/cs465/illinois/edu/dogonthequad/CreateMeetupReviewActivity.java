package cs465.illinois.edu.dogonthequad;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.Vector;
import java.util.stream.Collectors;

import cs465.illinois.edu.dogonthequad.DataModels.API;
import cs465.illinois.edu.dogonthequad.DataModels.Dog;
import cs465.illinois.edu.dogonthequad.DataModels.MeetupState;

public class CreateMeetupReviewActivity extends CreateMeetupActivity implements View.OnClickListener {

    private static final Vector<Integer> EDIT_BUTTON_IDS = new Vector(Arrays.asList(
        R.id.edit_location_button,
        R.id.edit_end_time_button,
        R.id.edit_social_level_button,
        R.id.edit_dogs_button
    ));

    private static final Vector<Class> EDIT_BUTTON_ACTIVITIES = new Vector(Arrays.asList(
        CreateMeetupLocationActivity.class,
        CreateMeetupEndTimeActivity.class,
        CreateMeetupSocialLevelActivity.class,
        CreateMeetupSelectDogsActivity.class
    ));

    TextView mEndTimeText;
    TextView mSocialLevelText;
    TextView mDogsText;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize(R.layout.activity_create_meetup_review);

        // set setupEdit button listeners
        for (int i = 0; i < EDIT_BUTTON_IDS.size(); i++) {
            Button b = findViewById(EDIT_BUTTON_IDS.get(i));
            final Class activity = EDIT_BUTTON_ACTIVITIES.get(i);
            b.setOnClickListener((v) -> {
                gotoActivity(activity, true);
            });
        }

        Bitmap bmp = CreateMeetupPhotoActivity.getBitmapFromString(mMeetup.mPhoto);
        ImageView imageView = findViewById(R.id.image_view);
        imageView.setImageBitmap(bmp);

        mEndTimeText = findViewById(R.id.end_time_text);
        mSocialLevelText = findViewById(R.id.social_level_text);
        mDogsText = findViewById(R.id.dogs_text);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mMeetup != null) {
            if (mMeetup.mEndTime != null) {
                String text = "End Time: " + mMeetup.mEndTime.toString();
                mEndTimeText.setText(text);
            }
            if (mMeetup.mSocialLevel != null) {
                String text = "Attendance: ";
                switch (mMeetup.mSocialLevel) {
                    case Low:
                        text += getResources().getString(R.string.social_level_low);
                        break;
                    case Medium:
                        text += getResources().getString(R.string.social_level_medium);
                        break;
                    case High:
                        text += getResources().getString(R.string.social_level_high);
                        break;
                }
                mSocialLevelText.setText(text);
            }
            if (mMeetup.mDogs != null) {
                List<String> names = new ArrayList<String>();
                List<Dog> dogs = API.getDogs();
                /* TODO: make this not gross, probably refactor into an API function */
                for (UUID doggoUUID : mMeetup.mDogs) {
                    Log.d("Review", doggoUUID.toString());
                    for (Dog doggo : dogs) {
                        Log.d("Review.", doggo.mId.toString());
                        if (doggo.mId.equals(doggoUUID)) {
                            names.add(doggo.mName);
                        }
                    }
                }
                String text = "Dogs: " + TextUtils.join(", ", names);
                mDogsText.setText(text);
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.cancel_button && mMeetup.mState == MeetupState.setupReview) {
            confirmCancel();
        } else {
            super.onClick(view);
        }
    }

    private void confirmCancel() {
        new AlertDialog.Builder(this)
            .setTitle("Cancel meetup?")
            .setMessage("Are you sure you want to cancel this meetup?")
            .setPositiveButton(R.string.cancel_meetup, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    clearPreviousActivities();
                    finish();
                }})
            .setNegativeButton(R.string.continue_review, null).show();
    }
}
