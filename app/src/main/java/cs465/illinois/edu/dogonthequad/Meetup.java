package cs465.illinois.edu.dogonthequad;


import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class Meetup {

    public double mLocationLatitude = 0.0;
    public double mLocationLongitude = 0.0;

    public int mLengthMinutes = 0;
    public int mLengthHours = 0;


    public SocialLevel mSocialLevel = SocialLevel.Low;

    public Date mStartTime = new Date();

    public UUID[] mDogs = new UUID[0]; //have a table of users and a table of dogs, use ID to get dog
    public UUID mUser;


    public String mPhoto = ""; // TODO figure out how best to store the photo
}

