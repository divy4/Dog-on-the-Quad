package cs465.illinois.edu.dogonthequad;


import android.graphics.Point;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class Meetup {

    public Point mLocation = new Point();

    public int mLengthMinutes = 0;
    public int mLengthHours = 0;


    public SocialLevel mSocialLevel = SocialLevel.Low;

    public Date mEndTime = new Date();

    public UUID[] mDogs = new UUID[0]; //have a table of users and a table of dogs, use ID to get dog
    public UUID mUser;


    public String mPhoto = ""; // TODO figure out how best to store the photo
}

