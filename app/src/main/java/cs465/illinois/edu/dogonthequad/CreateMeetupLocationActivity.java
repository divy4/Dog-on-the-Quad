package cs465.illinois.edu.dogonthequad;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import cs465.illinois.edu.dogonthequad.DataModels.API;

import static cs465.illinois.edu.dogonthequad.MapActivity.MEETUP_KEY;

public class CreateMeetupLocationActivity extends CreateMeetupActivity implements OnMapReadyCallback, GoogleMap.OnMarkerDragListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize(R.layout.activity_create_meetup_location);

        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng quad = API.getCurrentLocation();
        googleMap.addMarker(new MarkerOptions().position(API.getCurrentLocation())
                .icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.current_location)));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(quad, (float) 18.0));

        Log.d("MEETUPLOCATION", "In on map ready, location=" + mMeetup.mLocation);
        if(mMeetup.mLocation == null) {
            mMeetup.mLocation = quad;
        }
        googleMap.addMarker(new MarkerOptions().position(mMeetup.mLocation)
                .title(getString(R.string.create_meetup_location_title)).draggable(true)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.dog_map_icon)));

        googleMap.setOnMarkerDragListener(this);
        googleMap.getUiSettings().setZoomGesturesEnabled(false);
        googleMap.getUiSettings().setScrollGesturesEnabled(false);

    }


    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        if(marker.getTitle().equals(getString(R.string.create_meetup_location_title))){
            Log.d("MEETUPLOCATION", "New location" + marker.getPosition());
            mMeetup.mLocation = marker.getPosition();
        }
    }
}

