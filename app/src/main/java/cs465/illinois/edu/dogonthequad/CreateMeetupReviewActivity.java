package cs465.illinois.edu.dogonthequad;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.Vector;
import java.util.stream.Collectors;

import cs465.illinois.edu.dogonthequad.DataModels.API;
import cs465.illinois.edu.dogonthequad.DataModels.Dog;
import cs465.illinois.edu.dogonthequad.DataModels.Meetup;
import cs465.illinois.edu.dogonthequad.DataModels.MeetupState;

public class CreateMeetupReviewActivity extends CreateMeetupActivity implements View.OnClickListener, OnMapReadyCallback {

    private static final Vector<Integer> EDIT_BUTTON_IDS = new Vector(Arrays.asList(
        R.id.edit_end_time_button,
        R.id.edit_social_level_button,
        R.id.edit_dogs_button
    ));

    private static final Vector<Class> EDIT_BUTTON_ACTIVITIES = new Vector(Arrays.asList(
        CreateMeetupEndTimeActivity.class,
        CreateMeetupSocialLevelActivity.class,
        CreateMeetupSelectDogsActivity.class
    ));

    TextView mEndTimeText;
    TextView mSocialLevelText;
    TextView mDogsText;
    CircularImageView mImageView;
    GoogleMap mMap;

    Marker mLocationMarker;
    Marker mMeetupMarker;

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

        mImageView = findViewById(R.id.meetup_photo);

        mImageView.setOnClickListener((v) -> {
            gotoActivity(CreateMeetupPhotoActivity.class, true);
        });

        mEndTimeText = findViewById(R.id.end_time_text);
        mSocialLevelText = findViewById(R.id.social_level_text);
        mDogsText = findViewById(R.id.dogs_text);

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mMeetup != null) {
            if (mMeetup.mEndTime != null) {
                String text = "End Time: " + Util.formatDateTime(mMeetup.mEndTime);
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
            if(mMeetup.mPhoto != null){
                Bitmap bmp = CreateMeetupPhotoActivity.getBitmapFromString(mMeetup.mPhoto);
                mImageView.setImageBitmap(bmp);
            }

            if(mMap != null && mMeetup.mLocation != null){
                SetupMap(mMap);
            }

        }
    }

    @Override
    public void onClick(View view) {
        //if (view.getId() == R.id.cancel_button && mMeetup.mState == MeetupState.setupReview) {
        //    confirmCancel();
        //} else {
            super.onClick(view);
        //}
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        SetupMap(googleMap);
        googleMap.getUiSettings().setZoomGesturesEnabled(false);
        googleMap.getUiSettings().setScrollGesturesEnabled(false);
        googleMap.setOnMapClickListener((pos) -> {
            gotoActivity(CreateMeetupLocationActivity.class, true);
        });


        mMap = googleMap;
    }

    protected void SetupMap(GoogleMap googleMap) {
        LatLng viewLocation = API.getCurrentLocation();

        Log.d("MEETUPLOCATION", "In review: " + mMeetup.mLocation);
        if(mMeetupMarker != null){
            mMeetupMarker.remove();
        }
        if(mLocationMarker != null){
            mLocationMarker.remove();
        }

        mMeetupMarker = googleMap.addMarker(new MarkerOptions().position(mMeetup.mLocation)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.dog_map_icon)));

        mLocationMarker = googleMap.addMarker(new MarkerOptions().position(API.getCurrentLocation())
                .icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.current_location)));

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(viewLocation, 17));
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
