package cs465.illinois.edu.dogonthequad;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cs465.illinois.edu.dogonthequad.DataModels.API;

public class MapActivity extends Activity implements View.OnClickListener, OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    public static final String MEETUP_KEY = "meetup";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        Button createMeetupButton = (Button) findViewById(R.id.create_meetup_button);
        createMeetupButton.setOnClickListener(this);
        CircularImageView profileButton = (CircularImageView) findViewById(R.id.profile_picture);
        profileButton.setOnClickListener(this);

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Button debugButton = findViewById(R.id.debug_button);

        debugButton.setOnLongClickListener(new View.OnLongClickListener() {
            int checkedItem = 0;

            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MapActivity.this);
                builder.setTitle("Sneaky Debug Menu");
                List<String> names = new ArrayList<>();
                try{
                    names = API.getPresetFileNames(MapActivity.this);
                } catch (IOException e){
                    //do nothing
                }
                builder.setSingleChoiceItems(names.toArray(new String[0]), checkedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // user checked an item
                        checkedItem = which;
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // user clicked OK
                        API.setCurrentPreset(checkedItem);
                        Intent i = getBaseContext().getPackageManager()
                                .getLaunchIntentForPackage( getBaseContext().getPackageName() );
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    }
                });
                builder.setNegativeButton("Cancel", null);

                AlertDialog dialog = builder.create();
                dialog.show();
                return true;

            }

        });

        // add OK and Cancel buttons

    }


    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.create_meetup_button) {
            // Launch the create meetup location activity
            Intent intent = new Intent(this, CreateMeetupLocationActivity.class);
            Meetup meetup = new Meetup();
            Util.addMeetupToIntent(intent, meetup);
            startActivity(intent);
        } else if (view.getId() == R.id.profile_picture) {
            /* Launch dog profile activity */
            Intent intent = new Intent(this, DogOwnerProfileActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        List<Meetup> meetups = API.getMeetups();
        LatLng viewLocation = meetups.get(0).mLocation;

        Log.d("MapActivity", viewLocation.toString());

        for (Meetup meetup : meetups) {
            googleMap.addMarker(new MarkerOptions().position(meetup.mLocation)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.dog_map_icon)))
                    .setTag(meetup);
        }

        googleMap.setOnMarkerClickListener(this);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(viewLocation, 18));
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        /* TODO: Change this to meetup screen when created */
        Meetup meetup = (Meetup) marker.getTag();
        Intent intent = new Intent(this, DogOwnerProfileActivity.class);
        intent.putExtra(MEETUP_KEY, new Gson().toJson(meetup));
        startActivity(intent);

        return true;
    }
}
