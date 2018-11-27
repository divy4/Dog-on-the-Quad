package cs465.illinois.edu.dogonthequad;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.Vector;
import java.util.stream.Collectors;

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
        mMeetup.inReview = true;

        // set edit button listeners
        for (int i = 0; i < EDIT_BUTTON_IDS.size(); i++) {
            Button b = findViewById(EDIT_BUTTON_IDS.get(i));
            final Class activity = EDIT_BUTTON_ACTIVITIES.get(i);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gotoActivity(activity, true);
                }
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
                switch (mMeetup.mSocialLevel) {
                    case Low:
                        mSocialLevelText.setText(R.string.social_level_low);
                        break;
                    case Medium:
                        mSocialLevelText.setText(R.string.social_level_medium);
                        break;
                    case High:
                        mSocialLevelText.setText(R.string.social_level_high);
                        break;
                }
            }
            if (mMeetup.mDogs != null) {
                // TODO: get real names of dogs
                List<String> names = mMeetup.mDogs.stream().map(UUID::toString).collect(Collectors.toList());
                String text = "Dogs: " + TextUtils.join(", ", names);
                mDogsText.setText(text);
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.cancel_button) {
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
