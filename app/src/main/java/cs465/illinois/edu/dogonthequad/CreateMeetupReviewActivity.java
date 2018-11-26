package cs465.illinois.edu.dogonthequad;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Arrays;
import java.util.Vector;

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
                    gotoActivity(activity);
                }
            });
        }

        Bitmap bmp = CreateMeetupPhotoActivity.getBitmapFromString(mMeetup.mPhoto);
        ImageView imageView = findViewById(R.id.image_view);
        imageView.setImageBitmap(bmp);

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
