package cs465.illinois.edu.dogonthequad;


import android.graphics.Point;
import android.graphics.PointF;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class Meetup {

    public PointF mLocation;

    public SocialLevel mSocialLevel;

    public Date mEndTime;

    public UUID[] mDogs; //have a table of users and a table of dogs, use ID to get dog
    public UUID mUser;
    
    public String mPhoto; // TODO figure out how best to store the photo
}

