package cs465.illinois.edu.dogonthequad;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

import cs465.illinois.edu.dogonthequad.DataModels.API;
import cs465.illinois.edu.dogonthequad.DataModels.Meetup;
import cs465.illinois.edu.dogonthequad.DataModels.MeetupState;


public class MeetupInProgressActivity extends Activity implements OnMapReadyCallback {

    Meetup mMeetup;
    TextView mMinutesLeft;
    TextView mUsersVisiting;
    Handler mUpdater;
    AtomicBoolean mUpdaterIsEnabled;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meetup_in_progress);

        mMinutesLeft = findViewById(R.id.minutes_left_num);
        mUsersVisiting = findViewById(R.id.visitors_num);
        readMeetupFromLastActivity();

        Button editSettings = findViewById(R.id.edit_settings);
        editSettings.setOnClickListener((v) -> editSettings());

        Button add10Min = findViewById(R.id.add_time);
        add10Min.setOnClickListener((v) -> addTime());

        Button endMeetup = findViewById(R.id.end_meetup);
        endMeetup.setOnClickListener((v) -> endMeetup());

        mUpdater = new Handler();
        mUpdaterIsEnabled = new AtomicBoolean(false);

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mUpdaterIsEnabled.set(true);
        mUpdater.postDelayed(this::updateLoop, 60000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mUpdaterIsEnabled.set(false);
    }

    private void addTime() {
        Calendar endTime = Calendar.getInstance();
        endTime.setTime(mMeetup.mEndTime);
        endTime.add(Calendar.MINUTE, 10);
        mMeetup.mEndTime = endTime.getTime();
        updateDisplay();
    }

    private void editSettings() {
        mMeetup.mState = MeetupState.inProgressReview;
        Intent intent = new Intent(this, CreateMeetupReviewActivity.class);
        intent = Util.addMeetupToIntent(intent, mMeetup);
        startActivityForResult(intent, CreateMeetupActivity.REQUEST_EDIT);
    }

    private void endMeetup() {
        confirmCancel();
    }

    private void confirmCancel() {
        new AlertDialog.Builder(this)
            .setTitle("End meetup?")
            .setMessage("Are you sure you want to end this meetup?")
            .setPositiveButton(R.string.end_meetup, (a, b) -> finish())
            .setNegativeButton(R.string.continue_meetup, null).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            readMeetupFromNextActivity(data);
        }
    }

    @Override
    public void onBackPressed() {
        confirmCancel();
    }

    private void readMeetupFromLastActivity() {
        mMeetup = Util.getMeetupFromIntent(getIntent());
        updateDisplay();
    }

    private void readMeetupFromNextActivity(Intent intent) {
        mMeetup = Util.getMeetupFromIntent(intent);
        updateDisplay();
    }

    private void updateLoop() {
        updateDisplay();
        if (mUpdaterIsEnabled.get()) {
            mUpdater.postDelayed(this::updateLoop, 60000);
        }
    }

    private void updateDisplay() {
        Calendar currTime = Calendar.getInstance();
        currTime.setTime(new Date());
        Calendar endTime = Calendar.getInstance();
        endTime.setTime(mMeetup.mEndTime);
        Long minutes = (endTime.getTimeInMillis() - currTime.getTimeInMillis()) / 60000;
        mMinutesLeft.setText(minutes.toString());

        mUsersVisiting.setText("1234");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(API.getCurrentLocation(), 17));
        googleMap.addMarker(new MarkerOptions()
                .position(mMeetup.mLocation)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.dog_map_icon)));
        googleMap.addMarker(new MarkerOptions()
                .position(API.getCurrentLocation())
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.current_location)));
        googleMap.getUiSettings().setZoomGesturesEnabled(false);
        googleMap.getUiSettings().setScrollGesturesEnabled(false);
    }
}
