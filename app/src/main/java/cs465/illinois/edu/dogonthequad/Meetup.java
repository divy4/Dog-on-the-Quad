package cs465.illinois.edu.dogonthequad;


import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.PointF;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Meetup {

    public LatLng mLocation;

    public SocialLevel mSocialLevel;

    public Date mEndTime;

    public boolean inReview = false;

    public List<UUID> mDogs; //have a table of users and a table of mDogs, use ID to get dog
    public UUID mUser;
    
    public Bitmap mPhoto;
}

